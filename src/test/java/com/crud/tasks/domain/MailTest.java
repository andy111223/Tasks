package com.crud.tasks.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MailTest {
/*
Test for educational purpose. The tests ensure the integrity
and correct behavior of model classes rely on certain behaviors
(like the Optional wrapping) in the application logic.
 */

    @Test
    void mailConstructor_AllFieldsAreCorrectlySet() {
        List<String> ccList = Arrays.asList("cc1@example.com", "cc2@example.com");
        Mail mail = new Mail("to@example.com", "Subject", "Message", ccList);

        assertEquals("to@example.com", mail.getMailTo());
        assertEquals("Subject", mail.getSubject());
        assertEquals("Message", mail.getMessage());
        assertTrue(mail.getToCc().isPresent());
        assertEquals(ccList, mail.getToCc().get());
    }
    @Test
    void getToCc_ReturnsPresentOptionalWhenCcListIsNotNull() {
        List<String> ccList = Arrays.asList("cc1@example.com", "cc2@example.com");
        Mail mail = new Mail("to@example.com", "Subject", "Message", ccList);

        assertTrue(mail.getToCc().isPresent());
        assertEquals(ccList, mail.getToCc().get());
    }

    @Test
    void getToCc_ReturnsEmptyOptionalWhenCcListIsNull() {
        Mail mail = new Mail("to@example.com", "Subject", "Message", null);

        assertFalse(mail.getToCc().isPresent());
    }
    @Test
    void builder_CreatesMailObjectWithCorrectFields() {
        List<String> ccList = Arrays.asList("cc1@example.com", "cc2@example.com");
        Mail mail = Mail.builder()
                .mailTo("to@example.com")
                .subject("Subject")
                .message("Message")
                .toCc(ccList)
                .build();

        assertEquals("to@example.com", mail.getMailTo());
        assertEquals("Subject", mail.getSubject());
        assertEquals("Message", mail.getMessage());
        assertTrue(mail.getToCc().isPresent());
        assertEquals(ccList, mail.getToCc().get());
    }


}