package com.skinconsultationcenter.w1867160;

import java.time.DateTimeException;
import java.time.LocalDate;

import static com.skinconsultationcenter.w1867160.ConsoleApplication.MANAGEMENT_SYSTEM;

public class Validation {
    protected static final LocalDate CURRENT_DATE = LocalDate.now();
    protected static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1900,1,1);
    protected static final LocalDate MAX_CONSULT_DATE = LocalDate.of(2023, 12, 31);

    public Validation(){}

    //Takes the parameter of which string to validate (e.g. First Name, Last Name)
    public static boolean validateInput(String input, String pattern){
        //Return true if the input is valid or false if it is invalid
        return input.matches(pattern) && !input.equals("");
    }

    //Returns the date if it is valid according to the instance of the object or else null if the date is invalid
    public static LocalDate validDate(Object object, int year, int month, int date){
        try {
            LocalDate validDate = LocalDate.of(year, month, date);
            //Birthdate should be before the Current Date and after the specified min Birth Date
            //compareTo() method returns a negative value if less than the compared object
            //This if statement returns a value not between the min birth year and the current date
            if (object instanceof Person ? validDate.compareTo(CURRENT_DATE) < 0 && validDate.compareTo(MIN_BIRTH_DATE) >= 0 :
                    validDate.compareTo(CURRENT_DATE) > 0 && validDate.compareTo(MAX_CONSULT_DATE) <= 0) {
                //Return the valid if only all conditions are validated
                return validDate;
            }
        } catch (DateTimeException t) {
            return null;
        }
        return null;
    }

    public static boolean getValidId(Person person, String idNo, String pattern){
        //Check if the entered medical id matches the pattern
        if (!idNo.toUpperCase().matches(pattern)) {
            return false;
        } else {
            if (person instanceof Doctor) {
                //Check whether Doctor medical ID already exists. It should be unique
                for (Doctor doctor : MANAGEMENT_SYSTEM.getDoctorList()) {
                    if (idNo.equalsIgnoreCase(doctor.getMedLicenceNo())) {
                        return false;
                    }
                }
                //If program does not return false, ID is valid
                return true;
            } else {
                //Check whether patient ID already exists. It should be unique
                for (Patient patient : MANAGEMENT_SYSTEM.getPatientList()) {
                    if (idNo.equalsIgnoreCase(patient.getPatientId())) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}

