package com.crud.tasks.controller;

import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        List<TrelloBoardDto> filteredBoards = trelloBoards.stream()
                        .filter(trelloBoardDto -> trelloBoardDto.getId() != null && !trelloBoardDto.getId().isEmpty())
                        .filter(trelloBoardDto -> trelloBoardDto.getName() != null && !trelloBoardDto.getName().isEmpty())
                        .filter(trelloBoardDto -> trelloBoardDto.getName().contains("Kodilla"))
                        .collect(Collectors.toList());

        filteredBoards.forEach(trelloBoardDto -> {
            System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName());
        });
    }
}
