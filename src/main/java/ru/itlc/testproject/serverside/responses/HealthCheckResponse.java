package ru.itlc.testproject.serverside.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HealthCheckResponse {
    private final boolean dbStatus;
}
