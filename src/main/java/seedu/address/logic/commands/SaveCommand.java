package seedu.address.logic.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import seedu.address.commons.core.Config;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.task.Task;

public class SaveCommand extends Command {

	
	public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": specify location of folder to save data in.\n"
            + "Parameters: DATA_STORAGE_FOLDER_PATH"
            + "Example: " + COMMAND_WORD
            + "/Users/jim/GitHub/main/data";

    public static final String MESSAGE_SUCCESS = "New file location saved.";
    public static final String MESSAGE_FOLDER_DOES_NOT_EXIST = "This folder currently does not exist, new folder created.";
    
    private final String dirPath;

    /**
	 * Constructor
	 *
	 * @throws IllegalValueException
	 *             if any of the raw values are invalid
	 */
	public SaveCommand(String dirPath) {
		this.dirPath = dirPath;
	}
	
	@Override
	public CommandResult execute() {
		
		
		changeConfigPaths(dirPath);
		
		return new CommandResult(MESSAGE_SUCCESS);
	}

	
	
	private void copyDataFilesToNewPath(String arguments) {

		File dataPath = new File(arguments+"task.xml");
		File userPrefPath = new File(arguments+"preferences.json");
	}
	/**
     * change the path fields in the config.json file
     * @param arguments
     */
    private void changeConfigPaths(String arguments) {
    	FileReader configFileReader = null;
    	FileWriter configFileWriter = null;
		try {
			configFileReader = new FileReader(Config.DEFAULT_CONFIG_FILE);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
    	String currLine = "";
    	String newConfig = "";
    	
    	try {
    		BufferedReader br = new BufferedReader(configFileReader);
    		while ((currLine = br.readLine()) != null) {
    			if (currLine.contains("userPrefsFilePath")) {
        			currLine = currLine.replaceAll(currLine.substring(25, currLine.length()-2), (arguments + "preferences.json"));
    			} else if (currLine.contains("taskBookFilePath")) {
        			currLine = currLine.replaceAll(currLine.substring(24, currLine.length()-2), (arguments + "task.xml"));
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
			configFileWriter = new FileWriter(Config.DEFAULT_CONFIG_FILE, false);
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
