package com.workcloud.wtm.feeds.application.mapper;

import com.workcloud.wtm.feeds.application.dto.FeedDto;
import com.workcloud.wtm.feeds.domain.model.Feed;
import org.springframework.stereotype.Component;

/**
 * Mapper between domain entities and DTOs.
 */
@Component
public class FeedMapper {
    public FeedDto toDto(Feed feed) {
        return new FeedDto(
                feed.getId(),
                feed.getTitle(),
                feed.getMessage(),
                feed.getStatus().name(),
                feed.getCreatedAt()
        );
    }
}
