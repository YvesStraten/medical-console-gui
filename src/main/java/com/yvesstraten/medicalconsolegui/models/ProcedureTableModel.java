package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;
import java.util.List;

public class ProcedureTableModel extends MedicalTableModel {
  private List<Procedure> procedures;

  private final String[] columns =
      new String[] {"Id", "Name", "Description", "Elective or not", "Base fee"};

  public ProcedureTableModel(List<Procedure> procedures) {
    super();
    setProcedures(procedures);
  }

  public List<Procedure> getProcedures() {
    return procedures;
  }

  public void setProcedures(List<Procedure> procedures) {
    this.procedures = procedures;
  }

  @Override
  public int getRowCount() {
    return getProcedures().size();
  }

  @Override
  public int getColumnCount() {
    return columns.length;
  }

  @Override
  public String getColumnName(int column) {
      return columns[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Procedure selectedProcedure = getProcedures().get(rowIndex);

    switch (columnIndex) {
      case 0:
        return selectedProcedure.getId();
      case 1:
        return selectedProcedure.getName();
    case 2:
        return selectedProcedure.getDescription();
      case 3:
        return selectedProcedure.getCost();
      case 4:
        return selectedProcedure.isElective();
      case 6:
        ViewObjectButton view = new ViewObjectButton();
        return view;
    }

    return null;
  }

  @Override
  public Class<?> getColumnClass(int column) {
    return getValueAt(0, column).getClass();
  }
}
