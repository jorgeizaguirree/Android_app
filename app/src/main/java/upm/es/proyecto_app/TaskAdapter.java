package upm.es.proyecto_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private final List<Task> items;
    private final Context mContext;

    public TaskAdapter(Context context,  List<Task> items) {
        this.items=items;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            view = view.inflate(mContext, android.R.layout.simple_list_item_1,null);
        }

        Task task = items.get(position);

        // Asigna los datos a las vistas del ListView
        String text = task.getName() + "\n" + task.getDescription() + "\n" + task.getDate();
        TextView Tview = view.findViewById(android.R.id.text1);
        Tview.setText(text);

        return view;
    }
}
