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
      new String[] {
        "Id", "Name", "Description", "Elective or not", "Base fee", "Available in hospital"
      };

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
    Hospital containsProc = getHospitalWithProcedure(selected);

    if (containsProc != null) {
      int index = getProcedures().indexOf(selected);

      getProcedures().remove(index);
      containsProc.removeProcedure(selected);
      fireTableRowsDeleted(index, index);
    }
  }

  public void deleteProcedures(Hospital hospital) {
    ArrayList<Procedure> selected = hospital.getProcedures();
    // Delete from model
    for(Procedure procedure: selected){
      int index = getProcedures().indexOf(procedure);
      getProcedures().remove(index);
    }

    // Delete from actual object
    selected.removeAll(selected);

    fireTableDataChanged();
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
        return selectedProcedure.isElective();
      case 4:
        return selectedProcedure.getCost();
      case 5:
        Hospital containing = getHospitalWithProcedure(selectedProcedure);
        if (containing != null) {
          return containing.getName();
        }
        return "Not assigned";
    }

    return null;
  }

  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Procedure row = getProcedures().get(rowIndex);
    switch (columnIndex) {
      case 1:
        row.setName((String) aValue);
        break;
      case 2:
        row.setDescription((String) aValue);
        break;
      case 3:
        row.setIsElective((Boolean) aValue);
        break;
      case 4:
        row.setCost((Double) aValue);
        break;
      case 5:
        int givenId = (Integer) aValue;
        Hospital contained = getHospitalWithProcedure(row);
        // Procedure already assigned to hospital
        if (contained != null) {
          contained.removeProcedure(row);
        }
        Hospital toAddTo =
            getService()
                .getHospitals()
                .filter(hospital -> hospital.getId() == givenId)
                .findFirst()
                .orElse(null);

        if (toAddTo != null) toAddTo.getProcedures().add(row);
    }
  }

  @Override
  public Class<?> getColumnClass(int column) {
    switch (column) {
      case 1:
        return String.class;
      case 2:
        return String.class;
      case 3:
        return Boolean.class;
      case 4:
        return Double.class;
      case 5:
        return Integer.class;
      default:
        return Object.class;
    }
  }

  public Hospital getHospitalWithProcedure(Procedure selected) {
    return getService()
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
  }
}
