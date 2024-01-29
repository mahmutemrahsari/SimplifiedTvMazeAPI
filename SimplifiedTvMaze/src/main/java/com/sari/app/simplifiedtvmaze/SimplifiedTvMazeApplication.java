package com.sari.app.simplifiedtvmaze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
@EnableOpenApi
@SpringBootApplication
public class SimplifiedTvMazeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimplifiedTvMazeApplication.class, args);
    }
}
