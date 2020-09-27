package com.grieferpig.papertask;

import android.app.Activity;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class TaskItem implements Serializable {
    private static Activity a;
    Date deadLine;
    String subTitle;
    String title;

    public TaskItem(Date deadLine, String title, Activity a) {
        this.deadLine = deadLine;
        this.title = title;
        a = a;
        this.subTitle = new ItemStorageMan(a).getTranslation(R.string.end_up_on)
                + " " + deadLine.getSpecificDate(Date.MM)
                + new ItemStorageMan(a).getTranslation(R.string.month)
                + deadLine.getSpecificDate(Date.DD)
                + new ItemStorageMan(a).getTranslation(R.string.day);
    }

    public TaskItem(String title, String title2) {
        this.title = title;
        this.subTitle = title2;
    }

    public static String serialize(TaskItem t) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(t);
        String serStr = URLEncoder.encode(byteArrayOutputStream.toString(
                "ISO-8859-1"), "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    public static TaskItem deSerialize(String str) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream
                = new ByteArrayInputStream(URLDecoder.decode(str, "UTF-8")
                .getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        TaskItem item = (TaskItem) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return item;
    }

    public Date getDeadLine() {
        return this.deadLine;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSubTitle() {
        return this.subTitle;
    }

    public void setDeadLine(Date deadLine2) {
        this.deadLine = deadLine2;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public void setSubTitle(String subtitle) {
        this.subTitle = subtitle;
    }

    public static String serializeItemsList(List<TaskItem> list) {
        JSONArray jsonArray = new JSONArray();
        int i = 0;
        while (i < list.size()) {
            try {
                jsonArray.put(serialize(list.get(i)));
                i++;
            } catch (Exception e) {
            }
        }
        return jsonArray.toString();
    }

    public static List<TaskItem> deSerializeItemsList(String s) {
        List<TaskItem> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(deSerialize(jsonArray.getString(i)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void saveList(List<TaskItem> list) {
        new ItemStorageMan(a, ItemStorageMan.TASK_STORAGE).putString(serializeItemsList(list));
    }

    public static List<TaskItem> getList() {
        return deSerializeItemsList(new ItemStorageMan(a, ItemStorageMan.TASK_STORAGE).getString());
    }
}
