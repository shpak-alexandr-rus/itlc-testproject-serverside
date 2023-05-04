package ru.itlc.testproject.serverside.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BooleanResponse {
    private final boolean status;
}
