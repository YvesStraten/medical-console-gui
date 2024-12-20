package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcedureTableModel extends MedicalTableModel {
  private ArrayList<Procedure> procedures;

  private final String[] columns =
      new String[] {"Id", "Name", "Description", "Elective or not", "Base fee"};

  public ProcedureTableModel(HealthService service) {
    super(service);
    setProcedures(
        service
            .getHospitals()
            .flatMap(Hospital::getProceduresStream)
            .collect(Collectors.toCollection(ArrayList::new)));
  }

  public ArrayList<Procedure> getProcedures() {
    return procedures;
  }

  public void setProcedures(ArrayList<Procedure> procedures) {
    this.procedures = procedures;
  }

  public void addProcedure(
      Hospital hospital, String name, String description, boolean isElective, double cost) {
    getService().initializeProcedure(hospital, name, description, isElective, cost);
    ArrayList<Procedure> procedures = hospital.getProcedures();
    Procedure procedure = procedures.get(procedures.size() - 1);
    getProcedures().add(procedure);

    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void deleteProcedure(int index) {
    deleteProcedure(getProcedures().get(index));
  }

  public void deleteProcedure(Procedure selected) {
    Hospital containsProc =
        getService()
            .getHospitals()
            .filter(
                hospital -> {
                  Optional<Procedure> procedureOptional =
                      hospital
                          .getProceduresStream()
                          .filter(procedure -> procedure.equals(selected))
                          .findFirst();

                  return procedureOptional.isPresent();
                })
            .findFirst()
            .orElse(null);

    if (containsProc != null) {
      int index = getProcedures().indexOf(selected);

      getProcedures().remove(index);
      containsProc.removeProcedure(selected);
      fireTableRowsDeleted(index, index);
    }
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
    if (getProcedures().isEmpty()) {
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
    }

    return null;
  }

  @Override
  public Class<?> getColumnClass(int column) {
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }
}
