# A0126240Wreused
###### \src\main\java\seedu\whatnow\logic\commands\SelectCommand.java
``` java
import seedu.whatnow.commons.core.EventsCenter;
import seedu.whatnow.commons.core.Messages;
import seedu.whatnow.commons.core.UnmodifiableObservableList;
import seedu.whatnow.commons.events.ui.JumpToListRequestEvent;
import seedu.whatnow.model.task.ReadOnlyTask;

/**
 * Selects a task identified using it's last displayed index from WhatNow.
 */
public class SelectCommand extends Command {

    public final int targetIndex;

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_TASK_SUCCESS = "Selected Task: %1$s";

    public SelectCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex - 1));
        return new CommandResult(String.format(MESSAGE_SELECT_TASK_SUCCESS, targetIndex));

    }

}
```
###### \src\main\java\seedu\whatnow\model\ModelManager.java
``` java
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatNow.addTask(task);
        updateFilteredListToShowAllIncomplete();
        indicateAddTask(task);
        indicateWhatNowChanged();
    }
```
###### \src\main\java\seedu\whatnow\storage\XmlFileStorage.java
``` java
import javax.xml.bind.JAXBException;

import seedu.whatnow.commons.exceptions.DataConversionException;
import seedu.whatnow.commons.util.XmlUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Stores whatnow data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given whatnow data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableWhatNow whatNow)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, whatNow);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns WhatNow in the file or an empty WhatNow
     */
    public static XmlSerializableWhatNow loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableWhatNow.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### \src\main\java\seedu\whatnow\storage\XmlSerializableWhatNow.java
``` java
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An Immutable WhatNow that is serializable to XML format
 */
@XmlRootElement(name = "whatnow")
public class XmlSerializableWhatNow implements ReadOnlyWhatNow {

    @XmlElement
    private List<XmlAdaptedTask> tasks;
    @XmlElement
    private List<Tag> tags;

    {
        tasks = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableWhatNow() {}

    /**
     * Conversion
     */
    public XmlSerializableWhatNow(ReadOnlyWhatNow src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (UniqueTagList.DuplicateTagException e) {
            //TODO: better error handling
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                //TODO: better error handling
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return lists;
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return tasks.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags);
    }

}
```
