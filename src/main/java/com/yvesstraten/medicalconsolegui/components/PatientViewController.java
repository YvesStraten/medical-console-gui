package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PatientViewController extends ObjectViewController {
  private PatientTableModel model;

  public PatientViewController(PatientViewPanel view, PatientTableModel model) {
    this(view, model, -1);
  }

  public PatientViewController(PatientViewPanel view, PatientTableModel model, int currentRow) {
    super(view, currentRow);
    setModel(model);
  }

  public PatientTableModel getModel() {
    return model;
  }

  public void setModel(PatientTableModel model) {
    this.model = model;
  }

  public static PatientViewController getAddController(
      HealthService service, PatientTableModel model) {
    return new PatientViewController(
        PatientViewPanel.showAddPanel(service.getIdDispenser().next()), model);
  }

  public static PatientViewController getViewController(PatientTableModel model, int currentIndex) {
    Patient clinic = model.getPatients().get(currentIndex);
    return new PatientViewController(PatientViewPanel.showViewPanel(clinic), model, currentIndex);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source instanceof DeleteObjectButton) {
      getModel().deletePatient(getSelectedRow());
    } else if (source instanceof EditObjectButton) {
      getView().enableInputComponents();
    } else if (source instanceof SaveObjectButton) {
      PatientViewPanel view = (PatientViewPanel) getView();
      try {
        if (getSelectedRow() == -1) {
          getModel().addPatient(view.getPatient());
          JOptionPane.showMessageDialog(
              view, "Added patient successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } else {
          getModel().setPatient(getSelectedRow(), view.getPatient());
          JOptionPane.showMessageDialog(
              view, "Updated patient successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(
            getView(), nfe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public String getTitle() {
    Patient patient = ((PatientViewPanel) getView()).getPatient();

    return String.format("Patient %d %s", patient.getId(), patient.getName());
  }
}
