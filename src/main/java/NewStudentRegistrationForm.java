package com.mycompany.javafxapp;

//JavaFX imports
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//Java utilities
import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class NewStudentRegistrationForm extends Application {
    
//Form input fields
    private TextField studentfirstName, studentlastName, emailAddress, confirmEmailAddress;
    private PasswordField password, confirmPassword;
    private ComboBox<String> yearBox, monthBox, dayBox;
    private ToggleGroup genderGroup, deptGroup;
    private TextArea output;

    private static final String CSV_FILE = "students.csv";

    @Override
    public void start(Stage stage) {
        
    //Title label
        Label title = new Label("New Student Registration Form");
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(15));
        
    //Main form grid layout
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(12);
        grid.setPadding(new Insets(15));
        grid.setMaxWidth(Double.MAX_VALUE);
        
    //Password and Text fields
        studentfirstName = new TextField();
        studentlastName = new TextField();
        emailAddress = new TextField();
        confirmEmailAddress = new TextField();
        password = new PasswordField();
        confirmPassword = new PasswordField();

        addRow(grid, 0, "Student First Name:", studentfirstName);
        addRow(grid, 1, "Student Last Name:", studentlastName);
        addRow(grid, 2, "Email Address:", emailAddress);
        addRow(grid, 3, "Confirm Email Address:", confirmEmailAddress);
        addRow(grid, 4, "Password:", password);
        addRow(grid, 5, "Confirm Password:", confirmPassword);
        
    //Date of Birth
        yearBox = new ComboBox<>();
        monthBox = new ComboBox<>();
        dayBox = new ComboBox<>();

        yearBox.setPromptText("Select Year");
        monthBox.setPromptText("Select Month");
        dayBox.setPromptText("Select Day");
        
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 60; y <= currentYear - 16; y++)
            yearBox.getItems().add(String.valueOf(y));

        monthBox.getItems().addAll(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"
        );

        yearBox.setOnAction(e -> updateDays());
        monthBox.setOnAction(e -> updateDays());

        grid.add(new Label("Date of Birth:"), 0, 6);
        grid.add(new HBox(10, yearBox, monthBox, dayBox), 1, 6);
        
    //Gender
        genderGroup = new ToggleGroup();
        HBox genderBox = new HBox(10,
                radio("Male", genderGroup),
                radio("Female", genderGroup)
        );
        grid.add(new Label("Gender:"), 0, 7);
        grid.add(genderBox, 1, 7);
        
    //Departments
        deptGroup = new ToggleGroup();
        VBox deptBox = new VBox(8,
                radio("Civil", deptGroup),
                radio("Computer Science and Engineering", deptGroup),
                radio("Electrical", deptGroup),
                radio("Electronics and Communication", deptGroup),
                radio("Mechanical", deptGroup)
        );
        grid.add(new Label("Department:"), 0, 8);
        grid.add(deptBox, 1, 8);
        
    //Buttons
        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");
        
        submit.setOnAction(e -> submitForm());
        cancel.setOnAction(e -> clearForm());
    
        HBox buttonBox = new HBox(15, submit, cancel);
        buttonBox.setAlignment(Pos.CENTER);
        
    //Output text Area
        output = new TextArea();
        output.setEditable(false);
        
    //Output Label
      Label outputTitle = new Label("Your Data is Below:");
      outputTitle.setStyle("-fx-font-weight: bold;");

        VBox outputBox = new VBox(10, outputTitle, output);
        outputBox.setAlignment(Pos.TOP_LEFT); 
    
        output.setPrefSize(300, 90);
        output.setMaxSize(300, 90);
        
    //Bottom row for buttons and output
        HBox bottomRow = new HBox(20, buttonBox, outputBox);
        
        
    //Root layout and border style
        VBox root = new VBox(20, titleBox, grid, bottomRow);
        root.setStyle(
        "-fx-border-color: #6fa8dc;" +
        "-fx-border-width: 7;" +
        "-fx-border-radius: 0;" +
        "-fx-padding: 10;" +
        "-fx-xbackground-color: #f2f2f2;"
        );
        root.setFillWidth(true);
        root.setMaxWidth(Double.MAX_VALUE);
        
    //Stage setup
        stage.setScene(new Scene(root, 900, 700));
        stage.setResizable(false);
        stage.show();
    }
    //Update days based on months and years
    private void updateDays() {
        dayBox.getItems().clear();
        if (yearBox.getValue() == null || monthBox.getValue() == null) return;

        int year = Integer.parseInt(yearBox.getValue());
        int days = switch (monthBox.getValue()) {
            case "April","June","September","November" -> 30;
            case "February" -> (year % 4 == 0 ? 29 : 28);
            default -> 31;
        };

        for (int i = 1; i <= days; i++)
            dayBox.getItems().add(String.valueOf(i));
    }
    
    //Form validation and submission
    private void submitForm() {

        String fn = studentfirstName.getText().trim();
        String ln = studentlastName.getText().trim();
        String email = emailAddress.getText().trim();
        String cEmail = confirmEmailAddress.getText().trim();
        String pass = password.getText();
        String cPass = confirmPassword.getText();

        StringBuilder errors = new StringBuilder();

        if (fn.isEmpty() || ln.isEmpty()) errors.append("Name required\n");
        if (!Pattern.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$", email))
            errors.append("Invalid email\n");
        if (!email.equals(cEmail)) errors.append("Emails do not match\n");
        if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d).{8,20}$", pass))
            errors.append("Password must be 8–20 chars with letter & digit\n");
        if (!pass.equals(cPass)) errors.append("Passwords do not match\n");

        if (yearBox.getValue() == null || monthBox.getValue() == null || dayBox.getValue() == null)
            errors.append("DOB required\n");

        if (genderGroup.getSelectedToggle() == null)
            errors.append("Select gender\n");

        if (deptGroup.getSelectedToggle() == null)
            errors.append("Select department\n");        

        if (errors.length() > 0) {
            alert("Validation Errors", errors.toString());
            return;
        }

        LocalDate dob = LocalDate.of(
                Integer.parseInt(yearBox.getValue()),
                monthBox.getSelectionModel().getSelectedIndex() + 1,
                Integer.parseInt(dayBox.getValue())
        );

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 16 || age > 60) {
            alert("Invalid Age",
            "Student age must be between 16 and 60 years.\n" +
            "Please select a valid Date of Birth.");
            
            return;
        }

        String id = generateStudentId();
        String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
        String dept = ((RadioButton) deptGroup.getSelectedToggle()).getText();        

        String record = String.format(
                "%s | %s %s | %s | %s | %s | %s",
                id, 
                fn, 
                ln, 
                gender.charAt(0), 
                dob,
                dept,
                email
        );

        output.setText(record);
        appendToCSV(record);
    }
    
    //Student ID Generation
    private String generateStudentId() {
        int year = LocalDate.now().getYear();
        int count =1;

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while (br.readLine() != null) {
               count++;
                 }
        } 
        catch (IOException ignored) {}

        return year + "-" + String.format("%05d", count);
    }
    
    //CVS File Writing
    private void appendToCSV(String data) {
        try (FileWriter fw = new FileWriter(CSV_FILE, true)) {
            fw.write(data + "\n");
        } catch (IOException e) {
            alert("File Error", e.getMessage());
        }
    }
    
    //Clear Form(clears all input fields)
    private void clearForm() {
        studentfirstName.clear(); studentlastName.clear();
        emailAddress.clear(); confirmEmailAddress.clear();
        password.clear(); confirmPassword.clear();
        yearBox.setValue(null); monthBox.setValue(null); dayBox.setValue(null);
        genderGroup.selectToggle(null);
        deptGroup.selectToggle(null);
        output.clear();
    }
    
    //Errors Alert
    private void alert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.setTitle(title);
        a.showAndWait();
    }
    
    //Grid Helper Method
    private void addRow(GridPane g, int r, String label, Control field) {
        g.add(new Label(label), 0, r);
        g.add(field, 1, r);
    }
    
    //Radio Button Helper
    private RadioButton radio(String text, ToggleGroup g) {
        RadioButton rb = new RadioButton(text);
        rb.setToggleGroup(g);
        return rb;
    }

    public static void main(String[] args) {
        launch();
    }
}