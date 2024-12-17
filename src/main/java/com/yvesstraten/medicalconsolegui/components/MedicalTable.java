package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
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

    ActionListener listener =
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // TODO add remaining tab cases
            if (model instanceof ClinicTableModel) {
              Clinic selected = service.getClinics().toList().get(getSelectedRow());
              ClinicViewController controller =
                  new ClinicViewController(new ClinicViewPanel(selected), service);
              ObjectViewPanel view = controller.getView();
              ActionListener clinicEdit =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      // TODO Auto-generated method stub
                      throw new UnsupportedOperationException(
                          "Unimplemented method 'actionPerformed'");
                    }
                  };

              ActionListener clinicDelete =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      service.getMedicalFacilities().remove(selected);

                      tabs.remove(tabs.indexOfComponent(view));
                      setModel(new ClinicTableModel(service.getClinics().toList()));
                    }
                  };

              tabs.addMedicalTab(
                  String.format("Clinic %d %s", selected.getId(), selected.getName()),
                  view,
                  clinicDelete,
                  clinicEdit);
            } else if (model instanceof HospitalTableModel) {
              Hospital selected = service.getHospitals().toList().get(getSelectedRow());
              HospitalViewController controller =
                  new HospitalViewController(new HospitalViewPanel(selected), service);
              ObjectViewPanel view = controller.getView();
              ActionListener deleteHospital =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      service.getMedicalFacilities().remove(selected);
                      tabs.remove(tabs.indexOfComponent(view));
                      setModel(new HospitalTableModel(service.getHospitals().toList()));
                    }
                  };

              ActionListener editHospital =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      // TODO Auto-generated method stub
                      throw new UnsupportedOperationException(
                          "Unimplemented method 'actionPerformed'");
                    }
                  };

              tabs.addMedicalTab(
                  String.format("Hospital %d %s", selected.getId(), selected.getName()),
                  view,
                  deleteHospital,
                  editHospital);

            } else if (model instanceof PatientTableModel)
              throw new UnsupportedOperationException("IMPLEMENT!");
          }
        };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(listener));
  }
}
