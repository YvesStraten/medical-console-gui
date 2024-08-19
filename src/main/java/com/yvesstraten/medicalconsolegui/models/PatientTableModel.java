package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.util.List;

public class PatientTableModel extends MedicalTableModel {
  private List<Patient> patients;
  private final String[] columns =
      new String[] {"Id", "Name", "Private patient", "Balance", "Current facility"};

  public PatientTableModel(HealthService service) {
    super();
    setPatients(service.getPatients());
  }

  public List<Patient> getPatients() {
    return this.patients;
  }

  public void setPatients(List<Patient> patients) {
    this.patients = patients;
  }

  public void addPatient(Patient pat) {
    getPatients().add(pat);
    fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
  }

  public void setPatient(int index, Patient pat) {
    getPatients().set(index, pat);
    fireTableRowsUpdated(index, index);
  }

  public void deletePatient(int index) {
    getPatients().remove(index);
    fireTableRowsDeleted(index, index);
  }

  @Override
  public int getRowCount() {
    return getPatients().size();
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

  @Override
  public Class<?> getColumnClass(int column) {
    Object value = getValueAt(0, column);
    return value != null ? value.getClass() : Object.class;
  }
}
