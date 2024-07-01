package types;

import java.util.Objects;

public class Task {
    /** Уникальный идентификационный номер задачи */
    private final int id;
    /** Название */
    private final String name;
    /** Описание */
    private final String description;
    /** Статус */
    private final Status status;

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        return new Task(this.id, name, this.description, this.status);
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        return new Task(this.id, this.name, description, this.status);
    }

    public Status getStatus() {
        return status;
    }

    public Task setStatus(Status status) {
        return new Task(this.id, this.name, this.description, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
