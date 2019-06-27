package com.myapp.tata_parikshan_2019;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "trip_details")
public class TripTable {

        @NonNull
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "TripId")
        private int id;

        @ColumnInfo(name = "trip_no")
        private String trip_no;

        @ColumnInfo(name = "TransporterId")
        private String TransporterId;

        @ColumnInfo(name = "vechile_no")
        private String vechile_no;

        @ColumnInfo(name = "trip_date_time")
        private String trip_date_time;

        @ColumnInfo(name = "destination")
        private String destination;

        @ColumnInfo(name = "material_type")
        private String material_type;

        @ColumnInfo(name = "material_description")
        private String material_description;

        @ColumnInfo(name = "loading_area")
        private String loading_area;

        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        private byte[] image;

        public String getTrip_no() {
                return trip_no;
        }

        public void setTrip_no(String trip_no) {
                this.trip_no = trip_no;
        }

        public String getVechile_no() {
                return vechile_no;
        }

        public void setVechile_no(String vechile_no) {
                this.vechile_no = vechile_no;
        }

        public String getTrip_date_time() {
                return trip_date_time;
        }

        public void setTrip_date_time(String trip_date_time) {
                this.trip_date_time = trip_date_time;
        }

        public String getMaterial_type() {
                return material_type;
        }

        public void setMaterial_type(String material_type) {
                this.material_type = material_type;
        }

        public String getMaterial_description() {
                return material_description;
        }

        public void setMaterial_description(String material_description) {
                this.material_description = material_description;
        }

        public String getLoading_area() {
                return loading_area;
        }

        public void setLoading_area(String loading_area) {
                this.loading_area = loading_area;
        }

        public String getDestination() {
                return destination;
        }

        public void setDestination(String destination) {
                this.destination = destination;
        }

        public String getTransporterId() {
                return TransporterId;
        }

        public void setTransporterId(String TransporterId) {
                this.TransporterId = TransporterId;
        }

        public byte[] getImage() {
                return image;
        }

        public void setImage(byte[] image) {
                this.image = image;
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }
}