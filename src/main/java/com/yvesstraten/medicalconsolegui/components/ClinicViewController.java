package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClinicViewController extends ObjectViewController {
  private ClinicTableModel model;

  public ClinicViewController(ClinicViewPanel view, ClinicTableModel model, int selectedRow) {
    super(view, selectedRow);
    setModel(model);
    view.getEditButton().addActionListener(this);
    view.getDeleteButton().addActionListener(this);
  }

  public ClinicTableModel getModel() {
    return model;
  }

  public void setModel(ClinicTableModel model) {
    this.model = model;
  }

  // public static ClinicViewController getAddController(HealthService model) {
  //   model.initializeClinic("", 0, 0);
  //   ArrayList<MedicalFacility> clinics = model.getMedicalFacilities();
  //   Clinic generated = ((Clinic) clinics.get(clinics.size()));
  //   return new ClinicViewController(ClinicViewPanel.getAddPanel(generated), model);
  // }

  public static ClinicViewController getViewController(ClinicTableModel model, int selectedRow) {
    Clinic clinic = model.getClinics().get(selectedRow);
    return new ClinicViewController(ClinicViewPanel.getViewPanel(clinic), model, selectedRow);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source instanceof DeleteObjectButton) {
      getModel().deleteClinic(getSelectedRow());
    } else if (source instanceof EditObjectButton) {
      System.out.println("ALLOWING TO EDIT");
      getView().allowEdit();
    }
  }
}
