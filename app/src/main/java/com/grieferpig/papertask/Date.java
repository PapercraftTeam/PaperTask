package com.grieferpig.papertask;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

public class Date implements Serializable {
    public static final int DD = 3;
    public static final int MM = 2;
    public static final int YYYY = 1;
    private String Date;

    public Date() {
        this.Date = getTodayDate();
    }

    public Date(String date) {
        this.Date = date;
    }

    public String getDate() {
        return this.Date;
    }

    public String getSpecificDate(int type) {
        if (type == YYYY) {
            return this.Date.substring(0, 4);
        }
        else if (type == MM) {
            if(this.Date.substring(4, 5).equals("0")){
                return this.Date.substring(5, 6);
            }else {
                return this.Date.substring(4, 6);
            }
        }
        else if(type == DD) {
            if(this.Date.substring(6, 7).equals("0")){
                return this.Date.substring(7, 8);
            }else {
                return this.Date.substring(6, 8);
            }
        }else{
            return "";
        }
    }

    public void setDate(String Date2) {
        this.Date = Date2;
    }

    public static String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 10 && day < 10) {
            return year + "0" + month + "0" + day;
        } else if (month < 10) {
            return year + "0" + month + day;
        } else if (day < 10) {
            return year  + month + "0" + day;
        } else {
            return "" + year + month + day;
        }
    }

    public static boolean compareDate(Date date1, Date date2) {
        return Integer.parseInt(date1.getDate()) > Integer.parseInt(date2.getDate());
    }

    public static Date generateDate(int year, int month, int day) {
        if ((year + "" + month + "" + day).length() == 8) {
            return new Date(year + "" + month + "" + day);
        } else if (month < 10 && day < 10) {
            return new Date(year + "0" + month + "0" + day);
        } else if (month < 10) {
            return new Date(year + "0" + month + "" + day);
        } else if (day < 10) {
            return new Date(year + "" + month + "0" + day);
        } else {
            return new Date(year + "" + month + "" + day);
        }
    }

    public static String serialize(Date d) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(d);
        String serStr = URLEncoder.encode(byteArrayOutputStream.toString("ISO-8859-1"), "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    public static Date deSerialize(String str) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(URLDecoder.decode(str, "UTF-8").getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Date d = (Date) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return d;
    }
}
