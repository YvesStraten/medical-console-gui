package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.Patient;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PatientViewPanel extends ObjectViewPanel {
  private Patient patient;

  public PatientViewPanel(int id){
    this(new Patient(id, "Input a new name", false));
  }

  public PatientViewPanel(Patient patient) {
    super();
    setPatient(patient);

    String name = patient.getName();
    Double balance = patient.getBalance();

    // Initialize labels
    JLabel nameLabel = new JLabel("Name");
    JLabel statusLabel = new JLabel("Private patient");
    JLabel balanceLabel = new JLabel("Balance");
    JLabel currentFacilityLabel = new JLabel("Current facility");

    JTextField nameText = new MedicalTextField(name);
    JCheckBox statusBox = new MedicalCheckBox();
    statusBox.setSelected(patient.isPrivate());

    JTextField balanceText = new MedicalTextField(balance.toString());
    setInputComponents(new Component[] { nameText, balanceText, statusBox }); 
    // Add components
    add(nameLabel);
    add(nameText);
    add(statusLabel);
    add(statusBox);
    add(balanceLabel);
    add(balanceText);
    add(currentFacilityLabel);
  }

  public static PatientViewPanel showViewPanel(Patient patient){
    PatientViewPanel panel = new PatientViewPanel(patient);
    panel.getEditButton().setEnabled(true);
    panel.getDeleteButton().setEnabled(true);

    return panel;
  }

  public static PatientViewPanel showAddPanel(int id) {
    PatientViewPanel panel = new PatientViewPanel(id);
    panel.preventMutations();

    return panel;
  }

  @Override
  public void save() throws NumberFormatException {
    Component[] inputComponents = getInputComponents();
    String name = ((JTextField) inputComponents[0]).getText();
    String balanceText = ((JTextField) inputComponents[1]).getText();
    double balance = Double.parseDouble(balanceText);
    boolean isPrivate = ((JCheckBox) inputComponents[2]).isSelected();

    getPatient().setName(name);
    getPatient().setBalance(balance);
    getPatient().setPrivate(isPrivate);
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PatientViewPanel) {
      PatientViewPanel other = (PatientViewPanel) obj;

      return other.getPatient().equals(this.getPatient());
    }

    return false;
  }
}
