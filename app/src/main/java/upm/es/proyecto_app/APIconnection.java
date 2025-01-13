package upm.es.proyecto_app;


import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class APIconnection extends Thread {

    Activity activity;
    List<Quotes> quotes;
    TextView quote;

    public APIconnection(Activity activity, List<Quotes> quotes, TextView quote) {
        this.activity = activity;
        this.quotes = quotes;
        this.quote = quote;

    }
    @Override
    public void run() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("dd/MM/yyyy hh:mm a");
        Gson gson = gsonBuilder.create();
        String response = null;
        try {
            response = DescargaQuotes.getURLText("https://zenquotes.io/api/quotes/");
        } catch (Exception e) {
            Toast.makeText(activity, "Download failed", Toast.LENGTH_SHORT).show();
        }
        quotes = Arrays.asList(gson.fromJson(response, Quotes[].class));
        App app = (App) activity.getApplication();
        app.setQuotesList(quotes);
        int random = new Random().nextInt(quotes.size());
        String text = quotes.get(random).getQuote() + "\n\n" + quotes.get(random).getAuthor();
        activity.runOnUiThread(() -> quote.setText(text));

    }
}
