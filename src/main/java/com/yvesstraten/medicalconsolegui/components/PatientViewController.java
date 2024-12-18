package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientViewController extends ObjectViewController {

  public PatientViewController(PatientViewPanel panel, MedicalTabsPanel tabs, HealthService model) {
    super(panel, model);

    panel.deleteView(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            model.getPatients().remove(panel.getPatient());
          }
        });

    panel.viewFacility(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            MedicalFacility current = panel.getPatient().getCurrentFacility();
            ObjectViewController alternateViewController = null;
            String base = null;
            if (current instanceof Clinic) {
              alternateViewController =
                  new ClinicViewController(new ClinicViewPanel((Clinic) current), model);
              base = "Clinic";
            } else if (current instanceof Hospital) {
              alternateViewController =
                  HospitalViewController.getViewController(model, (Hospital) current);
              base = "Hospital";
            }

            String format = base + " %d %s";
            String tabName = String.format(format, current.getId(), current.getName());
            tabs.addMedicalTab(tabName, alternateViewController, null, null, null);
          }
        });
  }

  public static PatientViewController getAddController(
      HealthService model, MedicalTabsPanel tabs, Patient selected) {
    return new PatientViewController(PatientViewPanel.showAddPanel(selected), tabs, model);
  }

  public static PatientViewController getViewPanel(
      HealthService model, MedicalTabsPanel tabs, Patient selected) {
    return new PatientViewController(PatientViewPanel.showViewPanel(selected), tabs, model);
  }
}
