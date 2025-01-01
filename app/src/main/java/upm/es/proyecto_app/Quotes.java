package upm.es.proyecto_app;
import com.google.gson.annotations.SerializedName;

public class Quotes {
    @SerializedName("q")
    private String quote;
    @SerializedName("a")
    private String author;

    public String getQuote(){
        return quote;
    }
    public String getAuthor(){
        return author;
    }
    public void setQuote(String quote){
        this.quote = quote;
    }
    public void setAuthor(String author){
        this.author = author;
    }

}
