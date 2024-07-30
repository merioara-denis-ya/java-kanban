package com.yandex.app.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class SubtaskTest {
    public int id;
    public String name;
    public String description;
    public int epicId;

    @BeforeEach
    public void beforeEach() {
        id = new Random().nextInt(999);
        name = UUID.randomUUID().toString();
        description = UUID.randomUUID().toString();
        epicId = new Random().nextInt(999);
    }

    @Test
    public void shouldCreateWithoutIdAndStatus() {
        Subtask item = new Subtask(name, description, epicId);
        item.setId(id);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
        Assertions.assertEquals(epicId, item.getEpicId());
    }

    @Test
    public void shouldCreateTaskWithoutStatus() {
        Subtask item = new Subtask(id, name, description, epicId);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
        Assertions.assertEquals(epicId, item.getEpicId());
    }

    @Test
    public void shouldCreateTaskWithAllArguments() {
        Subtask item = new Subtask(id, name, description, Status.IN_PROGRESS, epicId);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.IN_PROGRESS, item.getStatus());
        Assertions.assertEquals(epicId, item.getEpicId());
    }

    @Test
    public void shouldBeEqualsToString() {
        Subtask item1 = new Subtask(id, name, description, epicId);
        Subtask item2 = new Subtask(id, name, description, epicId);
        Assertions.assertEquals(item1.toString(), item2.toString());
    }

    @Test
    public void shouldBeEquals() {
        Subtask item1 = new Subtask(id, name, description, Status.IN_PROGRESS, epicId);
        Subtask item2 = new Subtask(id, name, description, Status.IN_PROGRESS, epicId);
        Assertions.assertEquals(item1, item2);
    }

    @Test
    public void shouldBeEqualsHashCode() {
        Subtask item1 = new Subtask(id, name, description, epicId);
        Subtask item2 = new Subtask(id, name, description, epicId);
        Assertions.assertEquals(item1.hashCode(), item2.hashCode());
    }
}