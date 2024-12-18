package com.yvesstraten.medicalconsolegui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;

public class ClinicViewController extends ObjectViewController {
    public ClinicViewController(ClinicViewPanel view, HealthService model) {
        super(view, model);
        view.deleteView(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              model.getMedicalFacilities().remove(view.getClinic());
          }
        });
    }

    public static ClinicViewController getAddController(HealthService model){
        model.initializeClinic("", 0, 0);
        ArrayList<MedicalFacility> clinics = model.getMedicalFacilities();
        Clinic generated = ((Clinic) clinics.get(clinics.size()));
        return new ClinicViewController(ClinicViewPanel.getAddPanel(generated), model);
    }

    public static ClinicViewController getViewController(HealthService model, Clinic selected){
        return new ClinicViewController(ClinicViewPanel.getViewPanel(selected), model);
    }
}
