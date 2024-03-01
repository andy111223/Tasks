package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrelloMapperTest {

    @Test
    void mapToBoards() {
        //Arrange
        TrelloMapper trelloMapper = new TrelloMapper();
        List<TrelloBoardDto> inputBoardsDto = List.of(new TrelloBoardDto("1", "Test Board", List.of(new TrelloListDto("1", "Test List", false))));

        // Act
        List<TrelloBoard> mappedBoards = trelloMapper.mapToBoards(inputBoardsDto);

        // Assert
        assertNotNull(mappedBoards);
        assertEquals(1, mappedBoards.size());
        TrelloBoard resultBoard = mappedBoards.get(0);
        assertAll("Should correctly map to Boards: ",
                () -> assertEquals("1", resultBoard.getId()),
                () -> assertEquals("Test Board", resultBoard.getName()),
                () -> assertEquals(1, resultBoard.getLists().size())
        );
        TrelloList resultList = resultBoard.getLists().get(0);
        assertAll("The board should have the correct list: ",
                () -> assertEquals("1", resultList.getId()),
                () -> assertEquals("Test List", resultList.getName()),
                () -> assertFalse(resultList.isClosed())
        );
    }

    @Test
    void mapToList() {
        //Arrange
        TrelloMapper trelloMapper = new TrelloMapper();
        List<TrelloListDto> inputTrelloListDto = List.of(new TrelloListDto("1", "To do", true));
        //Act
        List<TrelloList> mappedList = trelloMapper.mapToList(inputTrelloListDto);
        //Assert
        assertNotNull(mappedList);
        assertEquals(1, mappedList.size());
        TrelloList resultList = mappedList.get(0);
        assertAll("Should correctly map to List: ",
                () -> assertEquals("1", resultList.getId()),
                () -> assertEquals("To do", resultList.getName()),
                () -> assertTrue(resultList.isClosed())
        );
    }
    @Test
    void mapToBoardsDto() {
        //Arrange
        TrelloMapper trelloMapper = new TrelloMapper();
        List<TrelloBoard> inputBoards = List.of(new TrelloBoard("1", "Test Board", List.of(new TrelloList("1", "Test List", false))));
        //Act
        List<TrelloBoardDto> mappedBoardsDto = trelloMapper.mapToBoardsDto(inputBoards);
        //Assert
        assertNotNull(mappedBoardsDto);
        assertEquals(1,mappedBoardsDto.size());
        TrelloBoardDto resultBoardDto = mappedBoardsDto.get(0);
        assertAll("Should correctly map to TrelloBoardDto: ",
                () -> assertEquals("1", resultBoardDto.getId()),
                () -> assertEquals("Test Board", resultBoardDto.getName()),
                () -> assertEquals(1, resultBoardDto.getLists().size())
        );
        TrelloListDto resultListDto = resultBoardDto.getLists().get(0);
        assertAll("The TrelloBoardDto should have the correct TrelloListDto: ",
                () -> assertEquals("1", resultListDto.getId()),
                () -> assertEquals("Test List", resultListDto.getName()),
                () -> assertFalse(resultListDto.isClosed())
        );
    }

    @Test
    void mapToListDto() {
        //Arrange
        TrelloMapper trelloMapper = new TrelloMapper();
        List<TrelloList> inputTrelloList = List.of(new TrelloList("1", "To do", true));
        //Act
        List<TrelloListDto> mappedListDto = trelloMapper.mapToListDto(inputTrelloList);
        //Assert
        assertNotNull(mappedListDto);
        assertEquals(1,mappedListDto.size());
        TrelloListDto resultListDto = mappedListDto.get(0);
        assertAll("Should correctly map to TrelloListDto: ",
                () -> assertEquals("1", resultListDto.getId()),
                () -> assertEquals("To do", resultListDto.getName()),
                () -> assertTrue(resultListDto.isClosed())
        );
    }

    @Test
    void mapToCardDto() {
        //Arrange
        TrelloMapper trelloMapper = new TrelloMapper();
        TrelloCard inputTrelloCard = new TrelloCard("Test Card", "Card to be tested","top","123");
        //Act
        TrelloCardDto mappedTrelloCardDto = trelloMapper.mapToCardDto(inputTrelloCard);
        //Assert
        assertAll("Should correctly map to CardDto",
                () -> assertEquals("Test Card", mappedTrelloCardDto.getName()),
                () -> assertEquals("Card to be tested", mappedTrelloCardDto.getDescription()),
                () -> assertEquals("top", mappedTrelloCardDto.getPos()),
                () -> assertEquals("123", mappedTrelloCardDto.getListId()),
                () -> assertNotNull(mappedTrelloCardDto)
        );


    }

    @Test
    void mapToCard() {
        //Arrange
        TrelloMapper trelloMapper = new TrelloMapper();
        //Act
        TrelloCardDto inputTrelloCardDto = new TrelloCardDto("Test Card", "Card to be tested","top","123");
        TrelloCard mappedTrelloCard = trelloMapper.mapToCard(inputTrelloCardDto);
        //Assert
        assertAll("Should correctly map to Card",
                () -> assertEquals("Test Card", mappedTrelloCard.getName()),
                () -> assertEquals("Card to be tested", mappedTrelloCard.getDescription()),
                () -> assertEquals("top", mappedTrelloCard.getPos()),
                () -> assertEquals("123", mappedTrelloCard.getListId()),
                () -> assertNotNull(mappedTrelloCard)
        );
    }
}