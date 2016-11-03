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
    private StorageCommandParser storageParser;

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
        storageParser = new StorageCommandParser();
    }
    
    @Test
    public void parseAdd_reorder() throws ParseException {
        assertNotNull(addParser.parse("add task l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"));
    }
    
    @Test
    public void parseAdd_multipleSpaces() throws ParseException {
        assertNotNull(addParser.parse("add    Multiple   Spaces    s/  2pm"));
    }
    
    @Test
    public void parseAdd_sameOptionMultipleTimes_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task s/ tomorrow 3pm s/ tomorrow 10pm");
    }
    
    @Test
    public void parseAdd_missingTaskName_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add s/ tomorrow 3pm");
    }
    
    @Test
    public void parseAdd_arbitrarySlash_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task s/ tomorrow 2pm/3pm e/ sunday");
    }
    
    @Test
    public void parseAdd_fullValid() throws ParseException {
        assertNotNull(addParser.parse("add task s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"));
    }
    
    @Test
    public void parseAdd_invalidRecurrenceType_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task r/ Error ");
    }

    @Test
    public void parseAdd_invalidPriorityLevel_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        addParser.parse("add task p/ Error ");
    }

    //==================================================================================
    
    @Test
    public void parseDelete_noIndexSpecified_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        deleteParser.parse("delete");
    }
    
    @Test
    public void parseDelete_oneIndex() throws ParseException {
        assertNotNull(deleteParser.parse("delete 1"));
    }
    
    @Test
    public void parseDelete_multipleIndices() throws ParseException {
        assertNotNull(deleteParser.parse("delete 1 2 3"));
    }
    
    @Test
    public void parseDelete_multipleSpacesIndices() throws ParseException {
        assertNotNull(deleteParser.parse("delete    1   2     3"));
    }

    @Test
    public void parseDelete_negativeIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        deleteParser.parse("delete -1");
    }

    @Test
    public void parseDelete_zeroIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        deleteParser.parse("delete 0");
    }
    
    //==================================================================================
    
    @Test
    public void parseModify_noIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify t/ newtask");
    }

    @Test
    public void parseModify_multipleIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify 1 2 3 t/ newtask");
    }

    @Test
    public void parseModify_negativeIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify -1 t/ newtask");
    }
    
    @Test
    public void parseModify_zeroIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        modifyParser.parse("modify 0 t/ newtask");
    }

    @Test
    public void parseModify_onlySpecifyIndex() throws ParseException {
        assertNotNull(modifyParser.parse("modify 1"));
    }
    
    @Test
    public void parseModify_fullValid() throws ParseException {
        assertNotNull(modifyParser.parse("modify 3 t/ newtask s/wednesday e/thursday l/ comp p/ high r/ none n/ 1 c/ test d/ test"));
    }

    @Test
    public void parseModify_reorder() throws ParseException {
        assertNotNull(modifyParser.parse("modify 1 l/ comp e/ tomorrow, 3pm s/ today, 2pm n/ 2"));
    }

    @Test
    public void parseModify_multipleSpaces() throws ParseException {
        assertNotNull(modifyParser.parse("modify   1  t/   Multiple   Spaces    s/  2pm"));
    }
    
    //==================================================================================
    
    @Test
    public void parseClear_spaces() throws ParseException {
        assertNotNull(clearParser.parse("clear     "));
    }
    
    @Test
    public void parseClear_invalid_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        clearParser.parse("clear 1");
    }
    
    @Test
    public void parseClear_valid() throws ParseException {
        assertNotNull(clearParser.parse("clear"));
    }

    //==================================================================================
    
    @Test
    public void parseList_noParameters() throws ParseException {
        assertNotNull(listParser.parse("list"));
    }

    @Test
    public void parseList_noParametersSpaces() throws ParseException {
        assertNotNull(listParser.parse("list   "));
    }
    
    @Test
    public void parse_list_valid() throws ParseException {
        assertNotNull(listParser.parse("list    Priority Level "));
    }
    
    @Test
    public void parseList_invalidType_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        listParser.parse("list  Error ");
    }

    //==================================================================================
    
    @Test
    public void parseFind_noKeywords_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find");
    }
    
    @Test
    public void parseFind_noKeywordsSpaces_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find    ");
    }

    @Test
    public void parseFind_noKeywordsButWithType_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find t/ Exact ");
    }
    
    @Test
    public void parseFind_invalidType_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        findParser.parse("find t/ Error some words");
    }
    
    @Test
    public void parseFind_validAfter() throws ParseException {
        assertNotNull(findParser.parse("find t/ Exact this word "));
    }
    
    @Test
    public void parseFind_validBefore() throws ParseException {
        assertNotNull(findParser.parse("find some words t/ Partial  "));
    }

    @Test
    public void parseFind_validBeforeAndAfter() throws ParseException {
        assertNotNull(findParser.parse("find some words t/ Full some words after "));
    }

    //==================================================================================
    
    @Test
    public void parseHelp_spaces() throws ParseException {
        assertNotNull(helpParser.parse("help     "));
    }
    
    @Test
    public void parseHelp_invalid_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("help 1");
    }
    
    @Test
    public void parseHelp_valid() throws ParseException {
        assertNotNull(helpParser.parse("help"));
    }

    //==================================================================================

    @Test
    public void parseExit_spaces() throws ParseException {
        assertNotNull(exitParser.parse("exit     "));
    }
    
    @Test
    public void parseExit_invalid_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("exit 1");
    }
    
    @Test
    public void parseExit_valid() throws ParseException {
        assertNotNull(exitParser.parse("exit"));
    }

    //==================================================================================
    
    @Test
    public void parseMark_noIndexSpecified_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark");
    }
    
    @Test
    public void parseMark_oneIndex() throws ParseException {
        assertNotNull(markParser.parse("mark 1"));
    }
    
    @Test
    public void parseMark_multipleIndices() throws ParseException {
        assertNotNull(markParser.parse("mark 1 2 3"));
    }
    
    @Test
    public void parseMark_multipleSpacesIndices() throws ParseException {
        assertNotNull(markParser.parse("mark    1   2     3"));
    }

    @Test
    public void parseMark_negativeIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark -1");
    }

    @Test
    public void parseMark_zeroIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        markParser.parse("mark 0");
    }

    //==================================================================================
    
    @Test
    public void parseUnmark_noIndexSpecified_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        unmarkParser.parse("unmark");
    }
    
    @Test
    public void parseUnmark_oneIndex() throws ParseException {
        assertNotNull(unmarkParser.parse("unmark 1"));
    }
    
    @Test
    public void parseUnmark_multipleIndices() throws ParseException {
        assertNotNull(unmarkParser.parse("unmark 1 2 3"));
    }
    
    @Test
    public void parseUnmark_multipleSpacesIndices() throws ParseException {
        assertNotNull(unmarkParser.parse("unmark    1   2     3"));
    }

    @Test
    public void parseUnmark_negativeIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        unmarkParser.parse("unmark -1");
    }

    @Test
    public void parseUnmark_zeroIndex_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        unmarkParser.parse("unmark 0");
    }

    //==================================================================================

    @Test
    public void parseUndo_spaces() throws ParseException {
        assertNotNull(undoParser.parse("undo     "));
    }
    
    @Test
    public void parseUndo_invalid() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("undo 1");
    }
    
    @Test
    public void parseUndo_valid() throws ParseException {
        assertNotNull(undoParser.parse("undo"));
    }

    //==================================================================================

    @Test
    public void parseRedo_spaces() throws ParseException {
        assertNotNull(redoParser.parse("redo     "));
    }
    
    @Test
    public void parseRedo_invalid_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        helpParser.parse("redo 1");
    }
    
    @Test
    public void parseRedo_valid() throws ParseException {
        assertNotNull(redoParser.parse("redo"));
    }

    //==================================================================================

    @Test
    public void parseAlias_keywordUnspecified_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias r/ a string of things");
    }

    @Test
    public void parseAlias_textUnspecified_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ xyz");
    }
    
    @Test
    public void parseAlias_noSwitchesSpecified_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias power overwhelming");
    }
    
    @Test
    public void parseAlias_keywordNotSingleWord_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ not a single word r/ project management");
    }
    
    @Test
    public void parseAlias_keywordEmpty_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/   r/ project management");
    }
    
    @Test
    public void parseAlias_textEmpty_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        aliasParser.parse("alias k/ pjm  r/  ");
    }
    
    @Test
    public void parseAlias_fullValid() throws ParseException {
        assertNotNull(aliasParser.parse("alias   k/ pjm  r/  project management  "));
    }

    //==================================================================================

    @Test
    public void parseUnalias_emptyKeyword_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        unaliasParser.parse("unalias    ");
    }

    @Test
    public void parseUnalias_valid() throws ParseException {
        assertNotNull(unaliasParser.parse("unalias  something "));
    }

    //==================================================================================

    @Test
    public void parseStorage_invalid_exceptionThrown() throws ParseException {
        thrown.expect(ParseException.class);
        storageParser.parse("storage  ");
    }

    @Test
    public void parseStorage_valid() throws ParseException {
        assertNotNull(storageParser.parse("storage C:/Users/Brown/Desktop/file.xml "));
    }

    //==================================================================================
    @Test
    public void masterParser_subparserParsing_returnParsedCommand() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        assertTrue(parser.parse(" add A New Task s/ tomorrow  e/ the day after tomorrow, l/ SR10 ") instanceof AddCommand);
    }

    @Test
    public void masterParser_subparserRemoved_returnIncorrectCommand() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        parser.unregisterCommandParser("add");
        assertTrue(parser.parse(" add A New Task s/ tomorrow  e/ the day after tomorrow, l/ SR10 ") instanceof IncorrectCommand);
    }

    @Test
    public void masterParser_alias_returnParsedCommand() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        parser.addAliasSymbol(new AliasSymbol("xyz", "add A New Task"));
        parser.addAliasSymbol(new AliasSymbol("pqr", "s/ tomorrow e/ 30 september 3pm"));
        assertTrue(parser.parse("xyz pqr") instanceof AddCommand);
    }

    @Test
    public void masterParser_invalidAlias_returnFalse() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        assertFalse(parser.addAliasSymbol(new AliasSymbol("add", "add A New Task")));
    }

    @Test
    public void masterParser_removedAlias_returnIncorrectCommand() throws ParseException {
        MasterParser parser = new MasterParser();
        parser.registerCommandParser(new AddCommandParser());
        parser.addAliasSymbol(new AliasSymbol("xyz", "add A New Task"));
        parser.removeAliasSymbol("xyz");
        assertTrue(parser.parse("xyz pqr") instanceof IncorrectCommand);
    }
}
