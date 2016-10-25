package seedu.todo.model.tag;

import javafx.collections.ObservableList;
import seedu.todo.model.task.ImmutableTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.Set;

//@@author A0135805H
/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 * Also supports minimal set of list operations for the app's features.
 *
 * Note: This class will disallow external access to {@link #uniqueTagsToTasksMap} so to
 * maintain uniqueness of the tag names.
 */
public class UniqueTagCollection implements Iterable<Tag>, UniqueTagCollectionModel {
    /* Variables */
    /*
        Stores a list of tags with unique tag names.
        TODO: ImmutableTask does not have consistent hashing.
        TODO: So, duplicated ImmutableTask may be found in the set of each Tag.
     */
    private final Map<Tag, Set<ImmutableTask>> uniqueTagsToTasksMap = new HashMap<>();

    /* Constants */
    private final Logger logger = Logger.getLogger(UniqueTagCollection.class.getName());

    /**
     * Constructs empty TagList.
     */
    public UniqueTagCollection() {}

    /* Interfacing Methods */
    @Override
    public void initialise(ObservableList<ImmutableTask> globalTaskList) {
        uniqueTagsToTasksMap.clear();
        globalTaskList.forEach(task -> task.getTags().forEach(tag -> associateTaskToTag(task, tag)));
    }

    @Override
    public void saveCollectionState() {
        //TODO: To be implemented
    }

    @Override
    public Tag registerTagWithTask(ImmutableTask task, String tagName) {
        Tag tag = getTagWithName(tagName, false);
        associateTaskToTag(task, tag);
        return tag;
    }

    @Override
    public Tag unregisterTagWithTask(ImmutableTask task, String tagName) {
        Tag tag = getTagWithName(tagName, true);
        dissociateTaskFromTag(task, tag);
        return tag;
    }

    @Override
    public void notifyTaskDeleted(ImmutableTask task) {
        task.getTags().forEach(tag -> dissociateTaskFromTag(task, tag));
    }

    @Override
    public void renameTag(String originalName, String newName) {
        Tag tag = getTagWithName(originalName, true);
        Set<ImmutableTask> setOfTasks = uniqueTagsToTasksMap.remove(tag);
        tag.rename(newName);
        uniqueTagsToTasksMap.put(tag, setOfTasks);
    }

    /* Helper Methods */
    /**
     * Links a {@code task} to the {@code tag} in the {@link #uniqueTagsToTasksMap}.
     */
    private void associateTaskToTag(ImmutableTask task, Tag tag) {
        Set<ImmutableTask> setOfTasks = uniqueTagsToTasksMap.get(tag);
        if (setOfTasks == null) {
            setOfTasks = new HashSet<>();
            uniqueTagsToTasksMap.put(tag, setOfTasks);
        }
        setOfTasks.add(task);
    }

    /**
     * Removes the association between the {@code task} from the {@code tag} in
     * the {@link #uniqueTagsToTasksMap}.
     */
    private void dissociateTaskFromTag(ImmutableTask task, Tag tag) {
        Set<ImmutableTask> setOfTasks = uniqueTagsToTasksMap.get(tag);
        if (setOfTasks != null) {
            setOfTasks.remove(task);
        }
    }

    /**
     * Obtains an instance of {@link Tag} with the supplied {@code tagName} from the
     * {@link #uniqueTagsToTasksMap}.
     *
     * Note: If such an instance is not found, a new {@link Tag} instance will \be added to the
     *       {@link #uniqueTagsToTasksMap}.
     * Note: If {@code expectAvailable} is true, logger will log an error when when we can't find the
     *       tag with the tag name.
     *
     * @param tagName The name of the {@link Tag}.
     * @param expectAvailable True implies that the {@link Tag} object must be found.
     * @return A {@link Tag} object that has the name {@code tagName}.
     * TODO: Allow this method to have less responsibility.
     */
    private Tag getTagWithName(String tagName, boolean expectAvailable) {
        Optional<Tag> possibleTag = uniqueTagsToTasksMap.keySet().stream()
                .filter(tag -> tag.getTagName().equals(tagName)).findAny();

        if (!possibleTag.isPresent() && expectAvailable) {
            logger.warning(UniqueTagCollectionUtil.ERROR_DATA_INTEGRITY);
        }

        Tag targetTag;
        if (possibleTag.isPresent()) {
            targetTag = possibleTag.get();
        } else {
            targetTag = new Tag(tagName);
            uniqueTagsToTasksMap.put(targetTag, new HashSet<>());
        }
        return targetTag;
    }

    /**
     * Simply finds a tag with the tag name.
     */
    private Optional<Tag> findTagWithName(String tagName) {
        return uniqueTagsToTasksMap.keySet().stream()
                .filter(tag -> tag.getTagName().equals(tagName)).findAny();
    }

    /* Interfacing Getters */
    @Override
    public List<Tag> getUniqueTagList() {
        return new ArrayList<>(uniqueTagsToTasksMap.keySet());
    }

    @Override
    public List<ImmutableTask> getTasksLinkedToTag(String tagName) {
        Optional<Tag> possibleTag = findTagWithName(tagName);
        if (possibleTag.isPresent()) {
            Set<ImmutableTask> tasks = uniqueTagsToTasksMap.get(possibleTag.get());
            return new ArrayList<>(tasks);
        } else {
            return new ArrayList<>();
        }
    }

    /* Other Override Methods */
    @Override
    public Iterator<Tag> iterator() {
        return uniqueTagsToTasksMap.keySet().iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagCollection // instanceof handles nulls
                && this.uniqueTagsToTasksMap.equals(
                ((UniqueTagCollection) other).uniqueTagsToTasksMap));
    }

    @Override
    public int hashCode() {
        return uniqueTagsToTasksMap.hashCode();
    }
}
