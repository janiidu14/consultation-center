package com.skinconsultationcenter.w1867160;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ConsultationTableModel extends AbstractTableModel {
    private String[] columnNames = {"Consultation No","Doctor Med ID", "Patient ID", "Date/Time", "Total Hours", "Total Cost"};
    private List<Consultation> consultationList;

    public ConsultationTableModel(List<Consultation> list) {
        this.consultationList = list;
    }

    @Override
    public int getRowCount() {
        return consultationList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object temp = null;
        if (columnIndex == 0){
            temp = consultationList.get(rowIndex).getConsultationNo();
        } else if (columnIndex == 1) {
            temp = consultationList.get(rowIndex).getDoctorMedId();
        } else if (columnIndex == 2) {
            temp = consultationList.get(rowIndex).getPatientId();
        } else if (columnIndex == 3) {
            temp = consultationList.get(rowIndex).getDatetime();
        } else if (columnIndex == 4) {
            temp = consultationList.get(rowIndex).getTotalConsultationHours();
        } else if (columnIndex == 5) {
            temp = consultationList.get(rowIndex).getCost();
        }
        return temp;
    }

    public String getColumnName(int col){
        return columnNames[col];
    }
}
