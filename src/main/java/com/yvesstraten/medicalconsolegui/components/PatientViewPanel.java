package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.Patient;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class PatientViewPanel extends ObjectViewPanel {
  Patient patient;
  JButton viewButton;

  public PatientViewPanel(Patient patient){
    this(false, patient);
  }

  public PatientViewPanel(boolean isMutable, Patient patient) {
    super(isMutable);
    setPatient(patient);

    String name = patient.getName();
    String status = patient.isPrivate() ? "Yes" : "No";
    Double balance = patient.getBalance();

    // Initialize labels
    JLabel nameLabel = new JLabel("Name");
    JLabel statusLabel = new JLabel("Private patient");
    JLabel balanceLabel = new JLabel("Balance");
    JLabel currentFacilityLabel = new JLabel("Current facility");

    JTextArea nameText = new JTextArea(name);
    JTextArea statusText = new JTextArea(status);
    JTextArea balanceText = new JTextArea(balance.toString());
    // Add components
    add(nameLabel);
    add(nameText);
    add(statusLabel);
    add(statusText);
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

  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public JButton getViewButton() {
    return viewButton;
  }

  public void setViewButton(JButton viewButton) {
    this.viewButton = viewButton;
  }

  public void viewFacility(ActionListener listener) {
      if(getViewButton() != null){
          getViewButton().addActionListener(listener);
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
