package org.example.Utilities;
public class XMLModification {

    private String expectedValue;
    private String actualValue;
    private String action;
    private String applicationId;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public XMLModification(String expectedValue, String actualValue, String action) {
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
        this.action = action;
    }
    public XMLModification(String expectedValue, String actualValue, String description, String applicationId, String action) {
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
        this.action = action;
        this.applicationId = applicationId;
        this.description=description;
    }

    public String getExpectedValue() {
        return expectedValue;
    }

    public String getActualValue() {
        return actualValue;
    }

    public String getAction() {
        return action;
    }

    public String getApplicationId() {
        return applicationId;
    }
}
