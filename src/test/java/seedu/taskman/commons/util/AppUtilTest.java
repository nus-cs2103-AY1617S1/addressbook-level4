package seedu.taskman.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.taskman.Constants;

import static org.junit.Assert.assertNotNull;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImage_exitingImage(){
        assertNotNull(AppUtil.getImage(Constants.APP_ICON_FILE_PATH));
    }


    @Test
    public void getImage_nullGiven_assertionError(){
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }


}
