package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.exceptions.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This panel contains all the buttons related to adding
 *
 * @author YvesStraten e2400068
 */
public class AddButtonsPane extends ButtonPane {
  /**
   * Constructor for AddButtonsPane.
   *
   * @param listPanel list panel to set
   */
  public AddButtonsPane(ListPanel listPanel) {
    super("Add Item", listPanel);

    // Add buttons
    JPanel buttonsGrouper = new JPanel();
    buttonsGrouper.setLayout(new GridLayout(2, 2, 5, 5));
    JButton addHospitalButton = new JButton("Add Hospital");
    addHospitalButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addHospital(getListPanel().getHospitalTableModel());
          }
        });

    JButton addClinicButton = new JButton("Add Clinic");
    addClinicButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addClinic(getListPanel().getClinicTableModel());
          }
        });

    JButton addPatientButton = new JButton("Add Patient");
    addPatientButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addPatient(getListPanel().getPatientTableModel());
          }
        });

    JButton addProcedureButton = new JButton("Add Procedure");
    addProcedureButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            ListPanel listPanel = getListPanel();
            addProcedure(
              listPanel.getHospitalTableModel(),
              listPanel.getProcedureTableModel());
          }
        });

    buttonsGrouper.add(addHospitalButton);
    buttonsGrouper.add(addClinicButton);
    buttonsGrouper.add(addPatientButton);
    buttonsGrouper.add(addProcedureButton);

    // Add buttons
    add(buttonsGrouper);
  }

  /**
   * Add a hospital to the model
   *
   * @param hospitalModel given model
   */
  private void addHospital(HospitalTableModel hospitalModel) {
    String name = getString("Please input the name for the hospital",
      "Adding a hospital");

    if (name == null) {
      return;
    }

    hospitalModel.addHospital(name);
  }

  /**
   * Add a clinic to the model
   *
   * @param clinicModel given model
   */
  private void addClinic(ClinicTableModel clinicModel) {
    String dialogTitle = "Adding a clinic";
    String name = getString("Please input the name for the clinic",
      dialogTitle);

    if (name == null) {
      return;
    }

    Double fee = getDoubleValue("Please input the fee for the clinic",
      dialogTitle);

    if (fee == null) return;

    Double gapPercent =
        getDoubleValue("Please input the gap percentage for the clinic",
      dialogTitle);

    if (gapPercent == null) return;

    clinicModel.addClinic(name, fee, gapPercent);
  }

  /**
   * Add a patient to the model
   *
   * @param patientModel given model
   */
  private void addPatient(PatientTableModel patientModel) {
    String dialogTitle = "Adding a patient";
    String name = getString("Please input the name for the patient",
      dialogTitle);

    if (name == null) {
      return;
    }

    Boolean isPrivate = getYesNo("Is the patient private?",
      dialogTitle);

    if (isPrivate == null) {
      return;
    }

    patientModel.addPatient(name, isPrivate);
  }

  /**
   * Add a procedure to the model
   *
   * @param procedureModel given model
   */
  private void addProcedure(HospitalTableModel hospitalModel, ProcedureTableModel procedureModel) {
    String dialogTitle = "Adding procedure";
    while (true) {
      try {
        Hospital[] hospitals = procedureModel
          .getService()
          .getHospitals()
          .toArray(Hospital[]::new);

        Object selected =
            SelectObjectDialog.attemptSelection(
                "Please select a hospital first",
                dialogTitle,
                "Please add a hospital first",
                hospitals);

        if (selected == null) {
          throw new NoHospitalsAvailableException();
        } else if (selected instanceof Integer) {
          break;
        }

        Hospital hospital = (Hospital) selected;

        String name = getString("Please input the name of the procedure",
          dialogTitle);

        if (name == null) {
          return;
        }

        String description =
            getString("Please input the description of the procedure",
          dialogTitle);
        
        if (description == null) {
          return;
        }

        Boolean isElective = getYesNo("Is the procedure elective?",
          dialogTitle);

        if (isElective == null) {
          return;
        }

        Double cost = getDoubleValue("Please input the base cost of the procedure",
          dialogTitle);

        if (cost == null) {
          return;
        }

        procedureModel.addProcedure(hospital,
          name,
          description,
          isElective,
          cost);

        break;
      } catch (NoHospitalsAvailableException nfe) {
        addHospital(hospitalModel);
      }
    }
  }

  /**
   * This function prompts the user to enter a string
   *
   * @param message dialog message
   * @param title dialog title
   * @return input string
   */
  private static String getString(String message, String title) {
    String input =
        (String) JOptionPane
          .showInputDialog(null,
                           message,
                           title,
          JOptionPane.QUESTION_MESSAGE);
    return input;
  }

  /**
   * This function prompts the user for a decimal value
   *
   * @param message dialog message
   * @param title dialog title
   * @return input decimal
   */
  private static Double getDoubleValue(String message, String title) {
    while (true) {
      String input = getString(message, title);
      try {
        if (input == null) {
          return null;
        }

        Double value = Double.parseDouble(input);
        return value;
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(
            null,
            input
            + " is not a decimal number!",
            "Failure",
          JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * This function prompts the user for yes or no
   *
   * @param message dialog message
   * @param title dialog title
   * @return true if yes, no otherwise
   */
  private static Boolean getYesNo(String message, String title) {
    int status =
        JOptionPane
          .showConfirmDialog(null,
                             message,
                             title,
          JOptionPane.YES_NO_CANCEL_OPTION);

    // The optionPane was closed
    if (status != JOptionPane.CLOSED_OPTION) {
      boolean isYes = status == JOptionPane.YES_OPTION ? true : false;
      return isYes;
    }

    return null;
  }
}
