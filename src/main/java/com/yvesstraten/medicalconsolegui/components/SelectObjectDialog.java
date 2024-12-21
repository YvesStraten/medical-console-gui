package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsolegui.RemoveComboRenderer;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>SelectObjectDialog class.</p>
 *
 * @author YvesStraten e2400068
 */
public class SelectObjectDialog extends JPanel {
  private JComboBox<Object> combo;

  private SelectObjectDialog() {
  }

  /**
   * <p>Constructor for SelectObjectDialog.</p>
   *
   * @param message a {@link java.lang.String} object
   * @param objects an array of {@link java.lang.Object} objects
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
   * @return a {@link javax.swing.JComboBox} object
   */
  public JComboBox<Object> getCombo() {
    return combo;
  }

  /**
   * <p>Setter for the field <code>combo</code>.</p>
   *
   * @param options a {@link javax.swing.JComboBox} object
   */
  public void setCombo(JComboBox<Object> options) {
    this.combo = options;
  }
}
