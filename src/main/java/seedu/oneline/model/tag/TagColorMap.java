package seedu.oneline.model.tag;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class TagColorMap {
    
    ObservableMap<Tag, TagColor> colorMap = FXCollections.observableHashMap();
    
    public TagColorMap() {
    }
    
    public TagColorMap(Map<Tag, TagColor> colorMap) {
        this.colorMap.putAll(colorMap);
    }
    
    public ObservableMap<Tag, TagColor> getInternalMap() {
        return colorMap;
    }
    
    public TagColor getTagColor(Tag tag) {
        if (!colorMap.containsKey(tag)) {
            return TagColor.getDefault();
        }
        return colorMap.get(tag);
    }
    
    public void setTagColor(Tag tag, TagColor color) {
        if (color.equals(TagColor.getDefault())) {
            colorMap.remove(tag);
            return;
        }
        colorMap.put(tag, color);
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagColorMap // instanceof handles nulls
                && this.colorMap.equals(
                ((TagColorMap) other).colorMap));
    }

    @Override
    public int hashCode() {
        return colorMap.hashCode();
    }
}
