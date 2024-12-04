package com.yvesstraten.medicalconsolegui;

import javax.swing.JFrame;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;

public class MedicalGui extends JFrame {
  public static void main(String[] args) {
    String currentOS = System.getProperty("os.name");
    if(currentOS.equals("Mac OS X")){
      System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    HealthService service = MedicalConsole.generateSampleData();
		MedicalGuiFrame medicalFrame = new MedicalGuiFrame(service);
		medicalFrame.setVisible(true);
  }
}
