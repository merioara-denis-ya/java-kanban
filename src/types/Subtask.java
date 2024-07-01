package types;

public class Subtask extends Task {
    private final Epic epic;

    public Subtask(int id, String name, String description, Status status, Epic epic) {
        super(id, name, description, status);
        this.epic = epic;
        epic.getSubtasks().put(id, this);
    }

    public Subtask(int id, String name, String description, Epic epic) {
        super(id, name, description);
        this.epic = epic;
        epic.getSubtasks().put(id, this);
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public Subtask setName(String name) {
        return new Subtask(this.getId(), name, this.getDescription(), this.getStatus(), this.getEpic());
    }

    @Override
    public Subtask setDescription(String description) {
        return new Subtask(this.getId(), this.getName(), description, this.getStatus(), this.getEpic());
    }

    @Override
    public Subtask setStatus(Status status) {
        return new Subtask(this.getId(), this.getName(), this.getDescription(), status, this.getEpic());
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + this.getId() +
                ", name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status=" + this.getStatus() +
                ", epicId=" + this.getEpic().getId() +
                '}';
    }
}
