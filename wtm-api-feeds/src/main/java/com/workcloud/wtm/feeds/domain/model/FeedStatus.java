package com.workcloud.wtm.feeds.domain.model;

/**
 * Value object representing Feed status.
 * Following Domain-Driven Design, this is an immutable value object.
 */
public enum FeedStatus {
    PENDING,
    CLAIMED,
    ACKNOWLEDGED,
    COMPLETED,
    ESCALATED
}
