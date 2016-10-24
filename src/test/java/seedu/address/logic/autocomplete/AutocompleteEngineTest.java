package seedu.address.logic.autocomplete;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class AutocompleteEngineTest {
	private AutocompleteEngine autocompleteEngine;
	
	@Test
	public void autocompleteEngine_noWordsToMatch() {
		autocompleteEngine = new AutocompleteEngine();
		AutocompleteResult result = autocompleteEngine.getQueryResult("ad");
		assertEquals(result.getNextMatch(), "ad");
		assertEquals(result.getNextMatch(), "ad");
	}
	
	@Test
	public void autocompleteEngine_noWordsCorrectlyMatch() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		
		autocompleteEngine = new AutocompleteEngine();
		AutocompleteResult result = autocompleteEngine.getQueryResult("ad");
		assertEquals(result.getNextMatch(), "ad");
		assertEquals(result.getNextMatch(), "ad");
	}
	
	@Test
	public void autocompleteEngine_oneWordMatches() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("add");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("ad");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "add");
	}
	
	@Test
	public void autocompleteEngine_twoWordsMatch() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("a");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "alias");
		assertEquals(result.getNextMatch(), "add");
	}
	
	@Test
	public void autocompleteEngine_twoWordsMatchOneWordDoesNot() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("a");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "alias");
		assertEquals(result.getNextMatch(), "add");
	}
	
	@Test
	public void autocompleteEngine_oneWordMatchesTwoWordsDoNot() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("d");
		assertEquals(result.getNextMatch(), "delete");
		assertEquals(result.getNextMatch(), "delete");
	}
	
	@Test
	public void autocompleteEngine_twoDifferentQueries() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("delete");
		wordsToMatch.add("add");
		wordsToMatch.add("alias");
		
		autocompleteEngine = new AutocompleteEngine(new HashSet<String>(wordsToMatch));
		AutocompleteResult result = autocompleteEngine.getQueryResult("d");
		assertEquals(result.getNextMatch(), "delete");
		assertEquals(result.getNextMatch(), "delete");
		
		result = autocompleteEngine.getQueryResult("a");
		assertEquals(result.getNextMatch(), "add");
		assertEquals(result.getNextMatch(), "alias");
		assertEquals(result.getNextMatch(), "add");
		
	}

	
}
