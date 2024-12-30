package com.yvesstraten.medicalconsolegui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
   * Button for adding objects 
   */
  private JButton addButton;
  /** 
   * Button for deleting objects 
   */
  private JButton deleteButton;
  /** 
   * Button for miscellaneous operations 
   */
  private JButton miscellaneousButton;

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

    JLabel buttonLabel = new JLabel("The following operations are available:");

    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(gridBagLayout);
    setButtonsPanel(buttonsPanel);

    JButton addObjectButton = new JButton("Add object");
    setAddButton(addObjectButton);
    JButton removeObjectButton = new JButton("Remove object");
    setDeleteButton(removeObjectButton);

    JButton miscellaneousButton = new JButton("Miscellaneous Operations");
    setMiscellaneousButton(miscellaneousButton);

    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    // Add components
    buttonsPanel.add(buttonLabel, gridBagConstraints);

    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = 1;
    buttonsPanel.add(addObjectButton, gridBagConstraints);

    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 1;
    buttonsPanel.add(removeObjectButton, gridBagConstraints);

    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    buttonsPanel.add(miscellaneousButton, gridBagConstraints);

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

  /**
   * <p>Getter for the field <code>addButton</code>.</p>
   *
   * @return add button
   */
  public JButton getAddButton() {
    return addButton;
  }

  /**
   * <p>Setter for the field <code>addButton</code>.</p>
   *
   * @param addButton button to set as add button 
   */
  public void setAddButton(JButton addButton) {
    this.addButton = addButton;
  }

  /**
   * <p>Getter for the field 
   * <code>deleteButton</code>.</p>
   *
   * @return delete button
   */
  public JButton getDeleteButton() {
    return deleteButton;
  }

  /**
   * <p>Setter for the field <code>deleteButton</code>.</p>
   *
   * @param deleteButton button to set as delete button 
   */
  public void setDeleteButton(JButton deleteButton) {
    this.deleteButton = deleteButton;
  }

  /**
   * <p>Getter for the field 
   * <code>miscellaneousButton</code>.</p>
   *
   * @return miscellaneous button
   */
  public JButton getMiscellaneousButton() {
    return miscellaneousButton;
  }

  /**
   * <p>Setter for the field 
   * <code>miscellaneousButton</code>.</p>
   *
   * @param miscellaneousButton button to set
   * as miscellaneous button 
   */
  public void setMiscellaneousButton(JButton miscellaneousButton) {
    this.miscellaneousButton = miscellaneousButton;
  }

  /**
   * This function setups all listeners for 
   * the buttons in the main menu
   */
  public void setupListeners(){
    JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
    getAddButton().addActionListener(
      new ButtonsDialog(
        parent,
        new AddButtonsPane(listPanel)
      ));

    getDeleteButton().addActionListener(
      new ButtonsDialog(
        parent,
        new RemoveButtonsPane(listPanel)
      ));

    getMiscellaneousButton()
      .addActionListener(
        new ButtonsDialog(
          parent,
          new MiscellaneousPane(listPanel)
    ));
  }
}
