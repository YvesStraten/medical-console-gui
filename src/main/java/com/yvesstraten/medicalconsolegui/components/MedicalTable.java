package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.Patient;
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
            // Actions
            ActionListener edit = null;
            ActionListener delete = null;
            ActionListener save = null;

            if (currentModel instanceof ClinicTableModel) {
              Clinic selected = service.getClinics().toList().get(selectedRow);
              viewController =
                  new ClinicViewController(new ClinicViewPanel(true, selected), service);
              edit =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      // TODO Auto-generated method stub
                      throw new UnsupportedOperationException(
                          "Unimplemented method 'actionPerformed'");
                    }
                  };

              delete =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      service.getMedicalFacilities().remove(selected);

                      tabs.remove(tabs.indexOfComponent(viewController.getView()));
                      setModel(new ClinicTableModel(service.getClinics().toList()));
                    }
                  };

              tabTitle = String.format("Clinic %d %s", selected.getId(), selected.getName());

            } else if (currentModel instanceof HospitalTableModel) {
              Hospital selected = service.getHospitals().toList().get(selectedRow);
              viewController = HospitalViewController.getViewController(service, selected);

              delete =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      service.getMedicalFacilities().remove(selected);
                      tabs.remove(tabs.indexOfComponent(viewController.getView()));
                      setModel(new HospitalTableModel(service.getHospitals().toList()));
                    }
                  };

              tabTitle = String.format("Hospital %d %s", selected.getId(), selected.getName());
            } else if (currentModel instanceof PatientTableModel) {
              Patient selected = service.getPatients().get(selectedRow);
              viewController =
                  new PatientViewController(new PatientViewPanel(true, selected), service);
              delete =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      service.getPatients().remove(selected);
                      tabs.remove(tabs.indexOfComponent(viewController));
                      setModel(new PatientTableModel(service.getPatients()));
                    }
                  };

              edit =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      // TODO Auto-generated method stub
                      throw new UnsupportedOperationException(
                          "Unimplemented method 'actionPerformed'");
                    }
                  };

              ((PatientViewPanel) viewController.getView())
                  .viewFacility(
                      new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                          MedicalFacility current = selected.getCurrentFacility();
                          ObjectViewController alternateViewController = null;
                          String base = null;
                          if (current instanceof Clinic) {
                            alternateViewController =
                                new ClinicViewController(
                                    new ClinicViewPanel((Clinic) current), service);
                            base = "Clinic";
                          } else if (current instanceof Hospital) {
                            alternateViewController =
                                HospitalViewController.getViewController(
                                    service, (Hospital) current);
                            base = "Hospital";
                          }

                          String format = base + " %d %s";
                          String tabName =
                              String.format(format, current.getId(), current.getName());
                          tabs.addMedicalTab(tabName, alternateViewController, null, null, null);
                        }
                      });
              tabTitle = String.format("Patient %d %s", selected.getId(), selected.getName());
            }

            tabs.addMedicalTab(tabTitle, viewController, delete, edit, save);
          }
        };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(onView));
  }
}
