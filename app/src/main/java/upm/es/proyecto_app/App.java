package upm.es.proyecto_app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private List<Quotes> quotesList = new ArrayList<>();
    private int image;

    public List<Quotes> getQuotesList() {
        return quotesList;

    }
    public void setQuotesList(List<Quotes> quotesList) {
        this.quotesList = quotesList;
    }
    public int getImage(){
        return image;
    }
    public void setImage(int image){
        this.image = image;
    }
}
