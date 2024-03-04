package com.crud.tasks.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class TrelloDtoTest {
/*
For educational purpose: tests verify the correct serialization
and deserialization of JSON to and from TrelloDto objects
 */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void serializeToJson_CorrectlyMapsFields() throws JsonProcessingException {
        //Arrange
        TrelloDto trelloDto = new TrelloDto();
        trelloDto.setBoard(10);
        trelloDto.setCard(20);
        //Act
        String jsonResult = objectMapper.writeValueAsString(trelloDto);
        //Assert
        assertThat(jsonResult).contains("\"board\":10");
        assertThat(jsonResult).contains("\"card\":20");
    }
    @Test
    void deserializeFromJson_CorrectlyMapsFields() throws JsonProcessingException {
        //Arrange
        String json = "{\"board\":10,\"card\":20}";
        //Act
        TrelloDto trelloDto = objectMapper.readValue(json, TrelloDto.class);
        //Assert
        assertThat(trelloDto.getBoard()).isEqualTo(10);
        assertThat(trelloDto.getCard()).isEqualTo(20);
    }

}
