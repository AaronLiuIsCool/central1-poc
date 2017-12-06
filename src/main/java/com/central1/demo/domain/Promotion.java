package com.central1.demo.domain;

public class Promotion {

    private String name;
    private String description;

    public Promotion(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
