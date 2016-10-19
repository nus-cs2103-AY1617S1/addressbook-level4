package seedu.agendum.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isUnsignedPositiveInteger() {
        assertFalse(StringUtil.isUnsignedInteger(null));
        assertFalse(StringUtil.isUnsignedInteger(""));
        assertFalse(StringUtil.isUnsignedInteger("a"));
        assertFalse(StringUtil.isUnsignedInteger("aaa"));
        assertFalse(StringUtil.isUnsignedInteger("  "));
        assertFalse(StringUtil.isUnsignedInteger("-1"));
        assertFalse(StringUtil.isUnsignedInteger("0"));
        assertFalse(StringUtil.isUnsignedInteger("+1")); //should be unsigned
        assertFalse(StringUtil.isUnsignedInteger("-1")); //should be unsigned
        assertFalse(StringUtil.isUnsignedInteger(" 10")); //should not contain whitespaces
        assertFalse(StringUtil.isUnsignedInteger("10 ")); //should not contain whitespaces
        assertFalse(StringUtil.isUnsignedInteger("1 0")); //should not contain whitespaces

        assertTrue(StringUtil.isUnsignedInteger("1"));
        assertTrue(StringUtil.isUnsignedInteger("10"));
    }

    @Test
    public void getDetails_exceptionGiven(){
        assertThat(StringUtil.getDetails(new FileNotFoundException("file not found")),
                   containsString("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_assertionError(){
        thrown.expect(AssertionError.class);
        StringUtil.getDetails(null);
    }
    
    /*
     * Valid equivalence partitions for path to file:
     *   - file path has spaces only between words
     *   - one alphabet for drive letters for windows absolute path
     *   - filename must be file.type
     *
     * Possible scenarios returning true:
     *   - valid relative path to a file
     *   - valid absolute path to a file for windows
     *   - valid absolute path to a file for Unix/MacOS
     *
     * Possible scenarios returning false:
     *   - file name missing
     *   - file type missing
     *   - absolute path has more than one alphabet for drive letter
     *   - absolute path has number for drive letter
     *   - null path
     *   - empty path
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */
    
    @Test
    public void isValidPathToFile(){
        // null of empty file paths
        assertFalse(StringUtil.isValidPathToFile(null));
        assertFalse(StringUtil.isValidPathToFile(""));
        
        // relative file paths
        assertFalse(StringUtil.isValidPathToFile("a")); // missing file type
        assertFalse(StringUtil.isValidPathToFile("data/.xml")); // missing file name
        assertFalse(StringUtil.isValidPathToFile("data/ .xml")); // invalid file name
        assertFalse(StringUtil.isValidPathToFile("data /valid.xml")); // invalid folder name with spaces after
        assertFalse(StringUtil.isValidPathToFile(" data/valid.xml")); // invalid folder name with spaces before
        
        assertTrue(StringUtil.isValidPathToFile("Program Files/data.xml")); // valid folder and file name with spaces
        assertTrue(StringUtil.isValidPathToFile("folder/some-other-folder/data.dat")); // valid folder and file name
        
        // absolute file paths for windows
        assertFalse(StringUtil.isValidPathToFile("CCCC:/valid.xml")); // invalid drive
        assertFalse(StringUtil.isValidPathToFile("1:/data.xml")); // invalid drive
        assertFalse(StringUtil.isValidPathToFile("C:/Program Files")); // missing file name and file type
        assertFalse(StringUtil.isValidPathToFile("C:/data/a")); // missing file type
        assertFalse(StringUtil.isValidPathToFile("C:/data/.xml")); // missing file name
        assertFalse(StringUtil.isValidPathToFile("C:/data/ .xml")); // invalid file name
        assertFalse(StringUtil.isValidPathToFile("C:/data /valid.xml")); // invalid folder name with spaces after
        assertFalse(StringUtil.isValidPathToFile("C:/ data/valid.xml")); // invalid folder name with spaces before

        assertTrue(StringUtil.isValidPathToFile("C:/a/a.xml")); // valid drive, folder and file name
        assertTrue(StringUtil.isValidPathToFile("C:/Program Files/data.xml")); // valid folder and file name with spaces
        assertTrue(StringUtil.isValidPathToFile("Z:/folder/some-other-folder/data.dat")); // valid drive, folder and file name
        assertTrue(StringUtil.isValidPathToFile("a:/folder/some-other-folder/data.dat")); // valid drive, folder and file name
        
        // absolute file path for unix/MacOX
        assertFalse(StringUtil.isValidPathToFile("/usr/")); // missing file name and type
        assertFalse(StringUtil.isValidPathToFile("/usr/data")); // missing file type
        assertFalse(StringUtil.isValidPathToFile("/usr/.xml")); // missing file name
        assertFalse(StringUtil.isValidPathToFile("/usr/ .xml")); // invalid file name
        assertFalse(StringUtil.isValidPathToFile("/ usr/data.xml")); // invalid folder with spaces before
        assertFalse(StringUtil.isValidPathToFile("/usr /data.xml")); // invalid folder with spaces after
        
        assertTrue(StringUtil.isValidPathToFile("/usr/bin/my folder/data.xml")); // valid folder and file name with spaces
        assertTrue(StringUtil.isValidPathToFile("/home/data.xml"));// valid folder and file name
    }
    
}
