package com.yvesstraten.medicalconsolegui.models;

import javax.swing.table.AbstractTableModel;

public abstract class MedicalTableModel extends AbstractTableModel {
  public MedicalTableModel() {
    super();
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }
}
