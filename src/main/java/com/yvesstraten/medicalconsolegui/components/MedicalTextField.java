package com.yvesstraten.medicalconsolegui.components;

import javax.swing.JTextField;

public class MedicalTextField extends JTextField {
  public MedicalTextField(){
    super();
  }

  public MedicalTextField(String text){
    this(text, text.length());
  }

  public MedicalTextField(String text, int cols){
    super(text, cols);
    setEnabled(false);
  }
}
