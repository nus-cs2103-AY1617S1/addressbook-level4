package seedu.task.commons.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.util.AppUtil;

import static org.junit.Assert.assertNotNull;

public class AppUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getImageExitingImage() {
        assertNotNull(AppUtil.getImage("/images/task_manager_32.png"));
    }

    @Test
    public void getImageNullGivenAssertionError() {
        thrown.expect(AssertionError.class);
        AppUtil.getImage(null);
    }

}
