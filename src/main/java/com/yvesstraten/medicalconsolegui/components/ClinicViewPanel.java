package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.Clinic;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClinicViewPanel extends MedicalFacilityViewPanel {
  private JTextField[] textFields;

  public ClinicViewPanel(int id) {
    this(new Clinic(id, "Please input new name", 0, 0));
  }

  public ClinicViewPanel(Clinic clinic) {
    super(clinic);
    String name = clinic.getName();
    Double fee = clinic.getFee();
    Double gapPercent = clinic.getGapPercent();

    // Initialize main labels
    JLabel nameLabel = new JLabel("Name");
    JLabel feeLabel = new JLabel("Clinic Fee");
    JLabel gapPercentLabel = new JLabel("Gap Percentage");

    // Initialize text areas
    JTextField nameTextArea = new MedicalTextField(name);
    JTextField feeTextArea = new MedicalTextField(fee.toString());
    JTextField gapPercentTextArea = new MedicalTextField(gapPercent.toString());
    setInputComponents(new JTextField[] {nameTextArea, feeTextArea, gapPercentTextArea});

    // Add components to Panel
    add(nameLabel);
    add(nameTextArea);
    add(feeLabel);
    add(feeTextArea);
    add(gapPercentLabel);
    add(gapPercentTextArea);
  }

  public static ClinicViewPanel showAddPanel(int id) {
    ClinicViewPanel panel = new ClinicViewPanel(id);
    panel.preventMutations();
    panel.enableInputComponents();

    return panel;
  }

  public static ClinicViewPanel getViewPanel(Clinic selected) {
    ClinicViewPanel panel = new ClinicViewPanel(selected);
    panel.getEditButton().setEnabled(true);
    panel.getDeleteButton().setEnabled(true);

    return panel;
  }

  @Override
  public Clinic getFacility() {
    return (Clinic) super.getFacility();
  }

  public void setFacility(Clinic clinic) {
    super.setFacility(clinic);
  }

  @Override
  public void save() throws NumberFormatException {
    JTextField[] inputComponents = (JTextField[]) getInputComponents();
    String name = inputComponents[0].getText();
    double fee = Double.parseDouble(inputComponents[1].getText());
    double gapPercentage = Double.parseDouble(inputComponents[2].getText());

    getFacility().setName(name);
    getFacility().setFee(fee);
    getFacility().setGapPercent(gapPercentage);
    
    // Update this field instantly, as depending on 
    // input it will need it, e.g 50 should become 0.5
    Double newGap = getFacility().getGapPercent();
    inputComponents[1].setText(newGap.toString());
    repaint();
  }
}
