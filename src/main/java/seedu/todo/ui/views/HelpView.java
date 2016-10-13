package seedu.todo.ui.views;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.todo.ui.UiPartLoader;
import seedu.todo.ui.components.HelpCommandItem;

public class HelpView extends View {

    private static final String FXML_PATH = "views/HelpView.fxml";
    
    // Props
    public List<CommandDefinitionStub> commandDefinitions = new ArrayList<CommandDefinitionStub>();
    
    // FXML
    @FXML
    private Pane helpCommandsPlaceholder;
    
    public static HelpView load(Stage primaryStage, Pane placeholderPane) {
        return UiPartLoader.loadUiPart(primaryStage, placeholderPane, new HelpView());
    }

    @Override
    public String getFxmlPath() {
        return FXML_PATH;
    }
    
    @Override
    public void componentDidMount() {
        // Clear help commands
        HelpCommandItem.reset(helpCommandsPlaceholder);
        
        // Load help commands
        for (CommandDefinitionStub command : commandDefinitions) {
            HelpCommandItem item = HelpCommandItem.load(primaryStage, helpCommandsPlaceholder);
            item.commandName = command.getCommandName();
            item.commandDescription = command.getCommandDescription();
            item.commandSyntax = command.getCommandSyntax();
            item.render();
        }
    }
    
    // TODO: Replace stub
    public static class CommandDefinitionStub {
        
        private String commandName;
        private String commandDescription;
        private String commandSyntax;
        
        // TODO: Remove after testing
        public CommandDefinitionStub(String name, String desc, String syntax) {
            commandName = name;
            commandDescription = desc;
            commandSyntax = syntax;
        }
        
        public String getCommandName() {
            return commandName;
        }
        
        public void setCommandName(String commandName) {
            this.commandName = commandName;
        }
        
        public String getCommandDescription() {
            return commandDescription;
        }
        
        public void setCommandDescription(String commandDescription) {
            this.commandDescription = commandDescription;
        }
        
        public String getCommandSyntax() {
            return commandSyntax;
        }
        
        public void setCommandSyntax(String commandSyntax) {
            this.commandSyntax = commandSyntax;
        }
    }

}
