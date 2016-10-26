//@@author A0139916U
package seedu.savvytasker.logic.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.savvytasker.logic.commands.AddCommand;
import seedu.savvytasker.logic.commands.Command;
import seedu.savvytasker.logic.commands.IncorrectCommand;
import seedu.savvytasker.model.alias.AliasSymbol;

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
    private UnaliasCommandParser unaliasParser;

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
        unaliasParser = new UnaliasCommandParser();
    }
    
    @Test
    public void parse_add_reorder() throws ParseException {
        assertNotNull(addParser.parse("add task l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"));
    }
    
    @Test
    public void parse_add_multipleSpaces() throws ParseException {
        assertNotNull(addParser.parse("add    Multiple   Spaces    s/  2pm"));
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
        assertNotNull(addParser.parse("add task s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"));
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
        assertNotNull(deleteParser.parse("delete 1"));
    }
    
    @Test
    public void parse_delete_multipleIndices() throws ParseException {
        assertNotNull(deleteParser.parse("delete 1 2 3"));
    }
    
    @Test
    public void parse_delete_multipleSpacesIndices() throws ParseException {
        assertNotNull(deleteParser.parse("delete    1   2     3"));
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
        assertNotNull(modifyParser.parse("modify 1"));
    }
    
    @Test
    public void parse_modify_fullValid() throws ParseException {
        assertNotNull(modifyParser.parse("modify 3 t/ newtask s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"));
    }

    @Test
    public void parse_modify_reorder() throws ParseException {
        assertNotNull(modifyParser.parse("modify 1 l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"));
    }

    @Test
    public void parse_modify_multipleSpaces() throws ParseException {
        assertNotNull(modifyParser.parse("modify   1  t/   Multiple   Spaces    s/  2pm"));
    }
    
    //==================================================================================
    
    @Test
    public void parse_clear_spaces() throws ParseException {
        assertNotNull(clearParser.parse("clear     "));
    }
    
    @Test
    public void parse_clear_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        clearParser.parse("clear 1");
    }
    
    @Test
    public void parse_clear_valid() throws ParseException {
        assertNotNull(clearParser.parse("clear"));
    }

    //==================================================================================
    
    @Test
    public void parse_list_noParameters() throws ParseException {
        assertNotNull(listParser.parse("list"));
    }

    @Test
    public void parse_list_noParametersSpaces() throws ParseException {
        assertNotNull(listParser.parse("list   "));
    }
    
    @Test
    public void parse_list_valid() throws ParseException {
        assertNotNull(listParser.parse("list   t/ Priority Level "));
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
        assertNotNull(findParser.parse("find t/ Exact this word "));
    }
    
    @Test
    public void parse_find_validBefore() throws ParseException {
        assertNotNull(findParser.parse("find some words t/ Partial  "));
    }

    @Test
    public void parse_find_validBeforeAndAfter() throws ParseException {
        assertNotNull(findParser.parse("find some words t/ Full some words after "));
    }

    //==================================================================================
    
    @Test
    public void parse_help_spaces() throws ParseException {
        assertNotNull(helpParser.parse("help     "));
    }
    
    @Test
    public void parse_help_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("help 1");
    }
    
    @Test
    public void parse_help_valid() throws ParseException {
        assertNotNull(helpParser.parse("help"));
    }

    //==================================================================================

    @Test
    public void parse_exit_spaces() throws ParseException {
        assertNotNull(exitParser.parse("exit     "));
    }
    
    @Test
    public void parse_exit_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("exit 1");
    }
    
    @Test
    public void parse_exit_valid() throws ParseException {
        assertNotNull(exitParser.parse("exit"));
    }

    //==================================================================================
    
    @Test
    public void parse_mark_noIndexSpecified() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark");
    }
    
    @Test
    public void parse_mark_oneIndex() throws ParseException {
        assertNotNull(markParser.parse("mark 1"));
    }
    
    @Test
    public void parse_mark_multipleIndices() throws ParseException {
        assertNotNull(markParser.parse("mark 1 2 3"));
    }
    
    @Test
    public void parse_mark_multipleSpacesIndices() throws ParseException {
        assertNotNull(markParser.parse("mark    1   2     3"));
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
        assertNotNull(unmarkParser.parse("unmark 1"));
    }
    
    @Test
    public void parse_unmark_multipleIndices() throws ParseException {
        assertNotNull(unmarkParser.parse("unmark 1 2 3"));
    }
    
    @Test
    public void parse_unmark_multipleSpacesIndices() throws ParseException {
        assertNotNull(unmarkParser.parse("unmark    1   2     3"));
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
        assertNotNull(undoParser.parse("undo     "));
    }
    
    @Test
    public void parse_undo_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("undo 1");
    }
    
    @Test
    public void parse_undo_valid() throws ParseException {
        assertNotNull(undoParser.parse("undo"));
    }

    //==================================================================================

    @Test
    public void parse_redo_spaces() throws ParseException {
        assertNotNull(redoParser.parse("redo     "));
    }
    
    @Test
    public void parse_redo_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("redo 1");
    }
    
    @Test
    public void parse_redo_valid() throws ParseException {
        assertNotNull(redoParser.parse("redo"));
    }

    //==================================================================================

    @Test
    public void parse_alias_keywordUnspecified() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias r/ a string of things");
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
        aliasParser.parse("alias k/ not a single word r/ project management");
    }
    
    @Test
    public void parse_alias_keywordEmpty() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/   r/ project management");
    }
    
    @Test
    public void parse_alias_textEmpty() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ pjm  r/  ");
    }
    
    @Test
    public void parse_alias_fullValid() throws ParseException {
        assertNotNull(aliasParser.parse("alias   k/ pjm  r/  project management  "));
    }

    //==================================================================================

    @Test
    public void parse_unalias_emptyKeyword() throws ParseException {
        thrown.expect(ParseException.class);
        unaliasParser.parse("unalias    ");
    }

    @Test
    public void parse_unalias_valid() throws ParseException {
        assertNotNull(unaliasParser.parse("unalias  something "));
    }

    //==================================================================================
    
    @Test
    public void parse_master_subparser() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        assertTrue(parser.parse(" add A New Task s/ tomorrow  e/ the day after tomorrow, l/ SR10 ") instanceof AddCommand);
    }

    @Test
    public void parse_master_subparserRemoved() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        parser.unregisterCommandParser("add");
        assertTrue(parser.parse(" add A New Task s/ tomorrow  e/ the day after tomorrow, l/ SR10 ") instanceof IncorrectCommand);
    }

    @Test
    public void parse_master_alias() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        parser.addAliasSymbol(new AliasSymbol("xyz", "add A New Task"));
        parser.addAliasSymbol(new AliasSymbol("pqr", "s/ tomorrow e/ 30 september 3pm"));
        assertTrue(parser.parse("xyz pqr") instanceof AddCommand);
    }

    @Test
    public void parse_master_invalidAlias() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        assertFalse(parser.addAliasSymbol(new AliasSymbol("add", "add A New Task")));
    }

    @Test
    public void parse_master_removedAlias() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        parser.addAliasSymbol(new AliasSymbol("xyz", "add A New Task"));
        parser.removeAliasSymbol("xyz");
        assertTrue(parser.parse("xyz pqr") instanceof IncorrectCommand);
    }
}
