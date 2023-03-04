package com.skinconsultationcenter.w1867160;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class DoctorTableModel extends AbstractTableModel {
    private String[] columnNames = {"Last Name", "First Name", "Licence No", "Specialization", "DOB", "Mobile No"};
    private List<Doctor> doctorList;

    public DoctorTableModel(List<Doctor> list) {
        this.doctorList = list;
    }

    @Override
    public int getRowCount() {
        return doctorList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        if (columnIndex == 0){
            temp = doctorList.get(rowIndex).getLastName();
        } else if (columnIndex == 1) {
            temp = doctorList.get(rowIndex).getFirstName();
        } else if (columnIndex == 2) {
            temp = doctorList.get(rowIndex).getMedLicenceNo();
        } else if (columnIndex == 3){
            temp = doctorList.get(rowIndex).getSpecialisation();
        } else if (columnIndex == 4) {
            temp = doctorList.get(rowIndex).getBirthDate();
        } else if (columnIndex == 5) {
            temp = doctorList.get(rowIndex).getMobileNo();
        }
        return temp;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
}

