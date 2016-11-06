package w15c2.tusk.model;

import javafx.beans.property.SimpleStringProperty;

//@@author:A0139708W
/*
 * Class for help panel information
*/
public class HelpGuide {
      
      private SimpleStringProperty commandName;
      private SimpleStringProperty format; 
      
      // Converts 
      public HelpGuide(String name, String format) {
          assert name != null;
          assert format != null;
          this.commandName = new SimpleStringProperty(name);;
          this.format = new SimpleStringProperty(format);
      }
      
      public String getName() {
          return commandName.get();
      }
      
      public String getFormat() {
          return format.get();
      }
  
}
