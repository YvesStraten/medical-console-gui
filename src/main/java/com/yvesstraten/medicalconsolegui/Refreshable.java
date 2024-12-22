package com.yvesstraten.medicalconsolegui;

/** 
 * This interface is used to allow 
 * a class to fully refresh its underlying 
 * data. A class which implements this 
 * interface can be refreshed, and
 * must be refreshed. 
 */
public interface Refreshable {
  /**
   * Refreshes the underlying 
   * data for a class which  
   * implements it.
   */
  public void refresh();
}
