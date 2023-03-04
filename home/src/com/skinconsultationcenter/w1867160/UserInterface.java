package com.skinconsultationcenter.w1867160;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.skinconsultationcenter.w1867160.ConsoleApplication.*;

public class UserInterface extends JFrame {

    //Assign variables to TextFields to input patient details
    private static JTextField textFName;
    private static JTextField textLName;
    private static JTextField textMobileNo;
    private static JTextField textPatientId;
    private static JTextField textBirthDate;
    private static JTextField textBirthMonth;
    private static JTextField textBirthYear;

    //Assign variables to hold consultation details
    private static String selectedDocMedId;
    private static String selectedPatientId;
    private static LocalTime selectedConsultTime;
    private static Integer selectedConsultTotalTime;
    private static File selectedFile;

    //Assign variables to TextFields to input consultation details
    private static JTextField textSelectedConsultNo;
    private static JTextField textConsultDate;
    private static JTextField textConsultMonth;
    private static JTextField textConsultYear;
    private static JTextArea textConsultNotes;
    private static JTextArea textEncryptNotes;
    private static JComboBox<String> comboBoxDocMedId;
    private static JComboBox<String> comboBoxPatientId;
    private static JComboBox<LocalTime> comboBoxOpenHours;
    private static JComboBox<Integer> comboBoxTotalHours;

    //Assign variables the buttons that perform actions
    private static JButton buttonDeleteConsultation;
    private static JButton buttonShowConsultDetails;

    //Define the constraints to be used in the GridBagLayout
    private static final GridBagConstraints CONSTRAINTS = new GridBagConstraints();

    //Get the screen size of the device being used
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final JFrame SYSTEM_FRAME = new JFrame("Westminster Skin Consultation System");

    //Initialize Secret Key
    static Encryption encryption;

    static {
        try {
            encryption = new Encryption();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public UserInterface() throws NoSuchAlgorithmException {
        //Display the Frame Containing Additional Details
        FrameInformation frameInformation = new FrameInformation();
        frameInformation.setVisible(true);

        //Set The Dimensions of the JFrame
        setSize(SCREEN_SIZE.width, 800);
        setLayout(new GridLayout(3, 1));

        //Set Close Operation
        addWindowListener(new SystemWindowListener());

        //Set the Dimensions of the Elements in the JPanels
        CONSTRAINTS.anchor = GridBagConstraints.CENTER;
        CONSTRAINTS.insets = new Insets(5, 10, 0, 10);

        //Panel 1 - Consists of the Panel with the Table with the Doctors and Panel with the sorting Button
        JPanel panelDoctorList = new JPanel();
        panelDoctorList.setLayout(new BorderLayout());

        //Create a Table to Display the Doctor List
        JTable tableDoctorList = new JTable();
        //Set the Customized Table Model as the Table Model and Pass the Doctor List as the Parameter
        Collections.shuffle(MANAGEMENT_SYSTEM.getDoctorList());
        tableDoctorList.setModel(new DoctorTableModel(MANAGEMENT_SYSTEM.getDoctorList()));
        //Add Table to Scroll Pane (To make the table scrollable)
        JScrollPane scrollPaneDoctorList = new JScrollPane(tableDoctorList);

        //A new Panel to display the sorting Button with the Default Layout - FlowLayout.CENTER
        JPanel panelSortBtn = new JPanel();
        JButton buttonSort = new JButton("Sort");
        //Add Action Listener to Button for sorting the table by the last name of the Doctor
        buttonSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Collections.sort(MANAGEMENT_SYSTEM.getDoctorList());
                tableDoctorList.setModel(new DoctorTableModel(MANAGEMENT_SYSTEM.getDoctorList()));
            }
        });

        //Add the panels to the system frame
        panelSortBtn.add(buttonSort);
        panelDoctorList.add(scrollPaneDoctorList, BorderLayout.CENTER);
        panelDoctorList.add(panelSortBtn, BorderLayout.SOUTH);

        //Set the border of the panel with the doctor list along with a title
        panelDoctorList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Doctor List"));

        //Panel 2 - Get the input of the details of the Patient
        //Layout is set to GridBag which is used to align components vertically, horizontally or along the baseline
        JPanel panelPatientDetails = new JPanel(new GridBagLayout());

        JLabel labelFName = new JLabel("Enter First Name: ");
        //Set Grid Width back to Default = 1
        //Invoke the user-defined method setElementPosition() to set the position of the element
        setElementPosition(panelPatientDetails, labelFName, CONSTRAINTS, 0, 0);
        //Set the size of the TextField
        textFName = new JTextField(30);
        setElementPosition(panelPatientDetails, textFName, CONSTRAINTS, 1, 0);

