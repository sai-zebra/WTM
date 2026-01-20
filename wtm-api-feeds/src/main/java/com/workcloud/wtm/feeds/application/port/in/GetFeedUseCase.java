package com.workcloud.wtm.feeds.application.port.in;

import com.workcloud.wtm.feeds.application.dto.FeedDto;

import java.util.Optional;

/**
 * Input Port for getting a feed by ID.
 */
public interface GetFeedUseCase {
    Optional<FeedDto> getFeed(String feedId);
}
