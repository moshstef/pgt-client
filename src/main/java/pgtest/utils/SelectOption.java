package pgtest.utils;

import java.io.Serializable;

public class SelectOption implements Serializable {
    String label;
    String value;
    String description;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SelectOption(String label, String value, String description) {
        this.label = label;
        this.value = value;
        this.description = description;
    }

    public SelectOption() {
    }

    @Override
    public String toString() {
        return Utilities.objToJsonString(this);
    }
}
