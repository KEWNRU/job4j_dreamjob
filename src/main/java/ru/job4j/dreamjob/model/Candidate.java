package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;

public class Candidate {
    private int id;
    private String name;
    private String description;
    private LocalDateTime data;
    private int fileId;
    private boolean visible;

    public Candidate(int id, String name, String description, int fileId, boolean visible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileId = fileId;
        this.visible = visible;
        data = LocalDateTime.now();
    }

    public Candidate() {
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
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
