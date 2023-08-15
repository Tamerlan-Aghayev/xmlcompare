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
        if (outcome == ComparisonResult.DIFFERENT || outcome == ComparisonResult.SIMILAR) {
            String expectedValue = String.valueOf(comparison.getControlDetails().getValue());
            String actualValue = String.valueOf(comparison.getTestDetails().getValue());
            String action = "";

            if (outcome == ComparisonResult.DIFFERENT) {
                action = "modified";
            } else if (outcome == ComparisonResult.SIMILAR) {
                action = "added";
            }

            modifications.add(new XMLModification(expectedValue, actualValue, action, null));
        }
        return outcome;
    }
}
