package com.yvesstraten.medicalconsolegui.models;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;

public abstract class MedicalTableModel extends AbstractTableModel {
    public MedicalTableModel(){
        super();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(getColumnClass(columnIndex).equals(ViewObjectButton.class))
            return true;
        else return false;
    }
}
