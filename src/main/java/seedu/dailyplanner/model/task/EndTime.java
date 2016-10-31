package seedu.dailyplanner.model.task;

import seedu.dailyplanner.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book. Guarantees: immutable; is
 * valid as declared in {@link #isValidAddress(String)}
 */
public class EndTime implements Comparable<EndTime> {

	public static final String MESSAGE_ADDRESS_CONSTRAINTS = "Person addresses can be in any format";
	public static final String ADDRESS_VALIDATION_REGEX = ".+";

	public final String value;
	public final int intValue;

	/**
	 * Validates given address.
	 *
	 * @throws IllegalValueException
	 *             if given address string is invalid.
	 */
	// @@author A0146749N
	public EndTime(String address) throws IllegalValueException {
		assert address != null;
		// if (!isValidAddress(address)) {
		// throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
		// }
		this.value = address;
		if (address.equals(""))
			this.intValue = 2400;
		else {
			String hour = address.substring(0, 2);
			String minutes = address.substring(3, 5);
			this.intValue = Integer.parseInt(hour + minutes);
		}
	}

	/**
	 * Returns true if a given string is a valid person email.
	 */
	public static boolean isValidAddress(String test) {
		return test.matches(ADDRESS_VALIDATION_REGEX);
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object other) {
		return other == this // short circuit if same object
				|| (other instanceof EndTime // instanceof handles nulls
						&& this.value.equals(((EndTime) other).value)); // state
																		// check
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	@Override
	public int compareTo(EndTime o) {
		return this.intValue - o.intValue;
	}

}