package seedu.todo.controllers;

public class CommandDefinition {
    private String commandName;
    private String commandDescription;
    private String commandSyntax;
    
    // TODO: Remove after testing
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