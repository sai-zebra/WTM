package com.workcloud.wtm.feeds.application.port.in;

import com.workcloud.wtm.feeds.application.dto.FeedDto;

/**
 * Input Port (Use Case) in Hexagonal Architecture.
 * This defines the application's use cases - what the application can do.
 */
public interface CreateFeedUseCase {
    FeedDto createFeed(CreateFeedCommand command);

    class CreateFeedCommand {
        private final String title;
        private final String message;

        public CreateFeedCommand(String title, String message) {
            this.title = title;
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public String getMessage() {
            return message;
        }
    }
}
