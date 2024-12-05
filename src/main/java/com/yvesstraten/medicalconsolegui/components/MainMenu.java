package com.yvesstraten.medicalconsolegui.components;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;

public class MainMenu extends ApplicationPane {
  private JTable currentTable;
  private JScrollPane scrollPane;

  public MainMenu(HealthService service, JTabbedPane tabs) {
    super(service, tabs);
    JTable table = new MedicalTable(new HospitalTableModel(getService().getHospitals().toList()));
    setCurrentTable(table);
    JScrollPane scrollPane = new JScrollPane(getCurrentTable());
    setScrollPane(scrollPane);

    JComboBox<String> comboBox = new JComboBox<String>(new String[] { "Hospitals", "Patients"});
    comboBox.addItemListener(new ItemListener() {

      @Override
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
          switch((String) e.getItem()){
            case "Patients": 
              setCurrentTable(new MedicalTable());
              break;
          }

          getScrollPane().setViewportView(getCurrentTable());

        }
        System.out.println(e.toString());
        
      }

});

    add(comboBox, BorderLayout.SOUTH);
    add(scrollPane, BorderLayout.CENTER);

    doLayout();
  }

  public JTable getCurrentTable(){
    return this.currentTable;
  } 

  public JScrollPane getScrollPane(){
    return this.scrollPane;
  }

  public void setCurrentTable(JTable table){
    this.currentTable = table;
  }

  public void setScrollPane(JScrollPane scrollPane){
    this.scrollPane = scrollPane;
  }
}
