package upm.es.proyecto_app;

import java.util.Date;

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
