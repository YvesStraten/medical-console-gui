package com.yvesstraten.medicalconsolegui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.yvesstraten.medicalconsole.HealthService;
import com.yvesstraten.medicalconsole.MedicalConsole;
import com.yvesstraten.medicalconsolegui.components.MedicalGuiFrame;

/**
 * This class acts as the main entry point for 
 * this program
 *
 * @author YvesStraten e2400068
 */
public class MedicalGui extends JFrame {
  /**
   * Main entry point for this program
   * @param args passed arguments
   */
  public static void main(String[] args) {
    String currentOS = System.getProperty("os.name");
    if(currentOS.equals("Mac OS X")){
      System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    HealthService service = MedicalConsole.generateSampleData();
    // HealthService service = new HealthService();
    
    // Instantiate GUI on Java AWT event thread
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        MedicalGuiFrame medicalFrame = new MedicalGuiFrame(service);
        medicalFrame.setVisible(true);
      }
    });
  }

  private MedicalGui(){}
}
