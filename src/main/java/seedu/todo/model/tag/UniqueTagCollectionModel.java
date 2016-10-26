package seedu.todo.model.tag;

import javafx.collections.ObservableList;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.model.task.ImmutableTask;

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
    void update(ObservableList<ImmutableTask> globalTaskList);

    /**
     * Notifies the {@link UniqueTagCollectionModel} that the given {@code task} is deleted,
     * so that the {@link UniqueTagCollectionModel} can update the relations accordingly.
     */
    void notifyTaskDeleted(ImmutableTask task);

    /* Tag Command Interfacing Methods */
    /**
     * Registers the given {@code task} to a {@link Tag} in the {@link UniqueTagCollectionModel}.
     *
     * @param task The task to be attached under the {@link Tag}.
     * @param tagName The name of the {@link Tag}.
     * @return Returns the corresponding {@link Tag} object with {@code tagName}
     *         so this tag can be added to the {@code task}.
     */
    Tag registerTagWithTask(ImmutableTask task, String tagName);

    /**
     * Unregistere the given {@code task} from the {@link Tag} in the {@link UniqueTagCollectionModel}.
     *
     * @param task The task to be detached from the {@link Tag}.
     * @param tagName The name of the {@link Tag}.
     * @return Returns the corresponding {@link Tag} object with {@code tagName}
     *         so this tag can be removed from the {@code task}.
     */
    Tag unregisterTagWithTask(ImmutableTask task, String tagName);

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
