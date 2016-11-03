# A0153751Hreused
###### /java/seedu/task/logic/parser/Parser.java
``` java
	private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
		// no tags
		if (tagArguments.isEmpty()) {
			return Collections.emptySet();
		}
		// replace first delimiter prefix, then split
		final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" ts/", "").split(" ts/"));
		return new HashSet<>(tagStrings);
	}
```
