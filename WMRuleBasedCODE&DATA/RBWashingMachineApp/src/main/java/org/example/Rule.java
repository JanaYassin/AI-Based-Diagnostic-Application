package org.example;

public class Rule {
    private String condition;
    private String diagnosis;

    public Rule(String condition, String diagnosis) {
        this.condition = condition;
        this.diagnosis = diagnosis;
    }

    public String getCondition() {
        return condition;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
}
