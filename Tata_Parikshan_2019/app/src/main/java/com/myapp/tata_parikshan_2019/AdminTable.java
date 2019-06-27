package com.myapp.tata_parikshan_2019;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "admin")
public class AdminTable{

    @NonNull
    @PrimaryKey
    private int id=1;

    @ColumnInfo(name = "admin_username")
    private String username="admin";

    @ColumnInfo(name = "admin_password")
    private String password = "adminA@123";


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
