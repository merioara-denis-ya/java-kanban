package com.yandex.app.service;

import com.yandex.app.model.Task;

import java.util.List;

public interface HistoryManager {
    <T extends Task> void add(T item);
    List<? super Task> getHistory();
}
