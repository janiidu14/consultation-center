package com.skinconsultationcenter.w1867160;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient extends Person implements Serializable {
    private String patientId;

    public Patient(String firstName, String lastName, LocalDate birthDate, String mobileNo, String patientId) {
        super(firstName, lastName, birthDate, mobileNo);
        this.patientId = patientId;
    }

    public Patient(){}

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Override
    public String toString() {
        return super.toString() + " | " + "Patient ID: " + patientId;
    }
}
