package com.workcloud.wtm.feeds.application.port.in;

import com.workcloud.wtm.feeds.application.dto.FeedDto;

import java.util.List;

/**
 * Input Port for listing feeds with pagination.
 */
public interface ListFeedsUseCase {
    List<FeedDto> listFeeds(int pageSize, String pageToken);
}
