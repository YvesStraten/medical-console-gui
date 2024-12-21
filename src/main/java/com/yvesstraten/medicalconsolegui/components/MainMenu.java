package com.yvesstraten.medicalconsolegui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.yvesstraten.medicalconsole.HealthService;

/**
 * This class acts as the main menu 
 * for the application
 * @author YvesStraten e2400068
 */
public class MainMenu extends JPanel {
  /**
   * Service used
   */
  private HealthService service;
  /**
   * Panel containing utility buttons
   */
  private JPanel buttonsPanel;
  /**
   * Panel containg tabular representation 
   * of all objects in the service
   */
  private ListPanel listPanel;

  /**
   * <p>Constructor for MainMenu.</p>
   *
   * @param service service to use
   */
  public MainMenu(HealthService service) {
    super();
    setService(service);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Instantiate components
    ListPanel listPanel = new ListPanel(service);
    setListPanel(listPanel);

    JPanel buttonsPanel = new JPanel();
    setButtonsPanel(buttonsPanel);
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gridBagConstraints = new GridBagConstraints();

    buttonsPanel.setLayout(gridBagLayout);
    AddButtonsPane addButtonsPane = new AddButtonsPane(listPanel);
    RemoveButtonsPane removeButtonsPane = new RemoveButtonsPane(listPanel);
    MiscellaneousPane miscellaniousPane = new MiscellaneousPane(listPanel);

    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    buttonsPanel.add(addButtonsPane, gridBagConstraints);
    gridBagConstraints.gridx = 1;
    buttonsPanel.add(removeButtonsPane, gridBagConstraints);

    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    buttonsPanel.add(miscellaniousPane, gridBagConstraints);

    add(buttonsPanel);
    add(listPanel);
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
   * @param service to set
   */
  public void setService(HealthService service) {
    this.service = service;
  }

  /**
   * <p>Getter for the field <code>listPanel</code>.</p>
   *
   * @return list panel
   */
  public ListPanel getListPanel() {
    return listPanel;
  }

  /**
   * <p>Setter for the field <code>listPanel</code>.</p>
   *
   * @param listPanel list panel to set
   */
  public void setListPanel(ListPanel listPanel) {
    this.listPanel = listPanel;
  }

  /**
   * <p>Getter for the field <code>buttonsPanel</code>.</p>
   *
   * @return buttons panel
   */
  public JPanel getButtonsPanel() {
    return buttonsPanel;
  }

  /**
   * <p>Setter for the field <code>buttonsPanel</code>.</p>
   *
   * @param buttonsPanel panel to set as buttons 
   * panel
   */
  public void setButtonsPanel(JPanel buttonsPanel) {
    this.buttonsPanel = buttonsPanel;
  }
}
