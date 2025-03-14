package org.example;

import java.util.List;

public class RuleEngine {
    private List<Rule> rules;

    // Constructor to initialize RuleEngine with a list of rules
    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    // Method to add a new rule to the list
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    // Method to process a list of input conditions and find a matching rule
    public String process(String[] inputs) {
        StringBuilder inputCondition = new StringBuilder();
        System.out.println("in rule engine:"+inputCondition);
        for (String input : inputs) {
            inputCondition.append(input).append(",");

        }

        // Remove trailing comma
        inputCondition.setLength(inputCondition.length() - 1);

        // Concatenate all input conditions into a single string separated by commas
        for (Rule rule : rules) {
            if (rule.getCondition().equals(inputCondition.toString())) {
                return rule.getDiagnosis();
            }
        }

        return "No diagnosis found.";
    }

    // Method to evaluate a single input condition string and find a matching rule
    public String evaluate(String inputCondition) {
        for (Rule rule : rules) {
            if (rule.getCondition().equals(inputCondition)) {
                System.out.println("Match found for condition: " + inputCondition);
                return rule.getDiagnosis();
            }
        }
        System.out.println("No match found for condition: " + inputCondition);
        return "No diagnosis found"; // Handle unmatched conditions
    }

}
