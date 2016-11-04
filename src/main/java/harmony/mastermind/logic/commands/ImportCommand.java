package harmony.mastermind.logic.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.model.tag.UniqueTagList;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskBuilder;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;


//@@author A0138862W
/**
 * Reads either ics/csv/txt file and import the tasks into Mastermind
 */
public class ImportCommand extends Command {
    private static final int COUNT_ONE = 1;

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                               + ": Reads file and add all task from file into Mastermind\n"
                                               + "Parameters: File location\n"
                                               + "Example:\n"
                                               + "for Mac:\n"
                                               + COMMAND_WORD + "from"
                                               + " Users/Jim/Desktop/jim@gmail.com.ics\n"
                                               + "for Windows:\n"
                                               + COMMAND_WORD + "from"
                                               + " C:\\Users\\Jim\\jim@gmail.com.ics";

    public static final String COMMAND_ARGUMENTS_REGEX = "from (?<source>.+)(?<=(?<extension>txt|csv|ics))";
    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);
    
    public static final String MESSAGE_READ_SUCCESS = "Read success on imported file";
    public static final String MESSAGE_READ_FAILURE = "Invalid file path: %1$s";
    private static final String MESSAGE_CSV_READ_FAILURE = "Header in csv File is invalid\n" 
                                                            + "First row of your csv file should include headers "
                                                            + "like Subject, Start Date, Start Time, End Date, End Time";
    public static final String MESSAGE_IMPORT_TXT_SUCCESS = "Import success: %1$s tasks added";
    public static final String MESSAGE_IMPORT_TXT_FAILURE = "Import failure: %1$s tasks added \nInvalid lines: %2$s";
    public static final String MESSAGE_IMPORT_ICS_SUCCESS = "Import ics success.";
    public static final String MESSAGE_IMPORT_ICS_FAILURE = "Failed to import ics.";
    
    public static final String MESSAGE_FAILURE_DUPLICATE_TASK = "Failed to import ics. Duplicate task detected when importing.";

    public static final String COMMAND_FORMAT = "import <File Location>";
    public static final String COMMAND_DESCRIPTION = "Reads file and add all task from file into Mastermind";
    
    public static final String EXT_CSV = "csv";
    public static final String EXT_ICS = "ics";
    public static final String EXT_TXT = "txt";
    
    public static final String REGEX_COMMA = ",";
    
    public static final int NUMBER_TASK_ARGUMENTS = 5;
    
    public static final MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
    
    private String fileToImport;
    private String extension;
    private ArrayList<String> lstOfCmd;
    
    public ImportCommand(String filePath, String extension) {
        this.fileToImport = filePath.trim();
        this.extension = extension;
        lstOfCmd = new ArrayList<String>();
    }
    
    //@@author A0124797R
    @Override
    public CommandResult execute() {
        assert fileToImport != null;
        assert lstOfCmd != null;
        
        CommandResult result = null;
        
        switch (extension) {
            case EXT_ICS:
                result = importIcsFile();
                break;
            case EXT_CSV:
                result = importCsvFile();
                break;
            case EXT_TXT:
                result = importTxtFile();
                break;
        }
        
        return result;
        
    }
    
    /** gets the list of commands for adding tasks */
    public ArrayList<String> getTaskToAdd() {
        return this.lstOfCmd;
    }
    
    private CommandResult importCsvFile() {
        int currLine = 0;
        int errCount = 0;
        String errLines = ""; 
        
        try {
            BufferedReader br = model.importFile(fileToImport);
            String line = br.readLine();
            String[] taskArgs = parseCsvRow(line);
            
            if (taskArgs.length != NUMBER_TASK_ARGUMENTS) {
                return new CommandResult(COMMAND_WORD, MESSAGE_CSV_READ_FAILURE);
            }
            
            while ((line = br.readLine()) != null) {
                currLine += 1;
                taskArgs = parseCsvRow(line);
                Optional<Task> task = parseCsvArgs(taskArgs);
                if (task.isPresent()) {
                    try {
                        model.addTask(task.get());
                    } catch (DuplicateTaskException dte) {
                        errCount += 1;
                        errLines += Integer.toString(currLine + 1) + ","; 
                    }
                } else {
                    errCount += 1;
                    errLines += Integer.toString(currLine + 1) + ",";
                }
            }
            
        } catch (IOException e) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_READ_FAILURE, fileToImport));
        }

        int addCount = currLine - errCount;
        if (errLines.isEmpty()) {
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(MESSAGE_IMPORT_TXT_SUCCESS, addCount));
        } else {
            errLines = errLines.substring(0,errLines.length()-1);
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(MESSAGE_IMPORT_TXT_FAILURE, addCount, errLines));
        }
    }
    
    /**
     * reads a csv row and return an array of the row split by ','
     */
    private String[] parseCsvRow(String row) {
        int startIndex = 0;
        int argIndex = 0;
        String[] taskArgs = new String[NUMBER_TASK_ARGUMENTS];
        String s;
        for (int i=0;i<row.length();i++) {
            if (row.charAt(i) == REGEX_COMMA.charAt(0)) {
                if (argIndex==3 && i-startIndex != 0) {
                    s = row.substring(startIndex, row.length());
                    taskArgs[argIndex] = s;
                    break;
                }
                if (i-startIndex != 0 || startIndex == 0) {
                    s = row.substring(startIndex, i);
                    taskArgs[argIndex] = s;
                }
                argIndex += 1;
                startIndex = i+1;
            }
        }
        return taskArgs;
    }
    
    private Optional<Task> parseCsvArgs(String[] args) {
        Optional<String> name = Optional.ofNullable(args[0]);
        Optional<String> startDate;
        Optional<String> endDate;
        Task task;
        
        if (args[1] == null) {
            startDate = Optional.empty();
        } else {
            startDate = Optional.ofNullable(args[1] + " " + args[2]);
        }
        
        if (args[3] == null) {
            endDate = Optional.empty();
        } else {
            endDate = Optional.ofNullable(args[3] + " " + args[4]);
        }
        
        if (!name.isPresent()) {
            return Optional.empty();
        }
        
        if (startDate.isPresent() && !endDate.isPresent()) {
            return Optional.empty();
        } else if (startDate.isPresent() && endDate.isPresent()) {
            task = new Task(name.get(), prettyTimeParser.parse(startDate.get()).get(0), prettyTimeParser.parse(endDate.get()).get(0), new UniqueTagList(), null, new Date());
            return Optional.ofNullable(task);
        } else if (endDate.isPresent()) {
            task = new Task(name.get(), prettyTimeParser.parse(endDate.get()).get(0), new UniqueTagList(), null, new Date());
            return Optional.ofNullable(task);
        } else if (!endDate.isPresent()) {
            task = new Task(name.get(), new UniqueTagList(), new Date());
            return Optional.ofNullable(task);
        } else {
            return Optional.empty();
        }
        
    }
    
    private CommandResult importTxtFile() {
        try {
            BufferedReader br = model.importFile(fileToImport);
            
            String line;
            
            while ((line = br.readLine()) != null) {
                lstOfCmd.add(line);
            }
            
        } catch (IOException e) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_READ_FAILURE, fileToImport));
        }
        
        return new CommandResult(COMMAND_WORD, MESSAGE_READ_SUCCESS);
    }


    //@@author A0138862W
    private CommandResult importIcsFile() {

        try (FileInputStream fis = new FileInputStream(fileToImport)){
            ICalendar ical = Biweekly.parse(fis).first();

            for (VEvent event : ical.getEvents()) {
                Task task = parseTask(event);
                model.addTask(task);
            }

            return new CommandResult(COMMAND_WORD, MESSAGE_IMPORT_ICS_SUCCESS);
        } catch (DuplicateTaskException e){
            return new CommandResult(COMMAND_WORD, MESSAGE_FAILURE_DUPLICATE_TASK);
        } catch (InvalidEventDateException | IOException | IllegalValueException e) {
            return new CommandResult(COMMAND_WORD, MESSAGE_IMPORT_ICS_FAILURE);
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
