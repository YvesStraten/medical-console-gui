package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsolegui.models.FacilityTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MainMenu extends ApplicationPane {
  private JTable currentTable;
  private JScrollPane scrollPane;

  public MainMenu(HealthService service, MedicalTabsPanel tabs) {
    super(service, tabs);
    setLayout(new BorderLayout(0, 30));
    JTable table = new MedicalTable(new FacilityTableModel(service), tabs, service);

    FacilityTableModel facilityTableModel = new FacilityTableModel(service);
    PatientTableModel patientTableModel = new PatientTableModel(service);
    ProcedureTableModel procedureTableModel = new ProcedureTableModel(service);

    setCurrentTable(table);
    JScrollPane scrollPane = new JScrollPane(getCurrentTable());
    setScrollPane(scrollPane);

    JPanel selectorPanel = new JPanel();
    JComboBox<String> comboBox =
        new JComboBox<String>(new String[] {"Facilities", "Patients", "Procedures"});

    JButton addButton = new JButton("+");
    // Triggers when trying to add a new Object
    addButton.addActionListener(
        new ActionListener() {
          ObjectViewController controller = null;

          @Override
          public void actionPerformed(ActionEvent e) {
            String selectedItem = (String) comboBox.getSelectedItem();
            if (selectedItem.equals(comboBox.getItemAt(0))) {
              String[] options = new String[] {"Clinic", "Hospital"};

              String selectedOption =
                  (String)
                      JOptionPane.showInputDialog(
                          null,
                          "Please select which type of facility you wish to add:",
                          "Select type",
                          JOptionPane.QUESTION_MESSAGE,
                          null,
                          options,
                          options[0]);
              if (selectedOption.equals(options[0])) {
                controller =
                    FacilityViewController.getAddClinicController(service, facilityTableModel);
              } else {
                controller =
                    FacilityViewController.getAddClinicController(service, facilityTableModel);
              }
            } else if (selectedItem.equals(comboBox.getItemAt(1))) {
              controller = PatientViewController.getAddController(service, patientTableModel);
            }
            tabs.addMedicalTab("Adding " + selectedItem, controller);
            controller
                .getView()
                .getSaveButton()
                .addActionListener(
                    new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                        tabs.removeMedicalTab(controller);
                      }
                    });
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
                tableModel = facilityTableModel;
              } else if (selectedIndex == 1) {
                tableModel = patientTableModel;
              } else if (selectedIndex == 2) {
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
