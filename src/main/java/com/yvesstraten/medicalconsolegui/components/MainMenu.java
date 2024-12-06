package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MainMenu extends ApplicationPane {
  private JTable currentTable;
  private JScrollPane scrollPane;

  public MainMenu(HealthService service, JTabbedPane tabs) {
    super(service, tabs);
    setLayout(new BorderLayout(0, 30));
    JTable table = new MedicalTable(new HospitalTableModel(getService().getHospitals().toList()), tabs);
    setCurrentTable(table);
    JScrollPane scrollPane = new JScrollPane(getCurrentTable());
    setScrollPane(scrollPane);

    JComboBox<String> comboBox =
        new JComboBox<String>(new String[] {"Hospitals", "Clinics", "Patients"});

    comboBox.addItemListener(
        new ItemListener() {

          @Override
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              TableModel tableModel = null;
              String item = (String) e.getItem();

              // TODO: Fix so that we dont have to
              // instantiate everytime
              if (item.equals("Hospitals")) {
                tableModel = new HospitalTableModel(service.getHospitals().toList());
              } else if (item.equals("Clinics")) {
                tableModel = new ClinicTableModel(service.getClinics().toList());
              } else if (item.equals("Patients")){
                tableModel = new PatientTableModel(service.getPatients());
              }

              setCurrentTable(new MedicalTable(tableModel, tabs));
              getScrollPane().setViewportView(getCurrentTable());
            }
          }
        });

    add(comboBox, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);

    doLayout();
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
