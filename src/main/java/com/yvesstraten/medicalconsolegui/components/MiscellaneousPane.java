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
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This pane contains all the buttons 
 * which can undertake miscellaneous
 * operations such as visiting and operating
 * @author YvesStraten e2400068
 */
public class MiscellaneousPane extends ButtonPane {
  /**
   * <p>Constructor for MiscellaneousPane.</p>
   *
   * @param listPanel list panel to use
   */
  public MiscellaneousPane(ListPanel listPanel) {
    super("Miscellaneous Operations", listPanel);
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
    add(buttonsGrouper);
  }

  /**
   * This function gives the user prompts 
   * to attemp a visit 
   *
   * @param patientModel patient model
   */
  private static void attemptVisit(PatientTableModel patientModel) {
    Patient[] patients = patientModel.getPatients().toArray(Patient[]::new);
    Patient wantsVisit =
        (Patient)
            SelectObjectDialog.attemptSelection(
                "Please select a patient to undertake visit",
                "Select a patient",
                "Please add a patient first",
                patients);
    if (wantsVisit == null) {
      return;
    }

    MedicalFacility[] facilities =
        patientModel.getService().getMedicalFacilities().toArray(MedicalFacility[]::new);
    MedicalFacility toVisit =
        (MedicalFacility)
            SelectObjectDialog.attemptSelection(
                "Please select facility to visit",
                "Select facility",
                "Please add a facility first",
                facilities);

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

  /**
   * This function gives prompts to the user 
   * to undertake an operation
   *
   * @param hospitalModel hospital model
   * @param patientModel patient model
   * @param procedureModel procedure model
   * @throws WrongHospitalException if no hospitals 
   * have been added yet
   */
  private static void attemptOperation(
      HospitalTableModel hospitalModel,
      PatientTableModel patientModel,
      ProcedureTableModel procedureModel)
      throws WrongHospitalException {
    Hospital[] hospitals = hospitalModel.getHospitals().toArray(Hospital[]::new);
    Hospital operateLocation =
        (Hospital)
            SelectObjectDialog.attemptSelection(
                "Please select a hospital first",
                "Selecting a hospital",
                "Please add a hospital first",
                hospitals);
    if (operateLocation == null) {
      return;
    }

    Patient[] patients = patientModel.getPatients().toArray(Patient[]::new);
    Patient toOperate =
        (Patient)
            SelectObjectDialog.attemptSelection(
                "Please select a patient first",
                "Selecting a patient",
                "Please add a patient first",
                patients);
    if (toOperate == null) {
      return;
    }

    if (toOperate.isInThisHospital(operateLocation)) {
      Procedure[] procedures = procedureModel.getProcedures().toArray(Procedure[]::new);
      Procedure toUndertake =
          (Procedure)
              SelectObjectDialog.attemptSelection(
                  "Which operation should be undertaken?",
                  "Selecting procedure",
                  "Please add a procedure first",
                  procedures);

      if (toUndertake == null) {
        return;
      }

      double cost = Hospital.getOperationCost(toOperate, toUndertake);

      toOperate.addBalance(cost);
      patientModel.setPatient(patientModel.getPatients().indexOf(toOperate), toOperate);
    }
  }
}
