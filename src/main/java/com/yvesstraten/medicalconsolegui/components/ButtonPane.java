package com.yvesstraten.medicalconsolegui.components;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel groups all buttons and their functions together
 *
 * @author YvesStraten e2400068
 */
public class ButtonPane extends JPanel {
  /**
   * list panel to manipulate
   */
  private ListPanel listPanel;

  /**
   * Constructor for ButtonPane.
   *
   * @param labelText the text that will be present 
   * in the label
   * @param listPanel the listPanel component in this
   * application
   */
  public ButtonPane(String labelText, ListPanel listPanel) {
    super();
    setListPanel(listPanel);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Upper label
    JLabel label = new JLabel(labelText, JLabel.CENTER);
    label.setAlignmentX(CENTER_ALIGNMENT);
    label.setAlignmentX(CENTER_ALIGNMENT);
    add(label);
  }

  /**
   * Getter for the field <code>listPanel</code>.
   *
   * @return list panel
   */
  public ListPanel getListPanel() {
    return listPanel;
  }

  /**
   * Setter for the field <code>listPanel</code>.
   *
   * @param listPanel list panel to set
   */
  public void setListPanel(ListPanel listPanel) {
    this.listPanel = listPanel;
  }
}
