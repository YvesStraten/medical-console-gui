package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import java.awt.event.ActionEvent;

public class PatientViewController extends ObjectViewController {
  private PatientTableModel model;

  public PatientViewController(PatientViewPanel view, PatientTableModel model, int currentRow) {
    super(view, currentRow);
    setModel(model);
    view.getDeleteButton().addActionListener(this);
    view.getEditButton().addActionListener(this);
  }

  public PatientTableModel getModel() {
    return model;
  }

  public void setModel(PatientTableModel model) {
    this.model = model;
  }

  // public static PatientViewController getAddController(
  //     HealthService model, MedicalTabsPanel tabs, Patient selected) {
  //   return new PatientViewController(PatientViewPanel.showAddPanel(selected), tabs, model);
  // }
  //
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
      getView().allowEdit();
    } else if (source instanceof SaveObjectButton) {
      System.out.println("Save");
    }   
  }
}
