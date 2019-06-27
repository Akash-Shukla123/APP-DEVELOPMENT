package com.myapp.tata_parikshan_2019;


import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addTrip(TripTable tripTable);
    @Insert
    public void addVechile(VechileTable vechileTable);
    @Insert
    public void addTransporter(TransporterTable transporterTable);
    @Insert
    public void addScanner(ScannerTable scannerTable);
    @Insert
    public void addTotal(TotalResults totalResults);

    @Query("select * from admin")
    List<AdminTable> getAdmin();
    @Query("select * from TRIP_DETAILS")
    List<TripTable> getTrip();
    @Query("select * from VECHILE_DETAILS")
    List<VechileTable> getVechile();
    @Query("select * from TRANSPORTERS")
    List<TransporterTable> getTransportes();
    @Query("select * from scanner")
    List<ScannerTable> getScanner();
    @Query("select * from total_results")
    List<TotalResults> getTotal();
    @Query("SELECT * FROM transporters WHERE transporter_id IN(:tID)")
    public abstract List<TransporterTable> findByIds(String tID);
    @Query("SELECT * FROM trip_details WHERE TripId IN(:tripId)")
    public abstract List<TripTable> findByTrip(int tripId);
    @Query("SELECT * FROM vechile_details WHERE vechile_id IN(:vId)")
    public abstract List<VechileTable> findByVechID(String vId);
    @Query("SELECT * FROM trip_details ORDER BY TripId DESC LIMIT 1")
    List<TripTable> getLastTripNo();

    @Update
    public void updateAdmin(AdminTable adminTable);
    @Update
    public void updateTrip(TripTable tripTable);
    @Update
    public void updateVechile(VechileTable vechileTable);
    @Update
    public void  updateTransporter(TransporterTable transporterTable);
    @Update
    public void updateScanner(ScannerTable scannerTable);
    @Update
    public void updateTotalResults(TotalResults totalResults);

    @Delete
    public void deleteTrip(TripTable tripTable);
    @Delete
    public void deleteVechile(VechileTable vechileTable);
    @Delete
    public void  deleteTransporter(TransporterTable transporterTable);
    @Delete
    public void deleteScanner(ScannerTable scannerTable);
    @Delete
    public void deleteTotalResults(TotalResults totalResults);

}
