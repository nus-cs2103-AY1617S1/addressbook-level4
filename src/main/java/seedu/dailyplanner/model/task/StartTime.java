package seedu.dailyplanner.model.task;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class StartTime implements Comparable<StartTime> {

	public static final String MESSAGE_EMAIL_CONSTRAINTS = "Person emails should be 2 alphanumeric/period strings separated by '@'";
	public static final String EMAIL_VALIDATION_REGEX = ".+";

	public final String value;
	private final int intValue;

	/**
	 * Validates given email.
	 *
	 * @throws IllegalValueException
	 *             if given email address string is invalid.
	 */
	// @@author A0139102U
	public StartTime(String email) throws IllegalValueException {
		assert email != null;
		email = email.trim();
		// if (!isValidEmail(email)) {
		// throw new IllegalValueException(MESSAGE_EMAIL_CONSTRAINTS);
		// }
		this.value = email;
		if (email.equals(""))
			this.intValue = 2400;
		else {
			String hour = email.substring(0, 2);
			String minutes = email.substring(3, 5);
			this.intValue = Integer.parseInt(hour + minutes);
		}
	}

	/**
	 * Returns if a given string is a valid person email.
	 */
	public static boolean isValidEmail(String test) {
		return test.matches(EMAIL_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof StartTime // instanceof handles nulls
						&& this.value.equals(((StartTime) other).value)); // state
																			// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(StartTime o) {
		return this.intValue - o.intValue;
	}

}
