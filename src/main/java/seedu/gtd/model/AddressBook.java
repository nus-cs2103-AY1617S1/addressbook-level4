package seedu.gtd.model;

import javafx.collections.ObservableList;

import seedu.gtd.commons.util.ConfigUtil;
import seedu.gtd.commons.core.Config;
import seedu.gtd.commons.exceptions.DataConversionException;
import seedu.gtd.model.tag.Tag;
import seedu.gtd.model.tag.UniqueTagList;
import seedu.gtd.model.task.ReadOnlyTask;
import seedu.gtd.model.task.Task;
import seedu.gtd.model.task.UniqueTaskList;
<<<<<<< HEAD
import seedu.gtd.model.task.UniqueTaskList.TaskNotFoundException;
=======
import seedu.gtd.storage.JsonUserPrefsStorage;
import seedu.gtd.storage.StorageManager;
import seedu.gtd.storage.XmlAddressBookStorage;
import seedu.gtd.MainApp;
>>>>>>> C3/change-storage-path

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {
	
	//@@author addressbook-level4
    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public AddressBook() {}

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
        System.out.println("start with first method");
    }

    /**
     * Tasks and Tags are copied into this addressbook
     */
    public AddressBook(UniqueTaskList tasks, UniqueTagList tags) {
        resetData(tasks.getInternalList(), tags.getInternalList());
        System.out.println("start with second method");
    }

    public static ReadOnlyAddressBook getEmptyAddressBook() {
        return new AddressBook();
    }
    
//@@author A0139072H    
//// application-wide operations
    public void setFilePathTask(String newFilePath) throws IOException{
        Config changedConfig;
        String configFilePathUsed;
        
        System.out.println("SetFilePathTask");
        
        //@@author addressbook-level4
        //Reused config saving
        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            changedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            changedConfig = new Config();
        }

        changedConfig.setAddressBookFilePath(newFilePath);
        System.out.println("Saved to " + newFilePath);
        
        //@@author A0139072H    
        //Save the config back to the file
    	ConfigUtil.saveConfig(changedConfig, configFilePathUsed);
    	StorageManager newSaveMgr = new StorageManager(
    			new XmlAddressBookStorage(newFilePath), 
    			new JsonUserPrefsStorage(changedConfig.getUserPrefsFilePath())
    			);
    	//Save the addressBook to the new location
    	newSaveMgr.saveAddressBook(this);
    };
    
//@@author addressbook-level4
//// list overwrite operations

    public ObservableList<Task> getTasks() {
        return tasks.getInternalList();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks.getInternalList().setAll(tasks);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags.getInternalList().setAll(tags);
    }

    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
    }

    public void resetData(ReadOnlyAddressBook newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }

//// task-level operations

    /**
     * Adds a task to the address book.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task t) throws UniqueTaskList.DuplicateTaskException {
        syncTagsWithMasterList(t);
        tasks.add(t);
    }
    
    //@@author A0146130W
    /**
     * Edits a task in the address book.
     * Also checks the updated task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws UniqueTaskList.TaskNotFoundException if the task was not found.
     */
    public void editTask(int index, Task t) throws UniqueTaskList.TaskNotFoundException {
        syncTagsWithMasterList(t);
        tasks.edit(index, t);
    }
    
    //@@author addressbook-level4
    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncTagsWithMasterList(Task t) {
        final UniqueTagList taskTags = t.getTags();
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        for (Tag tag : tags) {
            masterTagObjects.put(tag, tag);
        }

        // Rebuild the list of task tags using references from the master list
        final Set<Tag> commonTagReferences = new HashSet<>();
        for (Tag tag : taskTags) {
            commonTagReferences.add(masterTagObjects.get(tag));
        }
        t.setTags(new UniqueTagList(commonTagReferences));
    }

    public boolean removeTask(ReadOnlyTask key) throws UniqueTaskList.TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new UniqueTaskList.TaskNotFoundException();
        }
    }
    
    public void doneTask(int index, Task target) throws TaskNotFoundException {
		System.out.println("in addressbook");
		tasks.done(index, target);
	}

//// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

//// util methods

    @Override
    public String toString() {
        return tasks.getInternalList().size() + " tasks, " + tags.getInternalList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasks.getInternalList());
    }
 
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tags.getInternalList());
    }

    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasks;
    }

    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tags;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.tasks.equals(((AddressBook) other).tasks)
                && this.tags.equals(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
