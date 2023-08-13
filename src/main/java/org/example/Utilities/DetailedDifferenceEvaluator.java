package org.example.Utilities;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.DifferenceEvaluator;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


            modifications.add(new XMLModification(expectedValue, actualValue, action));
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

    private String extractApplicationID(String xPath, Source source) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(source.getSystemId()));

            Pattern pattern = Pattern.compile("LoanApplication\\[\\d+\\]");
            Matcher matcher = pattern.matcher(xPath);
            if (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String xPathRoot = xPath.substring(0, end);
                xPathRoot += "/applicationID";
                XPathEngine xPathEngine = new JAXPXPathEngine();
                return xPathEngine.evaluate(xPathRoot, document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

