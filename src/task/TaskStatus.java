package task;

public enum TaskStatus {
    NEW("Новая"),
    IN_PROGRESS("В работе"),
    DONE("Выполнена");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
