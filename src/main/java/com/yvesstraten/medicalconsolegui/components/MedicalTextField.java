package com.yvesstraten.medicalconsolegui.components;

import javax.swing.JTextField;

public class MedicalTextField extends JTextField {
  public MedicalTextField(){
    super();
  }

  public MedicalTextField(String text){
    super(text);
    setEnabled(false);
  }
}
