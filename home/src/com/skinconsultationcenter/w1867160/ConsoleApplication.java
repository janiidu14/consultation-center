package com.skinconsultationcenter.w1867160;


import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleApplication {
    //Initialize the components and make the static for the whole class to access them
    protected static final SkinConsultationManager MANAGEMENT_SYSTEM = new WestminsterSkinConsultationManager();
    protected static final Scanner INPUT = new Scanner(System.in);

    //Location of the File where information is to be saved and read from
    protected static final File SYSTEM_FILE = new File("ConsultationCenter.txt");
    protected static final String DOC_ID_PATTERN = "^D[0-9]{4}$"; //D as the first character and digits from 0-9 as the next 4 characters
    protected static final String PATIENT_ID_PATTERN = "^P[0-9]{4}$"; //P as the first character and digits from 0-9 as the next 4 characters
    protected static final String DOC_ID_PATTERN_SAMPLE = "Dxxxx";
    protected static final String PATIENT_ID_PATTERN_SAMPLE = "Pxxxx";
    protected static final String MOBILE_NO_PATTERN = "^0[0-9]{9}$"; //Phone number should have 0 as the first character and digits from 0-9 as the next 9 characters
    protected static final String STRING_NAME_PATTERN = "[A-Za-z]*"; //Should only contain alphabetical characters


    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println("\n////////////////////WELCOME///////////////////////////");

        //Read all the information in the file when the application starts only if the file is not empty
        //Errors are handled in the method readFromFile()
        String readFileText = MANAGEMENT_SYSTEM.readFromFile(SYSTEM_FILE);
        if (!readFileText.equals("")) {
            System.out.println(readFileText);
        } else {
            System.out.println("No Content To be Read From File");
        }

        //Introduce a variable to break from the console menu loop
        boolean quit = false;

        //Loop until the variable is set to True
        while (!quit) {
            //Display in the Console
            System.out.println("\n////////////////////CONSOLE///////////////////////////\n");
            System.out.println("Press 1: Add Doctor To List");
            System.out.println("Press 2: Delete Doctor From List");
            System.out.println("Press 3: Print Doctor List");
            System.out.println("Press 4: Save List To File");
            System.out.println("Press 5: Display GUI and Exit");
            System.out.println("Press 0: To Exit");
            System.out.println("\n//////////////////////////////////////////////////////");

            //Store the value entered by the user
            int choice = -1;
            try {
                choice = INPUT.nextInt();
            } catch (InputMismatchException e) {
                //Set input as a garbage value
                INPUT.next();
                System.out.println("Invalid Input!");
            }
            switch (choice) {
                case 1:
                    System.out.println("\nAdd Doctor\n");
                    consoleAddDoctor();
                    break;
                case 2:
                    System.out.println("\nDelete Doctor\n");
                    consoleDeleteDoctor();
                    break;
                case 3:
                    //Print Doctor list alphabetically
                    String docListText = MANAGEMENT_SYSTEM.printDoctorListAlphabetically(MANAGEMENT_SYSTEM.getDoctorList());
                    if (!docListText.equals("")) {
                        System.out.println("\nDoctors are Listed Alphabetically According to their Surnames\n");
                        System.out.println(docListText);
                    } else {
                        System.out.println("No Doctors in List");
                    }
                    break;
                case 4:
                    System.out.println("\nSave Information to File\n");
                    if (MANAGEMENT_SYSTEM.saveToFile(SYSTEM_FILE)) {
                        System.out.println("Information Saved To File");
                    } else {
                        System.out.println("Information Could Not be Saved To File");
                    }
                    break;
                case 5:
                    System.out.println("\nDisplay Graphical User Interface\n");
                    UserInterface userInterface = new UserInterface();
                    userInterface.setVisible(true);
                    break;
                case 0:
                    System.out.println("\nClosing Program\nThank You");
                    quit = true;
                    break;
                default:
                    System.out.println("Enter a Valid Integer!");
                    break;
            }
        }
    }

    public static void consoleAddDoctor() {
        //Use programmer-defined methods to get validated input from user
        String firstName;
        String lastName;
        LocalDate birthDate;
        String mobileNo;
        String medId;
        String specialization;

        //Get first and last name of Doctor
        while (true) {
            System.out.println("Enter First Name: ");
            firstName = INPUT.next();
            if (Validation.validateInput(firstName, STRING_NAME_PATTERN)) {
                System.out.println("Enter Last Name: ");
                lastName = INPUT.next();
                if (Validation.validateInput(lastName, STRING_NAME_PATTERN)) {
                    break;
                }
            }
            System.out.println("Input Invalid!");
        }

        //Get DoB of Doctor
        while (true) {
            System.out.println("\nEnter Date of Birth");
            int year;
            int month;
            int date;
            try {
                System.out.println("Enter Year: ");
                year = INPUT.nextInt();
                System.out.println("Enter Month: ");
                month = INPUT.nextInt();
                System.out.println("Enter Date: ");
                date = INPUT.nextInt();
                birthDate = Validation.validDate(new Doctor(), year, month, date);
                if (birthDate != null) {
                    break;
                } else {
                    System.out.println("Invalid Date!");
                }
            } catch (InputMismatchException e){
                INPUT.next();
                System.out.println("Invalid Input!");
            }
        }

        //Get phone no of Doctor
        while (true) {
            System.out.println("\nThe Mobile Number Should Have Only 10 Digits Starting with 0.");
            System.out.println("Enter Mobile Number: ");
            mobileNo = INPUT.next();
            if (Validation.validateInput(mobileNo, MOBILE_NO_PATTERN)) {
                break;
            }
            System.out.println("Invalid Phone Number!");
        }

        //Get Medical ID of Doctor
        //Continue the loop till a valid ID number is received as the input
        while (true) {
            System.out.printf("\nThe Medical ID Should have the Format [%s] \n", DOC_ID_PATTERN_SAMPLE);
            System.out.println("Enter Medical ID of Doctor: ");
            medId = INPUT.next();
            if (Validation.getValidId(new Doctor(), medId, DOC_ID_PATTERN)) {
                break;
            }
            System.out.println("Invalid ID or This ID Already Exits in the System!");
        }

        while (true) {
            System.out.println("\nEnter Specialization: ");
            specialization = INPUT.next();
            if (Validation.validateInput(specialization, STRING_NAME_PATTERN)) {
                break;
            } else {
                System.out.println("Invalid Input!");
            }
        }
        if (MANAGEMENT_SYSTEM.addDoctor(new Doctor(firstName, lastName, birthDate, mobileNo, medId.toUpperCase(), specialization))) {
            System.out.println("\nDoctor Added To System.");
        } else {
            System.out.println("\nCannot Add Doctor. List is Full!");
        }
    }

    private static void consoleDeleteDoctor() {
        //Check if the entered medical ID is valid
        System.out.println("Enter Medical ID of Doctor to Delete: ");
        String deleteDocId = INPUT.next();
        //Delete Doctor from list if found
        Doctor deletedDoctor = MANAGEMENT_SYSTEM.deleteDoctor(deleteDocId);
        if (deletedDoctor != null) {
            System.out.println("Information of the Doctor Removed from List: \n" + deletedDoctor);
            System.out.println("The Amount of Doctors Available in Center is " + MANAGEMENT_SYSTEM.getDoctorList().size());
        } else {
            System.out.println("Doctor with this ID could not be Found");
        }
    }
}