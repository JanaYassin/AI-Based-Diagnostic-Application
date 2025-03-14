package org.example;

public class Rule {
    private String condition;
    private String diagnosis;

    // Constructor to initialize the rule with a condition and diagnosis
    public Rule(String condition, String diagnosis) {
        this.condition = condition;
        this.diagnosis = diagnosis;
    }

    // Getter method to retrieve the condition of the rule
    public String getCondition() {
        return condition;
    }

    // Getter method to retrieve the diagnosis of the rule
    public String getDiagnosis() {
        return diagnosis;
    }
}
