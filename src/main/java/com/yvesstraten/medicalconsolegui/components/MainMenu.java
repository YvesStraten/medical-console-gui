package com.yvesstraten.medicalconsolegui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.yvesstraten.medicalconsole.HealthService;

public class MainMenu extends JPanel {
  private HealthService service;
  private JPanel buttonsPanel;
  private ListPanel listPanel;

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
    MiscellaniousPane miscellaniousPane = new MiscellaniousPane(listPanel);

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

  public HealthService getService() {
    return service;
  }

  public void setService(HealthService service) {
    this.service = service;
  }

  public ListPanel getListPanel() {
    return listPanel;
  }

  public void setListPanel(ListPanel listPanel) {
    this.listPanel = listPanel;
  }

  public JPanel getButtonsPanel() {
    return buttonsPanel;
  }

  public void setButtonsPanel(JPanel buttonsPanel) {
    this.buttonsPanel = buttonsPanel;
  }
}
