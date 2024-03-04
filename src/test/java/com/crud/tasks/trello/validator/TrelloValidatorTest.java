package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloValidatorTest {
    @InjectMocks
    private TrelloValidator trelloValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void validateTrelloBoards_RemovesTestNamedBoards() {
        // Arrange
        List<TrelloBoard> boards = List.of(
                new TrelloBoard("1", "test", null),
                new TrelloBoard("2", "Kodilla board", null)
        );
        // Act
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(boards);
        //Assert
        assertEquals(1,filteredBoards.size());
        assertEquals("Kodilla board", filteredBoards.get(0).getName());
    }
    @Test
    void validateTrelloBoards_KeepsNonTestNamedBoards() {
        // Given
        List<TrelloBoard> boards = List.of(new TrelloBoard("1", "Kodilla board", null));

        // When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(boards);

        // Then
        assertEquals(1, filteredBoards.size());
        assertEquals("Kodilla board", filteredBoards.get(0).getName());
    }
    @Test
    void validateTrelloCard_ReturnsEmptyListWhenAllBoardsAreTestNamed() {
        // Given
        List<TrelloBoard> boards = List.of(new TrelloBoard("1", "test", null));

        // When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(boards);

        // Then
        assertTrue(filteredBoards.isEmpty());
    }
    @Test
    void validateTrelloBoards_HandlesEmptyBoardList() {
        // Given
        List<TrelloBoard> boards = List.of();

        // When
        List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(boards);

        // Then
        assertTrue(filteredBoards.isEmpty());
    }

}