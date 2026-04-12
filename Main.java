import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

    ComboBox<String> id;
    TextField name;
    RadioButton male;
    RadioButton female;
    ToggleGroup tg;
    ListView<String> preferedPL;
    ListView<String> selectedPL;
    String[] pl = { "Java", "Python", "C++", "PHP", "JavaScript" };
    Button save;
    Button arrow;
    CheckBox c1, c2, c3;
    RadioButton r1, r2, r3;
    ComboBox<Integer> cb;
    ColorPicker cp;
    ListView<String> studentList;
    BorderPane root;
    Label title;
    Label message;
    ArrayList<Student> students = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        id = new ComboBox<>();
        id.setPromptText("Select ID");
        id.setPrefWidth(200);

        name = new TextField();
        name.setPromptText("Enter student name");
        name.setPrefWidth(200);

        male = new RadioButton("Male");
        female = new RadioButton("Female");
        tg = new ToggleGroup();
        male.setToggleGroup(tg);
        female.setToggleGroup(tg);

        ObservableList<String> ol = FXCollections.observableArrayList(pl);
        preferedPL = new ListView<>(ol);
        selectedPL = new ListView<>();
        preferedPL.setPrefSize(150, 120);
        selectedPL.setPrefSize(150, 120);

        preferedPL.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        arrow = new Button(">>>>>");
        arrow.setPrefWidth(60);
        save = new Button("Save");
        save.setPrefWidth(100);
        message = new Label("");
        message.setStyle("-fx-text-fill: red;");
        ///////////////////////////////////////////////
        c1 = new CheckBox("Normal");
        c2 = new CheckBox("Bold");
        c3 = new CheckBox("Italic");

        r1 = new RadioButton("Red");
        r2 = new RadioButton("Green");
        r3 = new RadioButton("Blue");

        ToggleGroup tg2 = new ToggleGroup();
        r1.setToggleGroup(tg2);
        r2.setToggleGroup(tg2);
        r3.setToggleGroup(tg2);

        cb = new ComboBox<>();
        cb.getItems().addAll(5, 10, 15, 20);
        cb.setValue(10);
        cb.setPrefWidth(120);

        cp = new ColorPicker();
        cp.setValue(Color.WHITE);
        ///////////////////////////////////////
        studentList = new ListView<>();
        studentList.setPrefSize(250, 350);
        ///////////////////////////////////////////
        Label designTitle = new Label("Design Tools");
        VBox leftBox = new VBox(12);
        leftBox.setPadding(new Insets(20));
        leftBox.setAlignment(Pos.TOP_LEFT);
        leftBox.setPrefWidth(180);
        leftBox.getChildren().addAll(
                designTitle,
                new Label("Style"),
                c1, c2, c3,
                new Label("Color"),
                r1, r2, r3,
                new Label("Font Size"),
                cb,
                new Label("Custom Color"),
                cp);
        ////////////////////////////////////////////////
        title = new Label("Student Registration System");

        HBox genderBox = new HBox(15, male, female);
        genderBox.setAlignment(Pos.CENTER_LEFT);

        HBox plBox = new HBox(20, preferedPL, arrow, selectedPL);
        plBox.setAlignment(Pos.CENTER_LEFT);

        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(18);
        formGrid.setAlignment(Pos.TOP_CENTER);

        formGrid.add(title, 0, 0, 2, 1);
        formGrid.add(new Label("ID:"), 0, 1);
        formGrid.add(id, 1, 1);
        formGrid.add(new Label("Name:"), 0, 2);
        formGrid.add(name, 1, 2);
        formGrid.add(new Label("Gender:"), 0, 3);
        formGrid.add(genderBox, 1, 3);
        formGrid.add(new Label("PL:"), 0, 4);
        formGrid.add(plBox, 1, 4);
        formGrid.add(save, 1, 5);
        formGrid.add(message, 1, 6);

        VBox centerBox = new VBox();
        centerBox.getChildren().add(formGrid);

        ////////////////////////////////////////////////

        Label savedTitle = new Label("Saved Students");

        VBox rightBox = new VBox(15);
        rightBox.setPadding(new Insets(20));
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.setPrefWidth(280);
        rightBox.getChildren().addAll(savedTitle, studentList);
        ///////////////////////////////////////////////////////////
        root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setLeft(leftBox);
        root.setCenter(centerBox);
        root.setRight(rightBox);

        /////////////////////////////////////////
        leftBox.setId("left-panel");
        centerBox.setId("center-panel");
        rightBox.setId("right-panel");
        formGrid.setId("form-grid");
        save.setId("save-btn");
        designTitle.setId("section-title");
        title.setId("main-title");
        savedTitle.setId("section-title");
        ////////////////////////////////////////////////////
        /// ////////////////////////////////////////////////
        c1.setOnAction(e -> {
            if (c1.isSelected()) {
                root.getStyleClass().remove("bold-style");
                root.getStyleClass().remove("italic-style");
            }
        });

        c2.setOnAction(e -> {
            if (c2.isSelected()) {
                root.getStyleClass().add("bold-style");
            } else {
                root.getStyleClass().remove("bold-style");
            }
        });
        c3.setOnAction(e -> {
            if (c3.isSelected()) {
                root.getStyleClass().add("italic-style");
            } else {
                root.getStyleClass().remove("italic-style");
            }
        });

        //////////////////////////////////////////////////////////
        try {
            readFromFile();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        /// ////////////////////////////////////////////////////

        cb.setOnAction(e -> applyFontSize());
        r1.setOnAction(this);
        r2.setOnAction(this);
        r3.setOnAction(this);
        cp.setOnAction(this);
        arrow.setOnAction(this);
        save.setOnAction(this);
        id.setOnAction(e -> {
            applyInfoForSelectedId();
        });
        /////////////////////////////////

        // ========== Scene===============

        Scene scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Student Registration System");
        stage.setScene(scene);
        stage.show();
        stage.setAlwaysOnTop(true);
        stage.setOnCloseRequest(e -> {
            saveToFile();
        });
    }

    @Override
    public void handle(ActionEvent a) {
        String backgroundColor = "white";

        String gender = "";
        if (r1.isSelected()) {
            backgroundColor = "red";
        }
        if (r2.isSelected()) {
            backgroundColor = "green";
        }
        if (r3.isSelected()) {
            backgroundColor = "blue";
        }
        root.setStyle("-fx-background-color:" + backgroundColor);

        if (a.getSource() == cp) {
            String color = cp.getValue().toString().replace("0x", "");
            save.setStyle("-fx-background-color: #" + color);
        }
        if (a.getSource() == arrow) {
            ObservableList<String> ol = FXCollections
                    .observableArrayList(preferedPL.getSelectionModel().getSelectedItems());
            preferedPL.getItems().removeAll(ol);
            selectedPL.getItems().addAll(ol);

        }

        if (male.isSelected()) {
            gender = "male";
        }
        if (female.isSelected()) {
            gender = "female";
        }
        if (a.getSource() == save) {
            if (validate()) {
                Student s = new Student(name.getText(), gender);
                s.setPl(new ArrayList<>(selectedPL.getItems()));
                students.add(s);
                studentList.getItems().add(s.toString());
                id.getItems().add(s.getId());
                clear();
            } else {
                message.setText("Name and gender are required!");
            }
        }
    }

    public void applyFontSize() {
        root.getStyleClass().removeAll(
                "small-font",
                "medium-font",
                "large-font",
                "xlarge-font");
        if (cb.getValue() == 5) {
            root.getStyleClass().add("small-font");
        } else if (cb.getValue() == 10) {
            root.getStyleClass().add("medium-font");
        } else if (cb.getValue() == 15) {
            root.getStyleClass().add("large-font");
        } else if (cb.getValue() == 20) {
            root.getStyleClass().add("xlarge-font");
        }
    }

    public boolean validate() {
        if (name.getText().isBlank() || tg.getSelectedToggle() == null) {
            return false;
        }
        return true;
    }

    public void clear() {
        name.setText("");
        male.setSelected(false);
        female.setSelected(false);
        selectedPL.getItems().clear();
        preferedPL.getItems().setAll(pl);
        message.setText("");
    }

    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void readFromFile() throws ClassNotFoundException {
        File f = new File("students.dat");
        if (!f.exists()) {
            System.out.println("No File yet");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            students = (ArrayList<Student>) ois.readObject(); 
                                                              
                                                              
            for (Student s : students) {
                studentList.getItems().add(s.toString());
                id.getItems().add(s.getId());
            }

            if (!students.isEmpty()) {

                int stdId = Integer.parseInt(students.get(students.size() - 1).getId().substring(5));

                Student.counter = stdId + 1;
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public void applyInfoForSelectedId() {
        if (id.getValue() == null)
            return;
        String selectedId = id.getValue();
        for (Student s : students) {
            if (s.getId().equals(selectedId)) {
                name.setText(s.getName());
                char firstDigit = s.getId().charAt(0);
                if (firstDigit == '1') {
                    male.setSelected(true);
                    female.setSelected(false);
                } else if (firstDigit == '2') {
                    female.setSelected(true);
                    male.setSelected(false);
                }
                preferedPL.getItems().setAll(pl);
                selectedPL.getItems().clear();
                selectedPL.getItems().addAll(s.getPl());

                break;
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}