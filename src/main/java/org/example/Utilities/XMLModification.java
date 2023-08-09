package org.example.Utilities;

public class XMLModification {

    private String xpath;
    private String expectedValue;
    private String actualValue;
    private String description;
    private String action;

    private String applicationId;

    public XMLModification(String xpath, String expectedValue, String actualValue) {
        this.xpath = xpath;
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
    }

    public XMLModification(String xpath, String expectedValue, String actualValue, String description, String applicationId) {
        this.xpath = xpath;
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;

        this.description=description;
        this.applicationId=applicationId;

    }

    public String getXpath(){
        return xpath;
    }
    public String getExpectedValue(){
        return expectedValue;
    }
    public String getActualValue(){
        return actualValue;

    }
    public String getDescription(){
        return description;

    }
    public String getApplicationId(){
        return applicationId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
