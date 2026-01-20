package com.workcloud.wtm.feeds.application.service;

import com.workcloud.wtm.feeds.application.dto.FeedDto;
import com.workcloud.wtm.feeds.application.mapper.FeedMapper;
import com.workcloud.wtm.feeds.application.port.in.PerformFeedOperationUseCase;
import com.workcloud.wtm.feeds.application.port.out.FeedRepositoryPort;
import com.workcloud.wtm.feeds.domain.model.Feed;
import com.workcloud.wtm.feeds.domain.model.FeedStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedServiceTest {

    @Mock
    private FeedRepositoryPort feedRepository;

    @Mock
    private FeedMapper feedMapper;

    @InjectMocks
    private FeedService feedService;

    private Feed testFeed;
    private FeedDto testFeedDto;

    @BeforeEach
    void setUp() {
        String feedId = UUID.randomUUID().toString();
        testFeed = new Feed(feedId, "Test Title", "Test Message", FeedStatus.PENDING, OffsetDateTime.now());
        testFeedDto = new FeedDto(feedId, "Test Title", "Test Message", "PENDING", OffsetDateTime.now());
    }

    @Test
    void createFeed_ShouldReturnFeedDto() {
        // Given
        CreateFeedUseCase.CreateFeedCommand command = new CreateFeedUseCase.CreateFeedCommand("New Title", "New Message");
        Feed newFeed = Feed.create("New Title", "New Message");
        newFeed.setId(UUID.randomUUID().toString());
        FeedDto expectedDto = new FeedDto(newFeed.getId(), "New Title", "New Message", "PENDING", newFeed.getCreatedAt());

        when(feedRepository.save(any(Feed.class))).thenReturn(newFeed);
        when(feedMapper.toDto(newFeed)).thenReturn(expectedDto);

        // When
        FeedDto result = feedService.createFeed(command);

        // Then
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        assertEquals("New Message", result.getMessage());
        verify(feedRepository).save(any(Feed.class));
        verify(feedMapper).toDto(newFeed);
    }

    @Test
    void getFeed_WhenFeedExists_ShouldReturnFeedDto() {
        // Given
        when(feedRepository.findById(testFeed.getId())).thenReturn(Optional.of(testFeed));
        when(feedMapper.toDto(testFeed)).thenReturn(testFeedDto);

        // When
        Optional<FeedDto> result = feedService.getFeed(testFeed.getId());

        // Then
        assertTrue(result.isPresent());
        assertEquals(testFeedDto.getId(), result.get().getId());
        verify(feedRepository).findById(testFeed.getId());
    }

    @Test
    void getFeed_WhenFeedNotExists_ShouldReturnEmpty() {
        // Given
        when(feedRepository.findById("non-existent")).thenReturn(Optional.empty());

        // When
        Optional<FeedDto> result = feedService.getFeed("non-existent");

        // Then
        assertFalse(result.isPresent());
        verify(feedRepository).findById("non-existent");
        verify(feedMapper, never()).toDto(any());
    }

    @Test
    void updateFeed_WhenFeedExists_ShouldUpdateFeed() {
        // Given
        UpdateFeedUseCase.UpdateFeedCommand command = new UpdateFeedUseCase.UpdateFeedCommand("Updated Title", "Updated Message");
        when(feedRepository.findById(testFeed.getId())).thenReturn(Optional.of(testFeed));
        when(feedRepository.save(any(Feed.class))).thenReturn(testFeed);

        // When
        feedService.updateFeed(testFeed.getId(), command);

        // Then
        verify(feedRepository).findById(testFeed.getId());
        verify(feedRepository).save(testFeed);
    }

    @Test
    void updateFeed_WhenFeedNotExists_ShouldThrowException() {
        // Given
        UpdateFeedUseCase.UpdateFeedCommand command = new UpdateFeedUseCase.UpdateFeedCommand("Title", "Message");
        when(feedRepository.findById("non-existent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> feedService.updateFeed("non-existent", command));
        verify(feedRepository, never()).save(any());
    }

    @Test
    void deleteFeed_WhenFeedExists_ShouldDeleteFeed() {
        // Given
        when(feedRepository.existsById(testFeed.getId())).thenReturn(true);

        // When
        feedService.deleteFeed(testFeed.getId());

        // Then
        verify(feedRepository).existsById(testFeed.getId());
        verify(feedRepository).deleteById(testFeed.getId());
    }

    @Test
    void deleteFeed_WhenFeedNotExists_ShouldThrowException() {
        // Given
        when(feedRepository.existsById("non-existent")).thenReturn(false);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> feedService.deleteFeed("non-existent"));
        verify(feedRepository, never()).deleteById(any());
    }

    @Test
    void performOperation_Claim_ShouldUpdateStatus() {
        // Given
        when(feedRepository.findById(testFeed.getId())).thenReturn(Optional.of(testFeed));
        when(feedRepository.save(any(Feed.class))).thenReturn(testFeed);

        // When
        feedService.performOperation(testFeed.getId(), PerformFeedOperationUseCase.FeedOperation.CLAIM);

        // Then
        verify(feedRepository).findById(testFeed.getId());
        verify(feedRepository).save(testFeed);
        assertEquals(FeedStatus.CLAIMED, testFeed.getStatus());
    }

    @Test
    void performOperation_Complete_ShouldUpdateStatus() {
        // Given
        testFeed.claim(); // First claim it
        when(feedRepository.findById(testFeed.getId())).thenReturn(Optional.of(testFeed));
        when(feedRepository.save(any(Feed.class))).thenReturn(testFeed);

        // When
        feedService.performOperation(testFeed.getId(), PerformFeedOperationUseCase.FeedOperation.COMPLETE);

        // Then
        verify(feedRepository).save(testFeed);
        assertEquals(FeedStatus.COMPLETED, testFeed.getStatus());
    }
}
