package seedu.whatnow.logic.commands;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.sun.glass.ui.Application;

import seedu.whatnow.MainApp;
import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.util.ConfigUtil;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.storage.Storage;
import seedu.whatnow.storage.StorageManager;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;

import static seedu.whatnow.commons.core.Messages.*;

public class ChangeCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the WhatNow data file storage location. "
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " location to"
            + " PATH: C:"+"\\" + "Users" + "\\" + "Jim"+"\\"+"Dropbox";

    public static final String MESSAGE_SUCCESS = "The data storage location has been successfully changed to: %1$s";

    public String newPath;

   // public Application app = Application.GetApplication();

    public Path source; 

    public Path destination; 

    public Config config = new Config();

    private static final Logger logger = LogsCenter.getLogger(ChangeCommand.class);

    public ChangeCommand(String newPath) {
        this.newPath=newPath;
    }

    @Override
    public CommandResult execute() {
        Path path = FileSystems.getDefault().getPath(newPath);
        
        if(Files.exists(path)){
            if (newPath.contains("\\"))
                newPath = newPath + "\\whatnow.xml";
            else if (newPath.contains("/"))
                newPath = newPath + "/whatnow.xml";
            else
                newPath = newPath + "/whatnow.xml";

            Path source =  FileSystems.getDefault().getPath(config.getWhatNowFilePath());
            
            System.out.println("source: " + config.getWhatNowFilePath());
            
            config.setWhatNowFilePath(newPath);
            
            String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
            
            try {
                ConfigUtil.saveConfig(config, configFilePathUsed);
            } catch (IOException e) {
                logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
            } 
            
            MainApp app = new MainApp();
            app.setConfig(config);
          
            Storage storage = new StorageManager(config.getWhatNowFilePath(), config.getUserPrefsFilePath());
            //try to immediately change file into new location
        
            Path destination = FileSystems.getDefault().getPath(config.getWhatNowFilePath());
            System.out.println("Destination: " + config.getWhatNowFilePath());

            try {
                Files.move(source, destination, REPLACE_EXISTING);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            //ends
            app.setStorage(storage);
          
            
            return new CommandResult(String.format(MESSAGE_SUCCESS, newPath));
        } else { 
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, newPath));
        }
    }

//    private void copyFile(File from, File to) throws IOException {
//        Files.copy( from.toPath(), to.toPath() ); 
//    }
}
