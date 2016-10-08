package seedu.address.logic.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SequentialParserTest {
    private SequentialParser sequentialParser = new SequentialParser();

    @Before
    public void setup() {
    }

    @After
    public void teardown() {
    }

    @Test
    public void execute_extractText_noKeywords() throws Exception {
        sequentialParser.setInput("No keywords");
        assertEquals("No keywords", sequentialParser.extractText().orElse(""));
        assertEquals("", sequentialParser.getInput());
    }

    @Test
    public void execute_extractText_trims() throws Exception {
        sequentialParser.setInput(" trims   ");
        assertEquals("trims", sequentialParser.extractText().orElse(""));
        assertEquals("", sequentialParser.getInput());
    }

    @Test
    public void execute_extractText_1Keyword() throws Exception {
        sequentialParser.setInput("1 keyword only");

        assertEquals("1", sequentialParser.extractText(
            "keyword"
        ).orElse(""));
        assertEquals("keyword only", sequentialParser.getInput().trim());
    }

    @Test
    public void execute_extractText_2Keywords() throws Exception {
        sequentialParser.setInput("2 keywords, not just 1");

        assertEquals("2 keywords,", sequentialParser.extractText(
            "not", "just"
        ).orElse(""));
        assertEquals("not just 1", sequentialParser.getInput().trim());
    }

    @Test
    public void execute_extractText_caseInsensitiveKeywords() throws Exception {
        sequentialParser.setInput("Case iN Sens itive");

        assertEquals("Case", sequentialParser.extractText(
            "In", "seNs"
        ).orElse(""));
        assertEquals("iN Sens itive", sequentialParser.getInput().trim());
    }

    @Test
    public void execute_extractText_noMatchingKeywords() throws Exception {
        sequentialParser.setInput("No matching keywords");

        assertEquals("No matching keywords", sequentialParser.extractText(
            "random", "words"
        ).orElse(""));
        assertEquals("", sequentialParser.getInput());
    }

    @Test
    public void execute_extractText_notMatchSubstrings() throws Exception {
        sequentialParser.setInput("Don't match substrings");

        assertEquals("Don't match substrings", sequentialParser.extractText(
            "mat", "Don"
        ).orElse(""));
        assertEquals("", sequentialParser.getInput());
    }

    @Test
    public void execute_extractWords_noWords() throws Exception {
        sequentialParser.setInput("");
        assertTrue(sequentialParser.extractWords().isEmpty());
    }

    @Test
    public void execute_extractWords_1Word() throws Exception {
        sequentialParser.setInput("1Word");
        assertEquals(prepareList("1Word"), sequentialParser.extractWords());
        assertEquals("", sequentialParser.getInput());
    }

    @Test
    public void execute_extractWords_2Words() throws Exception {
        sequentialParser.setInput("2 words");
        assertEquals(prepareList("2", "words"), sequentialParser.extractWords());
        assertEquals("", sequentialParser.getInput());
    }

    @Test
    public void execute_extractFirstWord_noWord() throws Exception {
        sequentialParser.setInput("");
        assertTrue(!sequentialParser.extractFirstWord().isPresent());
    }

    @Test
    public void execute_extractFirstWord_firstWord() throws Exception {
        sequentialParser.setInput("command word");
        assertEquals("command", sequentialParser.extractFirstWord().orElse(""));
        assertEquals("word", sequentialParser.getInput().trim());
    }

    @Test
    public void execute_extractFirstInteger_noInteger() throws Exception {
        sequentialParser.setInput("no index");
        assertTrue(!sequentialParser.extractFirstInteger().isPresent());
    }

    @Test
    public void execute_extractFirstInteger_integer() throws Exception {
        sequentialParser.setInput("1 index");
        assertTrue(1 == sequentialParser.extractFirstInteger().orElse(-1));
    }

    @Test
    public void execute_extractPrefixedWords_noMatches() throws Exception {
        sequentialParser.setInput("no matches");
        assertTrue(sequentialParser.extractPrefixedWords("#").isEmpty());
        assertEquals("no matches", sequentialParser.getInput());
    }

    @Test
    public void execute_extractPrefixedWords_allTags() throws Exception {
        sequentialParser.setInput("#tag1 #tag2 #tag3");
        assertEquals(prepareList("tag1", "tag2", "tag3"), sequentialParser.extractPrefixedWords("#"));
        assertEquals("", sequentialParser.getInput().trim());
    }

    @Test
    public void execute_extractPrefixedWords_tagsWithOtherWords() throws Exception {
        sequentialParser.setInput("other #tag1 #tag2 words");
        assertEquals(prepareList("tag1", "tag2"), sequentialParser.extractPrefixedWords("#"));
        assertEquals(prepareList("other", "words"), sequentialParser.extractWords());
    }

    @Test
    public void execute_extractTextFromIndex_indexWithKeywords() throws Exception {
        sequentialParser.setInput("Index with with keywords");
        assertEquals("th", sequentialParser.extractTextFromIndex(8, "with", "keywords").orElse(""));
        assertEquals(prepareList("Index", "wi", "with", "keywords"), sequentialParser.extractWords());
    }

    @Test
    public void execute_extractTextFromIndex_indexWithoutKeywords() throws Exception {
        sequentialParser.setInput("index without keywords");
        assertEquals("out keywords", sequentialParser.extractTextFromIndex(10).orElse(""));
        assertEquals(prepareList("index", "with"), sequentialParser.extractWords());
    }

    @Test
    public void execute_extractTextAfterKeyword_keyword() throws Exception {
        sequentialParser.setInput("extract text after keyword");
        assertEquals("keyword", sequentialParser.extractTextAfterKeyword("after").orElse(""));
        assertEquals(prepareList("extract","text"), sequentialParser.extractWords());
    }

    @Test
    public void execute_extractTextAfterKeyword_keywordNotFound() throws Exception {
        sequentialParser.setInput("keyword not found");
        assertTrue(!sequentialParser.extractTextAfterKeyword("key").isPresent());
        assertEquals(prepareList("keyword", "not", "found"), sequentialParser.extractWords());
    }

    @Test
    public void execute_extractTextAfterKeyword_caseInsensitive() throws Exception {
        sequentialParser.setInput("case InSensitive keyword");
        assertEquals("keyword", sequentialParser.extractTextAfterKeyword("iNsensitive").orElse(""));
        assertEquals(prepareList("case"), sequentialParser.extractWords());
    }

    private List<String> prepareList(String... values) {
        List<String> list = new LinkedList<>();
        for (String value : values) {
            list.add(value);
        }
        return list;
    }
}
