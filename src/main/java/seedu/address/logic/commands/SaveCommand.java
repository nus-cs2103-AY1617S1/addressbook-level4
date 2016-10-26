package seedu.address.logic.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ConfigUtil;

/**
 * @author A0139528W
 * 
 * Command to change the location of the task.xml file
 *
 */

public class SaveCommand extends Command {

	
	private static final String configFilePath = Config.DEFAULT_CONFIG_FILE;

	public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": specify location of folder to save data in.\n"
            + "Parameters: DATA_STORAGE_FOLDER_PATH"
            + "Example: " + COMMAND_WORD
            + "/Users/jim/GitHub/main/data";

    public static final String MESSAGE_SUCCESS = "New file location saved.";
    public static final String MESSAGE_PATH_IS_NOT_A_DIRECTORY = "The path given does not refer to a folder.";
    public static final String MESSAGE_FOLDER_CANNOT_BE_CREATED = "A new folder cannot be created with the given path.";
    public static final String MESSAGE_CONFIG_FILE_CANNOT_LOAD = "config.json file cannot be found.";
	public static final String MESSAGE_LOCATION_SPECIFIED_SAME = "The current Data Storage is already in the given folder.";
    
    private final String dirPath;

    /**
	 * Constructor
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public SaveCommand(String dirPath) {
		this.dirPath = dirPath + "task.xml";
	}
	
	@Override
	public CommandResult execute() {
		
		// Creates the folder if the file does not exist
		File f = new File(dirPath);
		if (!f.exists()) {
			try {
				f.mkdirs();
			} catch (SecurityException e) {
				return new CommandResult(MESSAGE_FOLDER_CANNOT_BE_CREATED);
			}
		}
		
		// Checks if the given path is a directory and not a file
		if (!f.isDirectory())
			return new CommandResult(MESSAGE_PATH_IS_NOT_A_DIRECTORY);

		Config config;
		try {
			config = ConfigUtil.readConfig(configFilePath).orElse(new Config());

			// Moves the old task.xml file to the new location
			File oldDataPath = new File(config.getAddressBookFilePath());
			if (this.dirPath.equals(oldDataPath.toString())) {
				return new CommandResult(MESSAGE_LOCATION_SPECIFIED_SAME);
			}
			File newDataPath = new File(dirPath);
			oldDataPath.renameTo(newDataPath);

			changeConfigPaths(dirPath);
			
			indicateStorageDataPathChangeCommand(oldDataPath.toString(), newDataPath.toString());
			
		} catch (DataConversionException e) {
			return new CommandResult(MESSAGE_CONFIG_FILE_CANNOT_LOAD);
		}

		return new CommandResult(MESSAGE_SUCCESS);
	}

	
	
	/**
     * change the path fields in the config.json file
     * 
     * @param arguments
     */
    private void changeConfigPaths(String arguments) {
    	FileReader configFileReader = null;
    	FileWriter configFileWriter = null;
		try {
			configFileReader = new FileReader(configFilePath);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    	String currLine = "";
    	String newConfig = "";
    	
    	try {
    		BufferedReader br = new BufferedReader(configFileReader);
    		while ((currLine = br.readLine()) != null) {
				if (currLine.contains("taskBookFilePath")) {
        			currLine = currLine.replaceAll(currLine.substring(24, currLine.length()-2), (arguments));
    			}
    			if (!currLine.contains("}")) {
    				currLine = currLine + "\n";
    			}
    			newConfig += currLine;
    		}
    		br.close();
    	} catch (IOException e3){
    		e3.printStackTrace();
    	}

		try {
			configFileWriter = new FileWriter(configFilePath, false);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		BufferedWriter bw = new BufferedWriter(configFileWriter);
		try {
			bw.write(newConfig);
			bw.close();
		} catch (IOException e4) {
			e4.printStackTrace();
		}
    		
    }

}
