package harmony.mastermind.logic.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskBuilder;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;

//@@author A0138862W
/*
 * This command will read a .ics file and convert into Mastermind task.
 * This command cannot be undo/redo.
 */
public class ImportIcsCommand extends Command {

    public static final String COMMAND_KEYWORD_IMPORTICS = "importics";

    public static final String COMMAND_ARGUMENTS_REGEX = "from (?<source>.+)";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);
    
    public static final String MESSAGE_EXAMPLE = "importics from C:\\Users\\Jim\\jim@gmail.com.ics";
    
    public static final String MESSAGE_SUCCESS = "Imported ics.";
    
    public static final String MESSAGE_FAILURE = "Failed to import ics.";
    
    public static final String MESSAGE_FAILURE_DUPLICATE_TASK = "Failed to import ics. Duplicate task detected when importing.";

    private final String source;

    public ImportIcsCommand(String source) {
        this.source = source;
    }

    @Override
    public CommandResult execute() {
        
        try (FileInputStream fis = new FileInputStream(source)){
            ICalendar ical = Biweekly.parse(fis).first();

            for (VEvent event : ical.getEvents()) {
                Task task = parseTask(event);
                model.addTask(task);
            }

            return new CommandResult(COMMAND_KEYWORD_IMPORTICS, MESSAGE_SUCCESS);
        } catch (InvalidEventDateException | IOException e) {
            return new CommandResult(COMMAND_KEYWORD_IMPORTICS, MESSAGE_FAILURE);
        } catch (DuplicateTaskException e){
            return new CommandResult(COMMAND_KEYWORD_IMPORTICS, MESSAGE_FAILURE_DUPLICATE_TASK);
        } catch (IllegalValueException e){
            return new CommandResult(COMMAND_KEYWORD_IMPORTICS, MESSAGE_FAILURE);
        }
    }
    
    /**
     * This method will attempt to parse a ical's VEvent to a Mastermind Task Object
     * 
     * @param event The ical VEvent Object to parse
     * @return the parsed Task object
     * @throws InvalidEventDateException if start date is after end date
     * @throws IllegalValueException if tags contains non-alphanumeric characters
     */
    private Task parseTask(VEvent event) throws InvalidEventDateException, IllegalValueException {
        TaskBuilder taskBuilder = new TaskBuilder(event.getSummary().getValue());
        taskBuilder.asEvent(event.getDateStart().getValue(), event.getDateEnd().getValue());
        taskBuilder.withTags(new HashSet<String>());
        return taskBuilder.build();
    }

}
