package seedu.todo.controllers;


/**
 * A CommandDefinition encapsulates the definition of a
 * command that is handled by a Controller.
 * 
 * @@author A0139812A
 */
public class CommandDefinition {
    private String commandName;
    private String commandDescription;
    private String commandSyntax;
    
    public CommandDefinition(String name, String desc, String syntax) {
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