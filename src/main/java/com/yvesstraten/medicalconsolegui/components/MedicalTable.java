package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.editors.ViewButtonEditor;
import com.yvesstraten.medicalconsolegui.editors.VisitButtonEditor;
import com.yvesstraten.medicalconsolegui.models.FacilityTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.renderers.MedicalTableButtonRenderer;
import com.yvesstraten.medicalconsolegui.renderers.VisitButtonRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
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
            if (currentModel instanceof FacilityTableModel) {
              // Register a hospital view tab
              viewController =
                  FacilityViewController.getViewController(
                      (FacilityTableModel) currentModel, selectedRow);
            } else if (currentModel instanceof PatientTableModel) {
              // Register a patient view tab
              viewController =
                  PatientViewController.getViewController(
                      (PatientTableModel) currentModel, selectedRow);
            }

            tabs.addMedicalTab(viewController.getTitle(), viewController);
            // Unregister tab when object is removed from model
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

    ActionListener onVisit =
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            MedicalFacility[] facilities =
                service.getMedicalFacilitiesStream().toArray(MedicalFacility[]::new);
            

            MedicalFacility selected =
                (MedicalFacility)
                    JOptionPane.showInputDialog(
                        null,
                        "Please input facility to visit",
                        "Facility to visit",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        facilities,
                        facilities[0]);

            if (selected == null) {
              return;
            }

            PatientTableModel current = (PatientTableModel) getModel();
            boolean visitSuccess = selected.visit(current.getPatients().get(getSelectedRow()));

            if (visitSuccess) {
              JOptionPane.showMessageDialog(
                  null,
                  "Patient has successfully visited the facility",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE);
            } else {
              JOptionPane.showMessageDialog(
                  null,
                  "Patient failed to visit the facility",
                  "Failure",
                  JOptionPane.ERROR_MESSAGE);
            }
          }
        };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(onView));
    setDefaultRenderer(SimulateVisitButton.class, new VisitButtonRenderer());
    setDefaultEditor(SimulateVisitButton.class, new VisitButtonEditor(onVisit));
  }
}
