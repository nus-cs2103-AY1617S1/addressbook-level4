package harmony.mastermind.logic.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import harmony.mastermind.commons.core.UnmodifiableObservableList;
import harmony.mastermind.model.task.ReadOnlyTask;

public class ExportCommand extends Command {

    public static final String COMMAND_KEYWORD_EXPORT = "export";

    public static final String COMMAND_ARGUMENTS_REGEX = "(?:(?=.*(?<tasks>tasks)))?"
                                                         + "(?:(?=.*(?<deadlines>deadlines)))?"
                                                         + "(?:(?=.*(?<events>events)))?"
                                                         + "(?:(?=.*(?<archives>archives)))?"
                                                         + ".*to "
                                                         + "(?<destination>.+)";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    public static final String COMMAND_FORMAT = "export [tasks] [deadlines] [events] [archives] to <destination>";

    public static final String MESSAGE_EXAMPLE = "export tasks deadlines to C:\\Desktop\\mastermind.csv";

    public static final String MESSAGE_SUCCESS = "CSV exported.";

    private static final String NEWLINE_CHARACTER = "\n";

    private static final Object[] GOOGLE_CALENDAR_HEADER = { "Subject", "Start Date", "Start Time", "End Date", "End Time", "All Day Event", "Description", "Location", "Private" };

    private static final SimpleDateFormat GOOGLE_CALENDAR_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    private static final SimpleDateFormat GOOGLE_CALENDAR_TIME_FORMAT = new SimpleDateFormat("HH:mm");

    private final String destination;

    private final boolean isExportingTasks;

    private final boolean isExportingDeadlines;

    private final boolean isExportingEvents;

    private final boolean isExportingArchives;

    public ExportCommand(String destination, boolean isExportingTasks, boolean isExportingDeadlines, boolean isExportingEvents, boolean isExportingArchives) throws IOException {
        this.destination = destination;
        this.isExportingTasks = isExportingTasks;
        this.isExportingDeadlines = isExportingDeadlines;
        this.isExportingEvents = isExportingEvents;
        this.isExportingArchives = isExportingArchives;
    }

    @Override
    public CommandResult execute() {

        CSVFormat csvFormat = CSVFormat.EXCEL;

        try (FileWriter fileWriter = new FileWriter(destination); CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);) {
            printHeader(csvPrinter);
            printTasks(csvPrinter);
            printDeadlines(csvPrinter);
            printEvents(csvPrinter);
            printArchives(csvPrinter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CommandResult(COMMAND_KEYWORD_EXPORT, MESSAGE_SUCCESS);
    }

    private void printHeader(CSVPrinter csvPrinter) throws IOException {
        csvPrinter.printRecord(GOOGLE_CALENDAR_HEADER);
    }

    private void printTasks(CSVPrinter csvPrinter) throws IOException {
        if (isExportingTasks) {
            UnmodifiableObservableList<ReadOnlyTask> tasks = model.getFilteredFloatingTaskList();
            printDataBody(csvPrinter, tasks);
        }
    }
    
    private void printDeadlines(CSVPrinter csvPrinter) throws IOException{
        if(isExportingDeadlines){
            UnmodifiableObservableList<ReadOnlyTask> deadlines = model.getFilteredDeadlineList();
            printDataBody(csvPrinter, deadlines);
        }
    }
    
    private void printEvents(CSVPrinter csvPrinter) throws IOException{
        if(isExportingEvents){
            UnmodifiableObservableList<ReadOnlyTask> events = model.getFilteredEventList();
            printDataBody(csvPrinter, events);
        }
    }
    
    private void printArchives(CSVPrinter csvPrinter) throws IOException{
        if(isExportingArchives){
            UnmodifiableObservableList<ReadOnlyTask> archives = model.getFilteredArchiveList();
            printDataBody(csvPrinter, archives);
        }
    }

    private void printDataBody(CSVPrinter csvPrinter, UnmodifiableObservableList<ReadOnlyTask> tasks) throws IOException {
        for (ReadOnlyTask task : tasks) {
            List<Object> data = new ArrayList<>();
            data.add(task.getName());
            if(task.isFloating()){
                Date dummyDate = new Date();
                data.add(GOOGLE_CALENDAR_DATE_FORMAT.format(dummyDate));
                data.add(GOOGLE_CALENDAR_TIME_FORMAT.format(dummyDate));
                data.add(GOOGLE_CALENDAR_DATE_FORMAT.format(dummyDate));
                data.add(GOOGLE_CALENDAR_TIME_FORMAT.format(dummyDate));
            } else if (task.isDeadline()){
                data.add(GOOGLE_CALENDAR_DATE_FORMAT.format(task.getEndDate()));
                data.add(GOOGLE_CALENDAR_TIME_FORMAT.format(task.getEndDate()));
                data.add(GOOGLE_CALENDAR_DATE_FORMAT.format(task.getEndDate()));
                data.add(GOOGLE_CALENDAR_TIME_FORMAT.format(task.getEndDate()));
            } else if (task.isEvent()){
                data.add(GOOGLE_CALENDAR_DATE_FORMAT.format(task.getStartDate()));
                data.add(GOOGLE_CALENDAR_TIME_FORMAT.format(task.getStartDate()));
                data.add(GOOGLE_CALENDAR_DATE_FORMAT.format(task.getEndDate()));
                data.add(GOOGLE_CALENDAR_TIME_FORMAT.format(task.getEndDate()));
            }
            data.add((task.isFloating())? "TRUE": "FALSE");
            data.add(task.getTags().toString().replaceAll(",", " "));
            data.add(null);
            data.add("TRUE");
            csvPrinter.printRecord(data);
        }
    }

}
