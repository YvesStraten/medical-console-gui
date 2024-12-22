package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.Refreshable;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This model represents a list of 
 * hospitals in tabular form
 *
 * @author YvesStraten e2400068
 */
public class HospitalTableModel extends MedicalTableModel
  implements Refreshable {
  /**
   * List of hospitals
   */
  private ArrayList<Hospital> hospitals;
  /**
   * Column names
   */
  private final String[] columns =
    new String[] {
      "Id",
      "Name",
      "ProbAdmit",
      "Num procedures"
    };

  /**
   * Procedure model
   */
  private ProcedureTableModel procedureModel;

  /**
   * <p>Constructor for HospitalTableModel.</p>
   *
   * @param service service to use
   * @param procedureModel procedure model to use
   */
  public HospitalTableModel(HealthService service,
    ProcedureTableModel procedureModel) {

    super(service);
    setHospitals(service
      .getHospitals()
      .collect(Collectors
        .toCollection(ArrayList::new)));
    setProcedureModel(procedureModel);
  }

  /**
   * <p>Getter for the field <code>hospitals</code>.</p>
   *
   * @return a list of hospitals
   */
  public ArrayList<Hospital> getHospitals() {
    return this.hospitals;
  }

  /**
   * <p>Setter for the field <code>hospitals</code>.</p>
   *
   * @param hospitals a list of hospitals
   */
  public void setHospitals(ArrayList<Hospital> hospitals) {
    this.hospitals = hospitals;
    fireTableDataChanged();
  }

  /**
   * Adds a hospital to the table 
   * and service
   *
   * @param name name of hospital
   */
  public void addHospital(String name) {
    getService().initializeHospital(name);
    ArrayList<MedicalFacility> facilities =
      getService()
      .getMedicalFacilities();
    Hospital hospital = 
      (Hospital) facilities
        .get(facilities.size() - 1);

    getHospitals().add(hospital);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  /**
   * Deletes a hospital from table 
   * and service
   *
   * @param selectedRow row to delete from
   */
  public void deleteHospital(int selectedRow) {
    Hospital hospital = getHospitals().get(selectedRow);

    getProcedureModel().deleteProcedures(hospital);
    getHospitals().remove(selectedRow);
    getService().getMedicalFacilities().remove(hospital);

    fireTableRowsDeleted(selectedRow, selectedRow);
  }

  /**
   * Deletes a hospital from table 
   * and service
   *
   * @param selected selected hospital
   */
  public void deleteHospital(Hospital selected) {
    int row = getHospitals().indexOf(selected);
    deleteHospital(row);
    fireTableRowsDeleted(row, row);
  }

  /**
   * <p>Getter for the field <code>procedureModel</code>.</p>
   *
   * @return a procedure model
   */
  public ProcedureTableModel getProcedureModel() {
    return procedureModel;
  }

  /**
   * <p>Setter for the field <code>procedureModel</code>.</p>
   *
   * @param procedureModel procedure model to set
   */
  public void setProcedureModel(ProcedureTableModel procedureModel) {
    this.procedureModel = procedureModel;
  }

  /** {@inheritDoc} */
  @Override
  public int getRowCount() {
    return getHospitals().size();
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
    if (getHospitals().isEmpty()) {
      return null;
    }

    Hospital selectedHospital = getHospitals().get(rowIndex);
    switch (columnIndex) {
      case 0:
        return selectedHospital.getId();
      case 1:
        return selectedHospital.getName();
      case 2:
        return selectedHospital.getProbAdmit();
      case 3:
        return selectedHospital.getProcedures().size();
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Hospital row = getHospitals().get(rowIndex);
    if (columnIndex == 1) {
      row.setName((String) aValue);
    } else if (columnIndex == 2) {
      row.setProbAdmit((Double) aValue);
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex == 0 || columnIndex == 3) {
      return false;
    }

    return true;
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getColumnClass(int column) {
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }

  @Override
  public void refresh() {
    setHospitals(
      getService().getHospitals()
        .collect(Collectors
          .toCollection(ArrayList::new))
    ); 
  }
}
