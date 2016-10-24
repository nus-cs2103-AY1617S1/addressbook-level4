package seedu.address.logic.autocomplete;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AutocompleteResultTest {
	AutocompleteResult autocompleteResult;

	@Test(expected = AssertionError.class)
	public void getNextMatch_nullWords() {
		autocompleteResult = new AutocompleteResult(null);
	}

	@Test(expected = AssertionError.class)
	public void getNextMatch_emptyWords() {
		autocompleteResult = new AutocompleteResult(new ArrayList<String>());
	}

	@Test
	public void getNextMatch_oneWord() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("add");

		autocompleteResult = new AutocompleteResult(wordsToMatch);

		for (int i = 0; i < 10; i++) {
			assertEquals(autocompleteResult.getNextMatch(), "add");
		}
	}

	@Test
	public void getNextMatch_twoWords() {
		List<String> wordsToMatch = new ArrayList<String>();
		wordsToMatch.add("add");
		wordsToMatch.add("delete");

		autocompleteResult = new AutocompleteResult(wordsToMatch);

		for (int i = 0; i < 10; i++) {
			assertEquals(autocompleteResult.getNextMatch(), "add");
			assertEquals(autocompleteResult.getNextMatch(), "delete");
		}
	}

}
