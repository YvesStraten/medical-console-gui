package com.yvesstraten.medicalconsolegui;

import javax.swing.JFrame;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;

public class MedicalGui extends JFrame {
  public static void main(String[] args) {
    HealthService service = MedicalConsole.generateSampleData();
		MedicalGuiFrame medicalFrame = new MedicalGuiFrame(service);
		medicalFrame.setVisible(true);
  }
}
