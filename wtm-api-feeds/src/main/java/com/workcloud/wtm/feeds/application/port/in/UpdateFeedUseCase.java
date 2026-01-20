package com.workcloud.wtm.feeds.application.port.in;

/**
 * Input Port for updating a feed.
 */
public interface UpdateFeedUseCase {
    void updateFeed(String feedId, UpdateFeedCommand command);

    class UpdateFeedCommand {
        private final String title;
        private final String message;

        public UpdateFeedCommand(String title, String message) {
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
