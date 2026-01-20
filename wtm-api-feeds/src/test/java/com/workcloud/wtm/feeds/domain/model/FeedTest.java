package com.workcloud.wtm.feeds.domain.model;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FeedTest {

    @Test
    void create_ShouldCreateFeedWithPendingStatus() {
        // When
        Feed feed = Feed.create("Test Title", "Test Message");

        // Then
        assertNull(feed.getId());
        assertEquals("Test Title", feed.getTitle());
        assertEquals("Test Message", feed.getMessage());
        assertEquals(FeedStatus.PENDING, feed.getStatus());
        assertNotNull(feed.getCreatedAt());
    }

    @Test
    void claim_WhenPending_ShouldChangeToClaimed() {
        // Given
        Feed feed = Feed.create("Title", "Message");

        // When
        feed.claim();

        // Then
        assertEquals(FeedStatus.CLAIMED, feed.getStatus());
    }

    @Test
    void claim_WhenNotPending_ShouldThrowException() {
        // Given
        Feed feed = Feed.create("Title", "Message");
        feed.claim(); // Already claimed

        // When & Then
        assertThrows(IllegalStateException.class, feed::claim);
    }

    @Test
    void acknowledge_WhenClaimed_ShouldChangeToAcknowledged() {
        // Given
        Feed feed = Feed.create("Title", "Message");
        feed.claim();

        // When
        feed.acknowledge();

        // Then
        assertEquals(FeedStatus.ACKNOWLEDGED, feed.getStatus());
    }

    @Test
    void acknowledge_WhenNotClaimed_ShouldThrowException() {
        // Given
        Feed feed = Feed.create("Title", "Message");

        // When & Then
        assertThrows(IllegalStateException.class, feed::acknowledge);
    }

    @Test
    void complete_ShouldChangeToCompleted() {
        // Given
        Feed feed = Feed.create("Title", "Message");
        feed.claim();

        // When
        feed.complete();

        // Then
        assertEquals(FeedStatus.COMPLETED, feed.getStatus());
    }

    @Test
    void complete_WhenAlreadyCompleted_ShouldThrowException() {
        // Given
        Feed feed = Feed.create("Title", "Message");
        feed.claim();
        feed.complete();

        // When & Then
        assertThrows(IllegalStateException.class, feed::complete);
    }

    @Test
    void escalate_ShouldChangeToEscalated() {
        // Given
        Feed feed = Feed.create("Title", "Message");

        // When
        feed.escalate();

        // Then
        assertEquals(FeedStatus.ESCALATED, feed.getStatus());
    }

    @Test
    void reassign_ShouldChangeToPending() {
        // Given
        Feed feed = Feed.create("Title", "Message");
        feed.claim();

        // When
        feed.reassign();

        // Then
        assertEquals(FeedStatus.PENDING, feed.getStatus());
    }

    @Test
    void reassign_WhenCompleted_ShouldThrowException() {
        // Given
        Feed feed = Feed.create("Title", "Message");
        feed.claim();
        feed.complete();

        // When & Then
        assertThrows(IllegalStateException.class, feed::reassign);
    }

    @Test
    void update_ShouldUpdateTitleAndMessage() {
        // Given
        Feed feed = Feed.create("Old Title", "Old Message");

        // When
        feed.update("New Title", "New Message");

        // Then
        assertEquals("New Title", feed.getTitle());
        assertEquals("New Message", feed.getMessage());
    }

    @Test
    void update_WithNullValues_ShouldNotUpdate() {
        // Given
        Feed feed = Feed.create("Title", "Message");

        // When
        feed.update(null, null);

        // Then
        assertEquals("Title", feed.getTitle());
        assertEquals("Message", feed.getMessage());
    }
}
