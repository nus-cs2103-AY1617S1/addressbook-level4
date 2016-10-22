package seedu.savvytasker.logic.parser;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import static org.junit.Assert.assertNotEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.savvytasker.logic.commands.IncorrectCommand;
import seedu.savvytasker.model.alias.AliasSymbol;

@RunWith(Parameterized.class)
public class ParserTest {
    private MasterParser masterParser;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    public ParserTest(MasterParser masterParser) {
        this.masterParser = masterParser;
    }
    
    public static MasterParser createGenericMasterParser() {
        MasterParser masterParser = new MasterParser();
        masterParser.registerCommandParser(new AddCommandParser());
        masterParser.registerCommandParser(new DeleteCommandParser());
        masterParser.registerCommandParser(new FindCommandParser());
        masterParser.registerCommandParser(new ListCommandParser());
        masterParser.registerCommandParser(new ModifyCommandParser());
        masterParser.registerCommandParser(new HelpCommandParser());
        masterParser.registerCommandParser(new ClearCommandParser());
        masterParser.registerCommandParser(new ExitCommandParser());
        masterParser.registerCommandParser(new MarkCommandParser());
        masterParser.registerCommandParser(new UnmarkCommandParser());
        masterParser.registerCommandParser(new UndoCommandParser());
        masterParser.registerCommandParser(new RedoCommandParser());
        masterParser.registerCommandParser(new AliasCommandParser());
        masterParser.registerCommandParser(new UnaliasCommandParser());
        return masterParser;
    }
    
    @SuppressWarnings("unchecked")
    @Parameters
    public static Collection<MasterParser> parameterized() {
        MasterParser masterParser1 = createGenericMasterParser();
        MasterParser masterParser2 = createGenericMasterParser();
        masterParser2.addAliasSymbol(new AliasSymbol("xyz", "string of words"));
        MasterParser masterParser3 = createGenericMasterParser();
        masterParser3.addAliasSymbol(new AliasSymbol("xyz", "string  of words"));
        masterParser3.removeAliasSymbol("xyz");
        
        return Arrays.asList(new MasterParser[] {
                masterParser1,
                masterParser2,
                masterParser3,
        });
    }
    
    @Test
    public void parse_add_reorder() throws ParseException {
        assertNotEquals(masterParser.parse("add xyz l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"), null);
    }
    
    @Test
    public void parse_add_multipleSpaces() throws ParseException {
        assertNotEquals(masterParser.parse("add    Multiple   xyz    s/  2pm"), null);
    }
    
