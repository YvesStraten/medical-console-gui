package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.ArrayList;

/**
 * This model represents a list of patients in a tabular manner
 *
 * @author YvesStraten e2400068
 */
public class PatientTableModel extends MedicalTableModel {
  /** List of columns */
  private final String[] columns =
      new String[] {
        "Id",
        "Name",
        "Private patient",
        "Balance",
        "Current facility"};

  /**
   * Constructor for PatientTableModel.
   *
   * @param service service to use
   */
  public PatientTableModel(HealthService service) {
    super(service);
  }

  /**
   * Adds a patient to table and service
   *
   * @param name name of patients
   * @param isPrivate whether the patient is private
   */
  public void addPatient(String name, boolean isPrivate) {
    getService().initializePatient(name, isPrivate);
    fireTableRowsInserted(getRowCount(), getRowCount());
  }

  /**
   * Update patient row
   *
   * @param index row index
   * @param pat patient to update with
   */
  public void setPatient(int index, Patient pat) {
    getService().getPatients().set(index, pat);
    fireTableRowsUpdated(index, index);
  }

  /**
   * Deletes a patient from table and service
   *
   * @param index row to delete
   */
  public void deletePatient(int index) {
    // No need to remove from the service as well
    // as we have a direct reference to the collection
    // not an intermediate list
    getService().getPatients().remove(index);

    fireTableRowsDeleted(index, index);
  }

  /**
   * Deletes a patient from table and service
   *
   * @param selected patient object
   */
  public void deletePatient(Patient selected) {
    deletePatient(getService().getPatients().indexOf(selected));
  }

  /** {@inheritDoc} */
  @Override
  public int getRowCount() {
    return getService().getPatients().size();
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
    ArrayList<Patient> patients = getService().getPatients();
    if (patients.isEmpty()) {
      return null;
    }

    Patient selectedPatient = patients.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return selectedPatient.getId();
      case 1:
        return selectedPatient.getName();
      case 2:
        return selectedPatient.isPrivate();
      case 3:
        return selectedPatient.getBalance();
      case 4:
        MedicalFacility current = selectedPatient.getCurrentFacility();
        if (current != null) {
          return current.getName();
        } else {
          return "None";
        }
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    Patient row = getService().getPatients().get(rowIndex);

    switch (columnIndex) {
      case 1:
        row.setName((String) aValue);
        break;
      case 2:
        row.setPrivate((Boolean) aValue);
        break;
      case 3:
        row.setBalance((Double) aValue);
        break;
      case 4:
        Integer id = (Integer) aValue;
        if (id.equals(0)) {
          row.setMedicalFacility(null);
        }

        MedicalFacility matchedFacility =
            getService()
                .getMedicalFacilitiesStream()
                .filter(facility -> facility.getId() == id)
                .findFirst()
                .orElse(null);
        if (matchedFacility != null) {
          row.setMedicalFacility(matchedFacility);
        }
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
        return Boolean.class;
      case 3:
        return Double.class;
      case 4:
        return Integer.class;
      default:
        return Object.class;
    }
  }
}
