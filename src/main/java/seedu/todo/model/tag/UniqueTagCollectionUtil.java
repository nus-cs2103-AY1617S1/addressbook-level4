package seedu.todo.model.tag;

import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.CollectionUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.task.ImmutableTask;

import java.util.*;
import java.util.stream.Collectors;

//@@author A0135805H
/**
 * Stores the utilities method to help with tag manipulation.
 * Contains:
 *      Data validation errors
 */
public class UniqueTagCollectionUtil {
    /* Error Handling */

    /* Constants */
    public static final String TAG_VALIDATION_REGEX = "([A-Za-z0-9_-])+";

    /* Default Error Messages */
    public static final String ERROR_DATA_INTEGRITY = "Data Integrity Issue: A tag is missing from the collection.";
    private static final String ERROR_TAGS_DUPLICATED = "You might have keyed in duplicated tag names.";
    private static final String ERROR_TAGS_ILLEGAL_CHAR = "Tags may only include alphanumeric characters, including dashes and underscores.";
    private static final String ERROR_TAGS_NOT_FOUND_IN_TASK = " are not found from the task.";

    /* Data validation methods */
    /**
     * Checks if the given tag names have duplicated entries.
     * @throws ValidationException when there are duplicated entries.
     */
    public static void checkForDuplicatedTagNames(String[] tagNames) throws ValidationException {
        if (!CollectionUtil.elementsAreUnique(Arrays.asList(tagNames))) {
            ErrorBag bag = new ErrorBag();
            bag.put(ERROR_TAGS_DUPLICATED);
            throw new ValidationException("", bag);
        }
    }

    /**
     * Check if the given tag names are alphanumeric, which also can contain dashes and underscores.
     * @throws ValidationException Throw an error otherwise.
     */
    public static void checkForIllegalCharInTagNames(String[] tagNames) throws ValidationException {
        for (String tagName : tagNames) {
            if (!isValidTagName(tagName)) {
                ErrorBag bag = new ErrorBag();
                bag.put(ERROR_TAGS_ILLEGAL_CHAR);
                throw new ValidationException("", bag);
            }
        }
    }

    /**
     * Check if the given tag names are actually not found in the {@code task}.
     * @throws ValidationException Throw an error if such a supplied tag name is not found.
     */
    public static void checkForTagNamesNotExisted(ImmutableTask task,
                                                  String[] tagNames) throws ValidationException {
        List<String> tagNamesNotFound = new ArrayList<>();
        Set<String> tagNamesFromTask = getTagNames(task.getTags());
        for (String tagName : tagNames) {
            if (!tagNamesFromTask.contains(tagName)) {
                tagNamesNotFound.add(tagName);
            }
        }
        if (!tagNamesNotFound.isEmpty()) {
            String tagsNotFoundText = StringUtil.convertListToString(tagNamesNotFound.toArray(new String[0]));
            ErrorBag bag = new ErrorBag();
            bag.put(tagsNotFoundText + ERROR_TAGS_DUPLICATED);
            throw new ValidationException("", bag);
        }
    }

    /* Helper Methods */
    /**
     * Returns true if a given string is a valid tag name (alphanumeric, can contain dashes and underscores)
     * Originated from {@link Tag}
     */
    private static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    /**
     * Returns a list of tag names fom the supplied {@code tags}
     */
    private static Set<String> getTagNames(Collection<Tag> tags) {
        return tags.stream().map(Tag::getTagName).collect(Collectors.toSet());
    }

}
