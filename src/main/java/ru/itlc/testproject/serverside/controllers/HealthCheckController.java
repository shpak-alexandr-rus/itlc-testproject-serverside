package ru.itlc.testproject.serverside.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itlc.testproject.serverside.responses.HealthCheckResponse;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthCheckController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HealthCheckController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/health-check")
    public ResponseEntity<HealthCheckResponse> getHealthCheck() {
        // Часть, проверяющая доступность базы данных методом выполнения простого запроса
        boolean dbCheckStatus;
        try {
            // Выполняется простой запрос и возвращается результат в виде Map
            // При нормальном выполнении запроса в переменной res будет значение
            //     равное (K,V) => "2", 2
            Map<String, Object> res = jdbcTemplate.queryForMap("select 1+1");

            // Если res не пустое, то тест на доступность БД пройден
            dbCheckStatus = !res.isEmpty();
        } catch (Exception e) {
            // При возникновении любой исключительной ситуации считаем,
            //     что тест на доступность БД не пройден
            e.printStackTrace();
            dbCheckStatus = false;
        }

        // Формируем ответ для клиентской части
        return new ResponseEntity<>(new HealthCheckResponse(dbCheckStatus), HttpStatus.OK);
    }
}
