package com.yvesstraten.medicalconsolegui.models;

import com.yvesstraten.medicalconsole.HealthService;
import javax.swing.table.AbstractTableModel;

/**
 * This class represents a default 
 * medical table model. The first column 
 * should never be editable as it is the id
 *
 * @author YvesStraten e2400068
 */
public abstract class MedicalTableModel extends AbstractTableModel {
  /**
   * Service used
   */
  private HealthService service;

  /**
   * <p>Constructor for MedicalTableModel.</p>
   *
   * @param service service to use
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
   * @return set service
   */
  public HealthService getService() {
    return service;
  }

  /**
   * <p>Setter for the field <code>service</code>.</p>
   *
   * @param service service to set
   */
  public void setService(HealthService service) {
    this.service = service;
  }
}
