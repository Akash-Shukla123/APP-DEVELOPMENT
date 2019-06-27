package com.myapp.tata_parikshan_2019;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Scanner")
public class ScannerTable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Sid")
    private String Sid;

    @ColumnInfo(name = "Results_Count")
    private int Results_Count;

    @ColumnInfo(name = "Results")
    private String Results;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }


    public int getResults_Count() {
        return Results_Count;
    }

    public void setResults_Count(int results_Count) {
        Results_Count = results_Count;
    }

    public String getResults() {
        return Results;
    }

    public void setResults(String results) {
        Results = results;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
