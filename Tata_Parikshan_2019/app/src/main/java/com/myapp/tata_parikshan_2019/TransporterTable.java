package com.myapp.tata_parikshan_2019;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transporters")
public class TransporterTable {

    @NonNull
    @PrimaryKey
    private String transporter_id;

    @ColumnInfo(name = "transporter_name")
    private String transporter_name;

    @ColumnInfo(name = "transporter_phone_number")
    private String transporter_phone_number;

    @ColumnInfo(name = "transporter_id_proof_type")
    private String transporter_id_proof_type;

    @ColumnInfo(name = "transporter_id_proof_number")
    private String transporter_id_proof_number;

    @ColumnInfo(name = "transporter_driving_licence")
    private String transporter_driving_licence;

    @ColumnInfo(name = "transporter_postalcode")
    private String transporter_postalcode;

    @ColumnInfo(name = "transporter_address")
    private String transporter_address;

    public String getTransporter_id() {
        return transporter_id;
    }

    public void setTransporter_id(String transporter_id) {
        this.transporter_id = transporter_id;
    }

    public String getTransporter_name() {
        return transporter_name;
    }

    public void setTransporter_name(String transporter_name) {
        this.transporter_name = transporter_name;
    }

    public String getTransporter_phone_number() {
        return transporter_phone_number;
    }

    public void setTransporter_phone_number(String transporter_phone_number) {
        this.transporter_phone_number = transporter_phone_number;
    }

    public String getTransporter_id_proof_type() {
        return transporter_id_proof_type;
    }

    public void setTransporter_id_proof_type(String transporter_id_proof_type) {
        this.transporter_id_proof_type = transporter_id_proof_type;
    }

    public String getTransporter_id_proof_number() {
        return transporter_id_proof_number;
    }

    public void setTransporter_id_proof_number(String transporter_id_proof_number) {
        this.transporter_id_proof_number = transporter_id_proof_number;
    }

    public String getTransporter_address() {
        return transporter_address;
    }

    public void setTransporter_address(String transporter_address) {
        this.transporter_address = transporter_address;
    }

    public String getTransporter_postalcode() {
        return transporter_postalcode;
    }

    public void setTransporter_postalcode(String transporter_postalcode) {
        this.transporter_postalcode = transporter_postalcode;
    }

    public String getTransporter_driving_licence() {
        return transporter_driving_licence;
    }

    public void setTransporter_driving_licence(String transporter_driving_licence) {
        this.transporter_driving_licence = transporter_driving_licence;
    }
}
