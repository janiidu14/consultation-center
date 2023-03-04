package com.skinconsultationcenter.w1867160;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.skinconsultationcenter.w1867160.ConsoleApplication.MANAGEMENT_SYSTEM;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{
    private static int maxDoctors = 10;
    private static int consultationNo = 0;
    protected static List<Doctor> doctorList;
    protected static List<Patient> patientList;
    protected static List<Consultation> consultationList;

    public WestminsterSkinConsultationManager(){
        maxDoctors = 10;
        doctorList = new ArrayList<>();
        patientList = new ArrayList<>();
        consultationList = new ArrayList<>();
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        if (doctorList.size() < maxDoctors){
            doctorList.add(doctor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Doctor deleteDoctor(String medLicenceNo) {
        for (Doctor doctor : doctorList){
            if (medLicenceNo.equalsIgnoreCase(doctor.getMedLicenceNo())){
                doctorList.remove(doctor);
                //This return avoids corrupting the for loop because the ArrayList cannot be altered while an operation is being performed
                //The program to exits the method soon as a Doctor is removed and the ArrayList is altered
                return doctor;
            }
        }
        return null;
    }

    @Override
    public List<Doctor> getDoctorList(){
        return doctorList;
    }

    @Override
    public String printDoctorListAlphabetically(List<Doctor> doctorList) {
        String doctorListText = "";
        Collections.sort(doctorList);
        for (Doctor doctor : doctorList){
            doctorListText +=  (doctor.toString() + "\n");
        }
        return doctorListText;
    }

    @Override
    public boolean saveToFile(File file) {
        //Append is false because the file will be corrupted
        try (FileOutputStream fileWriter = new FileOutputStream(file); ObjectOutputStream objectWriter = new ObjectOutputStream(fileWriter)){
            for (Doctor doctor : MANAGEMENT_SYSTEM.getDoctorList()){
                objectWriter.writeObject(doctor);
            }
            for (Consultation consultation : MANAGEMENT_SYSTEM.getConsultationList()) {
                objectWriter.writeObject(consultation);
            }
            for (Patient patient : MANAGEMENT_SYSTEM.getPatientList()) {
                objectWriter.writeObject(patient);
            }
            return true;
        } catch (IOException ioe) {
            //Inform the User about the Exception
            return false;
        }
    }

    @Override
    public String readFromFile(File file){
        String readText = "";
        //Use Resource-Try-Block to implement auto-closing of the file
        try (FileInputStream fileReader = new FileInputStream(file); ObjectInputStream objectReader = new ObjectInputStream(fileReader)){
            //Empty Array
            doctorList = new ArrayList<>();
            patientList = new ArrayList<>();
            consultationList = new ArrayList<>();
            while (true) {
                try {
                    Object object = objectReader.readObject();
                    if (object instanceof Doctor doctor) {
                        addDoctor(doctor);
                    } else if (object instanceof Patient patient){
                        addPatient(patient);
                    } else if (object instanceof Consultation consultation){
                        addConsultation(consultation);
                    }
                } catch (EOFException eof) {
                    for (int i = 0; i < doctorList.size(); i++){
                        readText += (i == 0 ? "\nDoctor List\n" + doctorList.get(i) + "\n" : doctorList.get(i) + "\n");
                    }

                    for (int i = 0; i < patientList.size(); i++){
                        readText += ( i == 0 ? "\nPatient List\n" + patientList.get(i) + "\n" : patientList.get(i) + "\n");
                    }

                    for (int i = 0; i < consultationList.size(); i++){
                        readText += (i == 0 ? "\nConsultation List\n" + consultationList.get(i) + "\n" : consultationList.get(i) + "\n");
                        consultationList.get(i).setConsultationNo(i);
                    }
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ioe) { //Handle the IOException which is also the parent element of FileNotFoundException
            readText = "";
        }
        return readText;
    }

    @Override
    public void addConsultation(Consultation consultation) {
        consultation.setConsultationNo(consultationNo);
        consultationList.add(consultation);
        consultationNo++;
    }

    @Override
    public void removeConsultation(int consultationId) {
        for (Consultation consultation: consultationList){
            if (consultationId == consultation.getConsultationNo()){
                consultationList.remove(consultation);
                return;
            }
        }
    }

    @Override
    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    @Override
    public void addPatient(Patient patient) {
        patientList.add(patient);
    }

    @Override
    public boolean deletePatient(String patientId) {
        for (Patient patient: patientList){
            if (patient.getPatientId().equals(patientId)) {
                patientList.remove(patient);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Patient> getPatientList() {
        return patientList;
    }

}
