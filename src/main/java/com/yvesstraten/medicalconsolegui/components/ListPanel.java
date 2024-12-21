package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.facilities.Clinic;
import com.yvesstraten.medicalconsole.facilities.Hospital;
import com.yvesstraten.medicalconsole.facilities.MedicalFacility;
import com.yvesstraten.medicalconsolegui.models.ClinicTableModel;
import com.yvesstraten.medicalconsolegui.models.HospitalTableModel;
import com.yvesstraten.medicalconsolegui.models.PatientTableModel;
import com.yvesstraten.medicalconsolegui.models.ProcedureTableModel;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * This panel acts is used for listing 
 * all and allow for the editing of
 * the objects in this application in 
 * a tabular manner
 * @author YvesStraten e2400068
 */
public class ListPanel extends JPanel {
  /**
   * Current selected table
   */
  private JTable currentTable;
  /**
   * Scroll pane for table
   */
  private JScrollPane scrollPane;
  /**
   * hospital table model
   */
  private HospitalTableModel hospitalTableModel;
  /**
   * clinic table model
   */
  private ClinicTableModel clinicTableModel;
  /**
   * patient table model
   */
  private PatientTableModel patientTableModel;
  /**
   * procedure table model
   */
  private ProcedureTableModel procedureTableModel;
  /**
   * list of registered table models 
   */
  private TableModel[] tableModels;

  /**
   * <p>Constructor for ListPanel.</p>
   *
   * @param service service to use 
   */
  public ListPanel(HealthService service) {
    ClinicTableModel clinicTableModel = new ClinicTableModel(service);
    PatientTableModel patientTableModel = new PatientTableModel(service);
    ProcedureTableModel procedureTableModel = new ProcedureTableModel(service);
    HospitalTableModel hospitalTableModel = new HospitalTableModel(service, procedureTableModel);
    setHospitalTableModel(hospitalTableModel);
    setClinicTableModel(clinicTableModel);
    setPatientTableModel(patientTableModel);
    setProcedureTableModel(procedureTableModel);
    setTableModels(
        new TableModel[] {
          hospitalTableModel, clinicTableModel, patientTableModel, procedureTableModel
        });

    JTable listTable =
        new JTable(hospitalTableModel) {
          @Override
          public String getToolTipText(MouseEvent e) {
            String tip = null;
            Point mouse = e.getPoint();
            int rowIndex = rowAtPoint(mouse);
            int columnIndex = columnAtPoint(mouse);
            int realColumnIndex = convertColumnIndexToModel(columnIndex);
            int realRowIndex = convertRowIndexToModel(rowIndex);

            if (getModel() instanceof HospitalTableModel) {
              if (realColumnIndex == 3) {
                Hospital hospital = getHospitalTableModel().getHospitals().get(realRowIndex);
                if (hospital != null) {
                  tip =
                      hospital.getProcedures().stream()
                          .map(
                              procedure ->
                                  procedure.getName() + " " + procedure.getDescription() + "\n")
                          .reduce("", (before, next) -> before + next);
                }
              }
            } else if (getModel() instanceof PatientTableModel) {
              if (realColumnIndex == 4) {
                MedicalFacility facility =
                    getPatientTableModel().getPatients().get(realRowIndex).getCurrentFacility();
                if (facility != null) {
                  if (facility instanceof Clinic) {
                    Clinic clinic = (Clinic) facility;
                    tip =
                        "Clinic "
                            + clinic.getId()
                            + " "
                            + clinic.getName()
                            + " with cost "
                            + clinic.getFee();
                  } else {
                    tip = "Hospital" + facility.getId() + " " + facility.getName();
                  }
                }
              }
            }

            return tip;
          }
        };

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

  /**
   * <p>Getter for the field <code>currentTable</code>.</p>
   *
   * @return current table
   */
  public JTable getCurrentTable() {
    return this.currentTable;
  }

  /**
   * <p>Getter for the field <code>scrollPane</code>.</p>
   *
   * @return scrollpane of table
   */
  public JScrollPane getScrollPane() {
    return this.scrollPane;
  }

  /**
   * <p>Setter for the field <code>currentTable</code>.</p>
   *
   * @param table table to set
   */
  public void setCurrentTable(JTable table) {
    this.currentTable = table;
  }

  /**
   * <p>Setter for the field <code>scrollPane</code>.</p>
   *
   * @param scrollPane scroll pane to set
   */
  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  /**
   * <p>Getter for the field <code>hospitalTableModel</code>.</p>
   *
   * @return hospital model
   */
  public HospitalTableModel getHospitalTableModel() {
    return hospitalTableModel;
  }

  /**
   * <p>Setter for the field <code>hospitalTableModel</code>.</p>
   *
   * @param hospitalTableModel hospital model to set
   */
  public void setHospitalTableModel(HospitalTableModel hospitalTableModel) {
    this.hospitalTableModel = hospitalTableModel;
  }

  /**
   * <p>Getter for the field <code>clinicTableModel</code>.</p>
   *
   * @return clinic model
   */
  public ClinicTableModel getClinicTableModel() {
    return clinicTableModel;
  }

  /**
   * <p>Setter for the field <code>clinicTableModel</code>.</p>
   *
   * @param clinicTableModel clinic model to set
   */
  public void setClinicTableModel(ClinicTableModel clinicTableModel) {
    this.clinicTableModel = clinicTableModel;
  }

  /**
   * <p>Getter for the field <code>patientTableModel</code>.</p>
   *
   * @return patient model
   */
  public PatientTableModel getPatientTableModel() {
    return patientTableModel;
  }

  /**
   * <p>Setter for the field <code>patientTableModel</code>.</p>
   *
   * @param patientTableModel patient model to set
   */
  public void setPatientTableModel(PatientTableModel patientTableModel) {
    this.patientTableModel = patientTableModel;
  }

  /**
   * <p>Getter for the field <code>procedureTableModel</code>.</p>
   *
   * @return procedure model
   */
  public ProcedureTableModel getProcedureTableModel() {
    return procedureTableModel;
  }

  /**
   * <p>Setter for the field <code>procedureTableModel</code>.</p>
   *
   * @param procedureTableModel procedure model to set
   */
  public void setProcedureTableModel(ProcedureTableModel procedureTableModel) {
    this.procedureTableModel = procedureTableModel;
  }

  /**
   * <p>Getter for the field <code>tableModels</code>.</p>
   *
   * @return table models currently present
   */
  public TableModel[] getTableModels() {
    return tableModels;
  }

  /**
   * <p>Setter for the field <code>tableModels</code>.</p>
   *
   * @param tableModels tableModels to set
   */
  public void setTableModels(TableModel[] tableModels) {
    this.tableModels = tableModels;
  }

  /**
   * This function adds given TableModelListener to 
   * all registered table models
   *
   * @param listener listenr to add
   */
  public void setUpListeners(TableModelListener listener) {
    for (TableModel model : getTableModels()) {
      model.addTableModelListener(listener);
    }
  }
}
