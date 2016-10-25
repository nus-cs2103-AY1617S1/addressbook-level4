package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.*;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.AddCommand;

/**
 * Tokenizes arguments string of the form:
 * {@code preamble <prefix>value <prefix>value ...}<br>
 * e.g. {@code some preamble text /t 11.00/dToday /t 12.00 /k /m July} where
 * prefixes are {@code /t /d /k /m}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code /k} in
 * the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be
 * discarded.<br>
 * 3. A prefix need not have leading and trailing spaces e.g. the
 * {@code /d in 11.00/dToday} in the above example<br>
 * 4. An argument may be repeated and all its values will be accumulated e.g.
 * the value of {@code /t} in the above example.<br>
 */
public class ArgumentTokenizer {
	/**
	 * A prefix that marks the beginning of an argument. e.g. '/t' in 'add James
	 * /t friend'
	 */
	public static class Prefix {
		private boolean isOptional;
		final String prefix;

		public Prefix(String prefix) {
			this.prefix = prefix;
		}

		Prefix(String prefix, boolean isOptional) {
			this.prefix = prefix;
			this.isOptional = isOptional;
		}

		public void SetIsOptional(boolean isOptional) {
			this.isOptional = isOptional;
		}

		String getPrefix() {
			return this.prefix;
		}

		boolean isOptional() {
			return this.isOptional;
		}

		@Override
		public int hashCode() {
			return this.prefix == null ? 0 : this.prefix.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Prefix)) {
				return false;
			}
			if (obj == this) {
				return true;
			}

			Prefix otherPrefix = (Prefix) obj;
			return otherPrefix.getPrefix().equals(getPrefix());
		}
	}

	/**
	 * Represents a prefix's position in an arguments string
	 */
	private class PrefixPosition {
		private int startPosition;
		private final Prefix prefix;

		PrefixPosition(Prefix prefix, int startPosition) {
			this.prefix = prefix;
			this.startPosition = startPosition;
		}

		int getStartPosition() {
			return this.startPosition;
		}

		Prefix getPrefix() {
			return this.prefix;
		}
	}

	/** Given prefixes **/
	private final List<Prefix> prefixes;

	/** Arguments found after tokenizing **/
	private final Map<Prefix, List<String>> tokenizedArguments = new HashMap<>();

	/**
	 * Creates an ArgumentTokenizer that can tokenize arguments string as
	 * described by prefixes
	 */
	public ArgumentTokenizer(Prefix... prefixes) {
		this.prefixes = Arrays.asList(prefixes);
	}

	/**
	 * @param argsString
	 *            arguments string of the form: preamble <prefix>value
	 *            <prefix>value ...
	 */
	public void tokenize(String argsString) {
		resetTokenizerState();
		List<PrefixPosition> positions = findAllPrefixPositions(argsString);
		extractArguments(argsString, positions);
	}

	/**
	 * Returns last value of given prefix.
	 * 
	 * @throws NoValueForRequiredTagException
	 *             is thrown when there is no value for required task
	 */
	public String getValue(Prefix prefix) throws NoValueForRequiredTagException {
		Optional<String> valuesForPrexix = getAllValues(prefix)
				.flatMap((values) -> Optional.of(values.get(values.size() - 1)));
		if (prefix.isOptional && !valuesForPrexix.isPresent()) {
			return null;
		} else if (!prefix.isOptional && !valuesForPrexix.isPresent()) {
			throw new NoValueForRequiredTagException(
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_EVENT_USAGE));
		} else {
			return valuesForPrexix.get().trim();
		}
	}

	/**
	 * Returns all values of given prefix.
	 */
	public Optional<List<String>> getAllValues(Prefix prefix) {
		if (!this.tokenizedArguments.containsKey(prefix))
			return Optional.empty();

		List<String> values = new ArrayList<>(this.tokenizedArguments.get(prefix));
		return Optional.of(values);
	}

	/**
	 * Returns the preamble (text before the first valid prefix), if any.
	 * Leading/trailing spaces will be trimmed. If the string before the first
	 * prefix is empty, NoSuchElementException will be returned.
	 * 
	 * @throws NoValueForRequiredTagException
	 */
	public String getPreamble() throws NoValueForRequiredTagException {
		String preambleValue = getValue(new Prefix(""));
		if (preambleValue.isEmpty())
			throw new NoSuchElementException();
		return preambleValue.trim();
	}

	private void resetTokenizerState() {
		this.tokenizedArguments.clear();
	}

	/**
	 * Finds all positions in an arguments string at which any prefix appears
	 */
	private List<PrefixPosition> findAllPrefixPositions(String argsString) {
		List<PrefixPosition> positions = new ArrayList<>();

		for (Prefix prefix : this.prefixes) {
			positions.addAll(findPrefixPositions(argsString, prefix));
		}

		return positions;
	}

	/**
	 * Finds all positions in an arguments string at which a given
	 * {@code prefix} appears
	 */
	private List<PrefixPosition> findPrefixPositions(String argsString, Prefix prefix) {
		List<PrefixPosition> positions = new ArrayList<>();

		int argumentStart = argsString.indexOf(prefix.getPrefix());
		while (argumentStart != -1) {
			PrefixPosition extendedPrefix = new PrefixPosition(prefix, argumentStart);
			positions.add(extendedPrefix);
			argumentStart = argsString.indexOf(prefix.getPrefix(), argumentStart + 1);
		}

		return positions;
	}

	/**
	 * Extracts the preamble/arguments and stores them in local variables.
	 * 
	 * @param prefixPositions
	 *            must contain all prefixes in the {@code argsString}
	 */
	private void extractArguments(String argsString, List<PrefixPosition> prefixPositions) {

		// Sort by start position
		prefixPositions.sort((prefix1, prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());

		// Insert a PrefixPosition to represent the preamble
		PrefixPosition preambleMarker = new PrefixPosition(new Prefix(""), 0);
		prefixPositions.add(0, preambleMarker);

		// Add a dummy PrefixPosition to represent the end of the string
		PrefixPosition endPositionMarker = new PrefixPosition(new Prefix(""), argsString.length());
		prefixPositions.add(endPositionMarker);

		// Extract the prefixed arguments and preamble (if any)
		for (int i = 0; i < prefixPositions.size() - 1; i++) {
			String argValue = extractArgumentValue(argsString, prefixPositions.get(i), prefixPositions.get(i + 1));
			saveArgument(prefixPositions.get(i).getPrefix(), argValue);
		}

	}

	/**
	 * Returns the trimmed value of the argument specified by
	 * {@code currentPrefixPosition}. The end position of the value is
	 * determined by {@code nextPrefixPosition}
	 */
	private String extractArgumentValue(String argsString, PrefixPosition currentPrefixPosition,
			PrefixPosition nextPrefixPosition) {
		Prefix prefix = currentPrefixPosition.getPrefix();

		int valueStartPos = currentPrefixPosition.getStartPosition() + prefix.getPrefix().length();
		String value = argsString.substring(valueStartPos, nextPrefixPosition.getStartPosition());

		return value.trim();
	}

	/**
	 * Stores the value of the given prefix in the state of this tokenizer
	 */
	private void saveArgument(Prefix prefix, String value) {
		if (this.tokenizedArguments.containsKey(prefix)) {
			this.tokenizedArguments.get(prefix).add(value);
			return;
		}

		List<String> values = new ArrayList<>();
		values.add(value);
		this.tokenizedArguments.put(prefix, values);
	}

	/**
	 * Throw when there is no value for required tag.
	 */
	public class NoValueForRequiredTagException extends Exception {
		public NoValueForRequiredTagException(String message) {
			super(message);
		}
	}

}