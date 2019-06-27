package com.myapp.tata_parikshan_2019;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "total_results")
public class TotalResults {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "total_scan")
    private int total_scan;

    public int getTotal_scan() {
        return total_scan;
    }

    public void setTotal_scan(int total_scan) {
        this.total_scan = total_scan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
