package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        //Arrange
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
    @Test
    void shouldFetchTrelloBoards() throws Exception {
        //Arrange
        List<TrelloListDto> lists = List.of(new TrelloListDto("1","Test list",false));
        List<TrelloBoardDto> boards = List.of(new TrelloBoardDto("1", "Test Task", lists));
        when(trelloFacade.fetchTrelloBoards()).thenReturn(boards);
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                //Board fields
                .andExpect(MockMvcResultMatchers.jsonPath("$",Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name",Matchers.is("Test Task")))
                //List fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists",Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id",Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name",Matchers.is("Test list")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed",Matchers.is(false)));
    }
    @Test
    void shouldCreateTrelloCard() throws Exception {
        //Arrange
        TrelloCardDto cardDto = new TrelloCardDto("Test name", "Test desc", "Test pos", "1");
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("123","Test name","http://test.com");
        when(trelloFacade.createCard(any(TrelloCardDto.class))).thenReturn(createdCardDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(cardDto);
        //Act & Assert
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Matchers.is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl",Matchers.is("http://test.com")));

    }
}