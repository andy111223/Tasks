package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private final List<String> toCc;

    public Optional <List<String>> getToCc() {
        return Optional.ofNullable(toCc);
    }
}
