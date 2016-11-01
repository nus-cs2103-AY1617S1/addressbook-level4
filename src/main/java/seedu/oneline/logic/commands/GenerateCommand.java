package seedu.oneline.logic.commands;

import java.util.Random;

import seedu.oneline.commons.core.EventsCenter;
import seedu.oneline.commons.core.Messages;
import seedu.oneline.commons.core.UnmodifiableObservableList;
import seedu.oneline.commons.events.ui.JumpToListRequestEvent;
import seedu.oneline.commons.exceptions.IllegalCmdArgsException;
import seedu.oneline.commons.exceptions.IllegalValueException;
import seedu.oneline.logic.Logic;
import seedu.oneline.logic.LogicManager;
import seedu.oneline.logic.parser.Parser;
import seedu.oneline.model.Model;
import seedu.oneline.model.ModelManager;
import seedu.oneline.model.TaskBook;
import seedu.oneline.model.UserPrefs;
import seedu.oneline.model.tag.Tag;
import seedu.oneline.model.task.ReadOnlyTask;
import seedu.oneline.model.task.Task;
import seedu.oneline.model.task.TaskName;
import seedu.oneline.model.task.TaskRecurrence;
import seedu.oneline.model.task.TaskTime;
import seedu.oneline.model.task.UniqueTaskList.DuplicateTaskException;

/**
 * Selects a task identified using it's last displayed index from the task book.
 */
public class GenerateCommand extends Command {

    public final int taskCount;

    public static final String COMMAND_WORD = "gen";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates the specified number of tasks\n"
            + "Parameters: [INDEX (must be a positive integer)]\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Tasks created: %1$s";
    
    public GenerateCommand(int taskCount) {
        this.taskCount = taskCount;
    }

    public static GenerateCommand createFromArgs(String args) throws IllegalValueException {
        args = args.trim();
        if (args.length() == 0) {
            return new GenerateCommand(1);
        }
        Integer index = null;
        try {
            index = Parser.getIndexFromArgs(args);
        } catch (IllegalValueException e) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        if (index == null) {
            throw new IllegalValueException(Messages.getInvalidCommandFormatMessage(MESSAGE_USAGE));
        }
        return new GenerateCommand(index);
    }
    
