package com.skinconsultationcenter.w1867160;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PatientTableModel extends AbstractTableModel {
    private String[] columnNames = {"Patient ID", "First Name", "Last Name", "DOB", "Mobile No"};
    private List<Patient> patientList;

    public PatientTableModel(List<Patient> list) {
        this.patientList = list;
    }

    @Override
    public int getRowCount() {
        return patientList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        if (columnIndex == 0){
            temp = patientList.get(rowIndex).getPatientId();
        } else if (columnIndex == 1) {
            temp = patientList.get(rowIndex).getFirstName();
        } else if (columnIndex == 2) {
            temp = patientList.get(rowIndex).getLastName();
        } else if (columnIndex == 3) {
            temp = patientList.get(rowIndex).getBirthDate();
        } else if (columnIndex == 4) {
            temp = patientList.get(rowIndex).getMobileNo();
        }
        return temp;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
}
