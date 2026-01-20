package com.workcloud.wtm.feeds.adapter.in.web;

import com.workcloud.wtm.feeds.application.dto.FeedDto;
import com.workcloud.wtm.feeds.application.port.in.*;
import org.openapitools.api.FeedsApi;
import org.openapitools.model.Feed;
import org.openapitools.model.FeedCreate;
import org.openapitools.model.FeedList;
import org.openapitools.model.FeedOperationRequest;
import org.openapitools.model.FeedUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Adapter (Input Adapter) in Hexagonal Architecture.
 * This adapter translates HTTP requests into use case calls.
 * It implements the OpenAPI-generated interface.
 */
@RestController
public class FeedController implements FeedsApi {

    private final CreateFeedUseCase createFeedUseCase;
    private final GetFeedUseCase getFeedUseCase;
    private final ListFeedsUseCase listFeedsUseCase;
    private final UpdateFeedUseCase updateFeedUseCase;
    private final DeleteFeedUseCase deleteFeedUseCase;
    private final PerformFeedOperationUseCase performFeedOperationUseCase;

    public FeedController(
            CreateFeedUseCase createFeedUseCase,
            GetFeedUseCase getFeedUseCase,
            ListFeedsUseCase listFeedsUseCase,
            UpdateFeedUseCase updateFeedUseCase,
            DeleteFeedUseCase deleteFeedUseCase,
            PerformFeedOperationUseCase performFeedOperationUseCase) {
        this.createFeedUseCase = createFeedUseCase;
        this.getFeedUseCase = getFeedUseCase;
        this.listFeedsUseCase = listFeedsUseCase;
        this.updateFeedUseCase = updateFeedUseCase;
        this.deleteFeedUseCase = deleteFeedUseCase;
        this.performFeedOperationUseCase = performFeedOperationUseCase;
    }

    @Override
    public ResponseEntity<Feed> createFeed(FeedCreate feedCreate) {
        CreateFeedUseCase.CreateFeedCommand command = new CreateFeedUseCase.CreateFeedCommand(
                feedCreate.getTitle(),
                feedCreate.getMessage()
        );
        FeedDto feedDto = createFeedUseCase.createFeed(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(toApiModel(feedDto));
    }

    @Override
    public ResponseEntity<Feed> getFeed(String feedId) {
        return getFeedUseCase.getFeed(feedId)
                .map(dto -> ResponseEntity.ok(toApiModel(dto)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<FeedList> listFeeds(Integer pageSize, String pageToken) {
        int size = pageSize != null ? pageSize : 10;
        List<FeedDto> feeds = listFeedsUseCase.listFeeds(size, pageToken);
        FeedList feedList = new FeedList();
        feedList.setItems(feeds.stream()
                .map(this::toApiModel)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(feedList);
    }

    @Override
    public ResponseEntity<Void> updateFeed(String feedId, FeedUpdate feedUpdate) {
        UpdateFeedUseCase.UpdateFeedCommand command = new UpdateFeedUseCase.UpdateFeedCommand(
                feedUpdate.getTitle(),
                feedUpdate.getMessage()
        );
        updateFeedUseCase.updateFeed(feedId, command);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteFeed(String feedId) {
        deleteFeedUseCase.deleteFeed(feedId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> performFeedOperation(String feedId, FeedOperationRequest feedOperationRequest) {
        PerformFeedOperationUseCase.FeedOperation operation = PerformFeedOperationUseCase.FeedOperation.valueOf(
                feedOperationRequest.getOperation().name()
        );
        performFeedOperationUseCase.performOperation(feedId, operation);
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<Void> createFeedNote(String feedId, org.openapitools.model.FeedNoteCreate feedNoteCreate) {
        // This will be handled by the feednotes module
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<org.openapitools.model.FeedNoteList> listFeedNotes(String feedId) {
        // This will be handled by the feednotes module
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Void> updateFeedNote(String feedId, String noteId, org.openapitools.model.FeedNoteUpdate feedNoteUpdate) {
        // This will be handled by the feednotes module
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<Void> deleteFeedNote(String feedId, String noteId) {
        // This will be handled by the feednotes module
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    private Feed toApiModel(FeedDto dto) {
        Feed feed = new Feed();
        feed.setId(dto.getId());
        feed.setTitle(dto.getTitle());
        feed.setMessage(dto.getMessage());
        feed.setStatus(dto.getStatus());
        feed.setCreatedAt(dto.getCreatedAt());
        return feed;
    }
}
