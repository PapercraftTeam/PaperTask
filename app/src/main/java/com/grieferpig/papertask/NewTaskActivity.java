package com.grieferpig.papertask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import java.io.IOException;
import java.util.Objects;

public class NewTaskActivity extends Activity {
    TextView Activitytitle;
    Date d;
    EditText editTitle;
    TextView itemTitle;
    ItemStorageMan superman;
    String title;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        this.superman = new ItemStorageMan(this);
        this.Activitytitle = (TextView) findViewById(R.id.NewTaskTitle);
        this.itemTitle = (TextView) findViewById(R.id.TaskTitle);
        this.editTitle = (EditText) findViewById(R.id.TaskTitleEdit);
        if (Objects.equals(getIntent().getStringExtra("item"), "NEW")) {
            this.Activitytitle.setText(R.string.add_task);
            this.itemTitle.setText(R.string.new_task);
            return;
        }
        this.Activitytitle.setText(R.string.edit_task);
        try {
            TaskItem item = TaskItem.deSerialize(getIntent().getStringExtra("item"));
            this.title = item.getTitle();
            this.d = item.getDeadLine();
            this.itemTitle.setText(this.title);
            this.editTitle.setText(this.title);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, R.string.failed_getting_item, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void onCommit(View v) {
        if(MainActivity.onlyNothingItemShowned){
            MainActivity.taskItemList.clear();
            MainActivity.onlyNothingItemShowned = false;
        }
        MainActivity.taskItemList.add(new TaskItem(new Date(Date.getTodayDate())
                , this.editTitle.getText().toString(), this));
        Snackbar.make(v, (CharSequence) this.superman.getTranslation(R.string.finished)
                , Snackbar.LENGTH_SHORT).setAction((CharSequence) this.superman.getTranslation(R.string.okay)
                , (View.OnClickListener) new View.OnClickListener() {
            public void onClick(View v) {
                NewTaskActivity.this.finish();
            }
        }).show();
    }

}
