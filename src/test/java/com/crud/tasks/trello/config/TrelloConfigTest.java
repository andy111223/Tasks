package com.crud.tasks.trello.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TrelloConfigTest {
    /*
    For educational purpose. These tests will confirm that the TrelloConfig
    is correctly initialized and that all necessary properties are not null,
    which implies they're correctly loaded from the application's properties files.
     */
    @Autowired
    private TrelloConfig trelloConfig;

    @Test
    void contextLoads_WithCorrectTrelloConfig() {
        assertNotNull(trelloConfig);
    }
    @Test
    void trelloConfigProperties_AreNotNull() {
        assertNotNull(trelloConfig.getUsername(), "Username is null");
        assertNotNull(trelloConfig.getTrelloApiEndpoint(), "API endpoint is null");
        assertNotNull(trelloConfig.getTrelloAppKey(), "App key is null");
        assertNotNull(trelloConfig.getTrelloToken(), "Token is null");
    }
}