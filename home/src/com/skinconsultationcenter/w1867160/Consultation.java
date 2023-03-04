package com.skinconsultationcenter.w1867160;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Consultation implements Serializable {
    private LocalDateTime datetime; //Validate Consult Date when Implementing (Should be after the current date)
    private int cost;
    private String notes;
    private String doctorMedId;
    private String patientId;
    private int totalConsultationHrs;
    private int consultationNo = -1; //Consultation No auto increments and starts with 0
    private File encryptedImageFile = null; //Stores the encrypted/simple image

    public Consultation(String doctorId, String patientId, LocalDateTime datetime, int totalConsultationHrs, int cost, String notes) {
        this.datetime = datetime;
        this.cost = cost;
        this.notes = notes;
        this.doctorMedId = doctorId;
        this.patientId = patientId;
        this.totalConsultationHrs = totalConsultationHrs;
        consultationNo++;
    }

    public Consultation(){}

    public int getConsultationNo() {
        return consultationNo;
    }

    public void setConsultationNo(int consultationNo) {
        this.consultationNo = consultationNo;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDoctorMedId() {
        return doctorMedId;
    }

    public void setDoctorMedId(String doctorMedId) {
        this.doctorMedId = doctorMedId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getTotalConsultationHours() {
        return totalConsultationHrs;
    }

    public void setTotalConsultationHours(int totalConsultationHrs) {
        this.totalConsultationHrs = totalConsultationHrs;
    }

    public File getEncryptedImageFile() {
        return encryptedImageFile;
    }

    public void setEncryptedImageFile(File encryptedImageFile) {
        this.encryptedImageFile = encryptedImageFile;
    }

    @Override
    public String toString() {
        return "Consultation No: " + consultationNo + " | " + "Doctor Medical License No: " + doctorMedId + " | " +
                "Patient Id: " + patientId + " | " + "Consultation Date Time: " + datetime + " | " +
                "Total Consultation Hrs: " + totalConsultationHrs +  " | " + "Cost: " + cost +  " | " + "Notes: " + notes;
    }
}
