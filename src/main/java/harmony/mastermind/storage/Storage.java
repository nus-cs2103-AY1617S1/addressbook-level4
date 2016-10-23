package harmony.mastermind.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Optional;

import harmony.mastermind.commons.core.Config;
import harmony.mastermind.commons.events.model.TaskManagerChangedEvent;
import harmony.mastermind.commons.events.storage.DataSavingExceptionEvent;
import harmony.mastermind.commons.exceptions.DataConversionException;
import harmony.mastermind.memory.GenericMemory;
import harmony.mastermind.memory.Memory;
import harmony.mastermind.model.ReadOnlyTaskManager;
import harmony.mastermind.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    //initializing variables 
    static String SAVE_FILE = "data.txt";
    static final String ERROR_READ = "Unable to read from file!\nTry checking " + SAVE_FILE + " or continue using to start over.\n\n";
    static final String ERROR_CREATE = "Problem creating file!";
    static final String ERROR_NOT_FOUND = "File not found";
    static final String NULL = "-";
    static final String SPACE = " ";
    
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTaskManagerFilePath();

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, FileNotFoundException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Mastermind to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent tmce);
    
    //@author A0143378Y
    static void saveToStorage(Memory memory) { 
        try {
            PrintWriter pw = new PrintWriter(SAVE_FILE);
            for (int i=0; i<memory.getSize(); i++) {
                pw.println(memory.get(i).getType());
                pw.println(memory.get(i).getName());
                printDescription(memory, pw, i);
                printStart(memory, pw, i);
                printEnd(memory, pw, i);
                pw.println(memory.get(i).getState());

                pw.println("");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(ERROR_NOT_FOUND);
            e.printStackTrace();
        }
    }

    //@author A0143378Y
    static void printEnd(Memory memory, PrintWriter pw, int i) { 
        if (memory.get(i).getEnd() != null) {
            pw.println(calendarToString(memory.get(i).getEnd()));
        } else {
            pw.println(NULL);
        }
    }

    //@author A0143378Y
    static void printStart(Memory memory, PrintWriter pw, int i) { 
        if (memory.get(i).getStart() != null) {
            pw.println(calendarToString(memory.get(i).getStart()));
        } else {
            pw.println(NULL);
        }
    }

    static String calendarToString(Calendar a) { 
        String dateTime = "";
        dateTime = a.get(Calendar.YEAR) + SPACE + a.get(Calendar.MONTH) + SPACE + a.get(Calendar.DATE);
        if(a.isSet(Calendar.HOUR_OF_DAY)){
            dateTime = dateTime + SPACE + a.get(Calendar.HOUR_OF_DAY) + SPACE + a.get(Calendar.MINUTE) + SPACE + a.get(Calendar.SECOND);
        }
        return dateTime;
    }

    //@author A0143378Y
    static void printDescription(Memory memory, PrintWriter pw, int i) {
        if (memory.get(i).getDescription() != null) {
            pw.println(memory.get(i).getDescription());
        } else {
            pw.println(NULL);
        }
    }

    //@author A0143378Y
    static void checkForFileExists(Memory memory) { 
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(ERROR_CREATE);
                System.exit(0);
            }
        }
        readFromFile(memory);
    }

    //@author A0143378Y
    static void readFromFile(Memory memory) { 
        try {
            BufferedReader br = new BufferedReader(new FileReader(SAVE_FILE));
            String line = br.readLine();

            while (line != null) {
                String type = null;
                String name = null;
                String description = null;
                Calendar startCal = null;
                Calendar endCal = null;
                int state = 0;

                type = line;
                name = br.readLine();
                description = readDescription(br);
                startCal = readCalendar(br, startCal);
                endCal = readCalendar(br, endCal);
                state = readState(br);

                br.readLine(); //blank line
                memory.memory.add(new GenericMemory(type, name, description, startCal, endCal, state));
                line = br.readLine();

            }
            br.close();
        } catch (IOException | NumberFormatException | NullPointerException e) {
            System.out.println(ERROR_READ);
        }
    }

    //@author A0143378Y
    // Read line for integer for state
    static int readState(BufferedReader br) throws IOException {
        int state;
        String stateString = br.readLine();
        state=Integer.parseInt(reduceToInt(stateString));
        return state;
    }
    
    //@author A0143378Y
    static String reduceToInt(String stateString) { 
        return stateString.replaceAll("[^0-9]", "");
    }

    //@author A0143378Y
    // Read line for calendar for start and end dates
    static Calendar readCalendar(BufferedReader br, Calendar startCal) throws IOException {
        String start = br.readLine();
        if (start.equals(NULL)) {
            start = null;
        } else {
            startCal = stringToCalendar(start);
        }
        return startCal;
    }

    //@author A0143378Y
    // Converts string representation of date and time back into Calendar object
    static Calendar stringToCalendar(String b){
        Calendar setNew = new GregorianCalendar();

        String[] details = b.split(SPACE);
        try {
            setNew.set(Integer.parseInt(details[0]), Integer.parseInt(details[1]), Integer.parseInt(details[2]));

            if(details.length == 6){
                setNew.set(Calendar.HOUR_OF_DAY,Integer.parseInt(details[3]));
                setNew.set(Calendar.MINUTE, Integer.parseInt(details[4]));
                setNew.set(Calendar.SECOND, Integer.parseInt(details[5]));
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println(ERROR_READ);
            e.printStackTrace();
        }

        return setNew;
    }

    //@author A0143378Y
    static String readDescription(BufferedReader br) throws IOException {
        String description;
        description = br.readLine();
        if (description.equals(NULL)) {
            description = null;
        }
        return description;
    }
}
