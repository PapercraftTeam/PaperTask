package com.grieferpig.papertask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static List<TaskItem> taskItemList = new ArrayList();
    ListView ItemListView;
    ListAdapter listAdapter;
    ItemStorageMan man;

    public static Boolean onlyNothingItemShowned = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.man = new ItemStorageMan(this);
        this.listAdapter = new ListAdapter(this, R.layout.list_layout, taskItemList);
        ListView listView = (ListView) findViewById(R.id.TaskItemList);
        this.ItemListView = listView;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(onlyNothingItemShowned){
                    Snackbar.make(view, R.string.no_edit , Snackbar.LENGTH_SHORT).show();
                }else{
                TaskItem item = MainActivity.taskItemList.get(position);
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                try {
                    intent.putExtra("item", TaskItem.serialize(item));
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Damn", "A f**king error appeared");
                }
                MainActivity.this.startActivity(intent);
            }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (onlyNothingItemShowned){
                    Snackbar.make(view, R.string.no_delete, Snackbar.LENGTH_SHORT).show();
                    return true;
                }
                final TaskItem _mayUndoItem = taskItemList.get(position);
                final int position_copy = position;
                taskItemList.remove(position);
                refreshList();
                Snackbar.make(view, R.string.already_deleted, Snackbar.LENGTH_SHORT).setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskItemList.add(position_copy, _mayUndoItem);
                        Snackbar.make(v, R.string.already_undo, Snackbar.LENGTH_SHORT).show();
                        refreshList();
                        System.gc();
                    }
                }).show();
                return true;
            }

        });
        this.ItemListView.setAdapter(this.listAdapter);
        refreshList();
    }

    private void refreshList() {
        listAdapter.notifyDataSetChanged();
        if(taskItemList.isEmpty()){
        onlyNothingItemShowned = true;
         taskItemList.add(new TaskItem(man.getTranslation(R.string.empty)
                 , man.getTranslation(R.string.empty_subtitle)));
        }
    }

    protected void onResume() {
        super.onResume();
        refreshList();
    }

    public void onAddItemClick(View v) {
        Intent i = new Intent(this, NewTaskActivity.class);
        i.putExtra("item", "NEW");
        startActivity(i);
    }
}
