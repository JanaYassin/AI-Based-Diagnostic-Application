package org.example;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.DenseInstance;
import weka.core.Instances;
import java.util.HashMap;
import java.util.Map;


public class WashingMachineApp extends Application {

    // Declare UI components and data structures
    private ComboBox<Integer> cbUsePerWeek;
    private ComboBox<Integer> cbPreviousRepair;
    private ComboBox<Integer> cbReplacement;
    private ComboBox<String> cbFaultType1;
    private ComboBox<String> cbFaultType2;
    private ComboBox<String> cbFaultType3;
    private Label lblResult;
    private J48 tree;
    private Instances data;
    private ObservableList<String> faultTypes;  // Declare faultTypes as a class-level variable
    private Classifier[] trees;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Washing Machine Fault Diagnosis");

        // Set up the UI layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        setupUIComponents(grid);

        Scene scene = new Scene(grid, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        cbUsePerWeek.setPrefWidth(300);
        cbPreviousRepair.setPrefWidth(300);
        cbReplacement.setPrefWidth(300);
        cbFaultType1.setPrefWidth(300);
        cbFaultType2.setPrefWidth(300);
        cbFaultType3.setPrefWidth(300);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(300);
        grid.getColumnConstraints().add(column1);

        // Load the model
        ModelLoader modelLoader = new ModelLoader();
        data = modelLoader.loadModel("C:/Users/USER/Desktop/MyProject/codeUpdate/WMC45&DATA/WashingMachineData.csv");
        tree = modelLoader.getTree();

        Evaluator evaluator = new Evaluator(data, tree);
        evaluator.evaluateModel();

        Button btnDiagnose = new Button("Diagnose");
        btnDiagnose.setOnAction((e) -> {
            this.diagnoseFault();
            this.resetInputs();
        });
        grid.add(btnDiagnose, 1, 13);

        lblResult = new Label("Result:");
        lblResult.setMaxWidth(500);
        lblResult.setWrapText(true); // Enable text wrapping
        grid.add(lblResult, 0, 14, 2, 1);
    }

