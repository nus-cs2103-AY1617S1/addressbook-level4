package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.logic.parser.ArgumentTokenizer;
import seedu.task.logic.parser.ArgumentTokenizer.NoValueForRequiredTagException;
import seedu.task.logic.parser.ArgumentTokenizer.Prefix;
//@@author A0153411W
public class ArgumentTokenizerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //---------------- Tests for isUnsignedPositiveInteger --------------------------------------

    public static final Prefix descriptionPrefix = new Prefix(" d/");
    
	ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(descriptionPrefix);
	
    @Test
    public void getPreambleTest() throws NoValueForRequiredTagException {
    		//Space between title and prefix
    		String inputedValue = "title d/description";
    		argsTokenizer.tokenize(inputedValue);
    		String exceptedValue = "title";
            assertEquals(exceptedValue, argsTokenizer.getPreamble());
            
    		//No Space between title and prefix
    		inputedValue = "titled/description";
    		argsTokenizer.tokenize(inputedValue);
    		exceptedValue = "titled/description";
            assertEquals(exceptedValue, argsTokenizer.getPreamble());

            //Title written after prefix
    		inputedValue = "d/description title";
    		argsTokenizer.tokenize(inputedValue);
    		exceptedValue = "d/description title";
            assertEquals(exceptedValue, argsTokenizer.getPreamble());
    }
    
    @Test
    public void getValueText() throws NoValueForRequiredTagException {
    		//Description contains only letters
    		String inputedValue = "title d/description";
    		argsTokenizer.tokenize(inputedValue);
    		String exceptedValue = "description";
            assertEquals(exceptedValue, argsTokenizer.getValue(descriptionPrefix));
            
    		//Description contains special characters
    		inputedValue = "title d/@#$%^&*(";
    		argsTokenizer.tokenize(inputedValue);
    		exceptedValue = "@#$%^&*(";
            assertEquals(exceptedValue, argsTokenizer.getValue(descriptionPrefix));
            
    		//Description doesn't contain value
    		inputedValue = "title d/";
    		argsTokenizer.tokenize(inputedValue);
    		exceptedValue = "";
            assertEquals(exceptedValue, argsTokenizer.getValue(descriptionPrefix));
            
    		//Description contains spaces
    		inputedValue = "title d/this is description";
    		argsTokenizer.tokenize(inputedValue);
    		exceptedValue = "this is description";
            assertEquals(exceptedValue, argsTokenizer.getValue(descriptionPrefix));
            
    		//inputedValue contains two descriptions
            //last value of description is expected
    		inputedValue = "title d/description1 d/description2";
    		argsTokenizer.tokenize(inputedValue);
    		exceptedValue = "description2";
            assertEquals(exceptedValue, argsTokenizer.getValue(descriptionPrefix));
            
    }
}