package seedu.todo.model.tag;

import seedu.todo.model.task.ImmutableTask;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collection;
import java.util.Optional;
import java.util.Iterator;
import java.util.Arrays;
import java.util.stream.Collectors;

//@@author A0135805H
/**
 * A list of tags that enforces no nulls and uniqueness between its elements.
 * Also supports minimal set of list operations for the app's features.
 *
 * Note: This class will disallow external access to {@link #uniqueTagsToTasksMap} so to
 * maintain uniqueness of the tag names.
 */
public class UniqueTagCollection implements Iterable<Tag>, UniqueTagCollectionModel {

    //Stores a list of tags with unique tag names.
    private final Map<Tag, Set<ImmutableTask>> uniqueTagsToTasksMap = new HashMap<>();

    /* Constructor */
    /**
     * Constructs this tag collection
     */
    public UniqueTagCollection(List<ImmutableTask> globalTaskList) {
        update(globalTaskList);
    }

    /* Interfacing Methods */
    @Override
    public void update(List<ImmutableTask> globalTaskList) {
        uniqueTagsToTasksMap.clear();
        globalTaskList.forEach(task -> task.getTags().forEach(tag -> associateTaskToTag(task, tag)));
    }

    @Override
    public void notifyTaskDeleted(ImmutableTask task) {
        task.getTags().forEach(tag -> dissociateTaskFromTag(task, tag));
    }

    @Override
    public Collection<Tag> associateTaskToTags(ImmutableTask task, String[] tagNames) {
        return Arrays.stream(tagNames)
                .map(name -> associateTaskToTag(task, getTagWithName(name)))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Tag> dissociateTaskFromTags(ImmutableTask task, String[] tagNames) {
        //Data validation at TodoModel should have checked if this is available.
        //Even if it is not, getTagWithName(...) will recreate the tag which gets deleted again, safe op.

        return Arrays.stream(tagNames)
                .map(name -> dissociateTaskFromTag(task, getTagWithName(name)))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Tag> deleteTags(String[] tagNames) {
        //Data validation at TodoModel should have checked if this is available.
        //Even if it is not, getTagWithName(...) will recreate the tag which gets deleted again, safe op.

        return Arrays.stream(tagNames)
                .map(name -> {
                    Tag tag = getTagWithName(name);
                    uniqueTagsToTasksMap.remove(tag);
                    return tag;
                }).collect(Collectors.toSet());
    }

    /* Helper Methods */
    /**
     * Links a {@code task} to the {@code tag} in the {@link #uniqueTagsToTasksMap}.
     * @return an instance of the {@code tag}
     */
    private Tag associateTaskToTag(ImmutableTask task, Tag tag) {
        Set<ImmutableTask> setOfTasks = uniqueTagsToTasksMap.get(tag);
        if (setOfTasks == null) {
            setOfTasks = new HashSet<>();
            uniqueTagsToTasksMap.put(tag, setOfTasks);
        }
        setOfTasks.add(task);
        return tag;
    }

    /**
     * Removes the association between the {@code task} from the {@code tag} in {@link #uniqueTagsToTasksMap}.
     * If the tag do not have any associated task, the tag will be removed from the map.
     * @return an instance of the {@code tag}
     */
    private Tag dissociateTaskFromTag(ImmutableTask task, Tag tag) {
        Set<ImmutableTask> setOfTasks = uniqueTagsToTasksMap.get(tag);
        if (setOfTasks != null) {
            setOfTasks.remove(task);

            if (setOfTasks.isEmpty()) {
                uniqueTagsToTasksMap.remove(tag);
            }
        }
        return tag;
    }

    /**
     * Obtains an instance of {@link Tag} with the supplied {@code tagName} from the
     * {@link #uniqueTagsToTasksMap}.
     *
     * Note: If such an instance is not found, a new {@link Tag} instance will be added to the
     *       {@link #uniqueTagsToTasksMap}.
     *
     * @param tagName The name of the {@link Tag}.
     * @return A {@link Tag} object that has the name {@code tagName}.
     */
    private Tag getTagWithName(String tagName) {
        Optional<Tag> possibleTag = uniqueTagsToTasksMap.keySet().stream()
                .filter(tag -> tag.getTagName().equals(tagName))
                .findAny();

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
     * Simply finds a tag with the {@code tagName}.
     */
    private Optional<Tag> findTagWithName(String tagName) {
        return uniqueTagsToTasksMap.keySet().stream()
                .filter(tag -> tag.getTagName().equals(tagName))
                .findAny();
    }

    /* Interfacing Getters */
    @Override
    public List<Tag> getUniqueTagList() {
        return new ArrayList<>(uniqueTagsToTasksMap.keySet());
    }

    @Override
    public Set<ImmutableTask> getTasksLinkedToTag(String tagName) {
        Optional<Tag> possibleTag = findTagWithName(tagName);
        if (possibleTag.isPresent()) {
            //We are getting a copy of the set of tasks.
            Set<ImmutableTask> tasks = uniqueTagsToTasksMap.get(possibleTag.get());
            return new HashSet<>(tasks);
        } else {
            return new HashSet<>();
        }
    }

    //@@author A0135805H-reused
    /* Other Override Methods */
    @Override
    public Iterator<Tag> iterator() {
        return uniqueTagsToTasksMap.keySet().iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTagCollection // instanceof handles nulls
                && this.uniqueTagsToTasksMap.equals(((UniqueTagCollection) other).uniqueTagsToTasksMap));
    }

    @Override
    public int hashCode() {
        return uniqueTagsToTasksMap.hashCode();
    }
}
