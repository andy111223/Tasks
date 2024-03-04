package com.crud.tasks.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatedTrelloCardDtoTest {
    /*
    These tests are for educational purpose and focus on ensuring that JSON serialization
    and deserialization work as expected, reflecting the correct
    mapping of JSON properties to class fields and vice versa.
    These tests can catch configuration errors that might otherwise only surface at runtime.
     */
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }
    @Test
    void serializeToJson_CorrectlyMapsFields() throws Exception {
        CreatedTrelloCardDto cardDto = new CreatedTrelloCardDto("1", "Test Card", "http://url.com");
        String expectedJson = "{\"id\":\"1\",\"name\":\"Test Card\",\"shortUrl\":\"http://url.com\"}";

        String jsonResult = objectMapper.writeValueAsString(cardDto);

        assertEquals(expectedJson, jsonResult);
    }
    @Test
    void deserializeFromJson_CorrectlyMapsFields() throws Exception {
        String json = "{\"id\":\"1\",\"name\":\"Test Card\",\"shortUrl\":\"http://url.com\"}";
        CreatedTrelloCardDto expectedCardDto = new CreatedTrelloCardDto("1", "Test Card", "http://url.com");

        CreatedTrelloCardDto resultCardDto = objectMapper.readValue(json, CreatedTrelloCardDto.class);

        assertEquals(expectedCardDto.getId(), resultCardDto.getId());
        assertEquals(expectedCardDto.getName(), resultCardDto.getName());
        assertEquals(expectedCardDto.getShortUrl(), resultCardDto.getShortUrl());
    }
    @Test
    void shortUrl_IsCorrectlySerializedAndDeserialized() throws Exception {
        String json = "{\"shortUrl\":\"http://url.com\"}";
        CreatedTrelloCardDto cardDto = objectMapper.readValue(json, CreatedTrelloCardDto.class);

        assertEquals("http://url.com", cardDto.getShortUrl());

        String serializedJson = objectMapper.writeValueAsString(cardDto);
        assertTrue(serializedJson.contains("\"shortUrl\":\"http://url.com\""));
    }
}