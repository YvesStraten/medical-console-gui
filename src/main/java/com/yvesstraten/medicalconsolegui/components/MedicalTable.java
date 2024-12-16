package com.yvesstraten.medicalconsolegui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsolegui.editors.ViewButtonEditor;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.renderers.MedicalTableButtonRenderer;

public class MedicalTable extends JTable {
  public MedicalTable(){
    throw new UnsupportedOperationException("Unsupported constructor " + " please provide a model");
  }

    public MedicalTable(TableModel model, JTabbedPane tabs, HealthService service){
    super(model);
    setAutoCreateRowSorter(true);

    ActionListener listener = new ActionListener() {
      boolean noDups = true;
      @Override
      public void actionPerformed(ActionEvent e) {
        if(noDups){
          JPanel panelToAdd = null;
          if(model instanceof ClinicTableModel){
              Clinic selected = service.getClinics().toList().get(getSelectedRow());
                    ClinicViewController controller = new ClinicViewController(new ClinicViewPanel(selected), service);

                    controller.getView().deleteView(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            service.getMedicalFacilities().remove(service.getClinics().toList().get(getSelectedRow()));
                        }
                    	
                    });

            panelToAdd = controller.getView();
          }
          tabs.addTab("Test", panelToAdd);
        }
      }
    };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(listener));
  }
}
