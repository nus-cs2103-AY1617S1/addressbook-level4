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
import seedu.address.commons.util.FileUtil;
import seedu.address.model.task.Task;

public class SaveCommand extends Command {

	
	public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": specify location of folder to save data in.\n"
            + "Parameters: DATA_STORAGE_FOLDER_PATH"
            + "Example: " + COMMAND_WORD
            + "/Users/jim/GitHub/main/data";

    public static final String MESSAGE_SUCCESS = "New file location saved.";
    public static final String MESSAGE_PATH_IS_NOT_A_DIRECTORY = "The path given does not refer to a folder.";
    public static final String MESSAGE_FOLDER_CANNOT_BE_CREATED = "A new folder cannot be created with the given path.";
    
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
		File f = new File(dirPath);
		if (!f.exists()) {
			try {
				f.mkdirs();
			} catch (SecurityException e) {
				return new CommandResult(MESSAGE_FOLDER_CANNOT_BE_CREATED);
			}
		}
		if (!f.isDirectory())
			return new CommandResult(MESSAGE_PATH_IS_NOT_A_DIRECTORY);
		
		try {
			copyDataFilesToNewPath(dirPath);
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
		changeConfigPaths(dirPath);
		return new CommandResult(MESSAGE_SUCCESS);
	}

	
	
	private void copyDataFilesToNewPath(String arguments) throws DataConversionException {

        File configFile = new File(Config.DEFAULT_CONFIG_FILE);
        
		Config config;

        try {
            config = FileUtil.deserializeObjectFromJsonFile(configFile, Config.class);
        } catch (IOException e) {
            throw new DataConversionException(e);
        }

		File oldDataPath = new File(config.getAddressBookFilePath());
		//File oldUserPrefPath = new File(config.getUserPrefsFilePath());
		File newDataPath = new File(arguments+"task.xml");
		//File newUserPrefPath = new File(arguments+"preferences.json");
		if(oldDataPath.renameTo(newDataPath))
			System.out.print("changed");
		//oldUserPrefPath.renameTo(newUserPrefPath);
		System.out.print(oldDataPath);
		System.out.print(newDataPath);
		
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
//    			if (currLine.contains("userPrefsFilePath")) {
//        			currLine = currLine.replaceAll(currLine.substring(25, currLine.length()-2), (arguments + "preferences.json"));
//    			} else 
				if (currLine.contains("taskBookFilePath")) {
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
