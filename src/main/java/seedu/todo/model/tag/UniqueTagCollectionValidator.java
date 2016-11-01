package seedu.todo.model.tag;

import com.google.common.collect.Sets;
import seedu.todo.commons.exceptions.ValidationException;
import seedu.todo.commons.util.CollectionUtil;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.model.ErrorBag;
import seedu.todo.model.task.ImmutableTask;

import java.util.Set;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
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
    private static final String ERROR_TAGS_EMPTY
            = "You need to supply tag names.";

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
        validateTagNameMissing(tagNames);
        validateTagNamesDoNotExist(task, tagNames);
    }

    /**
     * Validates the delete tag from task command.
     */
    public void validateDeleteTags(ImmutableTask task, String[] tagNames) {
        validateTagNamesExist(task, tagNames);
        validateTagNameMissing(tagNames);
    }

    /**
     * Validates the delete tag globally command.
     */
    public void validateDeleteTags(UniqueTagCollectionModel tagCollection, String[] tagNames) {
        validateTagNamesExist(tagCollection.getUniqueTagList(), tagNames);
        validateTagNameMissing(tagNames);
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
        List<String> possibleViolations = Arrays.stream(tagNames).filter(tagName -> !isValidTagName(tagName))
                .collect(Collectors.toList());

        if (!possibleViolations.isEmpty()) {
            errorBag.put(parameterName, ERROR_TAGS_ILLEGAL_CHAR + YOU_SUPPLIED
                    + StringUtil.convertIterableToString(possibleViolations));
        }
    }

    /**
     * Checks to ensure that the provided character limit is within 20 characters.
     */
    private void validateNameCharLimit(String... tagNames) {
        List<String> possibleViolations = Arrays.stream(tagNames).filter(tagName -> tagName.length() > 20)
                .collect(Collectors.toList());

        if (!possibleViolations.isEmpty()) {
            errorBag.put(parameterName, ERROR_TAGS_TOO_LONG + YOU_SUPPLIED
                    + StringUtil.convertIterableToString(possibleViolations));
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
        List<String> tagsDoNotExist = tagNamesSet.stream()
                .filter(tagName -> !tagPoolNameSet.contains(tagName))
                .collect(Collectors.toList());

        if (!tagsDoNotExist.isEmpty()) {
            errorBag.put(parameterName, ERROR_TAGS_DO_NOT_EXIST + YOU_SUPPLIED
                    + StringUtil.convertIterableToString(tagsDoNotExist));
        }
    }

    /**
     * Checks to ensure that tag names found in {@code task} exist in the {@code tagPool}.
     */
    private void validateTagNamesExist(ImmutableTask task, String... tagNames) {
        if (task != null) {
            validateTagNamesExist(task.getTags(), tagNames);
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
                    + StringUtil.convertIterableToString(tagsExist));
        }
    }

    /**
     * Checks to ensure that tag names found in {@code task} do not exist in the {@code tagPool}.
     */
    private void validateTagNamesDoNotExist(ImmutableTask task, String... tagNames) {
        if (task != null) {
            validateTagNamesDoNotExist(task.getTags(), tagNames);
        }
    }

    /**
     * Checks to prevent the case where the tag names provided are actually empty.
     */
    private void validateTagNameMissing(String... tagNames) {
        if (tagNames == null || tagNames.length == 0) {
            errorBag.put(parameterName, ERROR_TAGS_EMPTY);
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

    /* Public Helper Methods */
    /**
     * Abstracts the series of steps for performing data validation.
     *
     * @param actionName Name of the Command action
     * @param consumer The method call that performs the actual validation.
     */
    public static UniqueTagCollectionValidator validate(String actionName,
            Consumer<UniqueTagCollectionValidator> consumer) throws ValidationException {

        UniqueTagCollectionValidator validator = new UniqueTagCollectionValidator(actionName);
        consumer.accept(validator);
        validator.throwsExceptionIfNeeded();
        return validator;
    }
}
