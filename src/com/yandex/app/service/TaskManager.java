package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import java.util.List;

public interface TaskManager {
    /** Получение всех экземмляров типа Task */
    List<Task> getTasks();

    /** Удаление всех экземмляров типа Task */
    void removeAllTasks();

    /** Получение экземмляра типа Task по идентификатору */
    Task getTaskById(Integer id);

    /** Создание нового экземмляра типа Task */
    Integer createTask(Task item);

    /** Обновление экземмляра типа Task */
    void updateTask(Task item);

    /** Удаление экземмляра типа Task по идентификатору */
    void removeTaskById(Integer id);

    /** Получение всех экземмляров типа Epic */
    List<Epic> getEpics();

    /** Удаление всех экземмляров типа Epic */
    void removeAllEpics();

    /** Получение экземмляра типа Epic по идентификатору */
    Epic getEpicById(Integer id);

    /** Создание нового экземмляра типа Epic */
    Integer createEpic(Epic item);

    /** Обновление экземмляра типа Epic */
    void updateEpic(Epic item);

    /** Удаление экземмляра типа Epic по идентификатору */
    void removeEpicById(Integer id);

    /** Получения списка экземмляров типа Subtask привязаного к родителю типа Epic */
    List<Subtask> getSubtasksByEpicId(Integer epicId);

    /** Получение всех экземмляров типа Epic */
    List<Subtask> getSubtasks();

    /** Удаление всех экземмляров типа Epic */
    void removeAllSubtasks();

    /** Получение экземмляра типа Epic по идентификатору */
    Subtask getSubtaskById(Integer id);

    /** Создание нового экземмляра типа Epic */
    Integer createSubtask(Subtask item) throws Exception;

    /** Обновление экземмляра типа Epic */
    void updateSubtask(Subtask item) throws Exception;

    /** Удаление экземмляра типа Epic по идентификатору */
    void removeSubtaskById(Integer id);

    /**
     * История просмотреных задач
     */
    List<? super Task> getHistory();
}
