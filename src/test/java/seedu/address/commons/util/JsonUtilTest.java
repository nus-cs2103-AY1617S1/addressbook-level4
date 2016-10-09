package seedu.address.commons.util;

import com.fasterxml.jackson.core.JsonParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void fromJsonString_invalidJson() throws IOException {
        thrown.expect(JsonParseException.class);
        JsonUtil.fromJsonString("Invalid JSON", JsonSerializable.class);
    }

    @Test
    public void fromJsonString_valid() throws IOException {
        JsonSerializable actual = JsonUtil.fromJsonString(JsonSerializable.JSON_STRING_REPRESENTATION, JsonSerializable.class);
        JsonSerializable expected = new JsonSerializable();
        expected.setTestValues();
        assertEquals(expected, actual);
    }

    @Test
    public void toJsonString_valid() throws IOException {
        JsonSerializable testInstance = new JsonSerializable();
        testInstance.setTestValues();
        String actual = JsonUtil.toJsonString(testInstance);

        assertEquals(JsonSerializable.JSON_STRING_REPRESENTATION, actual);
    }
}
