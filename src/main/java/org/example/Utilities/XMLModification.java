package org.example.Utilities;
public class XMLModification {

    private String expectedValue;
    private String actualValue;
    private String action;
    private String applicationId;

    public XMLModification(String expectedValue, String actualValue, String action, String applicationId) {
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
        this.action = action;
        this.applicationId = applicationId;
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
