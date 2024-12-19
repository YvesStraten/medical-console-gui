package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ProcedureTableModel extends MedicalTableModel {
  private ArrayList<Procedure> procedures;

  private final String[] columns =
      new String[] {"Id", "Name", "Description", "Elective or not", "Base fee"};

  public ProcedureTableModel(ArrayList<Procedure> procedures){
    super();
    setProcedures(procedures);
  }

  public ProcedureTableModel(HealthService service) {
    super();
    setProcedures(service.getHospitals().flatMap(Hospital::getProceduresStream).collect(Collectors.toCollection(ArrayList::new)));
  }

  public ArrayList<Procedure> getProcedures() {
    return procedures;
  }

  public void setProcedures(ArrayList<Procedure> procedures) {
    this.procedures = procedures;
  }

  public void addProcedure(Procedure procedure){
    getProcedures().add(procedure);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteProcedure(int index){
    getProcedures().remove(index);
    fireTableRowsDeleted(index, index);
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
    if(getProcedures().isEmpty()){
      return null;
    }

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
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }
}
