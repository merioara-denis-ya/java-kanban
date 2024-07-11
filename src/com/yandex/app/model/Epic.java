package com.yandex.app.model;

import java.util.ArrayList;

public class Epic extends Task {
    protected final ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIds = new ArrayList<>();
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
        this.subtaskIds = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        this.subtaskIds = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status, ArrayList<Integer> subtaskIds) {
        super(id, name, description, status);
        this.subtaskIds = subtaskIds;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
