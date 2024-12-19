package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.exceptions.NoHospitalsAvailableException;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AddButtonsPane extends JPanel {
  private ListPanel listPanel;

  public AddButtonsPane(ListPanel listPanel) {
    super();
    setListPanel(listPanel);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Upper label
    JLabel label = new JLabel("Add Item", JLabel.CENTER);
    label.setAlignmentX(CENTER_ALIGNMENT);
    label.setAlignmentX(CENTER_ALIGNMENT);

    // Add buttons
    JPanel buttonsGrouper = new JPanel();
    buttonsGrouper.setLayout(new GridLayout(2, 2, 15, 15));
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
            while (true) {
              try {
                addProcedure(getListPanel().getProcedureTableModel());
                break;
              } catch (NoHospitalsAvailableException nha) {
                JOptionPane.showMessageDialog(
                    addProcedureButton,
                    nha.getMessage(),
                    "No hospitals added",
                    JOptionPane.ERROR_MESSAGE);
                addHospital(getListPanel().getHospitalTableModel());
              }
            }
          }
        });

    buttonsGrouper.add(addHospitalButton);
    buttonsGrouper.add(addClinicButton);
    buttonsGrouper.add(addPatientButton);
    buttonsGrouper.add(addProcedureButton);

    // Add components
    add(label);
    add(buttonsGrouper);
  }

  public ListPanel getListPanel() {
    return listPanel;
  }

  public void setListPanel(ListPanel listPanel) {
    this.listPanel = listPanel;
  }

  private void addHospital(HospitalTableModel hospitalModel) {
    String name = getString(this, "Please input the name for the hospital", "Adding a hospital");

    hospitalModel.addHospital(name);
  }

  private void addClinic(ClinicTableModel clinicModel) {
    String dialogTitle = "Adding a clinic";
    String name = getString(this, "Please input the name for the clinic", dialogTitle);
    Double fee = getDoubleValue(this, "Please input the fee for the clinic", dialogTitle);
    Double gapPercent =
        getDoubleValue(this, "Please input the gap percentage for the clinic", dialogTitle);
    clinicModel.addClinic(name, fee, gapPercent);
  }

  private void addPatient(PatientTableModel patientModel) {
    String dialogTitle = "Adding a patient";
    String name = getString(this, "Please input the name for the patient", dialogTitle);
    boolean isPrivate = getYesNo(this, "Is the patient private?", dialogTitle);

    patientModel.addPatient(name, isPrivate);
  }

  private void addProcedure(ProcedureTableModel procedureModel)
      throws NoHospitalsAvailableException {
    String dialogTitle = "Adding procedure";
    Hospital[] hospitals = procedureModel.getService().getHospitals().toArray(Hospital[]::new);
    if (hospitals.length == 0) {
      throw new NoHospitalsAvailableException();
    }

    Hospital selected =
        (Hospital)
            getObject(this, "Please select a hospital first", dialogTitle, hospitals, hospitals[0]);

    String name = getString(this, "Please input the name of the procedure", dialogTitle);
    String description =
        getString(this, "Please input the description of the procedure", dialogTitle);
    boolean isElective = getYesNo(this, "Is the procedure elective?", dialogTitle);
    double cost = getDoubleValue(this, "Please input the base cost of the procedure", dialogTitle);

    procedureModel.addProcedure(selected, name, description, isElective, cost);
  }

  private static Object getObject(
      Component parent, String message, String title, Object[] options, Object preselected) {
    while (true) {
      Object selected =
          JOptionPane.showInputDialog(
              parent, message, title, JOptionPane.QUESTION_MESSAGE, null, options, preselected);

      if (selected != null) {
        return selected;
      } else {
        JOptionPane.showMessageDialog(
            parent, "Invalid input, please try again", title, JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private static String getString(Component parent, String message, String title) {
    while (true) {
      String input =
          (String)
              JOptionPane.showInputDialog(parent, message, title, JOptionPane.QUESTION_MESSAGE);
      if (input != null && input.length() != 0) {
        return input;
      } else {
        JOptionPane.showMessageDialog(
            parent, "Invalid input, please try again", title, JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private static Double getDoubleValue(Component parent, String message, String title) {
    while (true) {
      String input = getString(parent, message, title);
      try {
        Double value = Double.parseDouble(input);
        return value;
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(
            parent, input + " is not a decimal number!", "Failure", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private static boolean getYesNo(Component parent, String message, String title) {
    while (true) {
      int status = JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION);

      // The optionPane was closed
      if (status != JOptionPane.CLOSED_OPTION) {
        boolean isYes = status == JOptionPane.YES_OPTION ? true : false;
        return isYes;
      }
    }
  }
}
