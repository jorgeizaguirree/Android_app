package upm.es.proyecto_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    private final Context context;
    private final List<Task> tasks;

    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.activity_home_screen, parent, false); // Inflate the main layout
        }

        Task currentTask = tasks.get(position);

        // Find TextViews in the main layout
        TextView nameTextView = listItemView.findViewById(R.id.homeScreen_txt_username); // Replace with the ID of the TextView for the name
        TextView descriptionTextView = listItemView.findViewById(R.id.homeScreen_txt_welcomeDescription); // Replace with the ID of the TextView for the description
        TextView dateTextView = listItemView.findViewById(R.id.homeScreen_txt_quote); // Replace with the ID of the TextView for the date

        // Set the text of the TextViews
        nameTextView.setText(currentTask.getName());
        descriptionTextView.setText(currentTask.getDescription());
        dateTextView.setText(currentTask.getDate());

        return listItemView;
    }
}