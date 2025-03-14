//package org.example;
//
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVRecord;
//
//import java.io.FileReader;
//import java.io.Reader;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//        try {
//            // Read CSV file
//            String csvFile = "C:/Users/USER/Desktop/MyProject/ProjectData/VCData/AugmentedVacuumCleanerData651.csv";
//            Reader in = new FileReader(csvFile);
//            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
//            System.out.println(records);
//
//            // Create rule engine
//            List<Rule> rules = new ArrayList<>();
//            RuleEngine ruleEngine = new RuleEngine(rules);
//
//            // Generate rules from CSV data
//            for (CSVRecord record : records) {
//                StringBuilder condition = new StringBuilder();
//                for (int i = 0; i < record.size() - 1; i++) {
//                    condition.append(record.get(i).trim()).append(",");
//                }
//                // Remove trailing comma
//                condition.setLength(condition.length() - 1);
//
//                // Assuming last column is the action
//                String action = record.get(record.size() - 1);
//                ruleEngine.addRule(new Rule(condition.toString(), action));
//            }
//
//            // Example input
//            String[] inputs = {"High", "Yes", "30", "Yes", "Yes", "No", "Needs Repair", "Needs deep cleaning", "Low", "Yes", "Overheating", "Needs Cleaning", "High"};
//
//            // Process input through rule engine
//            String result = ruleEngine.process(inputs);
//
//            System.out.println("Diagnosis result: " + result);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
