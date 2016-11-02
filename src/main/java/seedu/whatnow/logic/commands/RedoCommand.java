package seedu.whatnow.logic.commands;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.whatnow.commons.core.Config;
import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.ConfigUtil;
import seedu.whatnow.commons.util.StringUtil;
import seedu.whatnow.model.WhatNow;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.Task;
import seedu.whatnow.model.task.UniqueTaskList;
import seedu.whatnow.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.whatnow.model.task.UniqueTaskList.TaskNotFoundException;
import seedu.whatnow.storage.StorageManager;

public class RedoCommand extends Command {
    // @@author A0139128A
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo the previous action "
            + "Parameters: No parameters" + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Redo Successfully";
    public static final String MESSAGE_FAIL = "Redo failure due to unexisting undo commands";

    public static final String UNKNOWN_COMMAND_FOUND = "Unknown Command found";

    public static final String MESSAGE_LIST_NO_REDO_LIST = " No list command to redo";
    
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);

    // @@author A0139128A-reused
    public final String ADD_COMMAND = "add";
    public final String DELETE_COMMAND = "delete";
    public final String LIST_COMMAND = "list";
    public final String MARKDONE_COMMAND = "done";
    public final String MARKUNDONE_COMMAND = "undone";
    public final String UPDATE_COMMAND = "update";
    public final String CLEAR_COMMAND = "clear";
    public final String CHANGE_COMMAND = "change";

    // @@author A0139128A
    @Override
    public CommandResult execute() throws TaskNotFoundException {
        assert model != null;
        if (model.getRedoStack().isEmpty()) {
            return new CommandResult(MESSAGE_FAIL);
        } else {
            String reqCommand = model.getRedoStack().pop();
            model.getUndoStack().push(reqCommand);
            return performReqRedo(reqCommand);
        }
    }

    // @@author A0139128A-reused
    private CommandResult performReqRedo(String reqCommand) throws TaskNotFoundException {
        if (reqCommand.equals(ADD_COMMAND)) {
            return performRedoAdd();
        } else if (reqCommand.equals(DELETE_COMMAND)) {
            return performRedoDelete();
        } else if (reqCommand.equals(LIST_COMMAND)) {
            return performRedoList();
        } else if (reqCommand.equals(MARKDONE_COMMAND)) {
            return performRedoMarkDone();
        } else if (reqCommand.equals(MARKUNDONE_COMMAND)) {
            return performRedoMarkUnDone();
        } else if (reqCommand.equals(UPDATE_COMMAND)) {
            return performRedoUpdate();
        } else if (reqCommand.equals(CLEAR_COMMAND)) {
            return performRedoClear();
        } else if(reqCommand.equals(CHANGE_COMMAND)) {
            return performRedoChange();
        } else
            return new CommandResult(UNKNOWN_COMMAND_FOUND);
    }
    
    //@@author A0141021H
    private CommandResult performRedoChange() {
        if(model.getStackOfChangeFileLocationNew().isEmpty()) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
        }
        Config config;
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        String curr="";
        String old="";
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            config = configOptional.orElse(new Config());
            curr = config.getWhatNowFilePath();
            old = model.getStackOfChangeFileLocationNew().pop();
            model.getStackOfChangeFileLocationOld().push(curr);
            config.setWhatNowFilePath(old);
            model.changeLocation(FileSystems.getDefault().getPath(old), config);
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. " +
                    "Using default config properties");
            config = new Config();
        }
        
        try {
            ConfigUtil.saveConfig(config, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        
        return new CommandResult(String.format(ChangeCommand.MESSAGE_REDO_SUCCESS, old));
    }

    // @@author A0139128A
    private CommandResult performRedoAdd() {
        assert model != null;
        if (model.getDeletedStackOfTasksAddRedo().isEmpty()) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
        } else {
            try {
                ReadOnlyTask reqTask = model.getDeletedStackOfTasksAddRedo().pop();
                model.addTask((Task) reqTask);
                model.getDeletedStackOfTasksAdd().push(reqTask);
            } catch (DuplicateTaskException e) {
                return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
            }
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS));
        }
    }

    // @@author A0139128A
    private CommandResult performRedoDelete() {
        if (model.getDeletedStackOfTasksRedo().isEmpty() || model.getDeletedStackOfTasksIndexRedo().isEmpty()) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
        } else {
            ReadOnlyTask taskToDelete = model.getDeletedStackOfTasksRedo().pop();
            model.getDeletedStackOfTasks().push(taskToDelete);
            int idxToRedoAdd = model.getDeletedStackOfTasksIndexRedo().pop();
            try {
                model.deleteTask((Task) taskToDelete);
                model.getDeletedStackOfTasksIndex().push(idxToRedoAdd);
            } catch (TaskNotFoundException tnfe) {
                return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
            }
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS));
        }
    }

    // @@author A0139128A
    private CommandResult performRedoList() {
        if (model.getStackOfListTypesRedo().isEmpty()) {
            return new CommandResult(MESSAGE_LIST_NO_REDO_LIST);
        } else {
            String prevCommandListType = model.getStackOfListTypesRedo().pop();
            model.getStackOfListTypes().push(prevCommandListType);
            return performReqRedoList(prevCommandListType);
        }
    }

    private CommandResult performReqRedoList(String prevCommandListType) {
        if (prevCommandListType.equals(ListCommand.TASK_STATUS_ALL)) {
            model.updateFilteredListToShowAll();
            model.updateFilteredScheduleListToShowAll();
            return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
        } else if (prevCommandListType.equals(ListCommand.TASK_STATUS_INCOMPLETE)) {
            model.updateFilteredListToShowAllIncomplete();
            model.updateFilteredScheduleListToShowAllIncomplete();
            return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
        } else {
            model.updateFilteredListToShowAllCompleted();
            model.updateFilteredScheduleListToShowAllCompleted();
            return new CommandResult(UndoCommand.MESSAGE_SUCCESS);
        }
    }

    // @@author A0139128A
    private CommandResult performRedoMarkDone() {
        if (model.getStackOfMarkDoneTaskRedo().isEmpty()) {
            return new CommandResult(RedoCommand.MESSAGE_FAIL);
        } else {
            ReadOnlyTask taskToMark = model.getStackOfMarkDoneTaskRedo().pop();
            try {
                model.markTask(taskToMark);
                model.getStackOfMarkDoneTask().push(taskToMark);
            } catch (TaskNotFoundException tnfe) {
                return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
            }
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS));
        }
    }

    // @@author A0139128A
    private CommandResult performRedoMarkUnDone() {
        if (model.getStackOfMarkUndoneTaskRedo().isEmpty()) {
            return new CommandResult(RedoCommand.MESSAGE_FAIL);
        } else {
            ReadOnlyTask taskToMark = model.getStackOfMarkDoneTaskRedo().pop();
            try {
                model.unMarkTask(taskToMark);
                model.getStackOfMarkUndoneTask().push(taskToMark);
            } catch (TaskNotFoundException tnfe) {
                return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
            }
            return new CommandResult(String.format(RedoCommand.MESSAGE_SUCCESS));
        }
    }

    // @@author A0139128A
    private CommandResult performRedoUpdate() throws TaskNotFoundException {
        assert model != null;
        if (model.getOldTask().isEmpty() && model.getNewTask().isEmpty()) {
            return new CommandResult(String.format(RedoCommand.MESSAGE_FAIL));
        } else {
            ReadOnlyTask originalTask = model.getNewTask().pop();
            ReadOnlyTask wantedTask = model.getOldTask().pop();
            model.getOldTask().push(originalTask);
            model.getNewTask().push(wantedTask);
            try {
                model.updateTask(originalTask, (Task) wantedTask);
            } catch (UniqueTaskList.DuplicateTaskException utle) {
                model.getOldTask().pop();
                model.getNewTask().pop();
                return new CommandResult(RedoCommand.MESSAGE_FAIL);
            }
            return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
        }
    }

    // @@author A0139128A
    private CommandResult performRedoClear() {
        model.resetData(WhatNow.getEmptyWhatNow());
        return new CommandResult(RedoCommand.MESSAGE_SUCCESS);
    }
}
