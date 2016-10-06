package seedu.address.model.item;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Item's type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidItemType(String)}
 */
public class ItemType {

    public static final String MESSAGE_NAME_CONSTRAINTS = "Item types should only be 'task', 'deadline' or 'event'.";
    public static final String TASK_NAME = "task";
    public static final String DEADLINE_NAME = "deadline";
    public static final String EVENT_NAME = "event";

    public final String value;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given itemType string is invalid.
     */
    public ItemType(String itemType) throws IllegalValueException {
        assert itemType != null;
        itemType = itemType.trim();
        if (!isValidItemType(itemType)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.value = itemType;
    }

    /**
     * Returns true if a given string is a valid item type.
     */
    public static boolean isValidItemType(String test) {
    	if (test.equals(TASK_NAME) || test.equals(DEADLINE_NAME) || test.equals(EVENT_NAME)) {
    		return true;
    	}
        return false;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemType // instanceof handles nulls
                && this.value.equals(((ItemType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
