package seedu.todo.guitests.guihandles;

import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.commons.core.CommandDefinition;
import seedu.todo.guitests.GuiRobot;

//@@author A0139812A
public class HelpItemHandle extends GuiHandle {

    private static final String COMMAND_NAME_TEXT_ID = "#commandNameText";
    private static final String COMMAND_SYNTAX_TEXT_ID = "#commandSyntaxText";
    private static final String COMMAND_DESC_TEXT_ID = "#commandDescriptionText";
    private Node node;

    public HelpItemHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }
    
    public String getCommandName() {
        return getStringFromText(COMMAND_NAME_TEXT_ID, node);
    }
    
    public String getCommandSyntax() {
        return getStringFromText(COMMAND_SYNTAX_TEXT_ID, node);
    }
    
    public String getCommandDescription() {
        return getStringFromText(COMMAND_DESC_TEXT_ID, node);
    }
    
    public boolean isEqualsTo(CommandDefinition commandDefinition) {
        return getCommandName().equals(commandDefinition.getCommandName()) 
                && getCommandSyntax().equals(commandDefinition.getCommandSyntax()) 
                && getCommandDescription().equals(commandDefinition.getCommandDescription());
    }

}
