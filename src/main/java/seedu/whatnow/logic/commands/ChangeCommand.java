package seedu.whatnow.logic.commands;
import java.io.IOException;
import java.util.logging.Logger;

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

import static seedu.whatnow.commons.core.Messages.*;

public class ChangeCommand extends Command {
    
    public static final String COMMAND_WORD = "change";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the WhatNow data file storage location. "
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " location to"
            + " PATH: C:"+"\\" + "Users" + "\\" + "Jim"+"\\"+"Dropbox"+"\\"+"WhatNow";
    
    public static final String MESSAGE_SUCCESS = "The data storage location has been successfully changed to: %1$s";

    public String newPath;
    
    public MainApp app;
    
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
            config.setWhatNowFilePath(newPath);
            String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
            
            try {
                ConfigUtil.saveConfig(config, configFilePathUsed);
            } catch (IOException e) {
                logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
            } 
            
            app = new MainApp();
            app.setConfig(config);
            Storage storage = new StorageManager(config.getWhatNowFilePath(), config.getUserPrefsFilePath());
            app.setStorage(storage);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newPath));
        } else { 
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, newPath));
        }
    }
}
