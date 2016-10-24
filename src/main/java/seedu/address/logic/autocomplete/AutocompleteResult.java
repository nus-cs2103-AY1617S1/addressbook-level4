package seedu.address.logic.autocomplete;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;

/*
 * Cycles through all matches infinitely
 */
public class AutocompleteResult {
	
	private Iterator<String> matchIterator;
	
	public AutocompleteResult(List<String> matchedWords) {
		assert matchedWords != null;
		assert matchedWords.size() > 0;
		
		this.matchIterator = Iterables.cycle(matchedWords).iterator();
	}
	
	public String getNextMatch() {
		assert matchIterator != null;
		return matchIterator.next();		
	}
	
	
}
