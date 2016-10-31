package seedu.dailyplanner.model.task;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's phone number in the address book. Guarantees:
 * immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Date implements Comparable<Date> {

	public static final String MESSAGE_PHONE_CONSTRAINTS = "Person phone numbers should only contain numbers";
	public static final String PHONE_VALIDATION_REGEX = ".+";

	public final String value;
	private final int day;
	private final int month;
	private final int year;

	/**
	 * Validates given phone number.
	 *
	 * @throws IllegalValueException
	 *             if given phone string is invalid.
	 */
	// @@author A0140124B
	public Date(String phone) throws IllegalValueException {
		assert phone != null;
		phone = phone.trim();
		// if (!isValidPhone(phone)) {
		// throw new IllegalValueException(MESSAGE_PHONE_CONSTRAINTS);
		// }
		this.value = phone;
		if (phone.equals("")) {
			this.day = 0;
			this.month = 0;
			this.year = 9999;
		} else {
			this.day = Integer.parseInt(phone.substring(0, 2));
			this.month = Integer.parseInt(phone.substring(3, 5));
			this.year = Integer.parseInt(phone.substring(6, 8));
		}
	}

	// @@author
	/**
	 * Returns true if a given string is a valid person phone number.
	 */
	public static boolean isValidPhone(String test) {
		return test.matches(PHONE_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof Date // instanceof handles nulls
						&& this.value.equals(((Date) other).value)); // state
																		// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(Date o) {
		if (this.year != o.year)
			return this.year - o.year;
		if (this.month != o.month)
			return this.month - o.month;
		return this.day - o.day;
	}
}
