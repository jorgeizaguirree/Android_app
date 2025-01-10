package upm.es.proyecto_app;

public class Task {
    private String name;
    private String description;
    private String date;

    public Task(String name, String description, String date) {
        this.date = date;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }
}
