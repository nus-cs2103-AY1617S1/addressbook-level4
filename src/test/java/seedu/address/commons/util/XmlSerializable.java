package seedu.address.commons.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;

@XmlRootElement
public class XmlSerializable {
    public static final String XML_STRING_REPRESENTATION =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<xmlSerializable>\n" +
            "    <name>This is a test class</name>\n" +
            "    <mapOfIntegerToString>\n" +
            "        <entry>\n" +
            "            <key>1</key>\n" +
            "            <value>One</value>\n" +
            "        </entry>\n" +
            "        <entry>\n" +
            "            <key>2</key>\n" +
            "            <value>Two</value>\n" +
            "        </entry>\n" +
            "        <entry>\n" +
            "            <key>3</key>\n" +
            "            <value>Three</value>\n" +
            "        </entry>\n" +
            "    </mapOfIntegerToString>\n" +
            "</xmlSerializable>\n";

    @XmlElement
    private String name = "This is a test class";

    @XmlElement
    private HashMap<Integer, String> mapOfIntegerToString;
    {
        mapOfIntegerToString = new HashMap<>();

        mapOfIntegerToString.put(1, "One");
        mapOfIntegerToString.put(2, "Two");
        mapOfIntegerToString.put(3, "Three");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof XmlSerializable)) {
            return false;
        }

        XmlSerializable otherCasted = (XmlSerializable) other;

        return name.equals(otherCasted.name)
            && mapOfIntegerToString.equals(otherCasted.mapOfIntegerToString);
    }
}