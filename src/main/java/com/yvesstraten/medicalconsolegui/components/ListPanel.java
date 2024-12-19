package com.yvesstraten.medicalconsolegui.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;

public class ListPanel extends JPanel {
  private JTable currentTable;
  private JScrollPane scrollPane;

  public ListPanel(HealthService service){
    setLayout(new GridLayout(1, 2));
    HospitalTableModel hospitalTableModel = new HospitalTableModel(service);
    ClinicTableModel clinicTableModel = new ClinicTableModel(service);
    PatientTableModel patientTableModel = new PatientTableModel(service);
    ProcedureTableModel procedureTableModel = new ProcedureTableModel(service);

    JTable listTable = new JTable(hospitalTableModel);

    setCurrentTable(listTable);
    JScrollPane scrollPane = new JScrollPane(getCurrentTable());
    setScrollPane(scrollPane);

    JPanel selectorPanel = new JPanel();
    // ComboxBox to select between lists
    JComboBox<String> comboBox =
        new JComboBox<String>(new String[] {"Hospitals", "Clinics", "Patients", "Procedures"});

    comboBox.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              TableModel tableModel = null;
              int selectedIndex = comboBox.getSelectedIndex();

              if (selectedIndex == 0) {
                tableModel = hospitalTableModel;
              } else if (selectedIndex == 1) {
                tableModel = clinicTableModel;
              } else if (selectedIndex == 2) {
                tableModel = patientTableModel;
              } else {
                tableModel = procedureTableModel;
              }

              getCurrentTable().setModel(tableModel);
            }
          }
        });

    // Add components to selector panel
    selectorPanel.add(comboBox);

    // Add components to panel
    add(selectorPanel);
    add(scrollPane);
  }

  public JTable getCurrentTable() {
    return this.currentTable;
  }

  public JScrollPane getScrollPane() {
    return this.scrollPane;
  }

  public void setCurrentTable(JTable table) {
    this.currentTable = table;
  }

  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }
  
}
