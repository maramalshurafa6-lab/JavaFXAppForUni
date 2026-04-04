import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Main extends Application {

    private TextField firstNumberField;
    private TextField secondNumberField;
    private Label resultPlace;
    private Label history;
    private final String HISTORY_FILE = "history.txt";

    @Override
    public void start(Stage stage) {

        
        Label firstNumberLable = new Label("Number 1:");
        firstNumberField = new TextField();
        firstNumberField.setPromptText("Enter First Number");

        HBox firstNumberRow = new HBox(10, firstNumberLable, firstNumberField);
        firstNumberRow.setAlignment(Pos.CENTER);

        Label secondNumberLable = new Label("Number 2:");
        secondNumberField = new TextField();
        secondNumberField.setPromptText("Enter Second Number");

        HBox secondNumberRow = new HBox(10, secondNumberLable, secondNumberField);
        secondNumberRow.setAlignment(Pos.CENTER);

        
        Button addition = new Button("+");
        Button subtraction = new Button("-");
        Button multiplication = new Button("*");
        Button division = new Button("/");

        addition.setPrefWidth(60);
        subtraction.setPrefWidth(60);
        multiplication.setPrefWidth(60);
        division.setPrefWidth(60);

        addition.setOnAction(e -> calculate("+"));
        subtraction.setOnAction(e -> calculate("-"));
        multiplication.setOnAction(e -> calculate("*"));
        division.setOnAction(e -> calculate("/"));

        HBox operators = new HBox(10, addition, subtraction, multiplication, division);
        operators.setAlignment(Pos.CENTER);

        
        Label resultLabel = new Label("Result:");
        resultPlace = new Label("");

        VBox resultBox = new VBox(resultLabel, resultPlace);
        resultBox.setAlignment(Pos.CENTER);

        Button clearBtn = new Button("Clear");
        Button historyBtn = new Button("History");
        clearBtn.setPrefWidth(100);
        historyBtn.setPrefWidth(100);

        clearBtn.setOnAction(e -> clear());
        historyBtn.setOnAction(e -> history());

        HBox actions = new HBox(10, clearBtn, historyBtn);
        actions.setAlignment(Pos.CENTER);

        
        Label historyLabel = new Label("History:");
        history = new Label("");

        VBox historyBox = new VBox(5, historyLabel, history);
        historyBox.setAlignment(Pos.CENTER);

        
        VBox root = new VBox(15); // 15 هي المسافة بين كل node في ال VBox
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        root.getChildren().addAll(
                firstNumberRow,
                secondNumberRow,
                operators,
                resultBox,
                actions,
                historyBox);

        Scene scene = new Scene(root, 400, 400);

        stage.setScene(scene);
        stage.setTitle("JavaFX Calculator with History");
        stage.show();
    }

    private void calculate(String operator) {
        try {
            double n1 = Double.parseDouble(firstNumberField.getText());
            double n2 = Double.parseDouble(secondNumberField.getText());
            double result = 0;

            switch (operator) {
                case "+":
                    result = n1 + n2;
                    break;
                case "-":
                    result = n1 - n2;
                    break;
                case "*":
                    result = n1 * n2;
                    break;
                case "/":
                    if (n2 == 0) {
                        resultPlace.setText("Cannot divide by zero");
                        return;
                    }
                    result = n1 / n2;
                    break;
            }

            String operation = n1 + " " + operator + " " + n2 + " = " + result;

            resultPlace.setText(String.valueOf(result));

            saveToHistoryFile(operation);

        } catch (Exception e) {
            resultPlace.setText("Invalid input");
        }
    }

    private void saveToHistoryFile(String operation) {
        try {

            FileWriter writer = new FileWriter(HISTORY_FILE, true);

            writer.write(operation + "\n");
            writer.close();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    private void history() {
        try {
            Scanner sc = new Scanner(new File(HISTORY_FILE));
            history.setText(""); // مسح السجل القديم

            while (sc.hasNextLine()) {
                String line = sc.nextLine(); 
                history.setText(history.getText() + line + "\n"); // ننساش السجل القديم عشان هذه loop تنشئ كل شي من جديد
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("File Error");
        }
    }
    /// /////////////////////////////////////////////////////////////////////////////
    ///

    private void clear() {
        firstNumberField.clear();
        secondNumberField.clear();
        resultPlace.setText("");
        history.setText("");
    }

    public static void main(String[] args) {
        launch();
    }
}