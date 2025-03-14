package org.example;

import java.util.List;

public class RuleEngine {
    private List<Rule> rules;

    // Constructor to initialize the rule engine with a list of rules
    public RuleEngine(List<Rule> rules) {
        this.rules = rules;
    }

    // Method to add a new rule to the rule engine
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    // Check each rule to find a match for the input condition
    public String evaluate(String inputCondition) {
        for (Rule rule : rules) {
            String[] ruleConditions = rule.getCondition().split(" ");
            String[] inputConditions = inputCondition.split(" ");

//            System.out.println("Input Condition: " + inputConditions);
//            System.out.println("Rule Condition: " + rule.getCondition());
            // Check if each condition matches
            boolean allConditionsMatch = true;
            for (int i = 0; i < ruleConditions.length; i++) {
                if (!ruleConditions[i].equalsIgnoreCase(inputConditions[i])) {
                    allConditionsMatch = false;
                    break;
                }
            }

            if (allConditionsMatch) {
                return rule.getDiagnosis();
            }
        }
        return "No diagnosis found.";
    }
}
