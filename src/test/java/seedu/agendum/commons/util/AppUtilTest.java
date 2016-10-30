package seedu.agendum.commons.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AppUtilTest {

    @Test
    public void getImageExitingImage(){
        assertNotNull(AppUtil.getImage("/images/agendum_icon.png"));
    }


    @Test(expected = AssertionError.class)
    public void getImageNullGivenAssertionError(){
        AppUtil.getImage(null);
    }


}
