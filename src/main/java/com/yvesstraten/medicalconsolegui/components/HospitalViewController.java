package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HospitalViewController extends ObjectViewController {
  public HospitalViewController(HospitalViewPanel view, HealthService model) {
    super(view, model);
  }

  public static HospitalViewController getAddController(HealthService model) {
    model.initializeHospital("");
    Hospital generated = model.getHospitals().toList().get(0);
    ArrayList<Procedure> procedures =
        new ArrayList<Procedure>(
            model.getHospitals().flatMap(Hospital::getProceduresStream).toList());
    return new HospitalViewController(HospitalViewPanel.showAddPanel(generated, procedures), model);
  }

  public static HospitalViewController getViewController(HealthService model, Hospital hospital) {
    return new HospitalViewController(
        HospitalViewPanel.showViewPanel(hospital, hospital.getProcedures()), model);
  }
}
