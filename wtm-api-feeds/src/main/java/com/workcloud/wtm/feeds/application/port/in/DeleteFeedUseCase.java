package com.workcloud.wtm.feeds.application.port.in;

/**
 * Input Port for deleting a feed.
 */
public interface DeleteFeedUseCase {
    void deleteFeed(String feedId);
}
