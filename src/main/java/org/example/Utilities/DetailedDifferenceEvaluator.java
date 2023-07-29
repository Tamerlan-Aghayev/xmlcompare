package org.example.Utilities;

import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;

import java.util.List;

public class DetailedDifferenceEvaluator implements DifferenceEvaluator {

    private List<XMLModification> modifications;

    public DetailedDifferenceEvaluator (List<XMLModification> modifications) {
        this.modifications =modifications;}

    @Override
    public ComparisonResult evaluate (Comparison comparison, ComparisonResult outcome){
        if (outcome==ComparisonResult.DIFFERENT){

            String xpath = comparison.getControlDetails().getXPath () ;
            String expectedValue = String.valueOf(comparison.getControlDetails().getValue());
            String actualValue = String.valueOf (comparison.getTestDetails ().getValue() ) ;
            XMLModification modification = new XMLModification (xpath, expectedValue, actualValue);
            modifications.add (modification); }
        return outcome;
    }
}
