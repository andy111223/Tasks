package com.crud.tasks.service;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TrelloService {

    //Temat nie będzie się zmieniał zatem stała
    private final static String SUBJECT = "Tasks: New Trello card";
    //email będzie zawarty w klasie AdminConfig
    private final AdminConfig adminConfig;
    private final TrelloClient trelloClient;
    private final SimpleEmailService emailService;

    //Opakowanie metody getTrelloBoards()
    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }
    //Opakowanie metody createNewCard()
    public CreatedTrelloCard createTrelloCard(TrelloCardDto trelloCardDto) {
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        ofNullable(newCard).ifPresent(card -> emailService.send(
                new Mail(
                        adminConfig.getAdminMail(),
                        SUBJECT,
                        "New card: " + trelloCardDto.getName() + " has been created on your Trello account",
                        null
                )));

        return newCard;
    }
    //Zamienić wstrzykiwaną klasę TrelloClient na TrelloService w Controllerze
}
