package w15c2.tusk.model;

import javafx.beans.property.SimpleStringProperty;

//@@author:A0139708W
/**
 * Class for help panel information.
*/
public class HelpGuide {
      
      private SimpleStringProperty commandName;
      private SimpleStringProperty format; 
      
      /**
       * Constructor for HelpGuide, arguments 
       * should not be null.
       * 
       * @param name    Name of command.
       * @param format  Input format of command.
       */
      public HelpGuide(String name, String format) {
          assert name != null;
          assert format != null;
          this.commandName = new SimpleStringProperty(name);;
          this.format = new SimpleStringProperty(format);
      }
      
      /**
       * Returns String of name of 
       * comamnd.
       * 
       * @return    name of comamnd.
       */
      public String getName() {
          return commandName.get();
      }
      
      /**
       * Returns String of format
       * of command.
       * 
       * @return    format of command.
       */
      public String getFormat() {
          return format.get();
      }
  
}