    @Test
    public void parse_add_sameOptionMultipleTimes() throws ParseException {
        assertTrue(masterParser.parse("add xyz s/ tomorrow 3pm s/ tomorrow 10pm") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_add_missingTaskName() throws ParseException {
        assertTrue(masterParser.parse("add s/ tomorrow 3pm") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_add_arbitrarySlash() throws ParseException {
        assertTrue(masterParser.parse("add xyz s/ tomorrow 2pm/3pm e/ sunday") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_add_fullValid() throws ParseException {
        assertNotEquals(masterParser.parse("add xyz s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"), null);
    }
    
    @Test
    public void parse_add_invalidRecurrenceType() throws ParseException {
        assertTrue(masterParser.parse("add xyz r/ Error ") instanceof IncorrectCommand);
    }

    @Test
    public void parse_add_invalidPriorityLevel() throws ParseException {
        assertTrue(masterParser.parse("add xyz p/ Error ") instanceof IncorrectCommand);
    }

    //==================================================================================
    
    @Test
    public void parse_delete_noIndexSpecified() throws ParseException {
        assertTrue(masterParser.parse("delete") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_delete_oneIndex() throws ParseException {
        assertNotEquals(masterParser.parse("delete 1"), null);
    }
    
    @Test
    public void parse_delete_multipleIndices() throws ParseException {
        assertNotEquals(masterParser.parse("delete 1 2 3"), null);
    }
    
    @Test
    public void parse_delete_multipleSpacesIndices() throws ParseException {
        assertNotEquals(masterParser.parse("delete    1   2     3"), null);
    }

    @Test
    public void parse_delete_negativeIndex() throws ParseException {
        assertTrue(masterParser.parse("delete -1") instanceof IncorrectCommand);
    }

    @Test
    public void parse_delete_zeroIndex() throws ParseException {
        assertTrue(masterParser.parse("delete 0") instanceof IncorrectCommand);
    }
    
    //==================================================================================
    
    @Test
    public void parse_modify_noIndex() throws ParseException {
        assertTrue(masterParser.parse("modify t/ newtask") instanceof IncorrectCommand);
    }

    @Test
    public void parse_modify_multipleIndex() throws ParseException {
        assertTrue(masterParser.parse("modify 1 2 3 t/ xyz") instanceof IncorrectCommand);
    }

    @Test
    public void parse_modify_negativeIndex() throws ParseException {
        assertTrue(masterParser.parse("modify -1 t/ newtask") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_modify_zeroIndex() throws ParseException {
        assertTrue(masterParser.parse("modify 0 t/ newtask") instanceof IncorrectCommand);
    }

    @Test
    public void parse_modify_onlySpecifyIndex() throws ParseException {
        assertNotEquals(masterParser.parse("modify 1"), null);
    }
    
    @Test
    public void parse_modify_fullValid() throws ParseException {
        assertNotEquals(masterParser.parse("modify 3 t/ xyz s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"), null);
    }

    @Test
    public void parse_modify_reorder() throws ParseException {
        assertNotEquals(masterParser.parse("modify 1 l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"), null);
    }

    @Test
    public void parse_modify_multipleSpaces() throws ParseException {
        assertNotEquals(masterParser.parse("modify   1  t/   Multiple   Spaces    s/  2pm"), null);
    }
    
    //==================================================================================
    
    @Test
    public void parse_clear_spaces() throws ParseException {
        assertNotEquals(masterParser.parse("clear     "), null);
    }
    
    @Test
    public void parse_clear_invalid() throws ParseException {
        assertTrue(masterParser.parse("clear 1") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_clear_valid() throws ParseException {
        assertNotEquals(masterParser.parse("clear"), null);
    }

    //==================================================================================
    
    @Test
    public void parse_list_noParameters() throws ParseException {
        assertNotEquals(masterParser.parse("list"), null);
    }

    @Test
    public void parse_list_noParametersSpaces() throws ParseException {
        assertNotEquals(masterParser.parse("list   "), null);
    }
    
    @Test
    public void parse_list_valid() throws ParseException {
        assertNotEquals(masterParser.parse("list   t/ Priority Level "), null);
    }
    
    @Test
    public void parse_list_invalidType() throws ParseException {
        assertTrue(masterParser.parse("list t/ Error ") instanceof IncorrectCommand);
    }

    //==================================================================================
    
    @Test
    public void parse_find_noKeywords() throws ParseException {
        assertTrue(masterParser.parse("find") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_find_noKeywordsSpaces() throws ParseException {
        assertTrue(masterParser.parse("find    ") instanceof IncorrectCommand);
    }

    @Test
    public void parse_find_noKeywordsButWithType() throws ParseException {
        assertTrue(masterParser.parse("find t/ Exact ") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_find_invalidType() throws ParseException {
        assertTrue(masterParser.parse("find t/ Error some words") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_find_validAfter() throws ParseException {
        assertNotEquals(masterParser.parse("find t/ Exact this word "), null);
    }
    
    @Test
    public void parse_find_validBefore() throws ParseException {
        assertNotEquals(masterParser.parse("find some words t/ Partial  "), null);
    }

    @Test
    public void parse_find_validBeforeAndAfter() throws ParseException {
        assertNotEquals(masterParser.parse("find some words t/ Full some words after "), null);
    }

    //==================================================================================
    
    @Test
    public void parse_help_spaces() throws ParseException {
        assertNotEquals(masterParser.parse("help     "), null);
    }
    
    @Test
    public void parse_help_invalid() throws ParseException {
        assertTrue(masterParser.parse("help 1") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_help_valid() throws ParseException {
        assertNotEquals(masterParser.parse("help"), null);
    }

    //==================================================================================

    @Test
    public void parse_exit_spaces() throws ParseException {
        assertNotEquals(masterParser.parse("exit     "), null);
    }
    
    @Test
    public void parse_exit_invalid() throws ParseException {
        assertTrue(masterParser.parse("exit 1") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_exit_valid() throws ParseException {
        assertNotEquals(masterParser.parse("exit"), null);
    }

    //==================================================================================
    
    @Test
    public void parse_mark_noIndexSpecified() throws ParseException {
        assertTrue(masterParser.parse("mark") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_mark_oneIndex() throws ParseException {
        assertNotEquals(masterParser.parse("mark 1"), null);
    }
    
    @Test
    public void parse_mark_multipleIndices() throws ParseException {
        assertNotEquals(masterParser.parse("mark 1 2 3"), null);
    }
    
    @Test
    public void parse_mark_multipleSpacesIndices() throws ParseException {
        assertNotEquals(masterParser.parse("mark    1   2     3"), null);
    }

    @Test
    public void parse_mark_negativeIndex() throws ParseException {
        assertTrue(masterParser.parse("mark -1") instanceof IncorrectCommand);
    }

    @Test
    public void parse_mark_zeroIndex() throws ParseException {
        assertTrue(masterParser.parse("mark 0") instanceof IncorrectCommand);
    }

    //==================================================================================
    
    @Test
    public void parse_unmark_noIndexSpecified() throws ParseException {
        assertTrue(masterParser.parse("unmark") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_unmark_oneIndex() throws ParseException {
        assertNotEquals(masterParser.parse("unmark 1"), null);
    }
    
    @Test
    public void parse_unmark_multipleIndices() throws ParseException {
        assertNotEquals(masterParser.parse("unmark 1 2 3"), null);
    }
    
    @Test
    public void parse_unmark_multipleSpacesIndices() throws ParseException {
        assertNotEquals(masterParser.parse("unmark    1   2     3"), null);
    }

    @Test
    public void parse_unmark_negativeIndex() throws ParseException {
        assertTrue(masterParser.parse("unmark -1") instanceof IncorrectCommand);
    }

    @Test
    public void parse_unmark_zeroIndex() throws ParseException {
        assertTrue(masterParser.parse("unmark 0") instanceof IncorrectCommand);
    }

    //==================================================================================

    @Test
    public void parse_undo_spaces() throws ParseException {
        assertNotEquals(masterParser.parse("undo     "), null);
    }
    
    @Test
    public void parse_undo_invalid() throws ParseException {
        assertTrue(masterParser.parse("undo 1") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_undo_valid() throws ParseException {
        assertNotEquals(masterParser.parse("undo"), null);
    }

    //==================================================================================

    @Test
    public void parse_redo_spaces() throws ParseException {
        assertNotEquals(masterParser.parse("redo     "), null);
    }
    
    @Test
    public void parse_redo_invalid() throws ParseException {
        assertTrue(masterParser.parse("redo 1") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_redo_valid() throws ParseException {
        assertNotEquals(masterParser.parse("redo"), null);
    }

    //==================================================================================

    @Test
    public void parse_alias_keywordUnspecified() throws ParseException {
        assertTrue(masterParser.parse("alias r/ a string of things") instanceof IncorrectCommand);
    }

    @Test
    public void parse_alias_textUnspecified() throws ParseException {
        assertTrue(masterParser.parse("alias k/ xyz") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_alias_noSwitchesSpecified() throws ParseException {
        assertTrue(masterParser.parse("alias power xyz") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_alias_keywordTooLong() throws ParseException {
        assertTrue(masterParser.parse("alias k/ not a single word r/ project management") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_alias_keywordEmpty() throws ParseException {
        assertTrue(masterParser.parse("alias k/   r/ project management") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_alias_textEmpty() throws ParseException {
        assertTrue(masterParser.parse("alias k/ pjm  r/  ") instanceof IncorrectCommand);
    }
    
    @Test
    public void parse_alias_fullValid() throws ParseException {
        assertNotEquals(masterParser.parse("alias   k/ pjm  r/  xyz management  "), null);
    }

    //==================================================================================

    @Test
    public void parse_unalias_emptyKeyword() throws ParseException {
        assertTrue(masterParser.parse("unalias    ") instanceof IncorrectCommand);
    }

    @Test
    public void parse_unalias_valid() throws ParseException {
        assertNotEquals(masterParser.parse("unalias  something "), null);
    }
}
