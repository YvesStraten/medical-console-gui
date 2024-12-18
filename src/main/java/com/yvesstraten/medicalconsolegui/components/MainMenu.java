package com.yvesstraten.medicalconsolegui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.Procedure;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;

public class MainMenu extends ApplicationPane {
  private JTable currentTable;
  private JScrollPane scrollPane;

  public MainMenu(HealthService service, MedicalTabsPanel tabs) {
    super(service, tabs);
    setLayout(new BorderLayout(0, 30));
    JTable table =
        new MedicalTable(
            new HospitalTableModel(getService().getHospitals().toList()), tabs, service);
    setCurrentTable(table);
    JScrollPane scrollPane = new JScrollPane(getCurrentTable());
    setScrollPane(scrollPane);

    JPanel selectorPanel = new JPanel();
    JComboBox<String> comboBox =
        new JComboBox<String>(new String[] {"Hospitals", "Clinics", "Patients"});

    JButton addButton = new JButton("+");
    addButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String selectedItem = (String) comboBox.getSelectedItem();
            if (selectedItem.equals(comboBox.getItemAt(0))) {
              List<Procedure> procedures =
                  new ArrayList<Procedure>(
                      service.getHospitals().flatMap(Hospital::getProceduresStream).toList());
              Hospital hospital =
                  new Hospital(service.getIdDispenser().next(), "New hospital name");
              HospitalViewController viewPanel = HospitalViewController.getAddController(service);

              ActionListener saveListener =
                  new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      service.addMedicalFacility(hospital);
                      tabs.remove(tabs.getSelectedIndex());
                      tabs.setSelectedIndex(0);

                      setCurrentTable(
                          new MedicalTable(
                              new HospitalTableModel(service.getHospitals().toList()),
                              tabs,
                              service));
                      getScrollPane().setViewportView(getCurrentTable());
                    }
                  };

              tabs.addMedicalTab("Adding hospital");

            } else if (selectedItem.equals(comboBox.getItemAt(1))) {

            }
          }
        });

    comboBox.addItemListener(
        new ItemListener() {

          @Override
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
              TableModel tableModel = null;
              int selectedIndex = comboBox.getSelectedIndex();

              // TODO: Fix so that we dont have to
              // instantiate everytime
              if (selectedIndex == 0) {
                tableModel = new HospitalTableModel(service.getHospitals().toList());
              } else if (selectedIndex == 1) {
                tableModel = new ClinicTableModel(service.getClinics().toList());
              } else if (selectedIndex == 2) {
                tableModel = new PatientTableModel(service.getPatients());
              }

              getCurrentTable().setModel(tableModel);
            }
          }
        });

    // Add components to selector panel
    selectorPanel.add(comboBox);
    selectorPanel.add(addButton);

    // Add components to frame
    add(selectorPanel, BorderLayout.NORTH);
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
