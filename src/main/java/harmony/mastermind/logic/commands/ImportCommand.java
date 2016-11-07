package harmony.mastermind.logic.commands;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;
import harmony.mastermind.commons.exceptions.IllegalValueException;
import harmony.mastermind.commons.exceptions.InvalidEventDateException;
import harmony.mastermind.model.task.Task;
import harmony.mastermind.model.task.TaskBuilder;
import harmony.mastermind.model.task.UniqueTaskList.DuplicateTaskException;


//@@author A0138862W
/**
 * Reads either ics/csv file and import the tasks into Mastermind
 */
public class ImportCommand extends Command {
    private static final int HEADER_LINE = 1;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_START_DATE = 1;
    private static final int INDEX_START_TIME = 2;
    private static final int INDEX_END_DATE = 3;
    private static final int INDEX_END_TIME = 4;

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
    public static final String MESSAGE_CSV_READ_FAILURE = "Header in csv File is invalid\n" 
                                                            + "First row of your csv file should include headers "
                                                            + "like Subject, Start Date, Start Time, End Date, End Time";
    public static final String MESSAGE_IMPORT_TXT_SUCCESS = "Import success: %1$s tasks added";
    public static final String MESSAGE_IMPORT_TXT_FAILURE = "Import failure: %1$s tasks added \nInvalid lines: %2$s";
    public static final String MESSAGE_IMPORT_ICS_SUCCESS = "Import ics success.";
    public static final String MESSAGE_IMPORT_ICS_FAILURE = "Failed to import ics.";
    
    public static final String MESSAGE_FAILURE_DUPLICATE_TASK = "Failed to import ics. Duplicate task detected when importing.";

    public static final String COMMAND_FORMAT = "import <File Location>";
    public static final String COMMAND_DESCRIPTION = "Reads file and add all task from file into Mastermind";

    public static final String HEADER_NAME = "Subject";
    public static final String HEADER_START_DATE = "Start Date";
    public static final String HEADER_START_TIME = "Start Time";
    public static final String HEADER_END_DATE = "End Date";
    public static final String HEADER_END_TIME = "End Time";
    
    
    public static final String EXT_CSV = "csv";
    public static final String EXT_ICS = "ics";
    
    public static final String REGEX_COMMA = ",";
    
    public static final String EMPTY_ARG = "";
    
    private String fileToImport;
    private String extension;
    private ArrayList<String> lstOfCmd;

    //@@author A0124797R
    public ImportCommand(String filePath, String extension) {
        this.fileToImport = filePath.trim();
        this.extension = extension;
        lstOfCmd = new ArrayList<String>();
    }

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
            Iterable<CSVRecord> records = CSVFormat.RFC4180
                    .withHeader(HEADER_NAME, HEADER_START_DATE, HEADER_START_TIME, HEADER_END_DATE, HEADER_END_TIME)
                    .parse(new FileReader(fileToImport));
            
