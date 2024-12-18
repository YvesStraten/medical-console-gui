package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.editors.ViewButtonEditor;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.renderers.MedicalTableButtonRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MedicalTable extends JTable {
  public MedicalTable() {
    throw new UnsupportedOperationException("Unsupported constructor " + " please provide a model");
  }

  public MedicalTable(TableModel model, MedicalTabsPanel tabs, HealthService service) {
    super(model);
    setAutoCreateRowSorter(true);

    ActionListener onView =
        new ActionListener() {
          ObjectViewController viewController = null;

          @Override
          public void actionPerformed(ActionEvent e) {
            TableModel currentModel = getModel();
            int selectedRow = getSelectedRow();
            // Tab title
            String tabTitle = null;

            if (currentModel instanceof HospitalTableModel) {
              viewController =
                  HospitalViewController.getViewController(
                      (HospitalTableModel) currentModel, selectedRow);
              HospitalViewPanel panel = (HospitalViewPanel) viewController.getView();
              tabTitle =
                  String.format(
                      "Hospital %d %s", panel.getHospital().getId(), panel.getHospital().getName());
            } else if (currentModel instanceof ClinicTableModel) {
              viewController =
                  ClinicViewController.getViewController(
                      (ClinicTableModel) currentModel, selectedRow);
              ClinicViewPanel panel = (ClinicViewPanel) viewController.getView();
              tabTitle =
                  String.format(
                      "Clinic %d %s", panel.getClinic().getId(), panel.getClinic().getName());
            } else if (currentModel instanceof PatientTableModel) {
              viewController =
                  PatientViewController.getViewController(
                      (PatientTableModel) currentModel, selectedRow);
              PatientViewPanel panel = (PatientViewPanel) viewController.getView();
              ViewObjectButton viewButton = panel.getViewButton();
              if (viewButton != null) {
                viewButton.addActionListener(
                    new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        MedicalFacility current = panel.getPatient().getCurrentFacility();
                        ObjectViewController alternateViewController = null;
                        String base = null;
                        if (current instanceof Clinic) {
                          ArrayList<Clinic> clinics =
                              service.getClinics().collect(Collectors.toCollection(ArrayList::new));
                          alternateViewController =
                              ClinicViewController.getViewController(
                                  new ClinicTableModel(clinics), clinics.indexOf(current));
                          base = "Clinic";
                        } else if (current instanceof Hospital) {
                          ArrayList<Hospital> hospitals =
                              service
                                  .getHospitals()
                                  .collect(Collectors.toCollection(ArrayList::new));
                          alternateViewController =
                              HospitalViewController.getViewController(
                                  new HospitalTableModel(hospitals), hospitals.indexOf(current));
                          base = "Hospital";
                        }

                        String format = base + " %d %s";
                        String tabName = String.format(format, current.getId(), current.getName());
                        tabs.addMedicalTab(tabName, alternateViewController);
                      }
                    });
              }

              tabTitle =
                  String.format(
                      "Patient %d %s", panel.getPatient().getId(), panel.getPatient().getName());
            }

            tabs.addMedicalTab(tabTitle, viewController);
            viewController
                .getView()
                .getDeleteButton()
                .addActionListener(
                    new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        tabs.removeMedicalTab(viewController);
                      }
                    });
          }
        };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(onView));
  }
}
