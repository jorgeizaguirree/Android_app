package upm.es.proyecto_app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Quotes> quotesList = new ArrayList<>();

    public List<Quotes> getQuotesList() {
        return quotesList;

    }
    public void setQuotesList(List<Quotes> quotesList) {
        this.quotesList = quotesList;
    }
}
