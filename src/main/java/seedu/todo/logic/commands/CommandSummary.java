package seedu.todo.logic.commands;

//@@author A0135817B
public class CommandSummary {
    /**
     * The scenario the summary is aiming to describe, eg. add event, delete task, etc. 
     * Keep it short but descriptive. 
     */
    public final String scenario;

    /**
     * The command to accomplish the scenario, eg. add, delete 
     */
    public final String command;

    /**
     * The parameters for the command
     */
    public final String arguments;
    
    public CommandSummary(String scenario, String command) {
        this(scenario, command, "");
    }
    
    public CommandSummary(String scenario, String command, String arguments) {
        this.scenario = scenario.trim();
        this.command = command.toLowerCase().trim();
        this.arguments = arguments.trim();
    }
}
