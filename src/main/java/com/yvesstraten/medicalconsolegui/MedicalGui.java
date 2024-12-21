package com.yvesstraten.medicalconsolegui;

import javax.swing.JFrame;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsolegui.components.MedicalGuiFrame;

/**
 * <p>MedicalGui class.</p>
 *
 * @author YvesStraten e2400068
 */
public class MedicalGui extends JFrame {
  /**
   * <p>main.</p>
   *
   * @param args an array of {@link java.lang.String} objects
   */
  public static void main(String[] args) {
    String currentOS = System.getProperty("os.name");
    if(currentOS.equals("Mac OS X")){
      System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    HealthService service = MedicalConsole.generateSampleData();
    // HealthService service = new HealthService();
		MedicalGuiFrame medicalFrame = new MedicalGuiFrame(service);
		medicalFrame.setVisible(true);
  }
}
