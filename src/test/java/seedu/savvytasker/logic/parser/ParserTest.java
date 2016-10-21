package seedu.savvytasker.logic.parser;

import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ParserTest {
    private AddCommandParser addParser;
    private DeleteCommandParser deleteParser;
    private ModifyCommandParser modifyParser;
    private ClearCommandParser clearParser;
    private ListCommandParser listParser;
    private FindCommandParser findParser;
    private HelpCommandParser helpParser;
    private ExitCommandParser exitParser;
    private MarkCommandParser markParser;
    private UnmarkCommandParser unmarkParser;
    private UndoCommandParser undoParser;
    private RedoCommandParser redoParser;
    private AliasCommandParser aliasParser;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void setup() {
        addParser = new AddCommandParser();
        deleteParser = new DeleteCommandParser();
        modifyParser = new ModifyCommandParser();
        clearParser = new ClearCommandParser();
        listParser = new ListCommandParser();
        findParser = new FindCommandParser();
        helpParser = new HelpCommandParser();
        exitParser = new ExitCommandParser();
        markParser = new MarkCommandParser();
        unmarkParser = new UnmarkCommandParser();
        undoParser = new UndoCommandParser();
        redoParser = new RedoCommandParser();
        aliasParser = new AliasCommandParser();
    }
    
    @Test
    public void parse_add_reorder() throws ParseException {
        assertNotEquals(addParser.parse("add task l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"), null);
    }
    
    @Test
    public void parse_add_multipleSpaces() throws ParseException {
        assertNotEquals(addParser.parse("add    Multiple   Spaces    s/  2pm"), null);
    }
    
    @Test
    public void parse_add_sameOptionMultipleTimes() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task s/ tomorrow 3pm s/ tomorrow 10pm");
    }
    
    @Test
    public void parse_add_missingTaskName() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add s/ tomorrow 3pm");
    }
    
    @Test
    public void parse_add_arbitrarySlash() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task s/ tomorrow 2pm/3pm e/ sunday");
    }
    
    @Test
    public void parse_add_fullValid() throws ParseException {
        assertNotEquals(addParser.parse("add task s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"), null);
    }
    
    @Test
    public void parse_add_invalidRecurrenceType() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task r/ Error ");
    }

    @Test
    public void parse_add_invalidPriorityLevel() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task p/ Error ");
    }

    //==================================================================================
    
    @Test
    public void parse_delete_noIndexSpecified() throws ParseException {
        thrown.expect(ParseException.class);
        deleteParser.parse("delete");
    }
    
    @Test
    public void parse_delete_oneIndex() throws ParseException {
        assertNotEquals(deleteParser.parse("delete 1"), null);
    }
    
    @Test
    public void parse_delete_multipleIndices() throws ParseException {
        assertNotEquals(deleteParser.parse("delete 1 2 3"), null);
    }
    
    @Test
    public void parse_delete_multipleSpacesIndices() throws ParseException {
        assertNotEquals(deleteParser.parse("delete    1   2     3"), null);
    }

    @Test
    public void parse_delete_negativeIndex() throws ParseException {
        thrown.expect(ParseException.class);
        deleteParser.parse("delete -1");
    }

    @Test
    public void parse_delete_zeroIndex() throws ParseException {
        thrown.expect(ParseException.class);
        deleteParser.parse("delete 0");
    }
    
    //==================================================================================
    
    @Test
    public void parse_modify_noIndex() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify t/ newtask");
    }

    @Test
    public void parse_modify_multipleIndex() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify 1 2 3 t/ newtask");
    }

    @Test
    public void parse_modify_negativeIndex() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify -1 t/ newtask");
    }
    
    @Test
    public void parse_modify_zeroIndex() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify 0 t/ newtask");
    }

    @Test
    public void parse_modify_onlySpecifyIndex() throws ParseException {
        assertNotEquals(modifyParser.parse("modify 1"), null);
    }
    
    @Test
    public void parse_modify_fullValid() throws ParseException {
        assertNotEquals(modifyParser.parse("modify 3 t/ newtask s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"), null);
    }

    @Test
    public void parse_modify_reorder() throws ParseException {
        assertNotEquals(modifyParser.parse("modify 1 l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"), null);
    }

    @Test
    public void parse_modify_multipleSpaces() throws ParseException {
        assertNotEquals(modifyParser.parse("modify   1  t/   Multiple   Spaces    s/  2pm"), null);
    }
    
    //==================================================================================
    
    @Test
    public void parse_clear_spaces() throws ParseException {
        assertNotEquals(clearParser.parse("clear     "), null);
    }
    
    @Test
    public void parse_clear_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        clearParser.parse("clear 1");
    }
    
    @Test
    public void parse_clear_valid() throws ParseException {
        assertNotEquals(clearParser.parse("clear"), null);
    }

    //==================================================================================
    
    @Test
    public void parse_list_noParameters() throws ParseException {
        assertNotEquals(listParser.parse("list"), null);
    }

    @Test
    public void parse_list_noParametersSpaces() throws ParseException {
        assertNotEquals(listParser.parse("list   "), null);
    }
    
    @Test
    public void parse_list_valid() throws ParseException {
        assertNotEquals(listParser.parse("list   t/ Priority Level "), null);
    }
    
    @Test
    public void parse_list_invalidType() throws ParseException {
        thrown.expect(ParseException.class);
        listParser.parse("list t/ Error ");
    }

    //==================================================================================
    
    @Test
    public void parse_find_noKeywords() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find");
    }
    
    @Test
    public void parse_find_noKeywordsSpaces() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find    ");
    }

    @Test
    public void parse_find_noKeywordsButWithType() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find t/ Exact ");
    }
    
    @Test
    public void parse_find_invalidType() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find t/ Error some words");
    }
    
    @Test
    public void parse_find_validAfter() throws ParseException {
        assertNotEquals(findParser.parse("find t/ Exact this word "), null);
    }
    
    @Test
    public void parse_find_validBefore() throws ParseException {
        assertNotEquals(findParser.parse("find some words t/ Partial  "), null);
    }

    @Test
    public void parse_find_validBeforeAndAfter() throws ParseException {
        assertNotEquals(findParser.parse("find some words t/ Full some words after "), null);
    }

    //==================================================================================
    
    @Test
    public void parse_help_spaces() throws ParseException {
        assertNotEquals(helpParser.parse("help     "), null);
    }
    
    @Test
    public void parse_help_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("help 1");
    }
    
    @Test
    public void parse_help_valid() throws ParseException {
        assertNotEquals(helpParser.parse("help"), null);
    }

    //==================================================================================

    @Test
    public void parse_exit_spaces() throws ParseException {
        assertNotEquals(exitParser.parse("exit     "), null);
    }
    
    @Test
    public void parse_exit_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("exit 1");
    }
    
    @Test
    public void parse_exit_valid() throws ParseException {
        assertNotEquals(exitParser.parse("exit"), null);
    }

    //==================================================================================
    
    @Test
    public void parse_mark_noIndexSpecified() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark");
    }
    
    @Test
    public void parse_mark_oneIndex() throws ParseException {
        assertNotEquals(markParser.parse("mark 1"), null);
    }
    
    @Test
    public void parse_mark_multipleIndices() throws ParseException {
        assertNotEquals(markParser.parse("mark 1 2 3"), null);
    }
    
    @Test
    public void parse_mark_multipleSpacesIndices() throws ParseException {
        assertNotEquals(markParser.parse("mark    1   2     3"), null);
    }

    @Test
    public void parse_mark_negativeIndex() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark -1");
    }

    @Test
    public void parse_mark_zeroIndex() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark 0");
    }

    //==================================================================================
    
    @Test
    public void parse_unmark_noIndexSpecified() throws ParseException {
        thrown.expect(ParseException.class);
        unmarkParser.parse("unmark");
    }
    
    @Test
    public void parse_unmark_oneIndex() throws ParseException {
        assertNotEquals(unmarkParser.parse("unmark 1"), null);
    }
    
    @Test
    public void parse_unmark_multipleIndices() throws ParseException {
        assertNotEquals(unmarkParser.parse("unmark 1 2 3"), null);
    }
    
    @Test
    public void parse_unmark_multipleSpacesIndices() throws ParseException {
        assertNotEquals(unmarkParser.parse("unmark    1   2     3"), null);
    }

    @Test
    public void parse_unmark_negativeIndex() throws ParseException {
        thrown.expect(ParseException.class);
        unmarkParser.parse("unmark -1");
    }

    @Test
    public void parse_unmark_zeroIndex() throws ParseException {
        thrown.expect(ParseException.class);
        unmarkParser.parse("unmark 0");
    }

    //==================================================================================

    @Test
    public void parse_undo_spaces() throws ParseException {
        assertNotEquals(undoParser.parse("undo     "), null);
    }
    
    @Test
    public void parse_undo_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("undo 1");
    }
    
    @Test
    public void parse_undo_valid() throws ParseException {
        assertNotEquals(undoParser.parse("undo"), null);
    }

    //==================================================================================

    @Test
    public void parse_redo_spaces() throws ParseException {
        assertNotEquals(redoParser.parse("redo     "), null);
    }
    
    @Test
    public void parse_redo_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("redo 1");
    }
    
    @Test
    public void parse_redo_valid() throws ParseException {
        assertNotEquals(redoParser.parse("redo"), null);
    }

    //==================================================================================

    @Test
    public void parse_alias_keywordUnspecified() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias t/ a string of things");
    }

    @Test
    public void parse_alias_textUnspecified() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ xyz");
    }
    
    @Test
    public void parse_alias_noSwitchesSpecified() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias power overwhelming");
    }
    
    @Test
    public void parse_alias_keywordTooLong() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ not a single word t/ project management");
    }
    
    @Test
    public void parse_alias_keywordEmpty() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/   t/ project management");
    }
    
    @Test
    public void parse_alias_textEmpty() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ pjm  t/  ");
    }
    
    @Test
    public void parse_alias_fullValid() throws ParseException {
        assertNotEquals(aliasParser.parse("alias   k/ pjm  t/  project management  "), null);
    }
}
