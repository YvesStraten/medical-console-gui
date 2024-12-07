package com.yvesstraten.medicalconsolegui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsolegui.editors.ViewButtonEditor;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.renderers.MedicalTableButtonRenderer;

public class MedicalTable extends JTable {
  public MedicalTable(){
    throw new UnsupportedOperationException("Unsupported constructor " + " please provide a model");
  }

  public MedicalTable(TableModel model, JTabbedPane tabs){
    super(model);
    setAutoCreateRowSorter(true);

    ActionListener listener = new ActionListener() {
      boolean noDups = true;
      @Override
      public void actionPerformed(ActionEvent e) {
        // TODO fix
        // System.out.println(e.toString());
        // JPanel viewPanel = new JPanel();
        // for(int i = 0; i < tabs.getTabCount(); i++){
        //   Component tabComp = tabs.getComponentAt(i);

        //   // TODO: Create ViewPanel and add equals method
        //   // to prevent duplicate tabs
        //   if(tabComp.equals(viewPanel)){
        //     System.out.println("THere is already a tab!");
        //     noDups = false;
        //     break;
        //   }
        // }

        if(noDups){
          JPanel panelToAdd = null;
          if(model instanceof ClinicTableModel){
            ClinicTableModel clinicModel = (ClinicTableModel) model;
            System.out.println("Selected row" + getSelectedRow());
            Clinic selectedClinic = clinicModel.getClinics().get(getSelectedRow());
            panelToAdd = new ClinicViewPanel(selectedClinic);
          }
          tabs.addTab("Test", panelToAdd);
        }
      }
    };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(listener));
  }
}
