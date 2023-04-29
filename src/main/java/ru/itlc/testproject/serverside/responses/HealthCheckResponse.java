package ru.itlc.testproject.serverside.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class HealthCheckResponse {
    private final boolean apiStatus;
    private final boolean dbStatus;
}
