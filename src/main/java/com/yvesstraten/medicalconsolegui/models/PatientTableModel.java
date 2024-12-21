package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.ArrayList;

/**
 * This model represents a list of patients 
 * in a tabular manner
 *
 * @author YvesStraten e2400068
 */
public class PatientTableModel extends MedicalTableModel {
  private ArrayList<Patient> patients;
  private final String[] columns =
      new String[] {"Id", "Name", "Private patient", "Balance", "Current facility"};

  /**
   * <p>Constructor for PatientTableModel.</p>
   *
   * @param service service to use
   */
  public PatientTableModel(HealthService service) {
    super(service);
    setPatients(service.getPatients());
  }

  /**
   * <p>Getter for the field <code>patients</code>.</p>
   *
   * @return list of patients
   */
  public ArrayList<Patient> getPatients() {
    return this.patients;
  }

  /**
   * <p>Setter for the field <code>patients</code>.</p>
   *
   * @param patients list of patients
   */
  public void setPatients(ArrayList<Patient> patients) {
    this.patients = patients;
  }

  /**
   * Adds a patient to table 
   * and service
   *
   * @param name name of patients
   * @param isPrivate whether the patient is private
   */
  public void addPatient(String name, boolean isPrivate) {
    getService().initializePatient(name, isPrivate);
    ArrayList<Patient> patients = getService().getPatients();
    Patient patient = patients.get(patients.size() - 1);
    getPatients().add(patient);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  /**
   * Update patient row
   *
   * @param index row index
   * @param pat patient to update with
   */
  public void setPatient(int index, Patient pat) {
    getPatients().set(index, pat);
    fireTableRowsUpdated(index, index);
  }

  /**
   * Deletes a patient from table
   * and service
   *
   * @param index row to delete
   */
  public void deletePatient(int index) {
    // No need to remove from the service as well
    // as we have a direct reference to the collection
    // not an intermediate list
    getPatients().remove(index);

    fireTableRowsDeleted(index, index);
  }

  /**
   * Deletes a patient from table
   * and service
   *
   * @param selected patient object
   */
  public void deletePatient(Patient selected) {
    deletePatient(getPatients().indexOf(selected));
  }

  /** {@inheritDoc} */
  @Override
  public int getRowCount() {
    return getPatients().size();
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
    if (getPatients().isEmpty()) {
      return null;
    }

    Patient selectedPatient = getPatients().get(rowIndex);
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
    Patient row = getPatients().get(rowIndex);

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