    private void setupUIComponents(GridPane grid) {
        // Initialize and add each UI component to the grid
        Label lblUsePerWeek = new Label("Use (per week):");
        cbUsePerWeek = new ComboBox<>();
        cbUsePerWeek.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7)); // Add values from 1 to 7
        cbUsePerWeek.setValue(1); // Set default value to 1
        cbUsePerWeek.setEditable(false); // Make sure it's not editable to restrict input to the listed values

        // Previous Repair
        Label lblPreviousRepair = new Label("Previous repair:");
        cbPreviousRepair = new ComboBox<>();
        cbPreviousRepair.setItems(FXCollections.observableArrayList(0, 1, 2, 3)); // Add values 0 to 3
        cbPreviousRepair.setValue(0); // Default value
        cbPreviousRepair.setEditable(false); // Disable manual editing

        // Replacement
        Label lblReplacement = new Label("Replacement:");
        cbReplacement = new ComboBox<>();
        cbReplacement.setItems(FXCollections.observableArrayList(0, 1, 2, 3)); // Add values 0 to 4
        cbReplacement.setValue(0); // Default value
        cbReplacement.setEditable(false); // Disable manual editing

        Label lblFaultType1 = new Label("Fault Type 1:");
        faultTypes = FXCollections.observableArrayList(
                "Inlet valves", "Shock absorbers and bearings", "Cables and plugs", "Carbon brushes",
                "Pumps", "Electronics", "Doors", "Detergent system", "Switches", "Foreign objects",
                "Engine", "Heater and thermostats", "Pressure control", "Drain system", "Pump filters",
                "Drive belt", "Drum and tub", "Other"
        );
        cbFaultType1 = new ComboBox<>(faultTypes);
        cbFaultType1.setEditable(false);

        Label lblFaultType2 = new Label("Fault Type 2:");
        cbFaultType2 = new ComboBox<>(faultTypes);
        cbFaultType2.setEditable(false);
        Label lblFaultType3 = new Label("Fault Type 3:");
        cbFaultType3 = new ComboBox<>(faultTypes);
        cbFaultType3.setEditable(false);

        TextField cbEditor1 = cbFaultType1.getEditor();
        cbEditor1.textProperty().addListener((obs, oldText, newText) -> {
            if (!cbFaultType1.isShowing()) {
                cbFaultType1.show();
            }
            ObservableList<String> filteredItems = faultTypes.filtered(item -> item.toLowerCase().contains(newText.toLowerCase()));
            cbFaultType1.setItems(filteredItems);
        });

        TextField cbEditor2 = cbFaultType2.getEditor();
        cbEditor2.textProperty().addListener((obs, oldText, newText) -> {
            if (!cbFaultType2.isShowing()) {
                cbFaultType2.show();
            }
            ObservableList<String> filteredItems = faultTypes.filtered(item -> item.toLowerCase().contains(newText.toLowerCase()));
            cbFaultType2.setItems(filteredItems);
        });

        TextField cbEditor3 = cbFaultType3.getEditor();
        cbEditor3.textProperty().addListener((obs, oldText, newText) -> {
            if (!cbFaultType3.isShowing()) {
                cbFaultType3.show();
            }
            ObservableList<String> filteredItems = faultTypes.filtered(item -> item.toLowerCase().contains(newText.toLowerCase()));
            cbFaultType3.setItems(filteredItems);
        });


        grid.add(lblUsePerWeek , 0, 2);
        grid.add(cbUsePerWeek , 1, 2);
        grid.add(lblPreviousRepair , 0, 3);
        grid.add(cbPreviousRepair , 1, 3);
        grid.add(lblReplacement, 0, 5);
        grid.add(cbReplacement, 1, 5);
        grid.add(lblFaultType1, 0, 10);
        grid.add(cbFaultType1, 1, 10);
        grid.add(lblFaultType2, 0, 11);
        grid.add(cbFaultType2, 1, 11);
        grid.add(lblFaultType3, 0, 12);
        grid.add(cbFaultType3, 1, 12);
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


    // Method to diagnose the fault based on user input
    private void diagnoseFault() {
        try {
            // Prepare instance with inputs
            double[] vals = new double[data.numAttributes()];
            int usePerWeek = cbUsePerWeek.getValue();
            int previousRepair = cbPreviousRepair.getValue();
            int replacement = cbReplacement.getValue();

            vals[0] = usePerWeek;
            vals[1] = previousRepair;
            vals[2] = replacement;

            System.out.println(vals[0]);
            System.out.println(vals[1]);
            System.out.println(vals[2]);

            // Initialize all fault type attributes to 0
            for (int i = 3; i < vals.length; i++) {
                vals[i] = 0.0;
            }

            // Set the selected fault type attributes to 1 and print them
            String selectedFaultType1 = cbFaultType1.getValue();
            int faultTypeIndex1 = faultTypes.indexOf(selectedFaultType1) + 3;  // Assuming fault type attributes start from index 10
            if (faultTypeIndex1 >= 3 && faultTypeIndex1 < vals.length) {
                vals[faultTypeIndex1] = 1.0;
                System.out.println("Fault Type 1: " + selectedFaultType1 + " (Index: " + faultTypeIndex1 + ")");
            } else {
                System.out.println("Fault Type 1 not selected or out of range.");
            }

            String selectedFaultType2 = cbFaultType2.getValue();
            int faultTypeIndex2 = faultTypes.indexOf(selectedFaultType2) + 3;
            if (faultTypeIndex2 >= 3 && faultTypeIndex2 < vals.length) {
                vals[faultTypeIndex2] = 1.0;
                System.out.println("Fault Type 2: " + selectedFaultType2 + " (Index: " + faultTypeIndex2 + ")");
            } else {
                System.out.println("Fault Type 2 not selected or out of range.");
            }

            String selectedFaultType3 = cbFaultType3.getValue();
            int faultTypeIndex3 = faultTypes.indexOf(selectedFaultType3) + 3;
            if (faultTypeIndex3 >= 3 && faultTypeIndex3 < vals.length) {
                vals[faultTypeIndex3] = 1.0;
                System.out.println("Fault Type 3: " + selectedFaultType3 + " (Index: " + faultTypeIndex3 + ")");
            } else {
                System.out.println("Fault Type 3 not selected or out of range.");
            }

            // Print all fault type values for verification
            System.out.println("Fault type values: ");
            for (int i = 3; i < vals.length; i++) {
                System.out.println("Index " + i + ": " + vals[i]);
            }

            // Create a new instance
            Instances newInst = new Instances(data, 0);
            newInst.add(new DenseInstance(1.0, vals));
            newInst.setClassIndex(data.classIndex());

            // Classify instance
            double result = tree.classifyInstance(newInst.firstInstance());
            String compositeClass = data.classAttribute().value((int) result);

            // Parse and display results
            String[] outputs = compositeClass.split("_");
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
            }else {
                // Handle invalid diagnosis format gracefully
                this.lblResult.setText("Error: Diagnosis format is invalid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblResult.setText("Error in diagnosing fault. Check input data.");
        }
    }
}
