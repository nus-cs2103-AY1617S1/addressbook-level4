package tars.commons.prefixes;

import java.util.Arrays;
import java.util.List;

/**
 * Container for user input prefixes.
 */
public class Prefixes {

    public static final int LENGTH_TWO = 2;
    public static final int LENGTH_THREE = 3;
    
    public static final String NAME = "-n";
    public static final String PRIORITY = "-p";
    public static final String DATETIME = "-dt";
    public static final String ADDTAG = "-ta";
    public static final String REMOVETAG = "-tr";
    
    public static final List<String> LENGTH_TWO_PREFIXES = Arrays.asList(NAME, PRIORITY);
    public static final List<String> LENGTH_THREE_PREFIXES = Arrays.asList(DATETIME, ADDTAG, REMOVETAG);
    
}
