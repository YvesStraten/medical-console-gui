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
 * <p>ListPanel class.</p>
 *
 * @author YvesStraten e2400068
 */
public class ListPanel extends JPanel {
  private JTable currentTable;
  private JScrollPane scrollPane;
  private HospitalTableModel hospitalTableModel;
  private ClinicTableModel clinicTableModel;
  private PatientTableModel patientTableModel;
  private ProcedureTableModel procedureTableModel;
  private TableModel[] tableModels;

  /**
   * <p>Constructor for ListPanel.</p>
   *
   * @param service a {@link com.yvesstraten.medicalconsole.HealthService} object
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
   * @return a {@link javax.swing.JTable} object
   */
  public JTable getCurrentTable() {
    return this.currentTable;
  }

  /**
   * <p>Getter for the field <code>scrollPane</code>.</p>
   *
   * @return a {@link javax.swing.JScrollPane} object
   */
  public JScrollPane getScrollPane() {
    return this.scrollPane;
  }

  /**
   * <p>Setter for the field <code>currentTable</code>.</p>
   *
   * @param table a {@link javax.swing.JTable} object
   */
  public void setCurrentTable(JTable table) {
    this.currentTable = table;
  }

  /**
   * <p>Setter for the field <code>scrollPane</code>.</p>
   *
   * @param scrollPane a {@link javax.swing.JScrollPane} object
   */
  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  /**
   * <p>Getter for the field <code>hospitalTableModel</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsolegui.models.HospitalTableModel} object
   */
  public HospitalTableModel getHospitalTableModel() {
    return hospitalTableModel;
  }

  /**
   * <p>Setter for the field <code>hospitalTableModel</code>.</p>
   *
   * @param hospitalTableModel a {@link com.yvesstraten.medicalconsolegui.models.HospitalTableModel} object
   */
  public void setHospitalTableModel(HospitalTableModel hospitalTableModel) {
    this.hospitalTableModel = hospitalTableModel;
  }

  /**
   * <p>Getter for the field <code>clinicTableModel</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsolegui.models.ClinicTableModel} object
   */
  public ClinicTableModel getClinicTableModel() {
    return clinicTableModel;
  }

  /**
   * <p>Setter for the field <code>clinicTableModel</code>.</p>
   *
   * @param clinicTableModel a {@link com.yvesstraten.medicalconsolegui.models.ClinicTableModel} object
   */
  public void setClinicTableModel(ClinicTableModel clinicTableModel) {
    this.clinicTableModel = clinicTableModel;
  }

  /**
   * <p>Getter for the field <code>patientTableModel</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsolegui.models.PatientTableModel} object
   */
  public PatientTableModel getPatientTableModel() {
    return patientTableModel;
  }

  /**
   * <p>Setter for the field <code>patientTableModel</code>.</p>
   *
   * @param patientTableModel a {@link com.yvesstraten.medicalconsolegui.models.PatientTableModel} object
   */
  public void setPatientTableModel(PatientTableModel patientTableModel) {
    this.patientTableModel = patientTableModel;
  }

  /**
   * <p>Getter for the field <code>procedureTableModel</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsolegui.models.ProcedureTableModel} object
   */
  public ProcedureTableModel getProcedureTableModel() {
    return procedureTableModel;
  }

  /**
   * <p>Setter for the field <code>procedureTableModel</code>.</p>
   *
   * @param procedureTableModel a {@link com.yvesstraten.medicalconsolegui.models.ProcedureTableModel} object
   */
  public void setProcedureTableModel(ProcedureTableModel procedureTableModel) {
    this.procedureTableModel = procedureTableModel;
  }

  /**
   * <p>Getter for the field <code>tableModels</code>.</p>
   *
   * @return an array of {@link javax.swing.table.TableModel} objects
   */
  public TableModel[] getTableModels() {
    return tableModels;
  }

  /**
   * <p>Setter for the field <code>tableModels</code>.</p>
   *
   * @param tableModels an array of {@link javax.swing.table.TableModel} objects
   */
  public void setTableModels(TableModel[] tableModels) {
    this.tableModels = tableModels;
  }

  /**
   * <p>setUpListeners.</p>
   *
   * @param listener a {@link javax.swing.event.TableModelListener} object
   */
  public void setUpListeners(TableModelListener listener) {
    for (TableModel model : getTableModels()) {
      model.addTableModelListener(listener);
    }
  }
}
