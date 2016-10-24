package seedu.address.logic.autocomplete;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Contains a set of words to match autocomplete 
 * When a new query is received, returns an AutocompleteResult object that the consumer
 * can iterate through.
 *
 */
public class AutocompleteEngine {

	private final Set<String> wordsToMatch;
	
	public AutocompleteEngine() {
		this(new HashSet<String>());
	}

	public AutocompleteEngine(Set<String> wordsToMatch) {
		this.wordsToMatch = wordsToMatch;
	}

	/*
	 * Filters the list of autocompletable words and dispatches it to 
	 * an AutocompleteResult object, which is responsible for continually passing a
	 * string to autocomplete
	 */
	public AutocompleteResult getQueryResult(String toMatch) {
		assert wordsToMatch != null;
		
		// Checks if any words in the set start with the word we are matching with
		List<String> matchedWords = wordsToMatch.stream().filter(word -> word.startsWith(toMatch)).collect(Collectors.toList());
		
		// If we receive no matches - we need to return the word itself as a match.
		if (matchedWords.isEmpty()) {
			matchedWords.add(toMatch);
		}
		
		return new AutocompleteResult(matchedWords);
	}
}
