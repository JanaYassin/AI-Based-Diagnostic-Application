package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class AccuracyCalculator {

    private RuleEngine ruleEngine;

    // Constructor to initialize the RuleEngine instance
    public AccuracyCalculator(RuleEngine ruleEngine) {
        this.ruleEngine = ruleEngine;
    }

    // Method to calculate various metrics from the CSV file
    public void calculateMetrics(String csvFilePath) {
        try {
            // Reading the CSV file
            Reader in = new FileReader(csvFilePath);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);

            int totalRecords = 0;

            // Initialize confusion matrix counters
            int tp = 0; // True Positives
            int fp = 0; // False Positives
            int fn = 0; // False Negatives
            int tn = 0; // True Negatives

            // Iterate through the records
            for (CSVRecord record : records) {
                totalRecords++;

                StringBuilder condition = new StringBuilder();
                for (int i = 0; i < record.size() - 1; i++) {
                    condition.append(record.get(i).trim()).append(" ");
                }

                String actualDiagnosis = record.get(record.size() - 1).trim(); // Actual label from CSV
                String predictedDiagnosis = ruleEngine.evaluate(condition.toString().trim()); // Predicted by RuleEngine

                // Debugging output
                System.out.println("Actual Diagnosis: " + actualDiagnosis);
                System.out.println("Predicted Diagnosis: " + predictedDiagnosis);

                // Update confusion matrix components
                if (actualDiagnosis.equals(predictedDiagnosis)) {
                    // Correct prediction
                    tp++; // True Positive
                } else {
                    // Incorrect prediction
                    if (predictedDiagnosis.equals("Positive")) {
                        fp++; // False Positive
                    } else if (actualDiagnosis.equals("Positive")) {
                        fn++; // False Negative
                    }
                }

                // Update True Negatives
                if (!actualDiagnosis.equals("Positive") && !predictedDiagnosis.equals("Positive")) {
                    tn++;
                }
            }

            // Compute True Negatives (if applicable)
            tn = totalRecords - tp - fp - fn; // Ensure this is valid for the given dataset

            // Calculate percentages
            double tpPercentage = (double) tp / totalRecords * 100;
            double fpPercentage = (double) fp / totalRecords * 100;
            double tnPercentage = (double) tn / totalRecords * 100;
            double fnPercentage = (double) fn / totalRecords * 100;

            // Print results
            System.out.println("Confusion Matrix (as percentages of total records):");
            System.out.println(String.format("TP: %.2f%%, FP: %.2f%%, TN: %.2f%%, FN: %.2f%%", tpPercentage, fpPercentage, tnPercentage, fnPercentage));

            System.out.println("Global Confusion Matrix:");
            System.out.println("--------------------------------------------------");
            System.out.println("|           | Predicted Positive | Predicted Negative |");
            System.out.println("--------------------------------------------------");
            System.out.println(String.format("| Actual Positive | TP: %-14d | FN: %-15d |", tp, fn));
            System.out.println("--------------------------------------------------");
            System.out.println(String.format("| Actual Negative | FP: %-14d | TN: %-15d |", fp, tn));
            System.out.println("--------------------------------------------------");
            // Accuracy
            double accuracy = (double) (tp + tn) / totalRecords * 100;
            System.out.println("Accuracy: " + String.format("%.2f", accuracy) + "%");

            // Precision, Recall, and F1-Score
            double precision = tp + fp > 0 ? (double) tp / (tp + fp) : 0;
            double recall = tp + fn > 0 ? (double) tp / (tp + fn) : 0;
            double f1Score = (precision + recall) > 0 ? 2 * (precision * recall) / (precision + recall) : 0;

            System.out.println(String.format("Precision: %.2f%%", precision * 100));
            System.out.println(String.format("Recall: %.2f%%", recall * 100));
            System.out.println(String.format("F1 Score: %.2f%%", f1Score * 100));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurred while calculating accuracy.");
        }
    }
}