        JLabel labelLName = new JLabel("Enter Last Name: ");
        setElementPosition(panelPatientDetails, labelLName, CONSTRAINTS, 0, 1);
        textLName = new JTextField(30);
        setElementPosition(panelPatientDetails, textLName, CONSTRAINTS, 1, 1);

        JLabel labelBirthDate = new JLabel("Enter Date of Birth: ");
        setElementPosition(panelPatientDetails, labelBirthDate, CONSTRAINTS, 0, 2);
        //Horizontal TextFields to input the DOB of Patient
        textBirthDate = new JTextField(2);
        textBirthMonth = new JTextField(2);
        textBirthYear = new JTextField(4);
        //Create a new Panel to get the input for DOB and display the TextFields with the user-defined method setDatePanel()
        JPanel birthDatePanel = setDatePanel(textBirthYear, textBirthMonth, textBirthDate);
        //Add this panel to the parent panel
        setElementPosition(panelPatientDetails, birthDatePanel, CONSTRAINTS, 1, 2);

        JLabel labelMobileNo = new JLabel("Enter Mobile No: ");
        setElementPosition(panelPatientDetails, labelMobileNo, CONSTRAINTS, 0, 3);
        textMobileNo = new JTextField(30);
        setElementPosition(panelPatientDetails, textMobileNo, CONSTRAINTS, 1, 3);

        JLabel labelPatientId = new JLabel("Enter Id No: ");
        setElementPosition(panelPatientDetails, labelPatientId, CONSTRAINTS, 0, 4);
        textPatientId = new JTextField(30);
        setElementPosition(panelPatientDetails, textPatientId, CONSTRAINTS, 1, 4);

        //Button to save all the input details of the Patient
        JButton buttonSave = new JButton("Save Details");
        //Add Action Listener to the Save Button which validates the input and save the information to the system
        buttonSave.addActionListener(new SavePatientDetailsListener());
        //Add the button to the parent panel
        setElementPosition(panelPatientDetails, buttonSave, CONSTRAINTS, 1, 5);

        //Set the border of the panel with the patient details along with a title
        panelPatientDetails.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Patient Details"));

        //Panel 3 - Get the input of the details of the Consultations and View or Delete the Consultations
        //Create a parent panel and set layout
        JPanel panelConsultation = new JPanel(new BorderLayout());

        //Create a panel to input the details of the consultation and set the layout
        JPanel panelBooking = new JPanel(new GridBagLayout());

        //Add elements to the panel
        JLabel labelDocMedId = new JLabel("Select ID of the Doctor and Patient: ");
        setElementPosition(panelBooking, labelDocMedId, CONSTRAINTS, 0, 0);

        JPanel panelID = setIDPanel();
        setElementPosition(panelBooking, panelID, CONSTRAINTS, 1, 0);

        JLabel labelConsultDate = new JLabel("Enter Date/Time of Appointment: ");
        setElementPosition(panelBooking, labelConsultDate, CONSTRAINTS, 0, 1);

        textConsultYear = new JTextField(4);
        textConsultMonth = new JTextField(2);
        textConsultDate = new JTextField(2);

        //Create a new Panel to get the input for date and time of consultation and display the TextFields with the user-defined method setDateTimePanel()
        JPanel panelConsultDate = setDateTimePanel(textConsultYear, textConsultMonth, textConsultDate);
        setElementPosition(panelBooking, panelConsultDate, CONSTRAINTS, 1, 1);

        JLabel labelConsultNotes = new JLabel("Enter Notes/ Upload Images: ");
        setElementPosition(panelBooking, labelConsultNotes, CONSTRAINTS, 0, 2);
        textConsultNotes = new JTextArea(2, 30);
        setElementPosition(panelBooking, textConsultNotes, CONSTRAINTS, 1, 2);
        textConsultNotes.setLineWrap(true);
        textConsultNotes.setBorder(BorderFactory.createEtchedBorder());

        JButton buttonUpload = new JButton("Upload Images");
        setElementPosition(panelBooking, buttonUpload, CONSTRAINTS, 2, 2);
        buttonUpload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                //Disable multiple file selection
                fileChooser.setMultiSelectionEnabled(false);

                //Restrict the user to select files of all types
                fileChooser.setAcceptAllFileFilterUsed(false);

                //Set a title for the dialog
                fileChooser.setDialogTitle("Select an Image");

                //Only allow files of .png extension
                FileNameExtensionFilter restrict = new FileNameExtensionFilter(".jpeg", "jpeg");
                fileChooser.addChoosableFileFilter(restrict);

                //Invoke the showsOpenDialog function to show the save dialog
                fileChooser.showOpenDialog(null);

                //Get the Selected files
                selectedFile = fileChooser.getSelectedFile();
                FileInputStream fileInputStream;

