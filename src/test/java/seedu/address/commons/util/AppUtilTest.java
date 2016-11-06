package seedu.address.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertNotNull;

import java.net.URL;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();



    @Test
    public void getImage_exitingImage() {
    	URL location = AppUtilTest.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println(location.getFile());
    	assertNotNull(AppUtil.getImage("/images/amethyst_task_manager.png"));
    }


    @Test
    public void getImage_nullGiven_assertionError(){
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }


}
