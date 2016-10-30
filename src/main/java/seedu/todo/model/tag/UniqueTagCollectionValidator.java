package seedu.todo.model.tag;

import com.google.common.collect.Sets;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.CollectionUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.task.ImmutableTask;

import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

//@@author A0135805H
/**
 * Handles the data validation for {@link UniqueTagCollection} in both
 * {@link seedu.todo.logic.commands.AddCommand} and {@link seedu.todo.logic.commands.TagCommand}
 */
public class UniqueTagCollectionValidator {

    /* Constants */
    private static final int MAX_ALLOWED_TAGS = 5;

    private static final String YOU_SUPPLIED = " They are: ";

    private static final String ERROR_MAX_TAGS_ALLOWED = "You have added too many tags. " +
            "Each task may have up to " + MAX_ALLOWED_TAGS + " tags.";
    private static final String ERROR_TAGS_ILLEGAL_CHAR
            = "Tags may only include alphanumeric characters, including dashes and underscores.";
    private static final String ERROR_TAGS_TOO_LONG
            = "Tags may only be at most 20 characters long.";
    private static final String ERROR_TAGS_DUPLICATED
            = "You might have keyed in duplicated tag names.";
    private static final String ERROR_TAGS_DO_NOT_EXIST
            = "The tag names you have entered do not exist.";
    private static final String ERROR_TAGS_EXIST
            = "The tag names you have entered are already in the task.";

    private static final Pattern TAG_VALIDATION_REGEX = Pattern.compile("^[\\w\\d_-]+$");

    /* Variables */
    private String parameterName;
    private ErrorBag errorBag;

    /* Constructor */
    /**
     * Constructs a validator with the name of the parameter {@code parameterName} and a reference to
     * {@link ErrorBag}
     */
    public UniqueTagCollectionValidator(String parameterName, ErrorBag errorBag) {
        this.parameterName = parameterName;
        this.errorBag = errorBag;
    }

    /**
     * Constructs a validator with the name of the parameter but without an external error bag.
     */
    public UniqueTagCollectionValidator(String parameterName) {
        this(parameterName, new ErrorBag());
    }

    /* Public Methods */
    /**
     * Throws an exception if there is something in the error bag.
     */
    public void throwsExceptionIfNeeded() throws ValidationException {
        //Message not required.
        errorBag.validate("");
    }

    /**
     * Validates the add tags command for {@link seedu.todo.logic.commands.AddCommand}.
     */
    public void validateAddTags(String[] tagNames) {
        validateAddTags(null, tagNames);
    }

    /**
     * Validates the add command.
     */
    public void validateAddTags(ImmutableTask task, String[] tagNames) {
        validateIllegalNameChar(tagNames);
        validateNameCharLimit(tagNames);
        validateDuplicatedNameTag(tagNames);
        validateNumberOfTags(task, tagNames);
        if (task != null) {
            validateTagNamesDoNotExist(task.getTags(), tagNames);
        }
    }

    /**
     * Validates the delete tag from task command.
     */
    public void validateDeleteTags(ImmutableTask task, String[] tagNames) {
        validateTagNamesExist(task.getTags(), tagNames);
    }

    /**
     * Validates the delete tag globally command.
     */
    public void validateDeleteTags(UniqueTagCollectionModel tagCollection, String[] tagNames) {
        validateTagNamesExist(tagCollection.getUniqueTagList(), tagNames);
    }

    /**
     * Validates the rename command.
     */
    public void validateRenameCommand(UniqueTagCollectionModel tagCollection, String oldName, String newName) {
        validateTagNamesExist(tagCollection.getUniqueTagList(), oldName);
        validateTagNamesDoNotExist(tagCollection.getUniqueTagList(), newName);
        validateIllegalNameChar(newName);
        validateNameCharLimit(newName);
    }

    /* Parameter Validation Helper */
    /**
     * Checks if the total number of tags after adding is within {@link #MAX_ALLOWED_TAGS}.
     */
    private void validateNumberOfTags(ImmutableTask task, String[] tagNames) {
        Set<String> totalTagNames = new HashSet<>();
        Collections.addAll(totalTagNames, tagNames);
        if (task != null) {
            task.getTags().forEach(tag -> totalTagNames.add(tag.getTagName()));
        }
        if (totalTagNames.size() > MAX_ALLOWED_TAGS) {
            errorBag.put(parameterName, ERROR_MAX_TAGS_ALLOWED);
        }
    }

    /**
     * Checks to ensure that given tag names are alphanumeric, which also can contain dashes and underscores.
     */
    private void validateIllegalNameChar(String... tagNames) {
        for (String tagName : tagNames) {
            if (!isValidTagName(tagName)) {
                errorBag.put(parameterName, ERROR_TAGS_ILLEGAL_CHAR + YOU_SUPPLIED + tagName);
            }
        }
    }

    /**
     * Checks to ensure that the provided character limit is within 20 characters.
     */
    private void validateNameCharLimit(String... tagNames) {
        for (String tag : tagNames) {
            if (tag.length() > 20) {
                errorBag.put(parameterName, ERROR_TAGS_TOO_LONG + YOU_SUPPLIED + tag);
                return;
            }
        }
    }

    /**
     * Checks to ensure that given tag names have no duplicated entries.
     */
    private void validateDuplicatedNameTag(String... tagNames) {
        if (!CollectionUtil.elementsAreUnique(Arrays.asList(tagNames))) {
            errorBag.put(parameterName, ERROR_TAGS_DUPLICATED);
        }
    }

    /**
     * Checks to ensure that tag names exist in the {@code tagPool}.
     */
    private void validateTagNamesExist(Collection<Tag> tagPool, String... tagNames) {
        Set<String> tagNamesSet = Sets.newHashSet(tagNames);
        Set<String> tagPoolNameSet = tagPool.stream().map(Tag::getTagName).collect(Collectors.toSet());
        List<String> tagsDoNotExist = tagNamesSet.stream().filter(tagName -> !tagPoolNameSet.contains(tagName))
                .collect(Collectors.toList());

        if (!tagsDoNotExist.isEmpty()) {
            errorBag.put(parameterName, ERROR_TAGS_DO_NOT_EXIST + YOU_SUPPLIED
                    + StringUtil.convertListToString(tagsDoNotExist.toArray(new String[0])));
        }
    }

    /**
     * Checks to ensure that tag names do not exist in the {@code tagPool}.
     */
    private void validateTagNamesDoNotExist(Collection<Tag> tagPool, String... tagNames) {
        Set<String> tagNamesSet = Sets.newHashSet(tagNames);
        List<String> tagsExist = tagPool.stream().filter(tag -> tagNamesSet.contains(tag.getTagName()))
                .map(Tag::getTagName).collect(Collectors.toList());

        if (!tagsExist.isEmpty()) {
            errorBag.put(parameterName, ERROR_TAGS_EXIST + YOU_SUPPLIED
                    + StringUtil.convertListToString(tagsExist.toArray(new String[0])));
        }
    }


    /* Private Helper Methods */
    /**
     * Returns true if a given string is a valid tag name (alphanumeric, can contain dashes and underscores)
     * Originated from {@link Tag}
     */
    private static boolean isValidTagName(String test) {
        return TAG_VALIDATION_REGEX.matcher(test).matches();
    }
}
