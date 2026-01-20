package com.workcloud.wtm.feeds.application.port.in;

/**
 * Input Port for performing feed operations (claim, acknowledge, complete, etc.).
 */
public interface PerformFeedOperationUseCase {
    void performOperation(String feedId, FeedOperation operation);

    enum FeedOperation {
        CLAIM,
        REASSIGN,
        ACKNOWLEDGE,
        COMPLETE,
        ESCALATE
    }
}
