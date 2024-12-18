package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsolegui.editors.ViewButtonEditor;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.renderers.MedicalTableButtonRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

            if (currentModel instanceof ClinicTableModel) {
              Clinic selected = service.getClinics().toList().get(selectedRow);
              viewController =
                  ClinicViewController.getViewController(service, selected);
              tabTitle = String.format("Clinic %d %s", selected.getId(), selected.getName());

            } else if (currentModel instanceof HospitalTableModel) {
              Hospital selected = service.getHospitals().toList().get(selectedRow);
              viewController = HospitalViewController.getViewController(service, selected);

              tabTitle = String.format("Hospital %d %s", selected.getId(), selected.getName());
            } else if (currentModel instanceof PatientTableModel) {
              Patient selected = service.getPatients().get(selectedRow);
              viewController = PatientViewController.getViewPanel(service, tabs, selected);
              tabTitle = String.format("Patient %d %s", selected.getId(), selected.getName());
            }

            viewController
                .getView()
                .deleteView(
                    new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        tabs.remove(tabs.indexOfComponent(viewController.getView()));
                      }
                    });

            tabs.addMedicalTab(tabTitle, viewController);
          }
        };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(onView));
  }
}
