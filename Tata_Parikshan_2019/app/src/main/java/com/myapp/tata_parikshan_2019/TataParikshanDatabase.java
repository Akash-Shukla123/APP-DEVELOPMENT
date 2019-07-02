package com.myapp.tata_parikshan_2019;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AdminTable.class,TripTable.
        class,VechileTable.class,TransporterTable.class,ScannerTable.class,TotalResults.class},version = 15, exportSchema = false)
public abstract class TataParikshanDatabase extends RoomDatabase {

    public abstract MyDao myDao();

}
