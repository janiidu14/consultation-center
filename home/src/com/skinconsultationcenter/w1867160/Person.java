package com.skinconsultationcenter.w1867160;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Person implements Serializable {
    private String firstName;
    private String lastName;
    private LocalDate birthDate; //Validate Birth Date when Implementing (Should be before the current date)
    private String mobileNo; //Validate Mobile Number when Implementing

    public Person(String firstName, String lastName, LocalDate birthDate, String mobileNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.mobileNo = mobileNo;
    }

    public Person(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "First Name:  " + firstName + " | " + "Last Name: " + lastName + " | " +
                "Date of Birth: " + birthDate + " | " + "Mobile No: " + mobileNo;
    }
}
