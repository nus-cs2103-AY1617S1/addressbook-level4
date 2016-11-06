package w15c2.tusk.model;

import javafx.beans.property.SimpleStringProperty;

public class HelpGuide {
      
      // Member variables for a CommandGuide object
      private SimpleStringProperty actionName;
      private SimpleStringProperty args; 
      
      /**
       * Private constructor for Command Guide so it cannot be constructed
       * without parameters
       */
      private HelpGuide() {
      }
      
      /**
       * Constructor for CommandGuide
       * Asserts that name and commandWord are non-null because it is
       * constructed by custom parameters in HelpWindow always.
       * @param name Name of the action and command
       * @param commandWord command keyword
       * @param args optional arguments for command keyword
       */
      public HelpGuide(String name, String args) {
          assert name != null;
          this.actionName = new SimpleStringProperty(name);;
          this.args = new SimpleStringProperty(args);
      }
      
      public String getName() {
          return actionName.get();
      }
      
      public String getArgs() {
          return args.get();
      }
  


}
