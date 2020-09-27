package com.grieferpig.papertask;

import android.app.Activity;
import android.content.SharedPreferences;

public class ItemStorageMan {
    public static final String TASK_STORAGE = "com.grieferpig.papertask.taskstoragekey";
    Activity a;
    String key;
    private SharedPreferences prefStorage;

    public ItemStorageMan(Activity a2) {
        this.a = a2;
    }

    public ItemStorageMan(Activity a2, String key2) {
        this.a = a2;
        this.key = key2;
        this.prefStorage = a2.getSharedPreferences(key2, 0);
    }

    public void putString(String s) {
        SharedPreferences.Editor editor = this.prefStorage.edit();
        editor.putString(this.key, s);
        editor.commit();
    }

    public void putString(String key2, String s) {
        SharedPreferences sharedPreferences = this.a.getSharedPreferences(this.key, 0);
        this.prefStorage = sharedPreferences;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key2, s);
        editor.commit();
    }

    public String getString(String key2) {
        return this.prefStorage.getString(key2, "");
    }

    public String getString() {
        return this.prefStorage.getString(this.key,"");
    }

    public String getTranslation(int resource_name) {
        return this.a.getResources().getString(resource_name);
    }
}
