package com.yvesstraten.medicalconsolegui.components;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MedicalTabsPanel extends JTabbedPane {
  public JPanel getTabComponent(String title) {
    JPanel tab = new JPanel(new GridLayout(1, 2));
    JLabel tabLabel = new JLabel(title);

    JButton closeButton = new JButton("x");
    closeButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            removeTabAt(getSelectedIndex());
          }
        });

    // Needed to make JLabel look the same
    // as default
    tab.setOpaque(false);
    tab.add(tabLabel);
    tab.add(closeButton);

    return tab;
  }

  public MedicalTabsPanel() {
    super();
  }

  public boolean isDuplicate(Component comp) {
    int tabCount = getTabCount();
    boolean isDuplicate = false;
    for (int i = 0; i < tabCount; i++) {
      Component current = getTabComponentAt(i);
      if (current != null) {
        if (current.equals(comp)) {
          setSelectedIndex(i);
          isDuplicate = true;
        }
      }
    }

    return isDuplicate;
  }

  public void addMedicalTab(
      String title,
      ObjectViewController controller) {
    ObjectViewPanel panel = controller.getView();

    if (!isDuplicate(panel)) {
      addTab(title, panel);
      setTabComponentAt(getTabCount() - 1, getTabComponent(title));
      setSelectedIndex(getTabCount() - 1);
    }
  }

  public void removeMedicalTab(ObjectViewController controller){
    System.out.println("REMOVING TAB");
    remove(indexOfComponent(controller.getView()));
  }
}
