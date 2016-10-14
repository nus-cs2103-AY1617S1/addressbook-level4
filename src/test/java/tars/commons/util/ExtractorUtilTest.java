package tars.commons.util;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.TreeMap;

import org.junit.Test;

import tars.commons.flags.Flag;

public class ExtractorUtilTest {
    
    @Test
    public void extract_prefix_successful() {
        TreeMap<Integer, Flag> expectedFlagsPosMap = new TreeMap<Integer, Flag>();
        TreeMap<Integer, Flag> actualFlagsPosMap = ExtractorUtil.getFlagPositon(null, null);

        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
        
        actualFlagsPosMap = ExtractorUtil.getFlagPositon(null, null);
        
        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
        
        actualFlagsPosMap = ExtractorUtil.getFlagPositon(null, new Flag[] { });

        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
        
        actualFlagsPosMap = ExtractorUtil.getFlagPositon("", null);

        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
        
        actualFlagsPosMap = ExtractorUtil.getFlagPositon("", new Flag[] { });

        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);

        Flag[] flags = { new Flag(Flag.NAME, false), new Flag(Flag.PRIORITY, false) };
        expectedFlagsPosMap.put(0, new Flag(Flag.NAME, false));
        expectedFlagsPosMap.put(8, new Flag(Flag.PRIORITY, false));
        actualFlagsPosMap = ExtractorUtil.getFlagPositon("-n name -p h", flags);
        
        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
        
        expectedFlagsPosMap.clear();
        actualFlagsPosMap = ExtractorUtil.getFlagPositon("-n name -p h", null);
        
        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
        
        actualFlagsPosMap = ExtractorUtil.getFlagPositon("-n name -p h", new Flag[] {});

        assertEquals(expectedFlagsPosMap, actualFlagsPosMap);
    }
    
    @Test
    public void extract_arguments_successful() {
        Flag nameFlag = new Flag(Flag.NAME, false);
        
        HashMap<Flag, String> expectedFlagsValueMap = new HashMap<Flag, String>();
        HashMap<Flag, String> actualFlagsValueMap = ExtractorUtil.getArguments(null, null, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments("-n name", null, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments("", null, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments(null, new Flag[] { }, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments("", new Flag[] { }, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments(null, new Flag[] { }, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments("-n name", new Flag[] { }, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        actualFlagsValueMap = ExtractorUtil.getArguments(null, new Flag[] { nameFlag }, null);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        expectedFlagsValueMap = new HashMap<Flag, String>();
        expectedFlagsValueMap.put(nameFlag, "");
        Flag[] flags = { nameFlag };
        TreeMap<Integer, Flag> flagsPosMap = new TreeMap<Integer, Flag>();
        flagsPosMap.put(-1, nameFlag);
        actualFlagsValueMap = ExtractorUtil.getArguments("-n name", flags, flagsPosMap);
        
        assertEquals(expectedFlagsValueMap, actualFlagsValueMap);
        
        flagsPosMap.put(7, nameFlag);
        
        actualFlagsValueMap = ExtractorUtil.getArguments("edit 1 -n name", flags, flagsPosMap);
        expectedFlagsValueMap.put(nameFlag, " -n name");
        
        assertEquals(actualFlagsValueMap, expectedFlagsValueMap);
    }
}
