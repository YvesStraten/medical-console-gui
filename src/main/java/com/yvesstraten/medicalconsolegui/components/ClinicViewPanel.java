package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.Clinic;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClinicViewPanel extends ObjectViewPanel {
  private Clinic clinic;
  private JTextField[] textFields;

  public ClinicViewPanel(Clinic clinic) {
    this(false, clinic);
  }

  public ClinicViewPanel(boolean isMutable, Clinic clinic) {
    super();
    setClinic(clinic);
    String name = clinic.getName();
    Double fee = clinic.getFee();
    Double gapPercent = clinic.getGapPercent();

    // Initialize main labels
    JLabel nameLabel = new JLabel("Name");
    JLabel feeLabel = new JLabel("Clinic Fee");
    JLabel gapPercentLabel = new JLabel("Gap Percentage");

    // Initialize text areas
    JTextField nameTextArea = new JTextField(name);
    JTextField feeTextArea = new JTextField(fee.toString());
    JTextField gapPercentTextArea = new JTextField(gapPercent.toString());
    textFields = new JTextField[] {nameTextArea, feeTextArea, gapPercentTextArea};

    // Add components to Panel
    add(nameLabel);
    add(nameTextArea);
    add(feeLabel);
    add(feeTextArea);
    add(gapPercentLabel);
    add(gapPercentTextArea);

    if (!isMutable) {
      preventMutations();
    }
  }

  public static ClinicViewPanel getAddPanel(Clinic selected){
    ClinicViewPanel panel = new ClinicViewPanel(selected);
    panel.preventMutations();

    return panel;
  }

  public static ClinicViewPanel getViewPanel(Clinic selected){
    ClinicViewPanel panel = new ClinicViewPanel(selected);
    // panel.getEditButton().setEnabled(true);
    // panel.getDeleteButton().setEnabled(true);

    return panel;
  }

  public Clinic getClinic() {
    return clinic;
  }

  public void setClinic(Clinic clinic) {
    this.clinic = clinic;
  }

  @Override
  public void allowEdit() {
    for (JTextField textField : textFields) {
      textField.setEnabled(true);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ClinicViewPanel) {
      ClinicViewPanel other = (ClinicViewPanel) obj;

      return other.getClinic().equals(this.getClinic());
    }

    return false;
  }

  @Override
  public void save() {
    JTextField[] textFields = getTextFields();
    try {
      String name = textFields[0].getText();
      double fee = Double.parseDouble(textFields[1].getText());
      double gapPercentage = Double.parseDouble(textFields[2].getText());

      getClinic().setName(name);
      getClinic().setFee(fee);
      getClinic().setGapPercent(gapPercentage);

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}
