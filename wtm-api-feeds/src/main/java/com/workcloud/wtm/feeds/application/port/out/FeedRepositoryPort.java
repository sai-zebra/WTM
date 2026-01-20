package com.workcloud.wtm.feeds.application.port.out;

import com.workcloud.wtm.feeds.domain.model.Feed;

import java.util.List;
import java.util.Optional;

/**
 * Output Port (Repository Port) in Hexagonal Architecture.
 * This interface defines what the application needs from the outside world.
 * Implementations (adapters) will be in the infrastructure layer.
 */
public interface FeedRepositoryPort {
    Feed save(Feed feed);
    Optional<Feed> findById(String id);
    List<Feed> findAll(int pageSize, String pageToken);
    void deleteById(String id);
    boolean existsById(String id);
}
