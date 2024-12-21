package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import javax.swing.table.AbstractTableModel;

/**
 * <p>Abstract MedicalTableModel class.</p>
 *
 * @author YvesStraten e2400068
 */
public abstract class MedicalTableModel extends AbstractTableModel {
  private HealthService service;

  /**
   * <p>Constructor for MedicalTableModel.</p>
   *
   * @param service a {@link com.yvesstraten.medicalconsole.HealthService} object
   */
  public MedicalTableModel(HealthService service) {
    super();
    setService(service);
  }

  /** {@inheritDoc} */
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    if (columnIndex != 0) {
      return true;
    }

    return false;
  }

  /**
   * <p>Getter for the field <code>service</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsole.HealthService} object
   */
  public HealthService getService() {
    return service;
  }

  /**
   * <p>Setter for the field <code>service</code>.</p>
   *
   * @param service a {@link com.yvesstraten.medicalconsole.HealthService} object
   */
  public void setService(HealthService service) {
    this.service = service;
  }
}
