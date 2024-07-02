package types;

public class Subtask extends Task {
    private final Epic epic;

    public Subtask(int id, String name, String description, Status status, Epic epic) {
        super(id, name, description, status);
        this.epic = epic;
    }

    public Subtask(int id, String name, String description, Epic epic) {
        super(id, name, description);
        this.epic = epic;
    }

    public Epic getEpic() {
        return this.epic;
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
