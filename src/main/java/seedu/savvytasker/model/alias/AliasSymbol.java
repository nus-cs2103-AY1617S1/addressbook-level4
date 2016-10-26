//@@author A0139916U
package seedu.savvytasker.model.alias;

import java.util.Objects;

/**
 * A class that represents a keyword-representation pair. This pair can be used 
 * by the parser for substituting keywords found in a commandText with its
 * representation. Instances of this class are immutable.
 */
public class AliasSymbol {
    private final String keyword;
    private final String representation;
    
    /**
     * Creates a Symbol object with the specified keyword and its representation.
     * The keyword must be a single word without spaces while the representation
     * can be any non-empty string. Both parameters cannot be null.
     * 
     * @param keyword
     * @param representation
     */
    public AliasSymbol(String keyword, String representation) {
        assert keyword != null && !keyword.matches(".*\\s+.*");
        assert representation != null && !representation.isEmpty();
        
        this.keyword = keyword;
        this.representation = representation;
    }
    
    public String getKeyword() {
        return this.keyword;
    }
    
    public String getRepresentation() {
        return this.representation;
    }
    
    @Override
    public String toString() {
        return "[Keyword: " + this.keyword + ", Representation: " + this.representation + "]";
    }

    @Override
    public boolean equals(Object object) {
        return this == object || (object instanceof AliasSymbol &&
                this.keyword.equals(((AliasSymbol)object).keyword) &&
                this.representation.equals(((AliasSymbol)object).representation));
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(keyword, representation);
    }
}
