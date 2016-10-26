package seedu.whatnow.logic.commands;
//@@author A0141021H
import java.io.IOException;
import java.util.logging.Logger;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.ConfigUtil;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import static seedu.whatnow.commons.core.Messages.*;

/**
 * Changes the data file location of WhatNow.
 */
public class ChangeCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the WhatNow data file storage location. "
            + "Parameters: PATH\n"
            + "Example: " + COMMAND_WORD + " location to"
            + " PATH: C:"+"\\" + "Users" + "\\" + "Jim"+"\\"+"Dropbox";

    public static final String MESSAGE_SUCCESS = "The data storage location has been successfully changed to: %1$s";

    public static final String WHATNOW_XMLFILE = "whatnow.xml";

    public static final String DOUBLE_BACKSLASH = "\\";

    public static final String SINGLE_FORWARDSLASH = "/";

    public String newPath;

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

        if(Files.exists(path)) {
            if (newPath.contains(DOUBLE_BACKSLASH)) {
                newPath = newPath + DOUBLE_BACKSLASH + WHATNOW_XMLFILE;
            } else if (newPath.contains(SINGLE_FORWARDSLASH)) {
                newPath = newPath + SINGLE_FORWARDSLASH + WHATNOW_XMLFILE;
            } else {
                newPath = newPath + SINGLE_FORWARDSLASH + WHATNOW_XMLFILE;
            }

            path = FileSystems.getDefault().getPath(newPath);

            config.setWhatNowFilePath(newPath);

            String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

            try {
                model.changeLocation(path, config);
            } catch (DataConversionException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (TaskNotFoundException e1) {
                e1.printStackTrace();
            }

            try {
                ConfigUtil.saveConfig(config, configFilePathUsed);
            } catch (IOException e) {
                logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
            } 

            return new CommandResult(String.format(MESSAGE_SUCCESS, newPath));
        } else { 
            return new CommandResult(String.format(MESSAGE_INVALID_PATH, newPath));
        }
    }
}
