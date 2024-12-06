package com.yvesstraten.medicalconsolegui.models;

import java.util.List;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;

public class ClinicTableModel extends AbstractTableModel {
    private List<Clinic> clinics;
    private final String[] columns = new String[]
        {
            "Id",
            "Name",
            "Fee",
            "Gap percentage",
            "Visit",
            "View"
        };

    public ClinicTableModel(List<Clinic> clinics){
        super();
        setClinics(clinics);
    }

    public List<Clinic> getClinics(){
        return this.clinics;
    }

    public void setClinics(List<Clinic> clinics){
        this.clinics = clinics;
    }

    @Override
    public int getRowCount() {
        return getClinics().size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column){
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Clinic selectedClinic = getClinics().get(rowIndex);

        switch(columnIndex){
            case 0:
                return selectedClinic.getId();
            case 1:
                return selectedClinic.getName();
            case 2:
                return selectedClinic.getFee();
            case 3:
                return (int) (selectedClinic.getGapPercent() * 100);
            case 4:
                JButton visit = new JButton("Visit");
                return visit;
            case 5:
                ViewObjectButton view = new ViewObjectButton();
                return view;
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int column){
        return getValueAt(0, column).getClass();
    }
}
