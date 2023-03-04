package com.skinconsultationcenter.w1867160;

import java.io.File;
import java.util.List;

public interface SkinConsultationManager {
    public abstract boolean addDoctor(Doctor doctor);
    public abstract Doctor deleteDoctor(String medLicenceNo);
    public abstract String printDoctorListAlphabetically(List<Doctor> doctorListList);
    public abstract boolean saveToFile(File file);
    public abstract String readFromFile(File file);
    public abstract void addConsultation(Consultation consultation);
    public abstract void removeConsultation(int consultationId);
    public abstract List<Consultation> getConsultationList();
    public abstract void addPatient(Patient patient);
    public abstract List<Doctor> getDoctorList();
    public abstract List<Patient> getPatientList();
    public abstract boolean deletePatient(String patientId);
}
