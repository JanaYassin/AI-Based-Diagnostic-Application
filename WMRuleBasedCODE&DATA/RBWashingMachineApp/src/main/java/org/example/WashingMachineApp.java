//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class WashingMachineApp extends Application {

    private ComboBox<Integer> cbUsePerWeek;
    private ComboBox<Integer> cbPreviousRepair;
    private ComboBox<Integer> cbReplacement;
    private ComboBox<String> cbFaultType1;
    private ComboBox<String> cbFaultType2;
    private ComboBox<String> cbFaultType3;
    private Label lblResult;
    private RuleEngine ruleEngine;

    public WashingMachineApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Washing Machine Fault Diagnosis");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10.0, 10.0, 10.0, 10.0));
        grid.setVgap(8.0);
        grid.setHgap(10.0);

        cbUsePerWeek = new ComboBox<>();
        cbUsePerWeek.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7)); // Add values from 1 to 7
        cbUsePerWeek.setValue(1); // Set default value to 1
        cbUsePerWeek.setEditable(false); // Make sure it's not editable to restrict input to the listed values

        Label lblUsePerWeek = new Label("Use (per week):");
        GridPane.setConstraints(lblUsePerWeek, 0, 2);
        GridPane.setConstraints(this.cbUsePerWeek, 1, 2);

        cbPreviousRepair = new ComboBox<>();
        cbPreviousRepair.setItems(FXCollections.observableArrayList(0, 1, 2, 3)); // Add values 0 to 3
        cbPreviousRepair.setValue(0); // Default value
        cbPreviousRepair.setEditable(false); // Disable manual editing
        Label lblPreviousRepair = new Label("Previous repair:");
        GridPane.setConstraints(lblPreviousRepair, 0, 3);
        GridPane.setConstraints(this.cbPreviousRepair, 1, 3);

        // Replacement
        cbReplacement = new ComboBox<>();
        cbReplacement.setItems(FXCollections.observableArrayList(0, 1, 2, 3)); // Add values 0 to 4
        cbReplacement.setValue(0); // Default value
        cbReplacement.setEditable(false); // Disable manual editing
        Label lblReplacement = new Label("Replacement:");
        GridPane.setConstraints(lblReplacement, 0, 5);
        GridPane.setConstraints(this.cbReplacement, 1, 5);
        this.cbFaultType1 = new ComboBox();
        this.cbFaultType2 = new ComboBox();
        this.cbFaultType3 = new ComboBox();
        this.cbFaultType1.setEditable(false);
        this.cbFaultType2.setEditable(false);
        this.cbFaultType3.setEditable(false);
        List<String> faultTypes = List.of("Inlet valves", "Shock absorbers and bearings", "Cables and plugs", "Carbon brushes",
                "Pumps", "Electronics", "Doors", "Detergent system", "Switches", "Foreign objects",
                "Engine", "Heater and thermostats", "Pressure control", "Drain system", "Pump filters",
                "Drive belt", "Drum and tub", "Other");
        this.cbFaultType1.getItems().addAll(faultTypes);
        this.cbFaultType2.getItems().addAll(faultTypes);
        this.cbFaultType3.getItems().addAll(faultTypes);
        Label lblFaultType1 = new Label("Fault type 1:");
        GridPane.setConstraints(lblFaultType1, 0, 10);
        GridPane.setConstraints(this.cbFaultType1, 1, 10);
        Label lblFaultType2 = new Label("Fault type 2:");
        GridPane.setConstraints(lblFaultType2, 0, 11);
        GridPane.setConstraints(this.cbFaultType2, 1, 11);
        Label lblFaultType3 = new Label("Fault type 3:");
        GridPane.setConstraints(lblFaultType3, 0, 12);
        GridPane.setConstraints(this.cbFaultType3, 1, 12);

        this.cbUsePerWeek.setPrefWidth(300.0);
        this.cbPreviousRepair.setPrefWidth(300.0);
        this.cbReplacement.setPrefWidth(300.0);
        this.cbFaultType1.setPrefWidth(300.0);
        this.cbFaultType2.setPrefWidth(300.0);
        this.cbFaultType3.setPrefWidth(300.0);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(300.0);
        grid.getColumnConstraints().add(column1);
        Button btnDiagnose = new Button("Diagnose");
        GridPane.setConstraints(btnDiagnose, 1, 13);
        btnDiagnose.setOnAction((e) -> {
            this.diagnoseFault();
            this.resetInputs();
        });
        this.lblResult = new Label("Result:");
        this.lblResult.setPrefWidth(400.0);
        this.lblResult.setMaxWidth(400.0);
        this.lblResult.setWrapText(true);
        GridPane.setConstraints(this.lblResult, 1, 14);
        grid.getChildren().addAll(new Node[]{ lblUsePerWeek, this.cbUsePerWeek, lblPreviousRepair, this.cbPreviousRepair, lblReplacement, this.cbReplacement, lblFaultType1, this.cbFaultType1, lblFaultType2, this.cbFaultType2, lblFaultType3, this.cbFaultType3, btnDiagnose, this.lblResult});
        Scene scene = new Scene(grid, 800.0, 600.0);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.loadRulesFromCSV();
        AccuracyCalculator accuracyCalculator = new AccuracyCalculator(this.ruleEngine);
        accuracyCalculator.calculateMetrics("C:/Users/USER/Desktop/MyProject/codeUpdate/WMRuleBasedCODE&DATA/WashingMachineData.csv");
    }

    private void loadRulesFromCSV() {
        try {
            String csvFile = "C:/Users/USER/Desktop/MyProject/codeUpdate/WMRuleBasedCODE&DATA/WashingMachineData.csv";
            File file = new File(csvFile);
            if (!file.exists()) {
                System.out.println("CSV file not found.");
                return;
            }

            if (file.length() == 0L) {
                System.out.println("CSV file is empty.");
                return;
            }

            Reader in = new FileReader(csvFile);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            if (!records.iterator().hasNext()) {
                System.out.println("No records found in the CSV file.");
                return;
            }

            System.out.println("CSV file read successfully.");
            List<Rule> rules = new ArrayList();
            this.ruleEngine = new RuleEngine(rules);
            Iterator var6 = records.iterator();

            while(var6.hasNext()) {
                CSVRecord record = (CSVRecord)var6.next();
                StringBuilder condition = new StringBuilder();

                for(int i = 0; i < record.size() - 1; ++i) {
                    condition.append(record.get(i).trim()).append(" ");
                }

                String diagnosis = record.get(record.size() - 1);
                Rule rule = new Rule(condition.toString().trim(), diagnosis);
                rules.add(rule);
                System.out.println("Condition: " + condition.toString().trim());
                System.out.println("Diagnosis: " + diagnosis);
                System.out.println();
            }

            System.out.println("Rules:");
            var6 = rules.iterator();

            while(var6.hasNext()) {
                Rule rule = (Rule)var6.next();
                System.out.println(rule);
            }
        } catch (Exception var11) {
            Exception e = var11;
            e.printStackTrace();
            System.out.println("Exception occurred while reading the CSV file.");
        }

    }

    // Method to reset inputs to their default values
    private void resetInputs() {
        // Reset ComboBoxes to default values
        cbUsePerWeek.setValue(1); // Default: 1
        cbPreviousRepair.setValue(0); // Default: 0
        cbReplacement.setValue(0); // Default: 0
        cbFaultType1.setValue(null); // Clear selection
        cbFaultType2.setValue(null); // Clear selection
        cbFaultType3.setValue(null); // Clear selection

    }

    private void diagnoseFault() {
        try {
            int usePerWeek = cbUsePerWeek.getValue();
            int previousRepair = cbPreviousRepair.getValue();
            int replacement = cbReplacement.getValue();
            String faultType1 = (String)this.cbFaultType1.getValue();
            String faultType2 = (String)this.cbFaultType2.getValue();
            String faultType3 = (String)this.cbFaultType3.getValue();
            System.out.println("Use Per Week: " + usePerWeek);
            System.out.println("Previous Repair: " + previousRepair);
            System.out.println("Replacement: " + replacement);
            System.out.println("Fault Type 1: " + faultType1);
            System.out.println("Fault Type 2: " + faultType2);
            System.out.println("Fault Type 3: " + faultType3);
            List<String> faultTypes = List.of("Inlet valves", "Shock absorbers and bearings", "Cables and plugs", "Carbon brushes",
                    "Pumps", "Electronics", "Doors", "Detergent system", "Switches", "Foreign objects",
                    "Engine", "Heater and thermostats", "Pressure control", "Drain system", "Pump filters",
                    "Drive belt", "Drum and tub", "Other");
            Map<String, Integer> faultTypeMap = new HashMap();
            Iterator var16 = faultTypes.iterator();

            String diagnosis;
            while(var16.hasNext()) {
                diagnosis = (String)var16.next();
                faultTypeMap.put(diagnosis, 0);
            }

            if (faultType1 != null) {
                faultTypeMap.put(faultType1, 1);
            }

            if (faultType2 != null) {
                faultTypeMap.put(faultType2, 1);
            }

            if (faultType3 != null) {
                faultTypeMap.put(faultType3, 1);
            }

            StringBuilder inputConditions = new StringBuilder();

            inputConditions.append(usePerWeek).append(" ").append(previousRepair).append(" ").append(replacement).append(" ");
            Iterator var22 = faultTypes.iterator();

            while(var22.hasNext()) {
                String faultType = (String)var22.next();
                inputConditions.append(faultTypeMap.get(faultType)).append(" ");
            }

            System.out.println("Input Conditions: " + inputConditions);
            diagnosis = this.ruleEngine.evaluate(inputConditions.toString().trim());

            // Split the diagnosis into parts using "_" as a delimiter
            String[] outputs = diagnosis.split("_");

            // Check if the split result has exactly three parts
            if (outputs.length == 3) {
                String fault = outputs[0];
                String technicallyInfeasible = outputs[1];
                String economicallyNonViable = outputs[2];

                // Conditional explanations
                String technicallyInfeasibleExplanation = "This fault is repairable with the available technical resources.";
                if ("Yes".equalsIgnoreCase(technicallyInfeasible)) {
                    technicallyInfeasibleExplanation = "This fault cannot be repaired due to technical constraints such as the unavailability of spare parts, lack of expertise, or design limitations of the appliance. Consider replacing the appliance.";
                }

                String economicallyNonViableExplanation = "The cost of repairing this fault is economically justifiable.";
                if ("Yes".equalsIgnoreCase(economicallyNonViable)) {
                    economicallyNonViableExplanation = "The cost of repairing this fault exceeds the value of the appliance or a reasonable replacement cost. It is recommended to consider purchasing a new appliance.";
                }

                // Design the output using Text objects
                Text faultText = new Text("Fault: ");
                faultText.setFont(Font.font("Arial", 16));
                faultText.setStyle("-fx-font-weight: bold;");
                faultText.setFill(javafx.scene.paint.Color.RED);

                Text faultValue = new Text(fault + "\n");

                Text technicallyInfeasibleText = new Text("Technically Infeasible: ");
                technicallyInfeasibleText.setFont(Font.font("Arial", 16));
                technicallyInfeasibleText.setStyle("-fx-font-weight: bold;");
                technicallyInfeasibleText.setFill(javafx.scene.paint.Color.RED); // Set text color to red

                Text technicallyInfeasibleValue = new Text(technicallyInfeasible + "\n");

                Text technicallyInfeasibleExplanationText = new Text("Explanation: ");
                technicallyInfeasibleExplanationText.setFont(Font.font("Arial", 14));
                technicallyInfeasibleExplanationText.setStyle("-fx-font-weight: bold;");

                Text technicallyInfeasibleExplanationValue = new Text(technicallyInfeasibleExplanation + "\n\n");

                Text economicallyNonViableText = new Text("Economically Non-Viable: ");
                economicallyNonViableText.setFont(Font.font("Arial", 16));
                economicallyNonViableText.setStyle("-fx-font-weight: bold;");
                economicallyNonViableText.setFill(javafx.scene.paint.Color.RED); // Set text color to red

                Text economicallyNonViableValue = new Text(economicallyNonViable + "\n");
                Text economicallyNonViableExplanationText = new Text("Explanation: ");
                economicallyNonViableExplanationText.setFont(Font.font("Arial", 14));
                economicallyNonViableExplanationText.setStyle("-fx-font-weight: bold;");

                Text economicallyNonViableExplanationValue = new Text(economicallyNonViableExplanation);

                // Combine everything into a TextFlow
                TextFlow outputFlow = new TextFlow(
                        faultText, faultValue,
                        technicallyInfeasibleText, technicallyInfeasibleValue,
                        technicallyInfeasibleExplanationText, technicallyInfeasibleExplanationValue,
                        economicallyNonViableText, economicallyNonViableValue,
                        economicallyNonViableExplanationText, economicallyNonViableExplanationValue
                );

                // Set width and enable wrapping for the TextFlow
                outputFlow.setPrefWidth(400); // Set preferred width
                outputFlow.setMaxWidth(400); // Set maximum width
                outputFlow.setStyle("-fx-padding: 10;"); // Optional: Add padding

                // Ensure text wraps within the set width
                for (Node textNode : outputFlow.getChildren()) {
                    if (textNode instanceof Text) {
                        ((Text) textNode).setWrappingWidth(380); // Set wrapping width slightly smaller than the TextFlow width
                    }
                }

                // Add the TextFlow to a Label or Scene
                this.lblResult.setGraphic(outputFlow);
                this.lblResult.setText(""); // Clear plain text
            } else {
                // Handle invalid diagnosis format gracefully
                this.lblResult.setText("Error: Diagnosis format is invalid.");
            }

            // Optional: Print for debugging
            System.out.println("Diagnosis: " + diagnosis);

        } catch (Exception var19) {
            Exception e = var19;
            e.printStackTrace();
            this.lblResult.setText("Error during diagnosis.");
        }

    }
}
