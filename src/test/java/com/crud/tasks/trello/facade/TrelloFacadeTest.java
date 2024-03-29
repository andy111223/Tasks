package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTest {
    @InjectMocks
    private TrelloFacade trelloFacade;

    @Mock
    private TrelloService trelloService;

    @Mock
    private TrelloValidator trelloValidator;

    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());
        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(0);
    }
    @Test
    void shouldFetchTrelloBoards() {
        // Given
        List<TrelloListDto> trelloLists =
                List.of(new TrelloListDto("1", "test_list", false));

        List<TrelloBoardDto> trelloBoards =
                List.of(new TrelloBoardDto("1", "test", trelloLists));

        List<TrelloList> mappedTrelloLists =
                List.of(new TrelloList("1", "test_list", false));

        List<TrelloBoard> mappedTrelloBoards =
                List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertThat(trelloBoardDtos).isNotNull();
        assertThat(trelloBoardDtos.size()).isEqualTo(1);

        trelloBoardDtos.forEach(trelloBoardDto -> {

            assertThat(trelloBoardDto.getId()).isEqualTo("1");
            assertThat(trelloBoardDto.getName()).isEqualTo("test");

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertThat(trelloListDto.getId()).isEqualTo("1");
                assertThat(trelloListDto.getName()).isEqualTo("test_list");
                assertThat(trelloListDto.isClosed()).isFalse();
            });
        });
    }
    @Test
    void createCard_CallsMapperAndServiceWithCorrectData() {
        // Arrange
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test name","Test desc","Test pos","Test listId");
        TrelloCard trelloCard = new TrelloCard("Test name","Test desc","Test pos","Test listId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("Test id", "Test name", "http://test.com");

        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard))).thenReturn(createdTrelloCardDto);

        // Act
        CreatedTrelloCardDto result = trelloFacade.createCard(trelloCardDto);

        // Assert
        assertThat(result).isNotNull();
        assertEquals("Test id", result.getId());
        assertEquals("Test name",result.getName());
        assertEquals("http://test.com", result.getShortUrl());
        verify(trelloMapper).mapToCard(trelloCardDto);
        verify(trelloValidator).validateCard(trelloCard);
        verify(trelloService).createTrelloCard(trelloMapper.mapToCardDto(trelloCard));
    }
    @Test
    void createCard_ValidatesCardBeforeCreation() {
        // Arrange
        TrelloCardDto cardDto = mock(TrelloCardDto.class);
        TrelloCard mappedCard = mock(TrelloCard.class);
        when(trelloMapper.mapToCard(cardDto)).thenReturn(mappedCard);

        // Act
        trelloFacade.createCard(cardDto);

        // Assert
        verify(trelloValidator).validateCard(mappedCard);
    }

    @Test
    void createCard_ReturnsNewCardOnSuccess() {
        // Arrange
        TrelloCardDto cardDto = new TrelloCardDto("Test name","Test desc","Test pos","Test listId");
        TrelloCard mappedCard = new TrelloCard("Test name","Test desc","Test pos","Test listId");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("1", "Test Card", "http://test.com");

        when(trelloMapper.mapToCard(cardDto)).thenReturn(mappedCard);
        when(trelloMapper.mapToCardDto(mappedCard)).thenReturn(cardDto);
        when(trelloService.createTrelloCard(cardDto)).thenReturn(createdCardDto);

        // Act
        CreatedTrelloCardDto result = trelloFacade.createCard(cardDto);

        // Assert
        assertEquals(createdCardDto, result);
    }

    @Test
    void createCard_ThrowsExceptionOnCardValidationFailure() {
        // Arrange
        TrelloCardDto cardDto = new TrelloCardDto("Invalid","Test desc","Test pos","Test listId");
        TrelloCard mappedCard = new TrelloCard("Invalid","Test desc","Test pos","Test listId");

        when(trelloMapper.mapToCard(cardDto)).thenReturn(mappedCard);
        doThrow(new IllegalArgumentException("Invalid card")).when(trelloValidator).validateCard(mappedCard);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> trelloFacade.createCard(cardDto));
    }
}