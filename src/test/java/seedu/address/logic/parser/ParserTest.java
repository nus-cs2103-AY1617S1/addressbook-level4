package seedu.address.logic.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {
    private Parser parser = new Parser();

    @Before
    public void setup() {
    }

    @After
    public void teardown() {
    }

    @Test
    public void execute_extractText_noKeywords() throws Exception {
        parser.setInput("No keywords");
        assertEquals("No keywords", parser.extractText().orElse(""));
        assertEquals("", parser.getInput());
    }

    @Test
    public void execute_extractText_trims() throws Exception {
        parser.setInput(" trims   ");
        assertEquals("trims", parser.extractText().orElse(""));
        assertEquals("", parser.getInput());
    }

    @Test
    public void execute_extractText_1Keyword() throws Exception {
        parser.setInput("1 keyword only");

        assertEquals("1", parser.extractText(
            "keyword"
        ).orElse(""));
        assertEquals("keyword only", parser.getInput().trim());
    }

    @Test
    public void execute_extractText_2Keywords() throws Exception {
        parser.setInput("2 keywords, not just 1");

        assertEquals("2 keywords,", parser.extractText(
            "not", "just"
        ).orElse(""));
        assertEquals("not just 1", parser.getInput().trim());
    }

    @Test
    public void execute_extractText_caseInsensitiveKeywords() throws Exception {
        parser.setInput("Case iN Sens itive");

        assertEquals("Case", parser.extractText(
            "In", "seNs"
        ).orElse(""));
        assertEquals("iN Sens itive", parser.getInput().trim());
    }

    @Test
    public void execute_extractText_noMatchingKeywords() throws Exception {
        parser.setInput("No matching keywords");

        assertEquals("No matching keywords", parser.extractText(
            "random", "words"
        ).orElse(""));
        assertEquals("", parser.getInput());
    }

    @Test
    public void execute_extractText_notMatchSubstrings() throws Exception {
        parser.setInput("Don't match substrings");

        assertEquals("Don't match substrings", parser.extractText(
            "mat", "Don"
        ).orElse(""));
        assertEquals("", parser.getInput());
    }

    @Test
    public void execute_extractWords_noWords() throws Exception {
        parser.setInput("");
        assertTrue(parser.extractWords().isEmpty());
    }

    @Test
    public void execute_extractWords_1Word() throws Exception {
        parser.setInput("1Word");
        assertEquals(prepareList("1Word"), parser.extractWords());
        assertEquals("", parser.getInput());
    }

    @Test
    public void execute_extractWords_2Words() throws Exception {
        parser.setInput("2 words");
        assertEquals(prepareList("2", "words"), parser.extractWords());
        assertEquals("", parser.getInput());
    }

    @Test
    public void execute_extractFirstWord_noWord() throws Exception {
        parser.setInput("");
        assertTrue(!parser.extractFirstWord().isPresent());
    }

    @Test
    public void execute_extractFirstWord_firstWord() throws Exception {
        parser.setInput("command word");
        assertEquals("command", parser.extractFirstWord().orElse(""));
        assertEquals("word", parser.getInput().trim());
    }

    @Test
    public void execute_extractFirstInteger_noInteger() throws Exception {
        parser.setInput("no index");
        assertTrue(!parser.extractFirstInteger().isPresent());
    }

    @Test
    public void execute_extractFirstInteger_integer() throws Exception {
        parser.setInput("1 index");
        assertTrue(1 == parser.extractFirstInteger().orElse(-1));
    }

    @Test
    public void execute_extractPrefixedWords_noMatches() throws Exception {
        parser.setInput("no matches");
        assertTrue(parser.extractPrefixedWords("#").isEmpty());
        assertEquals("no matches", parser.getInput());
    }

    @Test
    public void execute_extractPrefixedWords_allTags() throws Exception {
        parser.setInput("#tag1 #tag2 #tag3");
        assertEquals(prepareList("tag1", "tag2", "tag3"), parser.extractPrefixedWords("#"));
        assertEquals("", parser.getInput().trim());
    }

    @Test
    public void execute_extractPrefixedWords_tagsWithOtherWords() throws Exception {
        parser.setInput("other #tag1 #tag2 words");
        assertEquals(prepareList("tag1", "tag2"), parser.extractPrefixedWords("#"));
        assertEquals(prepareList("other", "words"), parser.extractWords());
    }

    @Test
    public void execute_extractTextFromIndex_indexWithKeywords() throws Exception {
        parser.setInput("Index with with keywords");
        assertEquals("th", parser.extractTextFromIndex(8, "with", "keywords").orElse(""));
        assertEquals(prepareList("Index", "wi", "with", "keywords"), parser.extractWords());
    }

    @Test
    public void execute_extractTextFromIndex_indexWithoutKeywords() throws Exception {
        parser.setInput("index without keywords");
        assertEquals("out keywords", parser.extractTextFromIndex(10).orElse(""));
        assertEquals(prepareList("index", "with"), parser.extractWords());
    }

    @Test
    public void execute_extractTextAfterKeyword_keyword() throws Exception {
        parser.setInput("extract text after keyword");
        assertEquals("keyword", parser.extractTextAfterKeyword("after").orElse(""));
        assertEquals(prepareList("extract","text"), parser.extractWords());
    }

    @Test
    public void execute_extractTextAfterKeyword_keywordNotFound() throws Exception {
        parser.setInput("keyword not found");
        assertTrue(!parser.extractTextAfterKeyword("key").isPresent());
        assertEquals(prepareList("keyword", "not", "found"), parser.extractWords());
    }

    @Test
    public void execute_extractTextAfterKeyword_caseInsensitive() throws Exception {
        parser.setInput("case InSensitive keyword");
        assertEquals("keyword", parser.extractTextAfterKeyword("iNsensitive").orElse(""));
        assertEquals(prepareList("case"), parser.extractWords());
    }

    private List<String> prepareList(String... values) {
        List<String> list = new LinkedList<>();
        for (String value : values) {
            list.add(value);
        }
        return list;
    }
}
