package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class VacuumCleanerApp extends Application {

    private ComboBox<String> cbWhistlingSound;
    private ComboBox<String> cbUsageDuration;
    private ComboBox<String> cbTubeClogStatus;
    private ComboBox<String> cbRepairStatus;
    private ComboBox<String> cbCleaningStatus;
    private ComboBox<String> cbSuctionEfficiency;
    private ComboBox<String> cbHoseRepairStatus;
    private ComboBox<String> cbMotorCondition;
    private ComboBox<String> cbNoiseIntensity;
    private Label lblResult;
    private RuleEngine ruleEngine;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Vacuum Cleaner Fault Diagnosis");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        cbWhistlingSound = new ComboBox<>();
        cbWhistlingSound.getItems().addAll("Yes", "No");
        Label lblWhistlingSound = new Label("Whistling Sound:");
        GridPane.setConstraints(lblWhistlingSound, 0, 1);
        GridPane.setConstraints(cbWhistlingSound, 1, 1);

        cbUsageDuration = new ComboBox<>();
        cbUsageDuration.getItems().addAll("1-2 (Short)", "3-5 (Medium)", "6-7 (Long)");
        cbUsageDuration.setValue("1-2 (Short)"); // Default value

        Label lblUsageDuration = new Label("Usage Duration:");
        GridPane.setConstraints(lblUsageDuration, 0, 2);
        GridPane.setConstraints(cbUsageDuration, 1, 2);

        cbTubeClogStatus = new ComboBox<>();
        cbTubeClogStatus.getItems().addAll("Clogged", "Not Clogged");
        Label lblTubeClogStatus = new Label("Tube Clog Status:");
        GridPane.setConstraints(lblTubeClogStatus, 0, 3);
        GridPane.setConstraints(cbTubeClogStatus, 1, 3);

        cbRepairStatus = new ComboBox<>();
        cbRepairStatus.setEditable(false);  // Make the ComboBox not editable
        cbRepairStatus.getItems().addAll(
                "Cleaned pipe, disassembled switch",
                "Replaced filters, cleaned pipes",
                "Disconnected sensor",
                "Fixed winder cord",
                "Cleared hose and tube clogs",
                "Needs Repair",
                "No Repair Needed",
                "Disassembled, reassembled",
                "Removed screws, unclipped device",
                "Motor brushes need changing",
                "Replace battery and card",
                "Fixed battery wire",
                "Replace battery and motor",
                "Check charging circuit",
                "Sensor issue",
                "Hidden screws",
                "Issue with hood clips",
                "Replace central filter",
                "Open vacuum cleaner",
                "Suspected switch issue",
                "Check bag compartment",
                "Bad reel contact",
                "Under warranty",
                "Needs cord replacement",
                "Needs inspection",
                "Needs repair",
                "Needs hose repair",
                "Needs filter replacement",
                "Checked pipe and cleaned filter",
                "Checked unclogged elbows",
                "Checked hose and air inlet",
                "Return to service",
                "Send photos for diagnosis",
                "Inspect hose and inlet",
                "Check pipe for splits",
                "Check nozzle and clean tank/filter",
                "Check motor with repairer"
        );
        Label lblRepairStatus = new Label("Repair Status:");
        GridPane.setConstraints(lblRepairStatus, 0, 4);
        GridPane.setConstraints(cbRepairStatus, 1, 4);

        cbCleaningStatus = new ComboBox<>();
        cbCleaningStatus.setEditable(false);
        cbCleaningStatus.getItems().addAll("Needs deep cleaning", "Need Regular cleaning", "Cleaned");
        Label lblCleaningStatus = new Label("Cleaning Status:");
        GridPane.setConstraints(lblCleaningStatus, 0, 5);
        GridPane.setConstraints(cbCleaningStatus, 1, 5);

        cbSuctionEfficiency = new ComboBox<>();
        cbSuctionEfficiency.getItems().addAll("Low", "Normal", "Medium", "High");
        Label lblSuctionEfficiency = new Label("Suction Efficiency:");
        GridPane.setConstraints(lblSuctionEfficiency, 0, 6);
        GridPane.setConstraints(cbSuctionEfficiency, 1, 6);

        cbHoseRepairStatus = new ComboBox<>();
        cbHoseRepairStatus.getItems().addAll("Yes", "No");
        Label lblHoseRepairStatus = new Label("Hose Repair Status:");
        GridPane.setConstraints(lblHoseRepairStatus, 0, 7);
        GridPane.setConstraints(cbHoseRepairStatus, 1, 7);

        cbMotorCondition = new ComboBox<>();
        cbMotorCondition.setEditable(false);
        cbMotorCondition.getItems().addAll("Good", "Overheating", "Clogged", "Burnt Smell", "Worn out", "Needs Attention");
        Label lblMotorCondition = new Label("Motor Condition:");
        GridPane.setConstraints(lblMotorCondition, 0, 8);
        GridPane.setConstraints(cbMotorCondition, 1, 8);

        cbNoiseIntensity = new ComboBox<>();
        cbNoiseIntensity.getItems().addAll("Low", "Normal", "Medium", "High", "Very High");
        Label lblNoiseIntensity = new Label("Noise Intensity:");
        GridPane.setConstraints(lblNoiseIntensity, 0, 9);
        GridPane.setConstraints(cbNoiseIntensity, 1, 9);

        Button btnDiagnose = new Button("Diagnose");
        btnDiagnose.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: Grey; -fx-text-fill: white;");
        GridPane.setConstraints(btnDiagnose, 1, 12);
        btnDiagnose.setOnAction((e) -> {
            this.diagnoseFault();
            this.resetInputs();
        });

        lblResult = new Label("Result: ");
        lblResult.setStyle("-fx-font-size: 16px; -fx-text-fill: black; -fx-font-weight: bold;");
        GridPane.setConstraints(lblResult, 1, 13);

        grid.getChildren().addAll(
                lblWhistlingSound, cbWhistlingSound,
                lblUsageDuration, cbUsageDuration,
                lblTubeClogStatus, cbTubeClogStatus,
                lblRepairStatus, cbRepairStatus,
                lblCleaningStatus, cbCleaningStatus,
                lblSuctionEfficiency, cbSuctionEfficiency,
                lblHoseRepairStatus, cbHoseRepairStatus,
                lblMotorCondition, cbMotorCondition,
                lblNoiseIntensity, cbNoiseIntensity,
                btnDiagnose, lblResult
        );

        cbWhistlingSound.setPrefWidth(300);
        cbUsageDuration.setPrefWidth(300);
        cbTubeClogStatus.setPrefWidth(300);
        cbRepairStatus.setPrefWidth(300);
        cbCleaningStatus.setPrefWidth(300);
        cbSuctionEfficiency.setPrefWidth(300);
        cbHoseRepairStatus.setPrefWidth(300);
        cbMotorCondition.setPrefWidth(300);
        cbNoiseIntensity.setPrefWidth(300);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(300);
        grid.getColumnConstraints().add(column1);

        Scene scene = new Scene(grid, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        loadRulesFromCSV();
        AccuracyCalculator accuracyCalculator = new AccuracyCalculator(ruleEngine);
        accuracyCalculator.calculateMetrics("C:/Users/USER/Desktop/MyProject/codeUpdate/VCRuleBasedCODE&DATA/WordBasedVacuumCleanerData_330Instances.csv");
    }

    private void loadRulesFromCSV() {
        try {
            // Read CSV file
            String csvFile = "C:/Users/USER/Desktop/MyProject/codeUpdate/VCRuleBasedCODE&DATA/WordBasedVacuumCleanerData_330Instances.csv";
            File file = new File(csvFile);
            if (!file.exists()) {
                System.out.println("CSV file not found.");
                return;
            } else if (file.length() == 0) {
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

            // Create rule engine
            List<Rule> rules = new ArrayList<>();
            ruleEngine = new RuleEngine(rules);

            // Generate rules from CSV data
            for (CSVRecord record : records) {
                System.out.println("Processing record: " + record);
                StringBuilder condition = new StringBuilder();
                for (int i = 0; i < record.size() - 1; i++) {
                    condition.append(record.get(i).trim()).append(" ");
                }
                String diagnosis = record.get(record.size() - 1);
                Rule rule = new Rule(condition.toString().trim(), diagnosis);
                rules.add(rule);

                // Print the constructed condition and diagnosis with better formatting
                System.out.println("Condition: " + condition.toString().trim());
                System.out.println("Diagnosis: " + diagnosis);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurred while reading the CSV file.");
        }
    }

    private void diagnoseFault() {
        try {
            String whistlingSound = cbWhistlingSound.getValue().toLowerCase();;
            Integer usageDuration = mapUsageDuration(cbUsageDuration.getValue());;
            String tubeClogStatus = cbTubeClogStatus.getValue().toLowerCase();;
            String repairStatus = cbRepairStatus.getValue().toLowerCase();;
            String cleaningStatus = cbCleaningStatus.getValue().toLowerCase();;
            String suctionEfficiency = cbSuctionEfficiency.getValue().toLowerCase();;
            String hoseRepairStatus = cbHoseRepairStatus.getValue().toLowerCase();;
            String motorCondition = cbMotorCondition.getValue().toLowerCase();;
            String noiseIntensity = cbNoiseIntensity.getValue().toLowerCase();;

            StringBuilder inputConditions = new StringBuilder();
            inputConditions.append(whistlingSound).append(" ")
                    .append(usageDuration).append(" ")
                    .append(tubeClogStatus).append(" ")
                    .append(repairStatus).append(" ")
                    .append(cleaningStatus).append(" ")
                    .append(suctionEfficiency).append(" ")
                    .append(hoseRepairStatus).append(" ")
                    .append(motorCondition).append(" ")
                    .append(noiseIntensity);
            System.out.println("Constructed Input Conditions: " + inputConditions.toString().trim());

            String diagnosis = ruleEngine.evaluate(inputConditions.toString().trim());
            lblResult.setPrefWidth(500);
            lblResult.setText("Result: " + diagnosis);
            System.out.println("diagnose:"+diagnosis);

        } catch (Exception e) {
            e.printStackTrace();
            lblResult.setText("Error during diagnosis.");
        }
    }

    private Integer mapUsageDuration(String value) {
        switch (value) {
            case "1-2 (Short)":
                return 0; // Short
            case "3-5 (Medium)":
                return 1; // Medium
            case "6-7 (Long)":
                return 2; // Long
            default:
                return -1; // Handle unexpected values
        }
    }
    private void resetInputs() {
        // Reset ComboBoxes to default values
        cbWhistlingSound.setValue(null); // Clear selection
        cbUsageDuration.setValue(null); // Clear selection
        cbTubeClogStatus.setValue(null); // Clear selection
        cbRepairStatus.setValue(null); // Clear selection
        cbCleaningStatus.setValue(null); // Clear selection
        cbSuctionEfficiency.setValue(null); // Clear selection
        cbHoseRepairStatus.setValue(null); // Clear selection
        cbMotorCondition.setValue(null); // Clear selection
        cbNoiseIntensity.setValue(null); // Clear selection

    }

}

