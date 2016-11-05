package seedu.todo.controllers;

// @@author A0139812A
/**
 * A CommandDefinition encapsulates the definition of a
 * command that is handled by a Controller.
 */
public class CommandDefinition {
    private String commandName;
    private String commandDescription;
    private String commandSyntax;
    private String commandKeyword;
    
    public CommandDefinition(String name, String desc, String syntax, String keyword) {
        commandName = name;
        commandDescription = desc;
        commandSyntax = syntax;
        setCommandKeyword(keyword);
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

    public String getCommandKeyword() {
        return commandKeyword;
    }

    public void setCommandKeyword(String commandKeyword) {
        this.commandKeyword = commandKeyword;
    }

}