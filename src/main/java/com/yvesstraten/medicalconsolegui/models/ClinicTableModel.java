package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This model represents a list of clinics 
 * in tabular form
 *
 * @author YvesStraten e2400068
 */
public class ClinicTableModel extends MedicalTableModel {
  /**
   * List of clinics
   */
  private ArrayList<Clinic> clinics;
  /**
   * Column names
   */
  private final String[] columns = new String[] {"Id", "Name", "Fee", "Gap percentage (%)"};

  /**
   * <p>Constructor for ClinicTableModel.</p>
   *
   * @param service service to use
   */
  public ClinicTableModel(HealthService service) {
    super(service);
    setClinics(service.getClinics().collect(Collectors.toCollection(ArrayList::new)));
  }

  /**
   * <p>Getter for the field <code>clinics</code>.</p>
   *
   * @return list of clinics
   */
  public ArrayList<Clinic> getClinics() {
    return this.clinics;
  }

  /**
   * <p>Setter for the field <code>clinics</code>.</p>
   *
   * @param clinics list of clinics
   */
  public void setClinics(ArrayList<Clinic> clinics) {
    this.clinics = clinics;
  }

  /**
   * Adds a Clinic to the table
   * and service
   *
   * @param name name of clinic
   * @param fee fee of clinic
   * @param gapPercentage gap percentage
   */
  public void addClinic(String name, double fee, double gapPercentage) {
    getService().initializeClinic(name, fee, gapPercentage);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  /**
   * Deletes a clinic from the table 
   * and service
   *
   * @param index row index to delete
   */
  public void deleteClinic(int index) {
    Clinic clinic = getClinics().get(index);
    getClinics().remove(index);
    getService().getMedicalFacilities().remove(clinic);
    fireTableRowsDeleted(index, index);
  }

  /**
   *
   * Deletes a clinic from the table 
   * and service
   *
   * @param selected clinic to delete
   */
  public void deleteClinic(Clinic selected) {
    int row = getClinics().indexOf(selected);
    getClinics().remove(selected);
    getService().getMedicalFacilities().remove(selected);
    fireTableRowsDeleted(row, row);
  }

  /** {@inheritDoc} */
  @Override
  public int getRowCount() {
    return getClinics().size();
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
    if (getClinics().isEmpty()) {
      return null;
    }

    Clinic selectedClinic = getClinics().get(rowIndex);

    switch (columnIndex) {
      case 0:
        return selectedClinic.getId();
      case 1:
        return selectedClinic.getName();
      case 2:
        return selectedClinic.getFee();
      case 3:
        return (int) (selectedClinic.getGapPercent() * 100);
    }

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Clinic row = getClinics().get(rowIndex);
    switch (columnIndex) {
      case 1:
        row.setName((String) aValue);
        break;
      case 2:
        row.setFee((Double) aValue);
        break;
      case 3:
        row.setGapPercent((Double) aValue);
        break;
    }
  }

  /** {@inheritDoc} */
  @Override
  public Class<?> getColumnClass(int column) {
    switch (column) {
      case 0:
        return Integer.class;
      case 1:
        return String.class;
      case 2:
        return Double.class;
      case 3:
        return Double.class;
      default:
        return Object.class;
    }
  }
}
