# A0126240Wreused
###### \java\seedu\whatnow\model\ModelManager.java
``` java
    @Override
    public synchronized void addTask(Task task) throws UniqueTaskList.DuplicateTaskException {
        whatNow.addTask(task);
        blockFreeTime(task);
        updateFilteredListToShowAllIncomplete();
        indicateAddTask(task, false);
        indicateWhatNowChanged();
    }

```
###### \java\seedu\whatnow\model\task\Name.java
``` java
package seedu.whatnow.model.task;

import seedu.whatnow.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's name in WhatNow. Guarantees: immutable; is valid as
 * declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Task names should not contain quotation marks \"";
    public static final String NAME_VALIDATION_REGEX = "[^\"]+";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException
     *             if given name string is invalid.
     */
    public Name(String name) throws IllegalValueException {
        assert name != null;
        String nameRemovedTrailSpace = name.trim();
        if (!isValidName(nameRemovedTrailSpace)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = nameRemovedTrailSpace;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                        && this.fullName.equals(((Name) other).fullName)); // state
                                                                           // check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### \java\seedu\whatnow\storage\XmlFileStorage.java
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
    public static void saveDataToFile(File file, XmlSerializableWhatNow whatNow) throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, whatNow);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns WhatNow in the file or an empty WhatNow
     */
    public static XmlSerializableWhatNow loadDataFromSaveFile(File file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableWhatNow.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### \java\seedu\whatnow\storage\XmlSerializableWhatNow.java
``` java
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.whatnow.commons.core.LogsCenter;
import seedu.whatnow.commons.exceptions.IllegalValueException;
import seedu.whatnow.commons.util.CollectionUtil;
import seedu.whatnow.model.ReadOnlyWhatNow;
import seedu.whatnow.model.tag.Tag;
import seedu.whatnow.model.tag.UniqueTagList;
import seedu.whatnow.model.tag.UniqueTagList.DuplicateTagException;
import seedu.whatnow.model.task.ReadOnlyTask;
import seedu.whatnow.model.task.UniqueTaskList;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * An Immutable WhatNow that is serializable to XML format
 */
@XmlRootElement(name = "whatnow")
public class XmlSerializableWhatNow implements ReadOnlyWhatNow {

    private static final Logger logger = LogsCenter.getLogger(XmlSerializableWhatNow.class);
    
    @XmlElement
    private List<XmlAdaptedTask> tasks = new ArrayList<>();
    @XmlElement
    private List<Tag> tags = new ArrayList<>();

    /**
     * Empty constructor required for marshalling
     */
    public XmlSerializableWhatNow() {
    }

    /**
     * Conversion
     */
    public XmlSerializableWhatNow(ReadOnlyWhatNow src) {
        tasks.addAll(src.getTaskList().stream().map(XmlAdaptedTask::new).collect(Collectors.toList()));
        tags = src.getTagList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public UniqueTagList getUniqueTagList() {
        try {
            return new UniqueTagList(tags);
        } catch (DuplicateTagException e) {
            logger.info("Duplicated tags will be removed from the data file.");
            return new UniqueTagList((Set<Tag>)CollectionUtil.getUniqueElements(tags));
        }
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        UniqueTaskList lists = new UniqueTaskList();
        for (XmlAdaptedTask p : tasks) {
            try {
                lists.add(p.toModelType());
            } catch (IllegalValueException e) {
                // TODO: better error handling
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
                logger.warning("At XmlSerializableWhatNow, getTaskList: \n" + e.getMessage());
                return null;
            } catch (ParseException e) {
                logger.warning("At XmlSerializableWhatNow, getTaskList: \n" + e.getMessage());
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
