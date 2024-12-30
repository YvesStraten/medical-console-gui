package com.yvesstraten.medicalconsolegui.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

/** 
 * This class acts as a dialog container 
 * containing a group of buttons which 
 * undertake similar operations 
 *
 * @author YvesStraten e2400068
 */
public class ButtonsDialog extends JDialog implements ActionListener {
  private ButtonPane buttonPane;

  /**
   * Instantiate this dialog containing 
   * buttons
   * @param owner owner of this dialog, 
   * preferably the main JFrame
   * @param buttonPane panel containing buttons
   */
  public ButtonsDialog(JFrame owner, ButtonPane buttonPane){
    super(owner);
    setLayout(new BorderLayout());
    setModalityType(ModalityType.APPLICATION_MODAL);
    add(buttonPane, BorderLayout.NORTH);
    setButtonPane(buttonPane);
    pack();
    setLocationRelativeTo(owner);
  }

  /**
   * <p>Getter for the field 
   * <code>buttonPane</code>.</p>
   *
   * @return set button pane
   */
  public ButtonPane getButtonPane() {
    return buttonPane;
  }

  /**
   * <p>Setter for the field 
   * <code>buttonPane</code>.</p>
   *
   * @param buttonPane button pane to set
   */
  public void setButtonPane(ButtonPane buttonPane) {
    this.buttonPane = buttonPane;
  }


  /** 
   * Shows this dialog as default action 
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    setVisible(true);
  }
}
