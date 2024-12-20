package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsolegui.RemoveComboRenderer;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectObjectDialog extends JPanel {
  private JComboBox<Object> combo;

  private SelectObjectDialog() {
  }

  public SelectObjectDialog(String message, Object[] objects) {
    super(new GridLayout(2, 1));
    JLabel label = new JLabel(message);
    JComboBox<Object> combo = new JComboBox<Object>(objects);
    combo.setRenderer(new RemoveComboRenderer());
    setCombo(combo);
    add(label);
    add(combo);
  }

  public JComboBox<Object> getCombo() {
    return combo;
  }

  public void setCombo(JComboBox<Object> options) {
    this.combo = options;
  }
}
