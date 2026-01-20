package com.workcloud.wtm.feeds.application.service;

import com.workcloud.wtm.feeds.application.dto.FeedDto;
import com.workcloud.wtm.feeds.application.mapper.FeedMapper;
import com.workcloud.wtm.feeds.application.port.in.*;
import com.workcloud.wtm.feeds.application.port.out.FeedRepositoryPort;
import com.workcloud.wtm.feeds.domain.model.Feed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Application Service implementing use cases.
 * This is the orchestrator that coordinates between domain and infrastructure.
 */
@Service
@Transactional
public class FeedService implements
        CreateFeedUseCase,
        GetFeedUseCase,
        ListFeedsUseCase,
        UpdateFeedUseCase,
        DeleteFeedUseCase,
        PerformFeedOperationUseCase {

    private final FeedRepositoryPort feedRepository;
    private final FeedMapper feedMapper;

    public FeedService(FeedRepositoryPort feedRepository, FeedMapper feedMapper) {
        this.feedRepository = feedRepository;
        this.feedMapper = feedMapper;
    }

    @Override
    public FeedDto createFeed(CreateFeedCommand command) {
        Feed feed = Feed.create(command.getTitle(), command.getMessage());
        feed.setId(UUID.randomUUID().toString());
        Feed savedFeed = feedRepository.save(feed);
        return feedMapper.toDto(savedFeed);
    }

    @Override
    public Optional<FeedDto> getFeed(String feedId) {
        return feedRepository.findById(feedId)
                .map(feedMapper::toDto);
    }

    @Override
    public List<FeedDto> listFeeds(int pageSize, String pageToken) {
        List<Feed> feeds = feedRepository.findAll(pageSize, pageToken);
        return feeds.stream()
                .map(feedMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateFeed(String feedId, UpdateFeedCommand command) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found: " + feedId));
        feed.update(command.getTitle(), command.getMessage());
        feedRepository.save(feed);
    }

    @Override
    public void deleteFeed(String feedId) {
        if (!feedRepository.existsById(feedId)) {
            throw new IllegalArgumentException("Feed not found: " + feedId);
        }
        feedRepository.deleteById(feedId);
    }

    @Override
    public void performOperation(String feedId, FeedOperation operation) {
        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new IllegalArgumentException("Feed not found: " + feedId));

        switch (operation) {
            case CLAIM:
                feed.claim();
                break;
            case REASSIGN:
                feed.reassign();
                break;
            case ACKNOWLEDGE:
                feed.acknowledge();
                break;
            case COMPLETE:
                feed.complete();
                break;
            case ESCALATE:
                feed.escalate();
                break;
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }

        feedRepository.save(feed);
    }
}