            boolean isTask = false;
            for (CSVRecord record : records) {
                if (isTask) {
                    currLine++;
                    try {
                        Optional<Task> taskToAdd = parseCsvRecord(record);
                        if (taskToAdd.isPresent()) {
                            model.addTask(taskToAdd.get());
                        } else {
                            errCount++;
                            errLines += Integer.toString(currLine) + ","; 
                        }
                    } catch (IllegalValueException | InvalidEventDateException | IllegalArgumentException e) {
                        errCount++;
                        errLines += Integer.toString(currLine) + ","; 
                    }
                    
                } else {
                    currLine++;
                    isTask = true;
                    if (!record.get(INDEX_NAME).equals(HEADER_NAME)) {
                        return new CommandResult(COMMAND_WORD, MESSAGE_CSV_READ_FAILURE);
                    }
                    if (!record.get(INDEX_START_DATE).equals(HEADER_START_DATE)) {
                        return new CommandResult(COMMAND_WORD, MESSAGE_CSV_READ_FAILURE);
                    }
                    if (!record.get(INDEX_START_TIME).equals(HEADER_START_TIME)) {
                        return new CommandResult(COMMAND_WORD, MESSAGE_CSV_READ_FAILURE);
                    }
                    if (!record.get(INDEX_END_DATE).equals(HEADER_END_DATE)) {
                        return new CommandResult(COMMAND_WORD, MESSAGE_CSV_READ_FAILURE);
                    }
                    if (!record.get(INDEX_END_TIME).equals(HEADER_END_TIME)) {
                        return new CommandResult(COMMAND_WORD, MESSAGE_CSV_READ_FAILURE);
                    }
                }
            }
            
        } catch (IOException ioe) {
            return new CommandResult(COMMAND_WORD, String.format(MESSAGE_READ_FAILURE, fileToImport));
        }

        int addCount = currLine - errCount - HEADER_LINE;
        if (errLines.isEmpty()) {
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(MESSAGE_IMPORT_TXT_SUCCESS, addCount));
        } else {
            errLines = errLines.substring(0,errLines.length()-1);
            return new CommandResult(ImportCommand.COMMAND_WORD, String.format(MESSAGE_IMPORT_TXT_FAILURE, addCount, errLines));
        }
    }
    
    /**
     * reads a csv record and return a Task if its valid else an empty Optional
     * @return Parsed Task object
     * @throws IllegalValueException 
     * @throws InvalidEventDateException 
     */
    private Optional<Task> parseCsvRecord(CSVRecord record) throws IllegalValueException, InvalidEventDateException {
        Optional<String> name;
        Optional<String> startDate;
        Optional<String> endDate;
        
        if (record.get(HEADER_NAME).equals(EMPTY_ARG)) {
            return Optional.empty();
        } else {
            name = Optional.ofNullable(record.get(HEADER_NAME));
        }

        if (record.get(HEADER_START_DATE).equals(EMPTY_ARG)) {
            startDate = Optional.empty();
        } else if (record.get(HEADER_START_TIME).equals(EMPTY_ARG)) {
            return Optional.empty();
        } else {
            startDate = Optional.ofNullable(record.get(HEADER_START_DATE) + " " + record.get(HEADER_START_TIME));
        }
        if (record.get(HEADER_END_DATE).equals(EMPTY_ARG)) {
            endDate = Optional.empty();
        } else if (record.get(HEADER_END_TIME).equals(EMPTY_ARG)) {
            return Optional.empty();            
        } else {
            endDate = Optional.ofNullable(record.get(HEADER_END_DATE) + " " + record.get(HEADER_END_TIME));
        }
        if (startDate.isPresent() && !endDate.isPresent()) {
            return Optional.empty();
        }
        
        
        Set<String> tags = new HashSet<String>();
        tags.add("CSVIMPORT");

        TaskBuilder taskBuilder = new TaskBuilder(name.get());
        taskBuilder.withTags(tags);
        
        if (startDate.isPresent() && endDate.isPresent()) {
            taskBuilder.asEvent(prettyTimeParser.parse(startDate.get()).get(0), prettyTimeParser.parse(endDate.get()).get(0));
            return Optional.ofNullable(taskBuilder.build());
        } else if (endDate.isPresent()) {
            taskBuilder.asDeadline(prettyTimeParser.parse(endDate.get()).get(0));
            return Optional.ofNullable(taskBuilder.build());
        } else if (!endDate.isPresent()) {
            taskBuilder.asFloating();
            return Optional.ofNullable(taskBuilder.build());
        } else {
            return Optional.empty();
        }
        
    }
    
    //@@author A0124797R-unused
    // find that it does not make sense to import a txt file of commands,
    // instead user could have 
    /*
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
    }*/


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
        Set<String> tags = new HashSet<String>();
        tags.add("ICALIMPORT");
        
        TaskBuilder taskBuilder = new TaskBuilder(event.getSummary().getValue());
        taskBuilder.asEvent(event.getDateStart().getValue(), event.getDateEnd().getValue());
        taskBuilder.withTags(tags);
        
        return taskBuilder.build();
    }
    

}
