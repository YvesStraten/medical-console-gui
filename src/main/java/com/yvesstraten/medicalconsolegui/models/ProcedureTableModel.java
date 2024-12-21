package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>ProcedureTableModel class.</p>
 *
 * This table model models a table for 
 * procedures present in the service
 * @author YvesStraten e2400068
 */
public class ProcedureTableModel extends MedicalTableModel {
  private ArrayList<Procedure> procedures;

  private final String[] columns =
      new String[] {
        "Id", "Name", "Description", "Elective or not", "Base fee", "Available in hospital"
      };

  /**
   * <p>Constructor for ProcedureTableModel.</p>
   *
   * @param service a {@link com.yvesstraten.medicalconsole.HealthService} object
   */
  public ProcedureTableModel(HealthService service) {
    super(service);
    setProcedures(
        service
            .getHospitals()
            .flatMap(Hospital::getProceduresStream)
            .collect(Collectors.toCollection(ArrayList::new)));
  }

  /**
   * <p>Getter for the field <code>procedures</code>.</p>
   *
   * @return a {@link java.util.ArrayList} object
   */
  public ArrayList<Procedure> getProcedures() {
    return procedures;
  }

  /**
   * <p>Setter for the field <code>procedures</code>.</p>
   *
   * @param procedures a {@link java.util.ArrayList} object
   */
  public void setProcedures(ArrayList<Procedure> procedures) {
    this.procedures = procedures;
  }

  /**
   * <p>addProcedure.</p>
   *
   * @param hospital a {@link com.yvesstraten.medicalconsole.facilities.Hospital} object
   * @param name a {@link java.lang.String} object
   * @param description a {@link java.lang.String} object
   * @param isElective a boolean
   * @param cost a double
   */
  public void addProcedure(
      Hospital hospital, String name, String description, boolean isElective, double cost) {
    getService().initializeProcedure(hospital, name, description, isElective, cost);
    ArrayList<Procedure> procedures = hospital.getProcedures();
    Procedure procedure = procedures.get(procedures.size() - 1);
    getProcedures().add(procedure);

    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  /**
   * <p>deleteProcedure.</p>
   *
   * @param index a int
   */
  public void deleteProcedure(int index) {
    deleteProcedure(getProcedures().get(index));
  }

  /**
   * <p>deleteProcedure.</p>
   *
   * @param selected a {@link com.yvesstraten.medicalconsole.facilities.Procedure} object
   */
  public void deleteProcedure(Procedure selected) {
    Hospital containsProc = getHospitalWithProcedure(selected);

    if (containsProc != null) {
      int index = getProcedures().indexOf(selected);

      getProcedures().remove(index);
      containsProc.removeProcedure(selected);
      fireTableRowsDeleted(index, index);
    }
  }

  /**
   * <p>deleteProcedures.</p>
   *
   * @param hospital a {@link com.yvesstraten.medicalconsole.facilities.Hospital} object
   */
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

  /** {@inheritDoc} */
  @Override
  public int getRowCount() {
    return getProcedures().size();
  }

  /** {@inheritDoc} */
  @Override
  public int getColumnCount() {
    return columns.length;
  }

  /** {@inheritDoc} */
  @Override
  public String getColumnName(int column) {
    return columns[column];
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
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

  /**
   * <p>getHospitalWithProcedure.</p>
   *
   * @param selected a {@link com.yvesstraten.medicalconsole.facilities.Procedure} object
   * @return a {@link com.yvesstraten.medicalconsole.facilities.Hospital} object
   */
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
