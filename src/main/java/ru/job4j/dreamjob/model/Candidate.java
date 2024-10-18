package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;

public class Candidate {
    private int id;
    private String name;
    private String description;
    private LocalDateTime data;

    public Candidate(int id, String name, String description, LocalDateTime data) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
