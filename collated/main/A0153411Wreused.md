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
```
