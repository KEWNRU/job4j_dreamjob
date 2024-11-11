package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Candidate {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "title", "title",
            "description", "description",
            "creation_date", "creationDate",
            "file_id", "fileId",
            "visible", "visible"
    );

    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
    private int fileId;
    private boolean visible;

    public Candidate(int id, String title, String description, int fileId, boolean visible) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.fileId = fileId;
        this.visible = visible;
        creationDate = LocalDateTime.now();
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
