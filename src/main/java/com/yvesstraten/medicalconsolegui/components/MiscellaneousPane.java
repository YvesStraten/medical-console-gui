package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.exceptions.WrongHospitalException;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MiscellaneousPane extends JPanel {
  private ListPanel listPanel;

  public MiscellaneousPane(ListPanel listPanel) {
    super();
    setListPanel(listPanel);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    JLabel label = new JLabel("Miscellanious Operations");
    label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
    label.setAlignmentY(JLabel.CENTER_ALIGNMENT);

    PatientTableModel patientModel = getListPanel().getPatientTableModel();
    JPanel buttonsGrouper = new JPanel();
    JButton visitButton = new JButton("Simulate a visit");
    visitButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            attemptVisit(patientModel);
          }
        });

    HospitalTableModel hospitalModel = getListPanel().getHospitalTableModel();
    ProcedureTableModel procedureModel = getListPanel().getProcedureTableModel();

    JButton operateButton = new JButton("Simulate operation");
    operateButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            try {
              attemptOperation(hospitalModel, patientModel, procedureModel);
            } catch (WrongHospitalException whe) {
              JOptionPane.showMessageDialog(
                  null, whe.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
            }
          }
        });

    // Add buttons to grouper
    buttonsGrouper.add(visitButton);
    buttonsGrouper.add(operateButton);

    // Add components to panel
    add(label);
    add(buttonsGrouper);
  }

  public ListPanel getListPanel() {
    return listPanel;
  }

  public void setListPanel(ListPanel service) {
    this.listPanel = service;
  }

  private static void attemptVisit(PatientTableModel patientModel) {
    Patient[] patients = patientModel.getPatients().toArray(Patient[]::new);

    if (patients.length == 0) {
      JOptionPane.showMessageDialog(
          null, "Please add a patient first", "Failure", JOptionPane.ERROR_MESSAGE);
      return;
    }

    SelectObjectDialog patientSelect =
        new SelectObjectDialog("Please select a patient to undertake visit", patients);
    int patientSelectResult =
        JOptionPane.showConfirmDialog(
            null, patientSelect, "Select a patient", JOptionPane.OK_CANCEL_OPTION);
    if (patientSelectResult == JOptionPane.CANCEL_OPTION
        || patientSelectResult == JOptionPane.CLOSED_OPTION) {
      return;
    }

    JComboBox<Object> patientCombo = patientSelect.getCombo();
    Patient wantsVisit = (Patient) patientCombo.getSelectedItem();
    MedicalFacility[] facilities =
        patientModel.getService().getMedicalFacilities().toArray(MedicalFacility[]::new);
    SelectObjectDialog facilitySelect =
        new SelectObjectDialog("Please select facility to visi", facilities);

    int facilitySelectResult =
        JOptionPane.showConfirmDialog(
            null, facilitySelect, "Select a patient", JOptionPane.OK_CANCEL_OPTION);
    if (facilitySelectResult == JOptionPane.CANCEL_OPTION
        || facilitySelectResult == JOptionPane.CLOSED_OPTION) {
      return;
    }

    JComboBox<Object> facilityCombo = facilitySelect.getCombo();
    MedicalFacility toVisit = (MedicalFacility) facilityCombo.getSelectedItem();

    boolean successfullVisit = toVisit.visit(wantsVisit);
    if (successfullVisit) {
      JOptionPane.showMessageDialog(
          null,
          "Patient successfully visited facility",
          "Success",
          JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(
          null, "Patient failed to visit facility", "Success", JOptionPane.ERROR_MESSAGE);
    }
  }

  private static void attemptOperation(
      HospitalTableModel hospitalModel,
      PatientTableModel patientModel,
      ProcedureTableModel procedureModel)
      throws WrongHospitalException {
    Hospital[] hospitals = hospitalModel.getHospitals().toArray(Hospital[]::new);
    SelectObjectDialog hospitalSelect =
        new SelectObjectDialog("Please select a hospital first", hospitals);

    int hospitalSelectResult =
        JOptionPane.showConfirmDialog(
            null, hospitalSelect, "Selecting a patient", JOptionPane.OK_CANCEL_OPTION);

    if (hospitalSelectResult == JOptionPane.CANCEL_OPTION
        || hospitalSelectResult == JOptionPane.CLOSED_OPTION) {
      return;
    }

    JComboBox<Object> hospitalCombo = hospitalSelect.getCombo();
    Hospital operateLocation = (Hospital) hospitalCombo.getSelectedItem();

    Patient[] patients = patientModel.getPatients().toArray(Patient[]::new);
    SelectObjectDialog patientSelect =
        new SelectObjectDialog("Please select a patient first", patients);

    int patientSelectResult =
        JOptionPane.showConfirmDialog(
            null, patientSelect, "Selecting a patient", JOptionPane.OK_CANCEL_OPTION);

    if (patientSelectResult == JOptionPane.CANCEL_OPTION
        || patientSelectResult == JOptionPane.CLOSED_OPTION) {
      return;
    }

    JComboBox<Object> patientCombo = patientSelect.getCombo();
    int patientIndex = patientCombo.getSelectedIndex();
    Patient toOperate = (Patient) patientCombo.getSelectedItem();
    if (toOperate.isInThisHospital(operateLocation)) {
      Procedure[] procedures = procedureModel.getProcedures().toArray(Procedure[]::new);
      SelectObjectDialog procedureSelect =
          new SelectObjectDialog("Which operation should be undertaken?", procedures);

      int procedureSelectResult =
          JOptionPane.showConfirmDialog(
              null, procedureSelect, "Selecting a patient", JOptionPane.OK_CANCEL_OPTION);

      if (procedureSelectResult == JOptionPane.CANCEL_OPTION
          || procedureSelectResult == JOptionPane.CLOSED_OPTION) {
        return;
      }

      JComboBox<Object> procedureCombo = procedureSelect.getCombo();
      Procedure toUndertake = (Procedure) procedureCombo.getSelectedItem();
      double cost = Hospital.getOperationCost(toOperate, toUndertake);

      toOperate.addBalance(cost);
      System.out.println(toOperate.toString());
      System.out.println(toOperate.getBalance());
      patientModel.setPatient(patientIndex, toOperate);
    }
  }
}
