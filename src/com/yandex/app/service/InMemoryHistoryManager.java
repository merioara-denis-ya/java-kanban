package com.yandex.app.service;

import com.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    /**
     * Количество задач в истории
     */
    private final int lengthOfHistory;

    /**
     * Колекция последних просмотреных задач
     */
    private final List<? super Task> lastViewedTasks = new ArrayList<>();

    public InMemoryHistoryManager() {
        lengthOfHistory = 10;
    }

    @Override
    public <T extends Task> void add(T item) {
        lastViewedTasks.add(item);

        while (lastViewedTasks.size() > lengthOfHistory) {
            lastViewedTasks.removeFirst();
        }
    }

    @Override
    public List<? super Task> getHistory() {
        return new ArrayList<>(lastViewedTasks);
    }
}
