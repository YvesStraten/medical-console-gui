package com.yvesstraten.medicalconsolegui.models;

import java.util.List;

import javax.swing.JButton;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsolegui.components.ViewObjectButton;

public class PatientTableModel extends MedicalTableModel {
  private List<Patient> patients;
  private final String[] columns = new String[] { "Id", "Name", "Private patient", "Balance", "Current facility", "View"};

  public PatientTableModel(List<Patient> patients){
    super();
    setPatients(patients);
  }

  public List<Patient> getPatients(){
    return this.patients;
  }

  public void setPatients(List<Patient> patients){
    this.patients = patients;
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
  public String getColumnName(int column){
    return columns[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Patient selectedPatient = getPatients().get(rowIndex);
    switch(columnIndex){
      case 0:
        return selectedPatient.getId();
      case 1:
        return selectedPatient.getName();
      case 2:
        return selectedPatient.isPrivate()
            ? "Yes"
            : "No";
      case 3:
        return selectedPatient.getBalance();
      case 4:
        JButton viewFacility = new JButton("View facility");
        return viewFacility;
      case 5:
        ViewObjectButton view = new ViewObjectButton();
        return view;
    }
    return null;
  }

  @Override
  public Class<?> getColumnClass(int column){
    return getValueAt(0, column).getClass();
  }
}
