package com.yandex.app.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtaskIds;

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

    public Epic(int id, String name, String description, Status status, List<Integer> subtaskIds) {
        super(id, name, description, status);
        this.subtaskIds = subtaskIds;
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    /**
     * Добавление идентификатора подзадачи
     */
    public void linkSubtaskById(Integer subtaskId) {
        if (this.getSubtaskIds().contains(subtaskId)) {
            return;
        }

        this.getSubtaskIds().add(subtaskId);
    }

    /**
     * Удаление идентификатора подзадачи
     */
    public void unlinkSubtaskById(Integer subtaskId) {
        if (!this.getSubtaskIds().contains(subtaskId)) {
            return;
        }

        this.getSubtaskIds().remove(subtaskId);
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
