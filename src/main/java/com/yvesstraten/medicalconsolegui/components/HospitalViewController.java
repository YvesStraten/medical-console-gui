package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class HospitalViewController extends ObjectViewController {
  HospitalTableModel model;
  public HospitalViewController(HospitalViewPanel view, HospitalTableModel model, int selectedRow) {
    super(view, selectedRow);
    setModel(model);
    view.getDeleteButton().addActionListener(this);
    view.getEditButton().addActionListener(this);
    view.getSaveButton().addActionListener(this);
  }

  public HospitalTableModel getModel() {
    return model;
  }

  public void setModel(HospitalTableModel model) {
    this.model = model;
  }

  // public static HospitalViewController getAddController(HealthService service, HospitalTableModel model) {
  //   service.initializeHospital("");
  //   Hospital generated = model.getHospitals().toList().get(0);
  //   ArrayList<Procedure> procedures =
  //       new ArrayList<Procedure>(
  //           model.getHospitals().flatMap(Hospital::getProceduresStream).toList());
  //   return new HospitalViewController(HospitalViewPanel.showAddPanel(generated, procedures), model);
  // }
  //
  //
  public static HospitalViewController getViewController(HospitalTableModel model, int currentIndex) {
    Hospital hospital = model.getHospitals().get(currentIndex);

    return new HospitalViewController(
        HospitalViewPanel.showViewPanel(hospital, hospital.getProcedures()), model, currentIndex);
  }

  @Override 
  public void actionPerformed(ActionEvent e){
    Object source = e.getSource();
    if(source instanceof DeleteObjectButton){
      getModel().deleteHospital(getSelectedRow());
    } else if(source instanceof EditObjectButton){
      getView().allowEdit();
    } else if(source instanceof SaveObjectButton) {
      System.out.println("Save");
    }
  }
}
