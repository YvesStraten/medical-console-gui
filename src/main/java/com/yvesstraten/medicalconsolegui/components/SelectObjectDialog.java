package com.yvesstraten.medicalconsolegui.components;

import com.yvesstraten.medicalconsolegui.RemoveComboRenderer;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

  @SuppressWarnings("unused")
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

  /**
   * This function attempts to show the 
   * user a dialog from where they can select
   * an option out of many options
   *
   * @param message message to show
   * @param title title of dialog
   * @param errorMessage error message for errors
   * @param options available options
   * @return selected option
   */
  public static Object attemptSelection(
      String message, String title, String errorMessage, Object[] options) {
    if (options.length == 0) {
      JOptionPane.showMessageDialog(
          null, errorMessage, "No objects added yet", JOptionPane.ERROR_MESSAGE);
    }
    SelectObjectDialog selectionDialog = new SelectObjectDialog(message, options);

    int selectionResult =
        JOptionPane.showConfirmDialog(null, selectionDialog, title, JOptionPane.OK_CANCEL_OPTION);

    if (selectionResult == JOptionPane.CANCEL_OPTION
        || selectionResult == JOptionPane.CLOSED_OPTION) {
      return null;
    }

    JComboBox<Object> combo = selectionDialog.getCombo();
    Object selected = combo.getSelectedItem();

    return selected;
  }
}
