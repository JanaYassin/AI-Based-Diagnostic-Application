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
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.DenseInstance;

// Main application class extending JavaFX Application
public class VacuumCleanerApp extends Application {

    // ComboBox and TextField declarations
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
    private J48 tree;
    private Instances data;

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }

    // Method to set up the UI and initialize components
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
        cbTubeClogStatus.getItems().addAll("Clogged", "NotClogged");
        Label lblTubeClogStatus = new Label("Tube Clog Status:");
        GridPane.setConstraints(lblTubeClogStatus, 0, 4);
        GridPane.setConstraints(cbTubeClogStatus, 1, 4);


        cbRepairStatus = new ComboBox<>();
        cbRepairStatus.setEditable(false); // Make the ComboBox editable
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
        GridPane.setConstraints(lblRepairStatus, 0, 5);
        GridPane.setConstraints(cbRepairStatus, 1, 5);


        cbCleaningStatus = new ComboBox<>();
        cbCleaningStatus.setEditable(false);
        cbCleaningStatus.getItems().addAll("Needs deep cleaning", "Need regular cleaning", "Cleaned");
        Label lblCleaningStatus = new Label("Cleaning Status:");
        GridPane.setConstraints(lblCleaningStatus, 0, 6);
        GridPane.setConstraints(cbCleaningStatus, 1, 6);

        cbSuctionEfficiency = new ComboBox<>();
        cbSuctionEfficiency.getItems().addAll("Low", "Normal", "Medium", "High");
        Label lblSuctionEfficiency = new Label("Suction Efficiency:");
        GridPane.setConstraints(lblSuctionEfficiency, 0, 7);
        GridPane.setConstraints(cbSuctionEfficiency, 1, 7);


        cbHoseRepairStatus = new ComboBox<>();
        cbHoseRepairStatus.getItems().addAll("Yes", "No");
        Label lblHoseRepairStatus = new Label("Hose Repair Status:");
        GridPane.setConstraints(lblHoseRepairStatus, 0, 8);
        GridPane.setConstraints(cbHoseRepairStatus, 1, 8);

        cbMotorCondition = new ComboBox<>();
        cbMotorCondition.setEditable(false);
        cbMotorCondition.getItems().addAll("Good", "Overheating", "Clogged", "Burnt Smell", "Worn out", "Needs Attention");
        Label lblMotorCondition = new Label("Motor Condition:");
        GridPane.setConstraints(lblMotorCondition, 0, 9);
        GridPane.setConstraints(cbMotorCondition, 1, 9);


        cbNoiseIntensity = new ComboBox<>();
        cbNoiseIntensity.getItems().addAll("Low", "Normal", "Medium", "High", "Very High");
        Label lblNoiseIntensity = new Label("Noise Intensity:");
        GridPane.setConstraints(lblNoiseIntensity, 0, 10);
        GridPane.setConstraints(cbNoiseIntensity, 1, 10);


        Button btnDiagnose = new Button("Diagnose");
        btnDiagnose.setStyle("-fx-font-size: 14px; -fx-padding: 10px; -fx-background-color: Grey; -fx-text-fill: white;");
        GridPane.setConstraints(btnDiagnose, 1, 12);
        btnDiagnose.setOnAction(e -> diagnoseFault());

        lblResult = new Label("Result: ");
        lblResult.setStyle("-fx-font-size: 16px; -fx-text-fill: black; -fx-font-weight: bold;");
        GridPane.setConstraints(lblResult, 1, 13);

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

        Scene scene = new Scene(grid, 700, 600);

        primaryStage.setScene(scene);
        primaryStage.show();

        ModelLoader modelLoader = new ModelLoader();
        data = modelLoader.loadModel("C:/Users/USER/Desktop/MyProject/Codes/VCUsingC45&DATA/NewVacuumCleanerData_330Instances.csv");
        tree = modelLoader.getTree();

        Evaluator evaluator = new Evaluator(data, tree);
        evaluator.evaluateModel();

        btnDiagnose.setOnAction((e) -> {
            this.diagnoseFault();
            this.resetInputs();
        });
    }

    // Function to diagnose faults based on the input values
    private void diagnoseFault() {
        try {
            double[] vals = new double[data.numAttributes()];

            // Convert input text to appropriate attribute values
            vals[0] = mapYesNo(cbWhistlingSound.getValue());
            vals[1] = mapUsageDuration(cbUsageDuration.getValue());
            if (vals[1] == -1) {
                lblResult.setText("Invalid Usage Duration selected.");
                return;
            }
            vals[2] = mapClogStatus(cbTubeClogStatus.getValue());
            vals[3] = mapRepairStatus(cbRepairStatus.getValue());
            vals[4] = mapCleaningStatus(cbCleaningStatus.getValue());
            vals[5] = mapSuctionEfficiency(cbSuctionEfficiency.getValue());
            vals[6] = mapYesNo(cbHoseRepairStatus.getValue());
            vals[7] = mapMotorCondition(cbMotorCondition.getValue());
            vals[8] = mapNoiseIntensity(cbNoiseIntensity.getValue());

            System.out.println("Whistling Sound:" + vals[0]);
            System.out.println("Usage Duration:" + vals[1]);
            System.out.println("Tube Clog Status:" + vals[2]);
            System.out.println("Repair Status:" + vals[3]);
            System.out.println("Cleaning Status:" + vals[4]);
            System.out.println("Suction Efficiency:" + vals[5]);
            System.out.println("Hose Repair Status:" + vals[6]);
            System.out.println("Motor Condition:" + vals[7]);
            System.out.println("Noise Intensity:" + vals[8]);

            Instances newInst = new Instances(data, 0);
            newInst.add(new DenseInstance(1.0, vals));

            double result = tree.classifyInstance(newInst.firstInstance());

            lblResult.setPrefWidth(500);
            lblResult.setText("Result: " + data.classAttribute().value((int) result));

        } catch (NumberFormatException e) {
            lblResult.setText("Invalid number format in one of the inputs.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Functions to map all comboboxes values into numerical values
    private double mapClogStatus(String value) {
        return value.equals("Clogged") ? 1 : 0;
    }

    private double mapYesNo(String value) {
        return value.equals("Yes") ? 1 : 0;
    }

    private double mapSuctionEfficiency(String value) {
        switch (value) {
            case "Low": return 0;
            case "Normal": return 1;
            case "Medium": return 2;
            case "High": return 3;
            default: return -1;
        }
    }

    private double mapUsageDuration(String value) {
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

    private double mapNoiseIntensity(String value) {
        switch (value) {
            case "Low": return 0;
            case "Normal": return 1;
            case "Medium": return 2;
            case "High": return 3;
            case "Very High": return 4;
            default: return -1;
        }
    }

    private double mapRepairStatus(String value) {
        if (value == null) {
            System.out.println("Repair Status is null");
            return -1;
        }
        value = value.toLowerCase().trim();
        System.out.println("Normalized Repair Status: " + value);

        switch (value) {
            case "cleaned pipe, disassembled switch": return 0;
            case "replaced filters, cleaned pipes": return 1;
            case "disconnected sensor": return 2;
            case "fixed winder cord": return 3;
            case "cleared hose and tube clogs": return 4;
            case "needs repair": return 5;
            case "no repair needed": return 6;
            case "disassembled, reassembled": return 7;
            case "removed screws, unclipped device": return 8;
            case "motor brushes need changing": return 9;
            case "replace battery and motor": return 10;
            case "fixed battery wire": return 11;
            case "check charging circuit": return 12;
            case "sensor issue": return 13;
            case "hidden screws": return 14;
            case "issue with hood clips": return 15;
            case "replace central filter": return 16;
            case "open vacuum cleaner": return 17;
            case "suspected switch issue": return 18;
            case "check bag compartment": return 19;
            case "bad reel contact": return 20;
            case "under warranty": return 21;
            case "needs cord repair": return 22;
            case "needs cord replacement": return 23;
            case "needs inspection": return 24;
            case "needs hose repair": return 25;
            case "needs filter replacement": return 26;
            case "checked pipe and cleaned filter": return 27;
            case "checked unclogged elbows": return 28;
            case "checked hose and air inlet": return 29;
            case "return to service": return 30;
            case "send photos for diagnosis": return 31;
            case "inspect hose and inlet": return 32;
            case "check pipe for splits": return 33;
            case "check nozzle and clean tank/filter": return 34;
            case "check motor with repairer": return 35;
            default: return -1;
        }
    }
    private double mapCleaningStatus(String value) {
        switch (value) {
            case "Needs deep cleaning": return 0;
            case "Need regular cleaning": return 1;
            case "Cleaned": return 2;
            default: return -1;
        }
    }

    private double mapMotorCondition(String value) {
        switch (value) {
            case "Good": return 0;
            case "Overheating": return 1;
            case "Clogged": return 2;
            case "Burnt Smell": return 3;
            case "Worn out": return 4;
            case "Needs Attention": return 5;
            default: return -1;
        }
    }

    private boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    // Method to reset inputs to their default values
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
