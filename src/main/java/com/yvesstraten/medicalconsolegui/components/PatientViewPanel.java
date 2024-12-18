package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.Patient;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.View;

public class PatientViewPanel extends ObjectViewPanel {
  private Patient patient;
  private ViewObjectButton viewButton;
  private JTextField[] textFields;

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
    JCheckBox statusBox = new JCheckBox();
    statusBox.setSelected(patient.isPrivate());

    JTextField balanceText = new MedicalTextField(balance.toString());
    textFields = new JTextField[] { nameText, balanceText }; 
    // Add components
    add(nameLabel);
    add(nameText);
    add(statusLabel);
    add(statusBox);
    add(balanceLabel);
    add(balanceText);
    add(currentFacilityLabel);

    if (patient.getCurrentFacility() != null) {
      ViewObjectButton viewFacility = new ViewObjectButton();
      setViewButton(viewFacility);
      add(viewFacility);
    } else {
      JTextArea viewFacilityLabel = new JTextArea("None");
      add(viewFacilityLabel);
    }
  }

  public static PatientViewPanel showViewPanel(Patient patient){
    PatientViewPanel panel = new PatientViewPanel(patient);
    panel.getEditButton().setEnabled(true);
    panel.getDeleteButton().setEnabled(true);

    return panel;
  }

  public static PatientViewPanel showAddPanel(Patient pat) {
    PatientViewPanel panel = new PatientViewPanel(pat);
    panel.preventMutations();

    return panel;
  }

  @Override
  public void save() {
  }

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public ViewObjectButton getViewButton() {
    return viewButton;
  }

  public void setViewButton(ViewObjectButton viewButton) {
    this.viewButton = viewButton;
  }

  public void viewFacility(ActionListener listener) {
      if(getViewButton() != null){
          getViewButton().addActionListener(listener);
      }
  }

  @Override
  public void allowEdit() {
    for(JTextField textField: textFields){
      textField.setEnabled(true);
    }
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
