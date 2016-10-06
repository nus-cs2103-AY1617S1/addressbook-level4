package seedu.address.commons.core;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Collection of static, application-specific config constants used globally
 */
public class Config {

    public static final String ApplicationTitle = "CommanDo";
    public static final String ApplicationName = "CommanDo";
    public static final Level LogLevel = Level.INFO;
    public static String DefaultUserPrefsFilePath = "preferences.json";
    public static String DefaultToDoListFilePath = "data/todos.xml";
    public static String UserGuideUrl = "https://github.com/CS2103AUG2016-W13-C3/main/blob/master/docs/UserGuide.md";
    private static Map<String, String> CommandWordsToUserGuideAnchors = new HashMap<String, String>() {{
        put("cheatsheet", "command-cheatsheet");
    }};

    /**
     * Returns anchor name for heading of {@param commandWord}on user guide at {@param UserGuideUrl}
     * If no mapping exists, the original value will be returned
     */
    public static String getUserGuideAnchorForCommandWord(String commandWord) {
        return CommandWordsToUserGuideAnchors.entrySet().stream()
            .filter(e -> e.getKey().equals(commandWord))
            .map(Map.Entry::getValue)
            .findFirst().orElse(commandWord);
    }
}
