package ru.itlc.testproject.serverside.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itlc.testproject.serverside.responses.HealthCheckResponse;

@RestController
@RequestMapping("/api")
public class ApiHealthCheckController {

    @GetMapping("/health-check")
    public HealthCheckResponse getHealthCheck() {
        // Временная реализация проверки работоспособности
        // !! Возможно, целесообразно удалить признак apiStatus - он избыточен
        // (т.к. признаком работы приложения является сам факт получения этого сообщения)
        // dbStatus - признак наличия соединения с БД.
        return new HealthCheckResponse(true, true);
    }
}
