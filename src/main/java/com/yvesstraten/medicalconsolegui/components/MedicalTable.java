package com.yvesstraten.medicalconsolegui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.yvesstraten.medicalconsolegui.editors.ViewButtonEditor;
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
        System.out.println(e.toString());
        JPanel viewPanel = new JPanel();
        for(int i = 0; i < tabs.getTabCount(); i++){
          Component tabComp = tabs.getComponentAt(i);

          // TODO: Create ViewPanel and add equals method
          // to prevent duplicate tabs
          if(tabComp.equals(viewPanel)){
            System.out.println("THere is already a tab!");
            noDups = false;
            break;
          }
        }

        if(noDups){
          tabs.addTab("Test", viewPanel);
        }
      }
    };

    setDefaultRenderer(ViewObjectButton.class, new MedicalTableButtonRenderer());
    setDefaultEditor(ViewObjectButton.class, new ViewButtonEditor(listener));
  }
}
