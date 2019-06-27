package com.myapp.tata_parikshan_2019;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vechile_details")
public class VechileTable {

    @NonNull
    @PrimaryKey
    private String vechile_id;

    @ColumnInfo(name = "vechile_no")
    public String vechile_no;

    @ColumnInfo(name = "vechile_type")
    public String vechile_type;

    public String getVechile_id() {
        return vechile_id;
    }

    public void setVechile_id(String vechile_id) {
        this.vechile_id = vechile_id;
    }

    public String getVechile_no() {
        return vechile_no;
    }

    public void setVechile_no(String vechile_no) {
        this.vechile_no = vechile_no;
    }

    public String getVechile_type() {
        return vechile_type;
    }

    public void setVechile_type(String vechile_type) {
        this.vechile_type = vechile_type;
    }
}
