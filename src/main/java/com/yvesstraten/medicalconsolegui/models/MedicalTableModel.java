package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsolegui.components.SimulateVisitButton;
import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;
import javax.swing.table.AbstractTableModel;

public abstract class MedicalTableModel extends AbstractTableModel {
  public MedicalTableModel() {
    super();
  }

  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    Class<?> clazz = getColumnClass(columnIndex);
    if (clazz.equals(ViewObjectButton.class)) return true;
    else if (clazz.equals(SimulateVisitButton.class)) return true;
    else return false;
  }
}
