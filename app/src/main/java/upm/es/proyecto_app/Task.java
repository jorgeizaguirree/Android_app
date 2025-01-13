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
    public void setName(String name){
        this.name = name;
    }


    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }
}
