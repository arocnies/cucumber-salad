package com.example.steps;

import io.cucumber.java8.En;

public class ExternalSteps implements En {
    public ExternalSteps() {
        Given("an external library has steps", () -> {
            System.out.println("Hello from an external library \uD83D\uDE00");
        });
    }
}
