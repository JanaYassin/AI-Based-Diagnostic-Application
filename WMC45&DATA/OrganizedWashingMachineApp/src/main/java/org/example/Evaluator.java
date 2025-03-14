package org.example;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Debug;
import weka.core.Instances;

public class Evaluator {

    private Instances data;
    private J48 tree; // Single tree for evaluation

    public Evaluator(Instances data, J48 tree) {
        this.data = data;
        this.tree = tree;
    }

    public void evaluateModel() {
        try {
            System.out.println("\nEvaluating the Decision Tree:");

            // Perform 10-fold cross-validation
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(tree, data, 10, new Debug.Random(1));

            // Output basic evaluation metrics
            double accuracy = eval.pctCorrect();
            System.out.printf("Accuracy: %.2f%%\n", accuracy);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println(eval.toMatrixString("\nConfusion Matrix\n======\n"));
            System.out.println(eval.toClassDetailsString("\nClass Details\n======\n"));

            // Extract confusion matrix values for detailed metrics calculation
            double[][] confusionMatrix = eval.confusionMatrix();

            double TP = eval.numTruePositives(1);
            double TN = eval.numTrueNegatives(1);
            double FP = eval.numFalsePositives(1);
            double FN = eval.numFalseNegatives(1);

            double total = TP + TN + FP + FN;

            // Calculating percentages
            double TP_pct = (TP / total) * 100;
            double TN_pct = (TN / total) * 100;
            double FP_pct = (FP / total) * 100;
            double FN_pct = (FN / total) * 100;

            // Printing confusion matrix values as percentages
            System.out.printf("Confusion Matrix Values (in %%):\n");
            System.out.printf("TP (True Positives): %.2f%%\n", TP_pct);
            System.out.printf("TN (True Negatives): %.2f%%\n", TN_pct);
            System.out.printf("FP (False Positives): %.2f%%\n", FP_pct);
            System.out.printf("FN (False Negatives): %.2f%%\n", FN_pct);

            // Printing metrics as percentages
            double precision = eval.precision(1);
            double recall = eval.recall(1);
            double f1Score = eval.fMeasure(1);
            double specificity = TN / (TN + FP);

            System.out.printf("\nDetailed Metrics (in %%):\n");
            System.out.printf("Accuracy: %.2f%%\n", accuracy);
            System.out.printf("Precision: %.2f%%\n", precision * 100);
            System.out.printf("Recall: %.2f%%\n", recall * 100);
            System.out.printf("F1-Score: %.2f%%\n", f1Score * 100);
            System.out.printf("Specificity: %.2f%%\n", specificity * 100);


            // Overall Confusion Matrix Table
            System.out.println("\nOverall Confusion Matrix Table Format:");
            System.out.printf("%-15s %-15s %-15s %-15s\n", "Metric", "Positive (1)", "Negative (0)", "Total");
            System.out.printf("%-15s %-15.2f %-15.2f %-15.2f\n", "Predicted Pos", eval.numTruePositives(1), eval.numFalsePositives(1), (eval.numTruePositives(1) + eval.numFalsePositives(1)));
            System.out.printf("%-15s %-15.2f %-15.2f %-15.2f\n", "Predicted Neg", eval.numFalseNegatives(1), eval.numTrueNegatives(1), (eval.numFalseNegatives(1) + eval.numTrueNegatives(1)));
            System.out.printf("%-15s %-15.2f %-15.2f %-15.2f\n", "Total", (eval.numTruePositives(1) + eval.numFalseNegatives(1)), (eval.numFalsePositives(1) + eval.numTrueNegatives(1)), eval.numInstances());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
