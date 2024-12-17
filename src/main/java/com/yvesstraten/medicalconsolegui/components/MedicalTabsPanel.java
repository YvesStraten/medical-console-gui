package com.yvesstraten.medicalconsolegui.components;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

public class MedicalTabsPanel extends JTabbedPane {
    public MedicalTabsPanel(){
        super();
    }

    public boolean isDuplicate(Component comp){
          Component[] components = getComponents();
          boolean isDuplicate = false;
          for(int i = 0; i < components.length; i++){
            Component current = components[i];
            if(current.equals(comp)){
              setSelectedIndex(i);
              isDuplicate = true;
            }
          }

          return isDuplicate;
    }

    public void addMedicalTab(String title, ObjectViewPanel panel, ActionListener deleteAction, ActionListener editAction){
        if(!isDuplicate(panel)){
            addTab(title, panel);
            panel.deleteView(deleteAction);
            panel.editView(editAction);
        }
    }
}
