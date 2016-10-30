package seedu.todo.model.tag;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;

import java.util.Collection;
import java.util.List;

//@@author A0135805H
/**
 * An interface that spells out the available methods for maintaining a unique tag list.
 */
public interface UniqueTagCollectionModel {

    /* Model Interfacing Methods*/
    /**
     * Update the {@link UniqueTagCollectionModel} with the main to-do list {@code globalTaskList}
     * stored in {@link seedu.todo.model.TodoModel}, for a new set of {@link Tag}
     *
     * @param globalTaskList To extract the unique list of {@link Tag}s from.
     */
    void update(List<ImmutableTask> globalTaskList);

    /**
     * Notifies the {@link UniqueTagCollectionModel} that the given {@code task} is deleted,
     * so that the {@link UniqueTagCollectionModel} can update the relations accordingly.
     */
    void notifyTaskDeleted(ImmutableTask task);


    /* Tag Command Interfacing Methods */
    /**
     * Registers the given {@code task}s to a {@link Tag} in the {@link UniqueTagCollectionModel}.
     * However, this method will not save the tags to the {@code task} since it is immutable.
     * Note: that data validation is done at the {@link seedu.todo.model.Model} side.
     *
     * @param task The immutable task to be attached under the {@link Tag}.
     * @param tagNames The list of tag names.
     * @return Returns the corresponding collections of {@link Tag}s object with {@code tagName}
     *         so this tag can be added to the {@code task}.\
     */
    Collection<Tag> associateTaskToTags(ImmutableTask task, String[] tagNames);


    /* Old Tag Command Interfacing Methods */
    /**
     * Unregisters the given {@code task} from the {@link Tag}s in the {@link UniqueTagCollectionModel}.
     * This method will not remove the tags to the task since it is immutable.
     * Also, data validation should be done at the {@link seedu.todo.model.Model} side.
     *
     * @param task The task to be detached from the {@link Tag}.
     * @param tagNames The names of the {@link Tag} to be deleted.
     * @return Returns the corresponding {@link Tag} object with {@code tagName}
     *         so this tag can be removed from the {@code task}.
     */
    Collection<Tag> dissociateTaskFromTags(ImmutableTask task, String[] tagNames);

    /**
     * Deletes the list of tags with {@code tagNames} from the data structure.
     * @return a collection of deleted tags.
     */
    Collection<Tag> deleteTags(String[] tagNames);

    /**
     * Renames a {@link Tag} with the given {@code originalName} with the {@code newName}
     */
    void renameTag(String originalName, String newName) throws ValidationException;

    /**
     * Gets a copy of the list of tags.
     */
    List<Tag> getUniqueTagList();

    /**
     * Gets a copy of list of task associated with the {@link Tag} with the name {@code tagName}
     */
    List<ImmutableTask> getTasksLinkedToTag(String tagName);
}
