package com.skinconsultationcenter.w1867160;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

//Class implements Comparable to compare the Doctors and Serializable to write information of Doctors to a file
public class Doctor extends Person implements Comparable<Doctor>, Serializable {
    private String medLicenceNo;
    private String specialisation;
    private ArrayList<LocalDateTime> listDoctorConsultationSlots;

    public Doctor(String firstName, String lastName, LocalDate birthDate, String mobileNo, String medLicenceNo, String specialisation) {
        super(firstName, lastName, birthDate, mobileNo);
        this.medLicenceNo = medLicenceNo;
        this.specialisation = specialisation;
        this.listDoctorConsultationSlots = new ArrayList<>();
    }

    public Doctor(){}

    public String getMedLicenceNo() {
        return medLicenceNo;
    }

    public void setMedLicenceNo(String medLicenceNo) {
        this.medLicenceNo = medLicenceNo;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public ArrayList<LocalDateTime> getListDoctorConsultationSlots() {
        return listDoctorConsultationSlots;
    }

    public void setListDoctorConsultationSlots(ArrayList<LocalDateTime> listDoctorConsultationSlots) {
        this.listDoctorConsultationSlots = listDoctorConsultationSlots;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + "Medical Licence No: " + medLicenceNo + " | " + "Specialisation: " + specialisation +  " | " + "Consultation Slots: " + listDoctorConsultationSlots;
    }

    //Compare Doctors from last name to sort Alphabetically
    @Override
    public int compareTo(Doctor doctor) {
        //Compare by lowercase characters to ignore case sensitivity
        return this.getLastName().toLowerCase().compareTo(doctor.getLastName().toLowerCase());
    }
}
