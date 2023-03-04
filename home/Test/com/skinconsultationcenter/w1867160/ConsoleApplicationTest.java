package com.skinconsultationcenter.w1867160;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleApplicationTest {
    //Create object of the WestminsterSkinConsultationManager
    WestminsterSkinConsultationManager skinConsultationManager = new WestminsterSkinConsultationManager();

    @Test
    public void testConsoleAddDoctor() {
        //Set up test data
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.of(1980, 1, 1);
        String mobileNo = "0771332465";
        String medId = "D2345";
        String specialization = "Surgery";

        //Call the addDoctor method
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        List<Doctor> doctorList = skinConsultationManager.getDoctorList();

        //Use JUnit assertions to check the expected results
        assertEquals(1, doctorList.size());
        Doctor addedDoctor = doctorList.get(0);
        assertEquals(firstName, addedDoctor.getFirstName());
        assertEquals(lastName, addedDoctor.getLastName());
        assertEquals(birthDate, addedDoctor.getBirthDate());
        assertEquals(mobileNo, addedDoctor.getMobileNo());
        assertEquals(medId, addedDoctor.getMedLicenceNo());
        assertEquals(specialization, addedDoctor.getSpecialisation());
    }

    @Test
    public void testConsoleAddDoctorFailedListIsFull(){
        //Set up test data
        String firstName = "John";
        String lastName = "Doe";
        LocalDate birthDate = LocalDate.of(1980, 1, 1);
        String mobileNo = "0771332465";
        String medId = "D2345";
        String specialization = "Surgery";

        //Doctor Added 11 times but the max value of Doctors that can be added is 10
        //1
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //2
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //3
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //4
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //5
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //6
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //7
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //8
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //9
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //10
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        //11
        skinConsultationManager.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId, specialization));
        List<Doctor> doctorList = skinConsultationManager.getDoctorList();

        //Use JUnit assertions to check the expected results
        //11th Doctor not added
        assertEquals(10, doctorList.size());
    }


    @Test
    public void testConsoleDeleteDoctor() {
        //Create a ManagementSystem object and add some doctors to it
        Doctor doc1 = new Doctor("John","Smith", LocalDate.of(1998,5,8), "0771332465", "D1234", "Surgery");
        Doctor doc2 = new Doctor("Jacob","Noor", LocalDate.of(1976,3,8), "0771334465", "D1235", "Pediatrics");
        skinConsultationManager.addDoctor(doc1);
        skinConsultationManager.addDoctor(doc2);

        // Call the DeleteDoctor method
        Doctor deletedDoctor = skinConsultationManager.deleteDoctor("D1234");

        // Check that the returned value is correct
        assertEquals(doc1, deletedDoctor);

        // Check that the size of the list of doctors is correct
        assertEquals(1, skinConsultationManager.getDoctorList().size());
    }

    @Test
    public void testConsoleDeleteDoctorNotFound() {
        Doctor doc1 = new Doctor("John","Smith", LocalDate.of(1998,5,8), "0771332465", "D1234", "Surgery");
        Doctor doc2 = new Doctor("Jacob","Noor", LocalDate.of(1976,3,8), "0771334465", "D1235", "Pediatrics");
        skinConsultationManager.addDoctor(doc1);
        skinConsultationManager.addDoctor(doc2);

        //Call the deleteDoctor method
        Doctor deletedDoctor = skinConsultationManager.deleteDoctor("D9234");

        //Check if the returned value is correct
        assertNotEquals(doc1, deletedDoctor);

        //Check if the size of the list of doctors is correct
        assertEquals(2, skinConsultationManager.getDoctorList().size());

    }

    @Test
    public void testPrintDoctorListAlphabetically() {
        Doctor doc1 = new Doctor("John", "Smith", LocalDate.of(1998, 1, 1), "0771332465", "D1234", "Surgery");
        Doctor doc2 = new Doctor("Jacob", "Noor", LocalDate.of(1998, 1, 8), "0771334465", "D1235", "Pediatrics");
        skinConsultationManager.addDoctor(doc1);
        skinConsultationManager.addDoctor(doc2);

        String expectedOutput = "First Name:  Jacob | Last Name: Noor | Date of Birth: 1998-01-08 | Mobile No: 0771334465 | Medical Licence No: D1235 | Specialisation: Pediatrics | Consultation Slots: []\n" +
                "First Name:  John | Last Name: Smith | Date of Birth: 1998-01-01 | Mobile No: 0771332465 | Medical Licence No: D1234 | Specialisation: Surgery | Consultation Slots: []\n";

        //Check if the actual output and expected output are the same
        assertEquals(expectedOutput, skinConsultationManager.printDoctorListAlphabetically(skinConsultationManager.getDoctorList()));
    }

    @Test
    public void testSaveInformationToFile() {
        Doctor doc1 = new Doctor("John", "Smith", LocalDate.of(1998, 1, 1), "0771332465", "D1234", "Surgery");
        Doctor doc2 = new Doctor("Jacob", "Noor", LocalDate.of(1998, 1, 8), "0771334465", "D1235", "Pediatrics");
        skinConsultationManager.addDoctor(doc1);
        skinConsultationManager.addDoctor(doc2);

        //Specify a file location
        File file = new File("test.txt");

        //Call the deleteDoctor method
        skinConsultationManager.saveToFile(file);

        //Check if the file is created
        assertTrue(file.exists());

        //Check if the contents are added to the file
        assertTrue(file.length() > 0);
    }
}