                try {
                    //Open file in InputStream
                    fileInputStream = new FileInputStream(selectedFile);
                    Encryption.encryptImageFile(fileInputStream, 123, selectedFile);
                    JOptionPane.showMessageDialog(SYSTEM_FRAME, "Image Encrypted", "Encryption", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | NullPointerException exception) {
                    JOptionPane.showMessageDialog(SYSTEM_FRAME, "Image Not Encrypted", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JLabel labelEncryptNotes = new JLabel("Encrypted Notes");
        setElementPosition(panelBooking, labelEncryptNotes, CONSTRAINTS, 0, 3);
        textEncryptNotes = new JTextArea(2, 30);
        setElementPosition(panelBooking, textEncryptNotes, CONSTRAINTS, 1, 3);
        textEncryptNotes.setLineWrap(true);
        textEncryptNotes.setBorder(BorderFactory.createEtchedBorder());

        JButton EncryptedButton = new JButton("Encrypt Notes");
        EncryptedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (!textConsultNotes.getText().equals("")) {
                    String simpleText;
                    String encryptedText;
                    try {
                        simpleText = textConsultNotes.getText();
                        encryptedText = encryption.encrypt(simpleText);
                        textEncryptNotes.setText(encryptedText);
                    } catch (Exception e) {
                        System.out.println(e);
                        JOptionPane.showMessageDialog(SYSTEM_FRAME, "Notes Cannot be Encrypted", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        EncryptedButton.setBounds(445, 244, 127, 23);
        setElementPosition(panelBooking, EncryptedButton, CONSTRAINTS, 2, 3);

        JButton buttonAppointment = new JButton("Book Appointment");
        //Add Action Listener to validate and save the details of the consultation
        BookConsultationListener bookConsultListener = new BookConsultationListener();
        buttonAppointment.addActionListener(bookConsultListener);
        setElementPosition(panelBooking, buttonAppointment, CONSTRAINTS, 1, 4);

        //Panel to view or delete Consultations with Default Layout
        JPanel panelViewDeleteConsultations = new JPanel();

        //Add elements to panel
        JLabel labelConsultNo = new JLabel(" Consultation No ");
        panelViewDeleteConsultations.add(labelConsultNo);
        textSelectedConsultNo = new JTextField(5);
        panelViewDeleteConsultations.add(textSelectedConsultNo);

        ConsultationDetailsListener consultDetailsListener = new ConsultationDetailsListener();

        buttonShowConsultDetails = new JButton("View Consultation");
        //Add Action Listener to show the details of the consultation chosen by the user after validating the patient Id
        buttonShowConsultDetails.addActionListener(consultDetailsListener);
        panelViewDeleteConsultations.add(buttonShowConsultDetails);

        buttonDeleteConsultation = new JButton("Delete Consultation");
        buttonDeleteConsultation.addActionListener(consultDetailsListener);
        panelViewDeleteConsultations.add(buttonDeleteConsultation);

        JButton buttonViewDetails = new JButton("View Consultations/Patients");
        buttonViewDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Display Frame with Consultation details
                FrameViewTables frameViewTables = new FrameViewTables();
                frameViewTables.setVisible(true);
            }
        });
        panelViewDeleteConsultations.add(buttonViewDetails);

        //Add child panel to the Consultation panel (Parent)
        panelConsultation.add(panelBooking, BorderLayout.CENTER);
        panelConsultation.add(panelViewDeleteConsultations, BorderLayout.SOUTH);

        //Set the border of the panel with the consultation details along with a title
        panelConsultation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Consultation Panel"));

        //Add All the parent panels to the system frame
        getContentPane().add(panelDoctorList);
        getContentPane().add(panelPatientDetails);
        getContentPane().add(panelConsultation);
    }

    //Action Listener which validates the input and save the information to the system
    public static class SavePatientDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Validate First and Last Name
            if (Validation.validateInput(textFName.getText(), STRING_NAME_PATTERN) &&
                    Validation.validateInput(textLName.getText(), STRING_NAME_PATTERN)) {
                //Assign variables to hold patient details
                String patientFName = textFName.getText();
                String patientLName = textLName.getText();
                if (Validation.validateInput(textMobileNo.getText(), MOBILE_NO_PATTERN)) {
                    String patientMobileNo = textMobileNo.getText();
                    //Patient Id hve to be unique
                    if (Validation.getValidId(new Patient(), textPatientId.getText().toUpperCase(), PATIENT_ID_PATTERN)) {
                        boolean validId = true;
                        for (int i = 0; i < MANAGEMENT_SYSTEM.getPatientList().size(); i++) {
                            if (MANAGEMENT_SYSTEM.getPatientList().get(i).getPatientId().equals(textPatientId.getText())) {
                                JOptionPane.showMessageDialog(SYSTEM_FRAME, "The Patient Id Entered Already Exists", "Error", JOptionPane.WARNING_MESSAGE);
                                textPatientId.setText("");
                                validId = false;
                                break;
                            }
                        }
                        if (validId) {
                            String patientId = textPatientId.getText().toUpperCase();
                            LocalDate patientDOB = getValidDate(new Patient());
                            if (patientDOB != null) {
                                MANAGEMENT_SYSTEM.addPatient(new Patient(patientFName, patientLName, patientDOB, patientMobileNo, patientId));
                                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Patient Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                                comboBoxPatientId.addItem(patientId);
                                resetPatientPanel();
                            } else {
                                resetDate(new Patient());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(SYSTEM_FRAME, "The Patient Id Entered is Invalid", "Error", JOptionPane.WARNING_MESSAGE);
                        textPatientId.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(SYSTEM_FRAME, "The Mobile No Entered is Invalid", "Error", JOptionPane.WARNING_MESSAGE);
                    textMobileNo.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Enter a Valid Name", "Error", JOptionPane.WARNING_MESSAGE);
                textFName.setText("");
                textLName.setText("");
            }
        }
    }

    //Action Listener which assigns a doctor to a consultation with the date and time specified by the user
    public static class BookConsultationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedPatientId != null) {
                boolean available = true;
                String consultNotes = textEncryptNotes.getText();
                ArrayList<LocalDateTime> selectedLocalDateTimeList = new ArrayList<>();

                //If the User has selected a Doctor
                if (selectedDocMedId != null) {
                    //Get valid date for consultation
                    LocalDate consultDate = getValidDate(new Consultation());
                    if (consultDate != null && selectedConsultTime != null && selectedConsultTotalTime != null) {
                        //Set the consultation Date and Time
                        LocalDateTime consultDateTime = LocalDateTime.of(consultDate, selectedConsultTime);
                        for (int i = 0; i < selectedConsultTotalTime; i++) {
                            selectedLocalDateTimeList.add(consultDateTime.plusHours(i));
                        }
                        for (Doctor doctor : MANAGEMENT_SYSTEM.getDoctorList()) {
                            //Check if the Doctor exits in the list
                            if (doctor.getMedLicenceNo().equals(selectedDocMedId)) {
                                //Loop through the slots taken up by the specific Doctor
                                for (LocalDateTime ldt : doctor.getListDoctorConsultationSlots()) {
                                    //Loop through the slots chosen by the user
                                    for (LocalDateTime selectedLDT : selectedLocalDateTimeList) {
                                        //If at least one slot chosen by the user is occupied by the doctor,
                                        //break from the loop and set availability to false
                                        if (ldt.equals(selectedLDT)) {
                                            available = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        //If the doctor is not available and is not the only one in the list, assign another doctor
                        if (!available && MANAGEMENT_SYSTEM.getDoctorList().size() != 1) {
                            selectedDocMedId = assignRandomDoctor(selectedLocalDateTimeList);
                            if (selectedDocMedId != null) {
                                available = true;
                            }
                        }

                        //If the doctor is found and is available
                        if (available && selectedDocMedId != null) {
                            for (Doctor doctor : MANAGEMENT_SYSTEM.getDoctorList()) {
                                if (doctor.getMedLicenceNo().equals(selectedDocMedId)) {
                                    for (int i = 0; i < selectedConsultTotalTime; i++) {
                                        doctor.getListDoctorConsultationSlots().add(consultDateTime.plusHours(i));
                                    }
                                }
                            }
                            Consultation consultation = new Consultation(selectedDocMedId, selectedPatientId, consultDateTime, selectedConsultTotalTime, getConsultationCost(selectedConsultTotalTime), consultNotes);
                            consultation.setEncryptedImageFile(selectedFile);
                            JOptionPane.showMessageDialog(SYSTEM_FRAME, "Booking is Confirmed", "Success", JOptionPane.INFORMATION_MESSAGE);

                            MANAGEMENT_SYSTEM.addConsultation(consultation);
                            FrameConsultDetails consultDetails = new FrameConsultDetails(consultation.getConsultationNo());
                            consultDetails.setVisible(true);
                        } else if (selectedDocMedId == null){
                            //If all the doctors are occupied at this time slot
                            JOptionPane.showMessageDialog(SYSTEM_FRAME, "All Doctors In List are Occupied at this Time", "Message", JOptionPane.WARNING_MESSAGE);
                        }
                        resetConsultPanel();
                    } else {
                        JOptionPane.showMessageDialog(SYSTEM_FRAME, "Select a Valid Date/Time for Consultation", "Message", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(SYSTEM_FRAME, "Select a Doctor", "Message", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Please Enter Patient ID", "Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    /*
    This method assigns a random doctor to a consultation if the doctor chosen by the
    user is already occupied at the date and time specified by the user
    Parameters - Takes in the date and time and the medical licence no of the doctor which the user has inputted
    Return - Returns the medical license no of the randomly chosen doctor
    */
    private static String assignRandomDoctor(ArrayList<LocalDateTime> selectedLocalDateTimeList) {
        String newlyAssignedDoctorId = null;

        //Shuffle the list to get a random doctor
        Collections.shuffle(MANAGEMENT_SYSTEM.getDoctorList());
        for (Doctor doctor : MANAGEMENT_SYSTEM.getDoctorList()) {
            //Select a doctor from list if the doctor is not the person selected by the user
            if (!doctor.getMedLicenceNo().equals(selectedDocMedId)) {
                //Loop through the slots taken up by the Doctor
                for (LocalDateTime selectedLDT : selectedLocalDateTimeList) {
                    //Loop through the slots chosen by the user
                    for (LocalDateTime takenLocalDateTime : doctor.getListDoctorConsultationSlots()) {
                        //If the doctor is occupied at this time, break from the loop and choose another doctor
                        if (takenLocalDateTime.equals(selectedLDT)) {
                            newlyAssignedDoctorId = null;
                            break;
                        } else {
                            //Assign the doctor if the slot is free and iterate through the loop until all the time slots
                            //of the doctor is covered
                            //Return the medical id of the doctor if all time slots are free and if the doctor id is not set to null
                            newlyAssignedDoctorId = doctor.getMedLicenceNo();
                        }
                    }
                    if (doctor.getListDoctorConsultationSlots().isEmpty()) {
                        newlyAssignedDoctorId = doctor.getMedLicenceNo();
                        break;
                    }
                }
            }
            //If a doctor with a free time slot is found, return the med Id
            if (newlyAssignedDoctorId != null) {
                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Doctor Selected is Unavailable. A new Doctor is Assigned. New Doctor Id: " + newlyAssignedDoctorId, "Message", JOptionPane.INFORMATION_MESSAGE);
                return newlyAssignedDoctorId;
            }
        }
        //If the method does not return a medical id, return null
        return null;
    }

    //Action Listener which lets the user view or delete a Consultation
    public static class ConsultationDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!textSelectedConsultNo.getText().equals("")) {
                try {
                    String consultPatientId = null;
                    int selectedConsultNo = Integer.parseInt(textSelectedConsultNo.getText());
                    for (Consultation consultation : MANAGEMENT_SYSTEM.getConsultationList()) {
                        if (selectedConsultNo == consultation.getConsultationNo()) {
                            consultPatientId = consultation.getPatientId();
                        }
                    }
                    //User should confirm their identity before deleting or viewing the Consultation
                    if (consultPatientId != null) {
                        if (encrypt(SYSTEM_FRAME, consultPatientId.toUpperCase())) {
                            if (e.getSource() == buttonShowConsultDetails) {
                                FrameConsultDetails frameConsultDetails = new FrameConsultDetails(selectedConsultNo);
                                frameConsultDetails.setVisible(true);
                            } else if (e.getSource() == buttonDeleteConsultation) {
                                MANAGEMENT_SYSTEM.removeConsultation(selectedConsultNo);
                                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Consultation No " + selectedConsultNo + " is Removed", "Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(SYSTEM_FRAME, "Incorrect Id No", "Message", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (NumberFormatException | IndexOutOfBoundsException inputErrorConsultNo) {
                    JOptionPane.showMessageDialog(SYSTEM_FRAME, "Enter a Valid Consultation No", "Message", JOptionPane.WARNING_MESSAGE);
                }
                textSelectedConsultNo.setText("");
            } else {
                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Enter the Consultation No", "Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Item Listener which stores the selected Combo Box option
    public static class ComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            //Run when the state of the combo box is changed
            try {
                if (e.getSource() == comboBoxOpenHours) {
                    selectedConsultTime = (LocalTime) comboBoxOpenHours.getSelectedItem();
                } else if (e.getSource() == comboBoxTotalHours) {
                    selectedConsultTotalTime = (Integer) comboBoxTotalHours.getSelectedItem();
                } else if (e.getSource() == comboBoxDocMedId) {
                    selectedDocMedId = (String) comboBoxDocMedId.getSelectedItem();
                } else if (e.getSource() == comboBoxPatientId) {
                    selectedPatientId = (String) comboBoxPatientId.getSelectedItem();
                }
            } catch (NullPointerException w) {
                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Select the Options", "Message", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    //Window Listener which saves the information before the frame is disposed
    public static class SystemWindowListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            MANAGEMENT_SYSTEM.saveToFile(SYSTEM_FILE);
            JOptionPane.showMessageDialog(SYSTEM_FRAME, "Information will be Saved to the File", "Thank You", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    /*
    This method add the elements to the panel relative to the x-axis and y-axis
    Parameters - Takes in the panel in which the element should be placed, the element, constraints for components, x-axis position, y-axis position
    //JComponent is the Parent of JTextField, JLabel and JButton
    Return - None
    */
    private static void setElementPosition(JPanel panel, JComponent component, GridBagConstraints constraints, int xAxis, int yAxis) {
        constraints.gridx = xAxis;
        constraints.gridy = yAxis;
        panel.add(component, constraints);
    }

    /*
    This method add the elements to the panel and sets its alignment vertically
    Parameters - Takes in the panel in which the element should be placed and the element
    Return - None
    */
    private static void setElementAlignment(JPanel panel, JComponent component) {
        component.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(component);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    /*
    This method creates the Date Panel
    Parameters - Takes in the elements which should consist in the panel
    Return - The Panel which is created
     */
    private static JPanel setDatePanel(JTextField year, JTextField month, JTextField day) {
        JPanel datePanel = new JPanel();
        JLabel labelYear = new JLabel("Year ");
        datePanel.add(labelYear);
        datePanel.add(year);
        JLabel labelMonth = new JLabel("Month ");
        datePanel.add(labelMonth);
        datePanel.add(month);
        JLabel labelDay = new JLabel("Day ");
        datePanel.add(labelDay);
        datePanel.add(day);
        return datePanel;
    }

    /*
    This method creates the Date and Time Panel by updating the Date Panel
    Parameters - Takes in the elements which should consist in the panel
    Return - The Panel which is created
     */
    private static JPanel setDateTimePanel(JTextField year, JTextField month, JTextField day) {
        //Get the date panel and update it
        JPanel dateTimePanel = setDatePanel(year, month, day);

        JLabel labelConsultHours = new JLabel("Time ");
        dateTimePanel.add(labelConsultHours);
        //Consultation is open from 8am - 5pm
        LocalTime[] listOpenHours = {LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0),
                LocalTime.of(11, 0), LocalTime.of(12, 0), LocalTime.of(13, 0),
                LocalTime.of(14, 0)};
        comboBoxOpenHours = new JComboBox<>(listOpenHours);
        ComboBoxListener comboBoxListener = new ComboBoxListener();
        comboBoxOpenHours.addItemListener(comboBoxListener);
        comboBoxOpenHours.setSelectedIndex(-1);
        dateTimePanel.add(comboBoxOpenHours);

        JLabel labelConsultTotalHours = new JLabel(" Total Hours ");
        dateTimePanel.add(labelConsultTotalHours);
        Integer[] ListTotalHours = {1, 2, 3};
        comboBoxTotalHours = new JComboBox<>(ListTotalHours);
        comboBoxTotalHours.setSelectedIndex(-1);
        dateTimePanel.add(comboBoxTotalHours);
        comboBoxTotalHours.addItemListener(comboBoxListener);

        return dateTimePanel;
    }

    /*
    This method creates the ID Panel for Doctor and Patient
    Parameters - None
    Return - The Panel which is created consisting of the combo boxes
     */
    private static JPanel setIDPanel() {
        JPanel panelId = new JPanel();

        JLabel labelDoctorId = new JLabel("Doctor ID ");
        panelId.add(labelDoctorId);

        String[] ListDocMedId = new String[MANAGEMENT_SYSTEM.getDoctorList().size()];
        for (int i = 0; i < MANAGEMENT_SYSTEM.getDoctorList().size(); i++) {
            ListDocMedId[i] = MANAGEMENT_SYSTEM.getDoctorList().get(i).getMedLicenceNo().toUpperCase();
        }
        comboBoxDocMedId = new JComboBox<>(ListDocMedId);
        comboBoxDocMedId.setSelectedIndex(-1);
        comboBoxDocMedId.addItemListener(new ComboBoxListener());
        panelId.add(comboBoxDocMedId);

        JLabel labelPatientID = new JLabel("Patient ID ");
        panelId.add(labelPatientID);

        String[] ListPatientId = new String[MANAGEMENT_SYSTEM.getPatientList().size()];
        for (int i = 0; i < MANAGEMENT_SYSTEM.getPatientList().size(); i++) {
            ListPatientId[i] = MANAGEMENT_SYSTEM.getPatientList().get(i).getPatientId().toUpperCase();
        }
        comboBoxPatientId = new JComboBox<>(ListPatientId);
        comboBoxPatientId.setSelectedIndex(-1);
        comboBoxPatientId.addItemListener(new ComboBoxListener());
        panelId.add(comboBoxPatientId);

        return panelId;
    }

    /*
    This method resets only the Patient Panel
    Parameters - None
    Return - None
     */
    private static void resetPatientPanel() {
        textFName.setText("");
        textLName.setText("");
        textMobileNo.setText("");
        textPatientId.setText("");
        resetDate(new Patient());
    }

    /*
    This method resets only the Consultation Panel
    Parameters - None
    Return - None
     */
    private static void resetConsultPanel() {
        resetDate(new Consultation());
        comboBoxDocMedId.setSelectedIndex(-1);
        comboBoxOpenHours.setSelectedIndex(-1);
        comboBoxTotalHours.setSelectedIndex(-1);
        comboBoxPatientId.setSelectedIndex(-1);
        textConsultNotes.setText("");
        textEncryptNotes.setText("");
    }

    /*
    This method resets the Date Panel
    Parameters - Takes in a Object and resets the panel according to the instance
    Return - None
     */
    private static void resetDate(Object object) {
        if (object instanceof Person) {
            textBirthDate.setText("");
            textBirthMonth.setText("");
            textBirthYear.setText("");
        } else {
            textConsultDate.setText("");
            textConsultMonth.setText("");
            textConsultYear.setText("");
        }
    }

    /*
   This method validates the Date
   Parameters - Takes in a Object and validates the date to the instance
   Return - None
    */
    private static LocalDate getValidDate(Object object) {
        int date;
        int month;
        int year;
        try {
            date = object instanceof Person ? Integer.parseInt(textBirthDate.getText()) : Integer.parseInt(textConsultDate.getText());
            month = object instanceof Person ? Integer.parseInt(textBirthMonth.getText()) : Integer.parseInt(textConsultMonth.getText());
            year = object instanceof Person ? Integer.parseInt(textBirthYear.getText()) : Integer.parseInt(textConsultYear.getText());

            LocalDate localDate = Validation.validDate(object, year, month, date);
            if (localDate != null) {
                return localDate;
            } else {
                JOptionPane.showMessageDialog(SYSTEM_FRAME, "An Invalid Date Format is Entered", "Error", JOptionPane.WARNING_MESSAGE);
                resetDate(object);
            }

        } catch (NumberFormatException npe) {
            JOptionPane.showMessageDialog(SYSTEM_FRAME, "Invalid Date Type", "Error", JOptionPane.WARNING_MESSAGE);
            resetDate(object);
        }
        return null;
    }

    /*
   This method calculates the consultation Cost
   Parameters - Total duration of the consultation
   Return - The total cost of the selected consultation
    */
    private static int getConsultationCost(int totalTime) {
        int totalCost = 0;
        for (int i = 0; i < totalTime; i++) {
            //For the first hour add 15 and for the rest add 25
            totalCost += (i == 0) ? 15 : 25;
        }
        return totalCost;
    }

    private static boolean encrypt(JFrame frame, String password) {
        String choice = JOptionPane.showInputDialog(frame, "Enter ID No", "Validation", JOptionPane.PLAIN_MESSAGE);
        return choice != null && choice.equals(password);
    }

    static class FrameConsultDetails extends JFrame {
        public FrameConsultDetails(int consultationNo) throws NullPointerException {

            JPanel panelShowDetails = new JPanel();

            panelShowDetails.setLayout(new BoxLayout(panelShowDetails, BoxLayout.PAGE_AXIS));

            JLabel labelConsultationDetails = new JLabel("Consultation: ");
            JLabel textConsultationDetails = new JLabel();

            JLabel labelPatientDetails = new JLabel("Patient: ");
            JLabel textPatientDetails = new JLabel();

            JLabel labelDecryptNotes = new JLabel("Decrypted Notes: ");
            JLabel textDecryptNotes = new JLabel();

            for (Consultation consultation : MANAGEMENT_SYSTEM.getConsultationList()) {
                if (consultationNo == consultation.getConsultationNo()) {
                    textConsultationDetails.setText(consultation.toString());
                }
            }

            for (Patient patient : MANAGEMENT_SYSTEM.getPatientList()) {
                if (MANAGEMENT_SYSTEM.getConsultationList().get(consultationNo).getPatientId().equals(patient.getPatientId())) {
                    textPatientDetails.setText(patient.toString());
                }
            }

            //Decryption Button
            JButton decryptButton = new JButton("Press To Decrypt and Show Notes");
            decryptButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    for (Consultation consultation : MANAGEMENT_SYSTEM.getConsultationList()) {
                        if (consultation.getConsultationNo() == consultationNo && !consultation.getNotes().equals("")) {
                            String encryptedText;
                            String convertedText;
                            System.out.println(consultation.getNotes());
                            try {
                                encryptedText = consultation.getNotes();
                                convertedText = encryption.decrypt(encryptedText);
                                textDecryptNotes.setText(convertedText);
                                break;
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Error in Decrypting Notes", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }
            });
            decryptButton.setBounds(448, 254, 124, 23);

            JLabel label = new JLabel();

            JButton decryptBtn = new JButton("Press to Decrypt and Show Image");
            decryptBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Consultation consultation : MANAGEMENT_SYSTEM.getConsultationList()) {
                        if (consultation.getConsultationNo() == consultationNo && consultation.getEncryptedImageFile() != null) {
                            try {
                                FileInputStream encryptFileInputStream = new FileInputStream(consultation.getEncryptedImageFile());
                                Encryption.encryptImageFile(encryptFileInputStream, 123, consultation.getEncryptedImageFile());

                                BufferedImage image;
                                ImageIcon imageIcon = null;
                                try {
                                    image = ImageIO.read(consultation.getEncryptedImageFile());
                                    //Set Dimensions of the image
                                    imageIcon = new ImageIcon(image.getScaledInstance(100, 100, java.awt.Image.SCALE_DEFAULT));
                                } catch (NullPointerException ignored)  {}
                                label.setIcon(imageIcon);
                                break;
                            } catch (IOException ioe) {
                                JOptionPane.showMessageDialog(SYSTEM_FRAME, "Error in Loading File", "Error", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                }
            });

            setElementAlignment(panelShowDetails, labelConsultationDetails);
            setElementAlignment(panelShowDetails, textConsultationDetails);
            setElementAlignment(panelShowDetails, labelPatientDetails);
            setElementAlignment(panelShowDetails, textPatientDetails);
            setElementAlignment(panelShowDetails, decryptButton);
            setElementAlignment(panelShowDetails, labelDecryptNotes);
            setElementAlignment(panelShowDetails, textDecryptNotes);
            setElementAlignment(panelShowDetails, decryptBtn);
            setElementAlignment(panelShowDetails, label);

            panelShowDetails.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Consultation Details"));

            getContentPane().add(panelShowDetails);

            setSize(SCREEN_SIZE.width, SCREEN_SIZE.height / 2);
        }
    }

    static class FrameInformation extends JFrame {
        public FrameInformation() {

            JLabel label1 = new JLabel("Patient can either encrypt the notes or else leave it as it is");
            JLabel label2 = new JLabel("Consultation Numbers will be starting from 0 and will be shown after the booking is confirmed");
            JLabel label3 = new JLabel("User must enter their Id(Patient Id) before Viewing or Deleting a Consultation");
            JLabel label4 = new JLabel("The Mobile Number should be in the Format 0xxxxxxxxx");
            JLabel label5 = new JLabel("Patient ID should be of Format " + PATIENT_ID_PATTERN_SAMPLE + " with x being digits from 1-9");
            JLabel label6 = new JLabel("Consultation Date should be after the current date and before 2024-01-01. BirthDate should be before the current date");
            JLabel label7 = new JLabel("Cost for the consultation in the first hour will be $15 and $25 for the following hours. Cost will be shown after the booking is confirmed");

            JPanel panelInformation = new JPanel();
            panelInformation.setLayout(new BoxLayout(panelInformation, BoxLayout.PAGE_AXIS));

            setElementAlignment(panelInformation, label1);
            setElementAlignment(panelInformation, label2);
            setElementAlignment(panelInformation, label3);
            setElementAlignment(panelInformation, label4);
            setElementAlignment(panelInformation, label5);
            setElementAlignment(panelInformation, label6);
            setElementAlignment(panelInformation, label7);

            panelInformation.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Important Points"));

            getContentPane().add(panelInformation);
            setSize(SCREEN_SIZE.width, SCREEN_SIZE.height / 2);
        }
    }


    static class FrameViewTables extends JFrame {

        public FrameViewTables() {
            setTitle("Consultation/Patient Details");
            setPreferredSize(new Dimension(600, 300));

            //Create a Table to Display the Doctor List
            JTable tablePatientList = new JTable();
            //Set the Customized Table Model as the Table Model and Pass the Doctor List as the Parameter
            tablePatientList.setModel(new PatientTableModel(MANAGEMENT_SYSTEM.getPatientList()));
            //Add Table to Scroll Pane (To make the table scrollable)
            JScrollPane scrollPaneDoctorList = new JScrollPane(tablePatientList);

            //Create a Table to Display the Doctor List
            JTable tableConsultationList = new JTable();
            //Set the Customized Table Model as the Table Model and Pass the Doctor List as the Parameter
            tableConsultationList.setModel(new ConsultationTableModel(MANAGEMENT_SYSTEM.getConsultationList()));
            //Add Table to Scroll Pane (To make the table scrollable)
            JScrollPane scrollPaneConsultationList = new JScrollPane(tableConsultationList);

            JPanel panelTableView = new JPanel();

            panelTableView.add(scrollPaneDoctorList);
            panelTableView.add(scrollPaneConsultationList);

            add(panelTableView);
            setSize(SCREEN_SIZE.width/2, SCREEN_SIZE.height);
        }
    }
}
