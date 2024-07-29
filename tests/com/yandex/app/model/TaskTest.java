package com.yandex.app.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

public class TaskTest {
    public int id;
    public String name;
    public String description;

    @BeforeEach
    public void beforeEach() {
        id = new Random().nextInt(999);
        name = UUID.randomUUID().toString();
        description = UUID.randomUUID().toString();
    }

    @Test
    public void shouldCreateWithoutIdAndStatus() {
        Task item = new Task(name, description);
        Assertions.assertEquals(0, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
    }

    @Test
    public void shouldCreateWithoutStatus() {
        Task item = new Task(id, name, description);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
    }

    @Test
    public void shouldCreateWithAllArguments() {
        Task item = new Task(id, name, description, Status.IN_PROGRESS);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.IN_PROGRESS, item.getStatus());
    }

    @Test
    public void shouldBeEqualsToString() {
        Task item1 = new Task(id, name, description);
        Task item2 = new Task(id, name, description);
        Assertions.assertEquals(item1.toString(), item2.toString());
    }

    @Test
    public void shouldBeEquals() {
        Task item1 = new Task(id, name, description, Status.IN_PROGRESS);
        Task item2 = new Task(id, name, description, Status.IN_PROGRESS);
        Assertions.assertEquals(item1, item2);
    }

    @Test
    public void shouldBeEqualsHashCode() {
        Task item1 = new Task(id, name, description);
        Task item2 = new Task(id, name, description);
        Assertions.assertEquals(item1.hashCode(), item2.hashCode());
    }
}
