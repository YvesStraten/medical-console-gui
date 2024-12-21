package com.yvesstraten.medicalconsolegui.components;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>ButtonPane class.</p>
 *
 * @author YvesStraten e2400068
 */
public class ButtonPane extends JPanel {
  private ListPanel listPanel;

  /**
   * <p>Constructor for ButtonPane.</p>
   *
   * @param labelText a {@link java.lang.String} object
   * @param listPanel a {@link com.yvesstraten.medicalconsolegui.components.ListPanel} object
   */
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

  /**
   * <p>Getter for the field <code>listPanel</code>.</p>
   *
   * @return a {@link com.yvesstraten.medicalconsolegui.components.ListPanel} object
   */
  public ListPanel getListPanel() {
    return listPanel;
  }
  /**
   * <p>Setter for the field <code>listPanel</code>.</p>
   *
   * @param listPanel a {@link com.yvesstraten.medicalconsolegui.components.ListPanel} object
   */
  public void setListPanel(ListPanel listPanel) {
    this.listPanel = listPanel;
  }
}
