package com.workcloud.wtm.feeds.infrastructure.adapter.persistence;

import com.workcloud.wtm.feeds.application.port.out.FeedRepositoryPort;
import com.workcloud.wtm.feeds.domain.model.Feed;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Infrastructure Adapter - In-Memory Repository Implementation.
 * This implements the FeedRepositoryPort (output port) from the application layer.
 * In a real system, this would be replaced with a JPA or database adapter.
 */
@Repository
public class InMemoryFeedRepository implements FeedRepositoryPort {
    private final Map<String, Feed> store = new ConcurrentHashMap<>();

    @Override
    public Feed save(Feed feed) {
        if (feed.getId() == null) {
            feed.setId(UUID.randomUUID().toString());
        }
        store.put(feed.getId(), feed);
        return feed;
    }

    @Override
    public Optional<Feed> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Feed> findAll(int pageSize, String pageToken) {
        List<Feed> allFeeds = new ArrayList<>(store.values());
        allFeeds.sort(Comparator.comparing(Feed::getCreatedAt).reversed());

        // Simple pagination - in production, use proper cursor-based pagination
        int startIndex = pageToken != null ? Integer.parseInt(pageToken) : 0;
        int endIndex = Math.min(startIndex + pageSize, allFeeds.size());

        if (startIndex >= allFeeds.size()) {
            return Collections.emptyList();
        }

        return allFeeds.subList(startIndex, endIndex);
    }

    @Override
    public void deleteById(String id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return store.containsKey(id);
    }
}
