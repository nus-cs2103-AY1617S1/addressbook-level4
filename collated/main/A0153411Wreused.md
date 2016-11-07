# A0153411Wreused
###### \java\seedu\task\logic\parser\ArgumentTokenizer.java
``` java
/**
 * Code taken from lecture notes.
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
		//Flag whether prefix is optional or not
		private boolean isOptional;
		final String prefix;
		final String description;

		public Prefix(String prefix, String description) {
			this.prefix = prefix;
			this.description = description;
		}

		Prefix(String prefix, String description, boolean isOptional) {
			this.prefix = prefix;
			this.description = description;
			this.isOptional = isOptional;
		}
		
		public String getDescription(){
			return this.description;
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
					String.format(MESSAGE_INVALID_COMMAND_FORMAT, String.format(AddCommand.MESSAGE_TASK_USAGE, prefix.getDescription())));
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
		String preambleValue = getValue(new Prefix("", ""));
		if (preambleValue.isEmpty())
			throw new NoSuchElementException();
		return preambleValue.trim();
	}
	
```
