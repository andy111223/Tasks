package com.crud.tasks.trello.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminConfigTest {
    /*
    For educational purpose. This test will confirm that the AdminConfig
    is correctly initialized.
     */
    @Autowired
    private AdminConfig adminConfig;
    @Test
    void contextLoads_WithCorrectAdminConfig() {
        assertNotNull(adminConfig);
    }
    @Test
    void trelloConfigProperties_AreNotNull() {
        assertNotNull(adminConfig.getAdminMail(), "Admin mail is not null");
    }
}