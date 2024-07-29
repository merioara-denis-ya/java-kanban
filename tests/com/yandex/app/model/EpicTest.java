package com.yandex.app.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EpicTest {
    public int id;
    public String name;
    public String description;
    public List<Integer> subtaskIds;

    @BeforeEach
    public void beforeEach() {
        id = new Random().nextInt(999);
        name = UUID.randomUUID().toString();
        description = UUID.randomUUID().toString();
        Integer subtaskId1 = new Random().nextInt(999);
        Integer subtaskId2 = new Random().nextInt(999);
        subtaskIds = new ArrayList<>();
        if (subtaskId1.equals(subtaskId2)) {
            subtaskIds.add(subtaskId1);
        } else {
            subtaskIds.add(subtaskId1);
            subtaskIds.add(subtaskId2);
        }
    }

    @Test
    public void shouldCreateWithoutIdAndStatusAndSubtaskIds() {
        Epic item = new Epic(name, description);
        Assertions.assertEquals(0, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
        Assertions.assertTrue(item.getSubtaskIds().isEmpty());
    }

    @Test
    public void shouldCreateWithoutStatusAndSubtaskIds() {
        Epic item = new Epic(id, name, description);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.NEW, item.getStatus());
        Assertions.assertTrue(item.getSubtaskIds().isEmpty());
    }

    @Test
    public void shouldCreateWithoutSubtaskIds() {
        Epic item = new Epic(id, name, description, Status.IN_PROGRESS);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.IN_PROGRESS, item.getStatus());
        Assertions.assertTrue(item.getSubtaskIds().isEmpty());
    }

    @Test
    public void shouldCreateWithAllArguments() {
        Epic item = new Epic(id, name, description, Status.IN_PROGRESS, subtaskIds);
        Assertions.assertEquals(id, item.getId());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(Status.IN_PROGRESS, item.getStatus());
        Assertions.assertArrayEquals(subtaskIds.toArray(), item.getSubtaskIds().toArray());
    }

    @Test
    public void shouldBeEqualsToString() {
        Epic item1 = new Epic(id, name, description);
        Epic item2 = new Epic(id, name, description);
        Assertions.assertEquals(item1.toString(), item2.toString());
    }

    @Test
    public void shouldBeEquals() {
        Epic item1 = new Epic(id, name, description, Status.IN_PROGRESS, subtaskIds);
        Epic item2 = new Epic(id, name, description, Status.IN_PROGRESS, subtaskIds);
        Assertions.assertEquals(item1, item2);
    }

    @Test
    public void shouldBeEqualsHashCode() {
        Epic item1 = new Epic(id, name, description, Status.IN_PROGRESS, subtaskIds);
        Epic item2 = new Epic(id, name, description, Status.IN_PROGRESS, subtaskIds);
        Assertions.assertEquals(item1.hashCode(), item2.hashCode());
    }
}
