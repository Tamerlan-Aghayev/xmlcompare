package org.example.Utilities;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;
import java.util.List;

public class DetailedDifferenceEvaluator implements DifferenceEvaluator {

    private List<XMLModification> modifications;

    public DetailedDifferenceEvaluator(List<XMLModification> modifications) {
        this.modifications = modifications;
    }

    @Override
    public ComparisonResult evaluate(Comparison comparison, ComparisonResult outcome) {
        if (outcome == ComparisonResult.DIFFERENT) {
            String expectedValue = String.valueOf(comparison.getControlDetails().getValue());
            String actualValue = String.valueOf(comparison.getTestDetails().getValue());
            String action = getActionFromDifference(comparison);

            String applicationID = extractApplicationID(comparison);

            modifications.add(new XMLModification(expectedValue, actualValue, action, applicationID));
        }
        return outcome;
    }

    private String getActionFromDifference(Comparison comparison) {
        Comparison.Detail controlDetail = comparison.getControlDetails();
        Comparison.Detail testDetail = comparison.getTestDetails();

        if (controlDetail != null && testDetail == null) {
            return "deleted";
        } else if (controlDetail == null && testDetail != null) {
            return "added";
        } else {
            return "";
        }
    }

    private String extractApplicationID(Comparison comparison) {
        // Your implementation to extract application ID based on the comparison
        // You may need to adapt this method based on your specific XML structure
        return "SampleApplicationID"; // Replace with the actual extracted application ID
    }
}

