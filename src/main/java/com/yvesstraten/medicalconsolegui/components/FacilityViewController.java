package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.models.FacilityTableModel;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class FacilityViewController extends ObjectViewController {
  private FacilityTableModel model;

  public FacilityViewController(MedicalFacilityViewPanel view, FacilityTableModel model){
    this(view, model, -1);
  }

  public FacilityViewController(
      MedicalFacilityViewPanel view, FacilityTableModel model, int selectedRow) {
    super(view, selectedRow);
    setModel(model);
  }

  public FacilityTableModel getModel() {
    return model;
  }

  public void setModel(FacilityTableModel model) {
    this.model = model;
  }

  public static FacilityViewController getAddHospitalController(
      HealthService service, FacilityTableModel model) {
    return new FacilityViewController(
        HospitalViewPanel.showAddPanel(service.getIdDispenser().next()),
        model);
  }

  public static FacilityViewController getAddClinicController(
      HealthService service, FacilityTableModel model) {
    return new FacilityViewController(
        ClinicViewPanel.showAddPanel(service.getIdDispenser().next()),
        model);
  }

  public static FacilityViewController getViewController(
      FacilityTableModel model, int currentIndex) {
    MedicalFacility facility = model.getFacilities().get(currentIndex);
    if (facility instanceof Clinic) {
      return new FacilityViewController(
          ClinicViewPanel.getViewPanel((Clinic) facility), model, currentIndex);
    } else {
      Hospital hospital = (Hospital) facility;
      return new FacilityViewController(
          HospitalViewPanel.showViewPanel(hospital, hospital.getProcedures()), model, currentIndex);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if (source instanceof DeleteObjectButton) {
      getModel().deleteFacility(getSelectedRow());
    } else if (source instanceof EditObjectButton) {
      getView().enableInputComponents();
    } else if (source instanceof SaveObjectButton) {
      MedicalFacilityViewPanel view = (MedicalFacilityViewPanel) getView();
      try {
        MedicalFacility current = view.getFacility();
        // Try to save current changes
        view.save();

        if (getSelectedRow() == -1) {
          System.out.println("Adding");
          getModel().addFacility(current);
          JOptionPane.showMessageDialog(
              view, "Added facility successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
          System.out.println("Updating");
          getModel().updateFacility(getSelectedRow(), current);
          JOptionPane.showMessageDialog(
              view, "Updated facility successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
      } catch (NumberFormatException nfe) {
        JOptionPane.showMessageDialog(
            getView(), nfe.toString(), "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  @Override
  public String getTitle() {
    MedicalFacility facility = ((MedicalFacilityViewPanel) getView()).getFacility();
    String base = "";

    if (facility instanceof Clinic) {
      base = "Clinic";
    } else {
      base = "Hospital";
    }

    String format = base + " %d %s";
    return String.format(format, facility.getId(), facility.getName());
  }
}
