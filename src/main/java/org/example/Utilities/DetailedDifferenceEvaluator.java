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
            String xpath = comparison.getControlDetails().getXPath();
            String expectedValue = String.valueOf(comparison.getControlDetails().getValue());
            String actualValue = String.valueOf(comparison.getTestDetails().getValue());

            if (xpath == null) {
                xpath = comparison.getTestDetails().getXPath();
                modifications.add(new XMLModification(expectedValue, actualValue, "added", ""));
            } else {
                modifications.add(new XMLModification(expectedValue, actualValue, "deleted", ""));
            }
        }
        return outcome;
    }
}
