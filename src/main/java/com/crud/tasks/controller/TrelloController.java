package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/trello") // http://localhost:8080/v1/trello/boards
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrelloController {

    private final TrelloClient trelloClient;

    @GetMapping("boards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

//        List<TrelloBoardDto> filteredBoards = trelloBoards.stream()
//                        .filter(trelloBoardDto -> trelloBoardDto.getId() != null && !trelloBoardDto.getId().isEmpty())
//                        .filter(trelloBoardDto -> trelloBoardDto.getName() != null && !trelloBoardDto.getName().isEmpty())
//                        .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
//                        .collect(Collectors.toList());

        trelloBoards.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " - " + trelloBoardDto.getName());
            System.out.println("This board contains lists: ");
            trelloBoardDto.getLists().forEach(trelloList -> {
                System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed());
            });
        });
    }
    @PostMapping("cards")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloClient.createNewCard(trelloCardDto);
    }
}
