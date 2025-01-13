package upm.es.proyecto_app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class quotes_activity extends AppCompatActivity {

    List<Quotes> quotes;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quotes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView backIcon = findViewById(R.id.quoteScreen_backIcon);
        list = findViewById(R.id.quoteScreen_listView_list);
        App app = (App) getApplication();
        quotes = app.getQuotesList();
        MyItemAdapter adapter = new MyItemAdapter(quotes, this);
        list.setAdapter(adapter);
        backIcon.setOnClickListener(v -> finish());
    }
}
class MyItemAdapter extends BaseAdapter {
    private List<Quotes> quotes;
    private Context context;

    public MyItemAdapter(List<Quotes> quotes, Context context) {
        this.quotes = quotes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return quotes.size();
    }

    @Override
    public Object getItem(int i) {
        return quotes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(context, R.layout.list_item, null);
        }
        Quotes q = quotes.get(i);
        String text = q.getQuote() + "\n\n" + q.getAuthor();
        TextView quoteTextView = view.findViewById(R.id.quoteScreen_txt_quote);
        quoteTextView.setText(text);
        return view;

    }
}