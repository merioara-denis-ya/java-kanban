package com.yandex.app.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManagersTest {
    @Test
    void shouldGetDefaultInMemoryTaskManager() {
        TaskManager instance = Managers.getDefault();
        assertTrue(instance instanceof InMemoryTaskManager);
    }
}
