package seedu.agendum.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImageExitingImage(){
        assertNotNull(AppUtil.getImage("/images/agendum_icon.png"));
    }


    @Test
    public void getImageNullGivenAssertionError(){
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }


}
