package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloServiceTest {
    @Mock
    private AdminConfig adminConfig;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService emailService;
    @InjectMocks
    private TrelloService trelloService;


    @Test
    void fetchTrelloBoards() {
        //Arrange
        List<TrelloBoardDto> mockedTrelloList =
                List.of(new TrelloBoardDto("1", "test_list", List.of()));
        when(trelloClient.getTrelloBoards()).thenReturn(mockedTrelloList);
        //Act
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        //Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(mockedTrelloList);
        verify(trelloClient).getTrelloBoards();
    }
    @Test
    void fetchTrelloBoards_WhenClientThrowsException() {
        //Arrange
        when(trelloClient.getTrelloBoards()).thenThrow(new RuntimeException("Client error"));
        //Act & Assert
        assertThrows(RuntimeException.class, () -> trelloService.fetchTrelloBoards());
    }
    @Test
    void shouldFetchEmptyList() {
        //Arrange
        when(trelloClient.getTrelloBoards()).thenReturn(Collections.emptyList());
        //Act
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void createTrelloCard() {
        //Arrange
        TrelloCardDto cardDto = new TrelloCardDto("Test name", "Test desc", "Test pos", "Test listId");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1", "Test name", "http://example.com");
        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@admin.com");
        //Act
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);
        //Assert
        assertThat(result).isEqualTo(createdCard);
        verify(emailService).send(any(Mail.class));
    }
    @Test
    void createNullTrelloCard() {
        //Arrange
        TrelloCardDto cardDto = new TrelloCardDto("Test name", "Test desc", "Test pos", "Test listId");
        when(trelloClient.createNewCard(cardDto)).thenReturn(null);
        //Act
        CreatedTrelloCardDto result = trelloService.createTrelloCard(cardDto);
        //Assert
        assertThat(result).isNull();
        verify(emailService, never()).send(any(Mail.class));
    }
    @Test
    void createTrelloCard_WhenCreationFails_ShouldNotSendEmail() {
        //Arrange
        when(trelloClient.createNewCard(any(TrelloCardDto.class))).thenReturn(null);
        //Act
        trelloService.createTrelloCard(new TrelloCardDto("name", "desc", "pos", "listId"));
        //Assert
        verify(emailService, never()).send(any(Mail.class));
    }
    @Test
    void createTrelloCard_WhenSuccessful_ShouldSendEmailWithCorrectContent() {
        // Arrange
        TrelloCardDto cardDto = new TrelloCardDto("Test", "Desc", "Top", "ListId");
        CreatedTrelloCardDto createdCard = new CreatedTrelloCardDto("1", "Test Card", "http://test.com");
        when(trelloClient.createNewCard(cardDto)).thenReturn(createdCard);
        when(adminConfig.getAdminMail()).thenReturn("admin@admin.com");

        ArgumentCaptor<Mail> mailCaptor = ArgumentCaptor.forClass(Mail.class);
        //Act
        trelloService.createTrelloCard(cardDto);
        //Assert
        verify(emailService).send(mailCaptor.capture());
        Mail sentMail = mailCaptor.getValue();
        assertEquals("admin@admin.com", sentMail.getMailTo());
        assertEquals("Tasks: New Trello card", sentMail.getSubject());
    }
}