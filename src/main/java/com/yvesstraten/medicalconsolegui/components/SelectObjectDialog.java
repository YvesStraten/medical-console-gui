package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsolegui.RemoveComboRenderer;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel acts as the object selector 
 * to be used when asking the user 
 * to select for an object
 *
 * @author YvesStraten e2400068
 */
public class SelectObjectDialog extends JPanel {
  /**
   * Combo Box to select objects from
   */
  private JComboBox<Object> combo;

  private SelectObjectDialog() {
  }

  /**
   * <p>Constructor for SelectObjectDialog.</p>
   *
   * @param message message to show
   * @param objects list of selectable options
   */
  public SelectObjectDialog(String message, Object[] objects) {
    super(new GridLayout(2, 1));
    JLabel label = new JLabel(message);
    JComboBox<Object> combo = new JComboBox<Object>(objects);
    combo.setRenderer(new RemoveComboRenderer());
    setCombo(combo);
    add(label);
    add(combo);
  }

  /**
   * <p>Getter for the field <code>combo</code>.</p>
   *
   * @return set combo
   */
  public JComboBox<Object> getCombo() {
    return combo;
  }

  /**
   * <p>Setter for the field <code>combo</code>.</p>
   *
   * @param options combo to set
   */
  public void setCombo(JComboBox<Object> options) {
    this.combo = options;
  }
}
