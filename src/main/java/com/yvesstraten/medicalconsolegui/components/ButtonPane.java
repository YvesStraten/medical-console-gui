package com.yvesstraten.medicalconsolegui.components;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ButtonPane extends JPanel {
  private ListPanel listPanel;

  public ButtonPane(String labelText, ListPanel listPanel){
    super();
    setListPanel(listPanel);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Upper label
    JLabel label = new JLabel(labelText, JLabel.CENTER);
    label.setAlignmentX(CENTER_ALIGNMENT);
    label.setAlignmentX(CENTER_ALIGNMENT);
    add(label);
  }

  public ListPanel getListPanel() {
    return listPanel;
  }
  public void setListPanel(ListPanel listPanel) {
    this.listPanel = listPanel;
  }
}