    @Override
    public CommandResult execute() {
//        for (int i = 0; i < taskCount; i++) {
//            try {
//                model.addTask(generateTask());
//            } catch (DuplicateTaskException e) {
//                i--;
//            }
//        }
        model.resetData(getTaskBook(myTasks));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, taskCount));
    }
    
    public static TaskBook getTaskBook(String[] cmds) {
        Model m = new ModelManager(new TaskBook(), new UserPrefs());
        Logic l = new LogicManager(m, null);
        for (int i = 0; i < cmds.length; i++) {
            System.out.println("Executing: " + cmds[i]);
            CommandResult r = l.execute(cmds[i]);
            System.out.println(r.feedbackToUser);
        }
        return new TaskBook(m.getTaskBook());
    }

    
    public static Task generateTask() {
        try {
            int type = rand.nextInt(2);
            switch (type) {
            case 0: // Floating task
                return new Task(
                            new TaskName(getNameString()),
                            TaskTime.getDefault(),
                            TaskTime.getDefault(),
                            TaskTime.getDefault(),
                            TaskRecurrence.getDefault(),
                            getTag()
                        );
            case 1: // Event
                TaskTime start = null;
                TaskTime end = null;
                do {
                start = new TaskTime(getTimeString());
                end = new TaskTime(getTimeString());
                } while (start.compareTo(end) >= 0);
                return new Task(
                        new TaskName(getNameString()),
                        start,
                        end,
                        TaskTime.getDefault(),
                        TaskRecurrence.getDefault(),
                        getTag()
                    );
            case 2: // Deadline
                return new Task(
                        new TaskName(getNameString()),
                        TaskTime.getDefault(),
                        TaskTime.getDefault(),
                        new TaskTime(getTimeString()),
                        TaskRecurrence.getDefault(),
                        getTag()
                    );
            }
        } catch (IllegalValueException e) {
            System.out.println("Invalid generation: " + e.getMessage());
        }
        return null;
    }
    
    private static Random rand = new Random();
    
    private static final String[] peopleNames = new String[] { "Alice", "Albert", "Aaron", "Benedict", "Bob", "Cathy", "Conan",
            "Dennis", "Deborah", "Edward", "Fabian", "Faye", "Gordon", "Gregory", "Henry", "Harry", "Judy",
            "Jane", "Jasper", "Ken", "Kaylyn", "Lenny", "Louis"
        };
    
    private static final String[] eventNames = new String[] { "Gym", "Meeting", "Business meeting", "Breakfast", "Lunch",
            "Dinner", "Supper", "Cycling", "Tennis", "Consultation", "Birthday celebration", "Shopping",
            "Movie", "Network"
            };
    
    private static final String[] soloEventNames = new String[] { "Check email", "Update report", "Prepare briefing", "Clear paperwork",
            "Send cheque to bank", "Arrange doctor appointment", "Schedule medical checkup", "Look for new laptop", "Order iPhone 7",
            "Update Windows", "Pay bills", "Pay insurance premium"
            };
            
    private static String getNameString() {
        int type = rand.nextInt(3);
        switch (type) {
        case 0:
            return chooseRand(eventNames) + " with " + chooseRand(peopleNames);
        case 1:
            return chooseRand(eventNames);
        case 2:
            return chooseRand(soloEventNames);
        default:
            return null;
        }
    }
    
    private static <T> T chooseRand(T[] arr) {
        return arr[rand.nextInt(arr.length)];
    }

    private static final String[] daysOfWeek = new String[] { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private static final String[] monthsOfYear = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
                                                                    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    private static String getTimeString() {
        int type = rand.nextInt(2);
        switch (type) {
        case 0:
            return chooseRand(new String[] {"", "Next ", "Following "}) + chooseRand(daysOfWeek);
        case 1:
            return rand.nextInt(32) + " " +
                    chooseRand(monthsOfYear);
        default:
            return null;
        }
    }
    
    private static final String[] tags = new String[] { "OTOT", "Urgent", "Enjoyment", "Project", "Work", "Meeting",
            "Important", "Priority", "Buy", "Shopping", "Todo", "Revised", "Updated", "KIV", "ASAP"};
    
    private static Tag getTag() {
        if (rand.nextDouble() < 0.1) {
            return Tag.EMPTY_TAG;
        }
        String tag = chooseRand(tags);
        try {
            return Tag.getTag(tag);
        } catch (IllegalValueException e) {
            System.out.println("Invalid generation for tag [" + tag + "]: " + e.getMessage());
        }
        return Tag.EMPTY_TAG;
    }
    
    @Override
    public boolean canUndo() {
        return true;
    }

    private static final String[] myTasks = new String[] {
            "add CS2101 SEC .from 17 Oct 10am .to 17 Oct 12pm .done true #sch", 
            "add GET1006 SEC .from 17 Oct 12pm .to 17 Oct 2pm .done true #sch", 
            "add ST2132 LEC .from 17 Oct 2pm .to 17 Oct 4pm .done true #sch", 
            "add CS1101S TUT .from 17 Oct 4pm .to 17 Oct 6pm .done true #sch", 
            "add GEH1036 LEC .from 17 Oct 6pm .to 17 Oct 8pm .done true #sch", 
            "add CS2103T TUT .from 19 Oct 9am .to 19 Oct 10am .done true #sch", 
            "add CS1101S LEC .from 19 Oct 10am .to 19 Oct 12pm .done true #sch", 
            "add GET1006 SEC .from 19 Oct 12pm .to 19 Oct 2pm .done true #sch", 
            "add GEH1036 LEC .from 19 Oct 6pm .to 19 Oct 8pm .done true #sch", 
            "add CS2101 SEC .from 20 Oct 10am .to 20 Oct 12pm .done true #sch", 
            "add CS2106 LAB .from 20 Oct 12pm .to 20 Oct 1pm .done true #sch", 
            "add ST2132 TUT .from 20 Oct 1pm .to 20 Oct 2pm .done true #sch", 
            "add ST2132 LEC .from 20 Oct 2pm .to 20 Oct 4pm .done true #sch", 
            "add GEH1036 TUT .from 20 Oct 5pm .to 20 Oct 6pm .done true #sch", 
            "add CS1101S LEC .from 20 Oct 10am .to 20 Oct 11am .done true #sch", 
            "add CS2106 TUT .from 21 Oct 11am .to 21 Oct 12pm .done true #sch", 
            "add CS2106 LEC .from 21 Oct 12pm .to 21 Oct 2pm .done true #sch", 
            "add CS2103 LEC .from 21 Oct 2pm .to 21 Oct 4pm .done true #sch", 
            "add CS2101 SEC .from 24 Oct 10am .to 24 Oct 12pm .done true #sch", 
            "add GET1006 SEC .from 24 Oct 12pm .to 24 Oct 2pm .done true #sch", 
            "add ST2132 LEC .from 24 Oct 2pm .to 24 Oct 4pm .done true #sch", 
            "add CS1101S TUT .from 24 Oct 4pm .to 24 Oct 6pm .done true #sch", 
            "add GEH1036 LEC .from 24 Oct 6pm .to 24 Oct 8pm .done true #sch", 
            "add CS2103T TUT .from 26 Oct 9am .to 26 Oct 10am .done true #sch", 
            "add CS1101S LEC .from 26 Oct 10am .to 26 Oct 12pm .done true #sch", 
            "add GET1006 SEC .from 26 Oct 12pm .to 26 Oct 2pm .done true #sch", 
            "add GEH1036 LEC .from 26 Oct 6pm .to 26 Oct 8pm .done true #sch", 
            "add CS2101 SEC .from 27 Oct 10am .to 27 Oct 12pm .done true #sch", 
            "add CS2106 LAB .from 27 Oct 12pm .to 27 Oct 1pm .done true #sch", 
            "add ST2132 TUT .from 27 Oct 1pm .to 27 Oct 2pm .done true #sch", 
            "add ST2132 LEC .from 27 Oct 2pm .to 27 Oct 4pm .done true #sch", 
            "add GEH1036 TUT .from 27 Oct 5pm .to 27 Oct 6pm .done true #sch", 
            "add CS1101S LEC .from 28 Oct 10am .to 28 Oct 11am .done true #sch", 
            "add CS2106 TUT .from 28 Oct 11am .to 28 Oct 12pm .done true #sch", 
            "add CS2106 LEC .from 28 Oct 12pm .to 28 Oct 2pm .done true #sch", 
            "add CS2103 LEC .from 28 Oct 2pm .to 28 Oct 4pm .done true #sch", 
            "add CS2101 SEC .from 31 Oct 10am .to 24 Oct 12pm .done true #sch", 
            "add GET1006 SEC .from 31 Oct 12pm .to 24 Oct 2pm .done true #sch", 
            "add ST2132 LEC .from 31 Oct 2pm .to 24 Oct 4pm .done true #sch", 
            "add CS1101S TUT .from 31 Oct 4pm .to 24 Oct 6pm .done true #sch", 
            "add GEH1036 LEC .from 31 Oct 6pm .to 24 Oct 8pm .done true #sch", 
            "add CS2103T TUT .from 2 Nov 9am .to 26 Oct 10am .done false #sch", 
            "add CS1101S LEC .from 2 Nov 10am .to 26 Oct 12pm .done false #sch", 
            "add GET1006 SEC .from 2 Nov 12pm .to 26 Oct 2pm .done false #sch", 
            "add GEH1036 LEC .from 2 Nov 6pm .to 26 Oct 8pm .done false #sch", 
            "add CS2101 SEC .from 3 Nov 10am .to 27 Oct 12pm .done false #sch", 
            "add CS2106 LAB .from 3 Nov 12pm .to 27 Oct 1pm .done false #sch", 
            "add ST2132 TUT .from 3 Nov 1pm .to 27 Oct 2pm .done false #sch", 
            "add ST2132 LEC .from 3 Nov 2pm .to 27 Oct 4pm .done false #sch", 
            "add GEH1036 TUT .from 3 Nov 5pm .to 27 Oct 6pm .done false #sch", 
            "add CS1101S LEC .from 4 Nov 10am .to 28 Oct 11am .done false #sch", 
            "add CS2106 TUT .from 4 Nov 11am .to 28 Oct 12pm .done false #sch", 
            "add CS2106 LEC .from 4 Nov 12pm .to 28 Oct 2pm .done false #sch", 
            "add CS2103 LEC .from 4 Nov 2pm .to 28 Oct 4pm .done false #sch", 
            "add CS2101 Project .due 17 Oct11:59pm .done true #hw", 
            "add Print GEH1036 notes .due 18 Oct 11:59pm .done true #hw", 
            "add ST2132 Assignment 2 .due 19 Oct 11:59pm .done true #hw", 
            "add ST2132 Assignment 3 .due 26 Oct 11:59pm .done true #hw", 
            "add ST2132 Assignment 4 .due 5 Nov 11:59pm .done false #hw", 
            "add CS2106 Lab 5 .due 7 Nov 11:59pm .done false #hw", 
            "add GEH1036 Revision .due 15 Nov", 
            "add GET1006 Revision .due 15 Nov", 
            "add CS2101 Revision .due 15 Nov", 
            "add CS2103 Revision .due 15 Nov", 
            "add CS2106 Revision .due 15 Nov", 
            "add ST2132 Revision .due 15 Nov", 
            "add Dinner with family .from 19 Oct 7pm .to 19 Oct 9pm .done true #life", 
            "add Dinner with Alex .from 20 Oct 7pm .to 20 Oct 9pm .done true #life", 
            "add Lunch with Yu Kai .from 21 Oct 12pm .to 21 Oct 1pm .done true #life", 
            "add Cycling .from 26 Nov9am .to 26 Oct 7pm .done false #life", 
            "add Return library books .due 14 Nov .done false #life", 
            "add Attend financial workshop .from 12 Nov 8am .to 12 Nov 6pm .done false #life", 
            "add Buy stationery .done false #life", 
            "add Plan night cycling trip .done false #life", 
            "add Install telegram .done false #life", 
            "add Set alarm for Monday .done true #life", 
            "add Prepare cue cards for presentation .done true #sch", 
            "add Haircut .done true #life", 
            "add Call Nicole .done true #life", 
            "add Collect EWP .done true #life",
            "edit #life blue",
            "edit #hw orange",
            "edit #sch green"
    };
}
