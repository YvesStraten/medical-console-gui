package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RemoveButtonsPane extends ButtonPane {
  public RemoveButtonsPane(ListPanel listPanel) {
    super("Remove operations", listPanel);
    JPanel buttonsGrouper = new JPanel();
    buttonsGrouper.setLayout(new GridLayout(2, 2, 5, 5));

    JButton removeHospitalButton = new JButton("Remove hospital");
    removeHospitalButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Hospital[] facilities =
                getListPanel().getHospitalTableModel().getHospitals().toArray(Hospital[]::new);

            Hospital toRemove =
                (Hospital)
                    getItemToRemove(
                        "Please select the hospital you wish to remove",
                        "Removing a hospital",
                        "No hospitals have been added yet",
                        facilities);

            if (toRemove == null) return;

            int numProcedures = toRemove.getProcedures().size();
            if (numProcedures > 0) {
              int selection =
                  JOptionPane.showConfirmDialog(
                      null,
                      "This hospital has "
                          + numProcedures
                          + " procedures. Do you still wish to remove it?",
                      "Removing a hospital",
                      JOptionPane.YES_NO_OPTION);

              if (selection == JOptionPane.YES_OPTION) {
                getListPanel().getHospitalTableModel().deleteHospital(toRemove);
                showSuccessMessage("Hospital was removed successfully");
              }
            } else {
              getListPanel().getHospitalTableModel().deleteHospital(toRemove);
            }
          }
        });

    JButton removeClinicButton = new JButton("Remove clinic");
    removeClinicButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Clinic[] clinics =
                getListPanel().getClinicTableModel().getClinics().toArray(Clinic[]::new);

            Clinic toRemove =
                (Clinic)
                    getItemToRemove(
                        "Please select the clinic you wish to remove",
                        "Removing a clinic",
                        "No clinics have been added yet",
                        clinics);

            if (toRemove == null) return;

            getListPanel().getClinicTableModel().deleteClinic(toRemove);
            showSuccessMessage("Clinic was removed successfully");
          }
        });

    JButton removePatientButton = new JButton("Remove patient");
    removePatientButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Patient[] patients =
                getListPanel().getPatientTableModel().getPatients().toArray(Patient[]::new);

            Patient toRemove =
                (Patient)
                    getItemToRemove(
                        "Please select the patient you wish to remove",
                        "Removing a patient",
                        "No patients have been added yet",
                        patients);

            if (toRemove == null) return;

            getListPanel().getPatientTableModel().deletePatient(toRemove);
            showSuccessMessage("Patient was removed successfully");
          }
        });

    JButton removeProcedure = new JButton("Remove procedure");
    removeProcedure.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Procedure[] patients =
                getListPanel().getProcedureTableModel().getProcedures().toArray(Procedure[]::new);

            Procedure toRemove =
                (Procedure)
                    getItemToRemove(
                        "Please select the procedure you wish to remove",
                        "Removing a patient",
                        "No procedures have been added yet",
                        patients);

            if (toRemove == null) return;

            getListPanel().getProcedureTableModel().deleteProcedure(toRemove);
            showSuccessMessage("Procedure was removed successfully");
          }
        });

    // Add buttons to grouper
    buttonsGrouper.add(removeHospitalButton);
    buttonsGrouper.add(removeClinicButton);
    buttonsGrouper.add(removePatientButton);
    buttonsGrouper.add(removeProcedure);

    // Add components
    add(buttonsGrouper);
  }

  public static Object getItemToRemove(
      String message, String title, String failureMessage, Object[] objects) {
    SelectObjectDialog removeDialog = new SelectObjectDialog(message, objects);
    if (objects.length == 0) {
      JOptionPane.showMessageDialog(null, failureMessage, "Failure", JOptionPane.ERROR_MESSAGE);
      return null;
    }

    int result =
        JOptionPane.showConfirmDialog(
            null, removeDialog, title, JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
      return null;
    }

    JComboBox<Object> combo = removeDialog.getCombo();
    Object toRemove = combo.getSelectedItem();

    return toRemove;
  }

  public static void showSuccessMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Success!", JOptionPane.INFORMATION_MESSAGE);
  }
}
