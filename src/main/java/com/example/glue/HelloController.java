package com.example.glue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.OutputStream;
import java.io.InputStream;

import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Arrays;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileReader;
import java.util.*;
import java.time.LocalDateTime;
import java.io.FileWriter;
import javafx.stage.DirectoryChooser;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.stage.Stage;
//import kotlin.coroutines.jvm.internal.SuspendFunction;

public class HelloController {
    @FXML public AnchorPane mainPage;

    public MenuItem menuClose;
    @FXML private Label welcomeText;
    @FXML private ComboBox<String> loadPrj;
    @FXML private ComboBox<String> loadModule;
    @FXML private Button browseTabular;
    @FXML public TextArea cmdOutput;
    @FXML private MenuItem loadDB;
    @FXML public TextArea browseFastaPath;
    @FXML private TextArea browseFastaDirPath;
    @FXML public TextArea browseSbtPath;
    @FXML public TextArea browseOutputPath;
    @FXML public TextArea browseTabularPath;
    @FXML public Button launchQuery;
    @FXML private MenuItem launchSetPath;
    @FXML private Button browseGlueConfig;
    @FXML private Button browseGlueDb;
    @FXML private Button browseFasta;
    @FXML private Button browseFastaDir;
    @FXML private Button browseGluePath;
    @FXML private TextArea glueConfigPath;
    @FXML private TextArea glueDatabase;
    @FXML private TextArea gluePath;
    @FXML private Button glueSaveSetting;
    @FXML private Button glueCloseButton;
    @FXML private Button viewSqlTable;
    @FXML private Label totalEntriesOnTable;
    @FXML private TableView<viewTable> tableView;
    @FXML private TableColumn<viewTable, String> column1;
    @FXML private TableColumn<viewTable, String> column2;
    @FXML private TableColumn<viewTable, String> column3;
    @FXML private TableColumn<viewTable, String> column4;
    @FXML private TableColumn<viewTable, String> column5;
    @FXML private TableColumn<viewTable, String> column6;
    @FXML private TableColumn<viewTable, String> column7;
    @FXML private TableColumn<viewTable, String> column8;
    @FXML private TableColumn<viewTable, String> column9;
    @FXML private TableColumn<viewTable, String> column10;
    @FXML private TableColumn<viewTable, String> column11;
    @FXML private TableColumn<viewTable, String> column12;
    @FXML private ComboBox<String> viewSequences;
    @FXML private ComboBox<String> viewClades;
    @FXML private ComboBox<String> viewSubClades;
    @FXML public TextArea filtersApplied;
    @FXML private Button writeAllMetaInfoToFile;
    @FXML private Button queryTextBox;

    @FXML private ComboBox<String> filterAllSeqNucID;
    @FXML private TextField allSeqNucID;

    @FXML private ComboBox<String> filterAllSeqSeqLen;
    @FXML private TextField allSeqSeqLen;

    @FXML private ComboBox<String> filterAllSeqEntryCreationDate;
    @FXML private ComboBox<String> filterAllSeqEntryCreationDateDay;
    @FXML private ComboBox<String> filterAllSeqEntryCreationDateMonth;
    @FXML private TextField filterAllSeqEntryCreationDateYear;

    @FXML private ComboBox<String> filterAllSeqLastUpdateDate;
    @FXML private ComboBox<String> filterAllSeqLastUpdateDateDay;
    @FXML private ComboBox<String> filterAllSeqLastUpdateDateMonth;
    @FXML private TextField filterAllSeqLastUpdateDateYear;


    @FXML private ComboBox<String> filterAllSeqEarliestCollectionYear;
    @FXML private TextField allSeqEarliestCollectionYear;
    @FXML private ComboBox<String> filterAllSeqLatestCollectionYear;
    @FXML private TextField allSeqLatestCollectionYear;

    @FXML private ComboBox<String> filterAllSeqCountryOfOrigin;
    @FXML private TextField allSeqCountryOfOrigin;

    @FXML private ComboBox<String> filterAllSeqGlobalRegionContinent;
    @FXML private ComboBox<String> filterAllSeqGlobalRegionArea;
    @FXML private ComboBox<String> filterAllSeqGlobalRegion;

    @FXML private ComboBox<String> filterAllSeqClade;
    @FXML private TextField allSeqClade;

    @FXML private ComboBox<String> filterAllSeqCountryOfDevelopmentStatus;
    @FXML private ComboBox<String> allSeqCountryOfDevelopmentStatus;

    @FXML private ComboBox<String> filterAllSeqPlacedSampled;
    @FXML private TextField allSeqPlacedSampled;

    @FXML private ComboBox<String> filterAllSeqHostSpecies;
    @FXML private TextField allSeqHostSpecies;

    @FXML private ComboBox<String> filterAllSeqPatent;

    @FXML private ComboBox<String> filterAllSeqPubmedID;
    @FXML private TextField allSeqPubmedID;

    @FXML private ComboBox<String> filterAllSeqIsolateID;
    @FXML private TextField allSeqIsolateID;

    private HelloController mainController;

    public void setMainController(HelloController controller) {
        this.mainController = controller;
    }

    private Map<String, String> cladeMap = new HashMap<>();
    private static Map<String, List<String>> globalRegionMap = new HashMap<>();

    @FXML
    private void menuClose(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    public void launchSetPath(ActionEvent events) {
        try {
            String filePath = globalVariables.pwd +
                    "/" + globalVariables.configDir +
                    "/" + globalVariables.configFile;
            // Load the new form
            Parent newForm = FXMLLoader.load(getClass().getResource("set-path.fxml"));
            TextArea gluePath = (TextArea) newForm.lookup("#gluePath");
            TextArea glueConfigPath = (TextArea) newForm.lookup("#glueConfigPath");
            TextArea glueDatabase = (TextArea) newForm.lookup("#glueDatabase");

            // Set the text of the TextArea
            if (checkConfigStatus(filePath)) {
                Map<String, String> hash = loadConfigToHash(filePath);
                gluePath.setText(hash.get("jar_file"));
                glueConfigPath.setText(hash.get("conf_file"));
                glueDatabase.setText(hash.get("db"));
            }
            Scene newFormScene = new Scene(newForm);
            Stage newFormStage = new Stage();
            newFormStage.setTitle("Save Settings");
            newFormStage.setScene(newFormScene);
            newFormStage.show();

        } catch (Exception e) {
            e.printStackTrace();
            GlobalFileLogger.getInstance("application.log").write(e.toString());
        }
    }

    @FXML
    public void launchQuery(ActionEvent event) throws IOException {
        Parent queryForm = FXMLLoader.load(getClass().getResource("query.fxml"));
        ComboBox<String> viewSequencesComboBox = (ComboBox<String>) queryForm.lookup("#viewSequences");
        //System.out.println(viewSequencesComboBox);
        if (viewSequencesComboBox != null) {
            viewSequencesComboBox.getItems().addAll("All sequences", "Sequences by clade");
            //viewSequences.getItems().addAll("All sequences", "Sequences by clade");
        }
        Scene newFormScene = new Scene(queryForm);
        Stage newFormStage = new Stage();
        newFormStage.setTitle("Query");
        newFormStage.setScene(newFormScene);
        newFormStage.show();
    }

    @FXML
    public void launchFilter(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("filter-all-seq.fxml"));
        Parent filterForm = loader.load();

        HelloController formController = loader.getController();
        formController.setMainController(this);

        Map<String, List<String>> hashGlobalRegion = loadM49Countries();
        ComboBox<String> ncbiSequenceID = formController.filterAllSeqNucID;
        ComboBox<String> sequenceLength = formController.filterAllSeqSeqLen;
        ComboBox<String> seqEntryCreationDate =  formController.filterAllSeqEntryCreationDate;
        ComboBox<String> seqEntryCreationDateDay = formController.filterAllSeqEntryCreationDateDay;
        ComboBox<String> seqEntryCreationDateMonth = formController.filterAllSeqEntryCreationDateMonth;
        TextField seqEntryCreationDateYear = formController.filterAllSeqEntryCreationDateYear;

        ComboBox<String> seqLastUpdateDate = formController.filterAllSeqLastUpdateDate;
        ComboBox<String> seqLastUpdateDateDay = formController.filterAllSeqLastUpdateDateDay;
        ComboBox<String> seqLastUpdateDateMonth = formController.filterAllSeqLastUpdateDateMonth;
        TextField seqLastUpdateDateYear = formController.filterAllSeqLastUpdateDateYear;

        ComboBox<String> seqEarliestCollectionYear = formController.filterAllSeqEarliestCollectionYear;
        ComboBox<String> seqLatestCollectionYear = formController.filterAllSeqLatestCollectionYear;
        ComboBox<String> seqCountryOfOrigin = formController.filterAllSeqCountryOfOrigin;

        ComboBox<String> seqGlobalRegion = formController.filterAllSeqGlobalRegion;
        ComboBox<String> seqGlobalRegionContinent = formController.filterAllSeqGlobalRegionContinent;
        ComboBox<String> seqGlobalRegionArea = formController.filterAllSeqGlobalRegionArea;

        ComboBox<String> seqClade = formController.filterAllSeqClade;
        TextField seqCladeValue = formController.allSeqClade;

        ComboBox<String> seqCountryOfDevelopmentStatus = formController.filterAllSeqCountryOfDevelopmentStatus;
        ComboBox<String> seqCountryOfDevelopmentStatusValues = formController.allSeqCountryOfDevelopmentStatus;
        ComboBox<String> seqPlacedSampled = formController.filterAllSeqPlacedSampled;
        ComboBox<String> seqHostSpecies = formController.filterAllSeqHostSpecies;
        ComboBox<String> seqPatent = formController.filterAllSeqPatent;
        ComboBox<String> seqPubmedID = formController.filterAllSeqPubmedID;
        ComboBox<String> seqIsolateID = formController.filterAllSeqIsolateID;

        if (ncbiSequenceID != null) {
            ncbiSequenceID.getItems().addAll("Select", "contains", "does not contains", "matches",
                    "does not match", "is null", "is not null");
        }
        if (sequenceLength != null) {
            sequenceLength.getItems().addAll("Select", "equals", "does not equals", ">", ">=", "<",
                    "<=", "is null", "is not null");
        }
        if (seqEntryCreationDate != null) {
            seqEntryCreationDate.getItems().addAll("Select", "equals", "does not equals", "after",
                    "on or after", "before", "on or before", "is null", "is not null");
        }
        if (seqEntryCreationDateDay != null) {
            seqEntryCreationDateDay.getItems().addAll("Select", "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15",
                    "16", "17", "18", "19", "20",
                    "21", "22", "23", "24", "25",
                    "26", "27", "28", "29", "30", "31");
        }
        if (seqEntryCreationDateMonth != null) {
            seqEntryCreationDateMonth.getItems().addAll("Select", "Jan", "Feb", "Mar", "Apr", "May",
                    "June", "Jul", "Aug", "Sep", "Nov",
                    "Dec");
        }
        if (seqEntryCreationDateYear != null) {
            seqEntryCreationDateYear.setText("Year");
        }
        if (seqLastUpdateDate != null) {
            seqLastUpdateDate.getItems().addAll("Select", "equals", "does not equals", "after",
                    "on or after", "before", "on or before", "is null", "is not null");
        }
        if (seqLastUpdateDateDay != null) {
            seqLastUpdateDateDay.getItems().addAll("Select", "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "10",
                    "11", "12", "13", "14", "15",
                    "16", "17", "18", "19", "20",
                    "21", "22", "23", "24", "25",
                    "26", "27", "28", "29", "30", "31");
        }
        if (seqLastUpdateDateMonth != null) {
            seqLastUpdateDateMonth.getItems().addAll("Select", "Jan", "Feb", "Mar", "Apr", "May",
                    "June", "Jul", "Aug", "Sep", "Nov",
                    "Dec");
        }
        if (seqLastUpdateDateYear != null) {
            seqLastUpdateDateYear.setText("Year");
        }
        if (seqEarliestCollectionYear != null) {
            seqEarliestCollectionYear.getItems().addAll("Select", "equals", "does not equals", "after",
                    "on or after", "before", "on or before", "is null", "is not null");
        }
        if (seqLatestCollectionYear != null) {
            seqLatestCollectionYear.getItems().addAll("Select", "equals", "does not equals", "after",
                    "on or after", "before", "on or before", "is null", "is not null");
        }
        if (seqCountryOfOrigin != null) {
            seqCountryOfOrigin.getItems().addAll("Select", "contains", "does not contains", "matches",
                    "does not match", "is null", "is not null");
        }
        if (seqGlobalRegion != null) {
            seqGlobalRegion.getItems().addAll("Select", "matches", "does not match",
                    "is null", "is not null");
        }
        if (seqGlobalRegionContinent != null) {
            seqGlobalRegionContinent.getItems().addAll(hashGlobalRegion.keySet());

            seqGlobalRegionContinent.setOnAction(e -> {
                String selectedContinent = seqGlobalRegionContinent.getSelectionModel().getSelectedItem();
                if (selectedContinent != null) {
                    List<String> areas = hashGlobalRegion.get(selectedContinent);
                    if (areas != null) {
                        Set<String> uniqueAreas = new HashSet<>(areas);  // Convert to Set to remove duplicates
                        seqGlobalRegionArea.getItems().clear();
                        seqGlobalRegionArea.getItems().addAll(uniqueAreas);
                    } else {
                        seqGlobalRegionArea.getItems().clear();
                        seqGlobalRegionArea.getItems().add("error reading");
                    }
                }
            });

            if (seqClade != null) {
                seqClade.getItems().addAll("Select", "matches", "does not match",
                        "is null", "is not null");
            }

        }

        if (seqCountryOfDevelopmentStatus != null) {
            seqCountryOfDevelopmentStatus.getItems().addAll("Select", "matches", "does not match",
                    "is null", "is not null");
        }

        if(seqCountryOfDevelopmentStatusValues != null) {
            seqCountryOfDevelopmentStatusValues.getItems().addAll("Select", "Developed country", "Developing country",
                    "Least developed country", "Landlocked developing country", "Small island developing state");
        }

        if (seqPlacedSampled != null) {
            seqPlacedSampled.getItems().addAll("Select", "contains", "does not contains", "matches",
                    "does not match", "is null", "is not null");
        }
        if (seqHostSpecies != null) {
            seqHostSpecies.getItems().addAll("Select", "contains", "does not contains", "matches",
                    "does not match", "is null", "is not null");
        }
        if (seqPatent != null) {
            seqPatent.getItems().addAll("Select", "is true", "is false", "is null",
                    "is not null");
        }
        if (seqPubmedID != null) {
            seqPubmedID.getItems().addAll("Select", "contains", "does not contains", "matches",
                    "does not match", "is null", "is not null");
        }
        if (seqIsolateID != null) {
            seqIsolateID.getItems().addAll("Select", "contains", "does not contains", "matches",
                    "does not match", "is null", "is not null");
        }

        Scene newFormScene = new Scene(filterForm);
        Stage newFormStage = new Stage();
        newFormStage.setTitle("Filter");
        newFormStage.setScene(newFormScene);
        newFormStage.show();
    }

    private static boolean checkConfigStatus(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    // Check if the settings file exist and load it to hash
    private static Map<String, String> loadConfigToHash(String filePath) {

        Map<String, String> configMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    configMap.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return configMap;
    }

    public void loadDBForLinux() throws IOException {
        loadModule.getItems().clear();
        loadModule.getItems().addAll("maxLikeHoodGenotyper", "genBankSubmission");
        //Map<String, String> fileCheck = checkConfigFile(globalVariables.pwd + "/" + globalVariables.configFile);
        List<String> proj_list = readProjectFile(globalVariables.pwd + "/" + globalVariables.configDir +
                "/" + "Projects.txt");
        //List<String> dataList = Arrays.asList(projectArray);

        final String[] projs_list = {
                "rabv",
                "flue",
                "cov"
        };

        List<String> command = new ArrayList<>();
        String draftCommand = "source ~/miniconda3/etc/profile.d/conda.sh && conda activate gluetools && java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase; // Replace with the command you want to execute

        command.add("/bin/bash");
        command.add("-c");
        command.add(draftCommand);
        System.out.println("testing command");
        System.out.println(draftCommand);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }
        cmdOutput.setText(output.toString());

        for (String item : proj_list) {
            if (output.toString().contains(item)) {
                System.out.println(item);
                loadPrj.getItems().add(item);
            }
        }
        if (loadPrj.getItems().isEmpty()) {
            if (output.toString().contains("DatabaseConfiguration WARNING")) {
                cmdOutput.setText("Looks like database connection error: To debug, try running\n java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase);
                GlobalFileLogger.getInstance("application.log").write("Looks like database connection error: To debug, try running\n java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase);
            }
        }
    }

    public void loadDBForMac() throws IOException {
        //loadModules();
        loadModule.getItems().clear();
        loadModule.getItems().addAll("maxLikeHoodGenotyper", "genBankSubmission");
        //Map<String, String> fileCheck = checkConfigFile(globalVariables.pwd + "/" + globalVariables.configFile);
        List<String> proj_list = readProjectFile(globalVariables.pwd + "/" + globalVariables.configDir +
                "/" + "Projects.txt");
        //List<String> dataList = Arrays.asList(projectArray);

        final String[] projs_list = {
                "rabv",
                "flue",
                "cov"
        };

        loadPrj.getItems().clear();

        String command = "java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase; // Replace with the command you want to execute
        System.out.println(command);
        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }
        cmdOutput.setText(output.toString());

        for (String item : proj_list) {
            if (output.toString().contains(item)) {
                System.out.println(item);
                loadPrj.getItems().add(item);
            }
        }
        if (loadPrj.getItems().isEmpty()) {
            if (output.toString().contains("DatabaseConfiguration WARNING")) {
                cmdOutput.setText("Looks like database connection error: To debug, try running\n java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase);
                GlobalFileLogger.getInstance("application.log").write("Looks like database connection error: To debug, try running\n java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase);
            }
        }
    }


    private void importSQLForMac() throws  IOException {
        String osType = getOsType(); // Assuming you have a method to get the OS type
        System.out.println(osType);

        String filePath = globalVariables.pwd +
                "/" + globalVariables.configDir +
                "/" + globalVariables.configFile;

        Map<String, String> hash = loadConfigToHash(filePath);
        String sqlDBPath = hash.get("db");

        if (osType.equals("Mac") || osType.equals("Linux")) {
            Platform.runLater(() -> cmdOutput.appendText("Executing MySQL commands, please wait...\n\n"));

            // Create a custom dialog with a PasswordField
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("MySQL Password");
            dialog.setHeaderText("Enter MySQL root password");

            // Set the button types
            ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            // Create the PasswordField
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");

            // Set the content of the dialog to the password field
            GridPane grid = new GridPane();
            grid.add(new Label("Password:"), 0, 0);
            grid.add(passwordField, 1, 0);
            dialog.getDialogPane().setContent(grid);

            // Request focus on the password field by default
            Platform.runLater(() -> passwordField.requestFocus());

            // Convert the result to the password when the login button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return passwordField.getText();
                }
                return null;
            });

            // Wait for user input
            Optional<String> result = dialog.showAndWait();

            // If user provides a password, proceed with the process
            result.ifPresent(password -> {
                new Thread(() -> {
                    String[] command = {
                            "/bin/bash",
                            "-c",
                            "mysql -h localhost --force -u root -p" // MySQL login command
                    };

                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder(command);
                        processBuilder.redirectErrorStream(true); // Merge stdout and stderr

                        Process process = processBuilder.start();

                        // Handle input/output streams
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                        // Sending password entered by the user
                        writer.write(password + "\n");
                        writer.flush();

                        // Send SQL commands
                        String[] sqlCommands = {
                                "DROP DATABASE IF EXISTS GLUE_TOOLS;",
                                "create user 'gluetools'@'localhost' identified by 'Password123#@!';",
                                "create database GLUE_TOOLS character set UTF8;",
                                "grant all privileges on GLUE_TOOLS.* to 'gluetools'@'localhost';",
                                "use GLUE_TOOLS;",
                                "SOURCE " + sqlDBPath + ";"
                        };

                        for (String sqlCommand : sqlCommands) {
                            writer.write(sqlCommand + "\n");
                            writer.flush();
                        }
                        writer.close(); // Close the output stream when done sending commands

                        // Start reading the output and displaying it in real-time
                        String line;
                        StringBuilder sb = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            String finalLine = line;
                            sb.append(finalLine).append("\n");
                            Platform.runLater(() -> cmdOutput.appendText(finalLine + "\n"));
                        }

                        reader.close(); // Close the reader when done

                        // Wait for process to exit and capture exit code
                        int exitCode = process.waitFor();
                        if (exitCode != 0) {
                            Platform.runLater(() -> cmdOutput.appendText("MySQL exited with error code: " + exitCode + "\n"));
                        } else {
                            Platform.runLater(() -> cmdOutput.appendText("MySQL commands executed successfully.\n"));
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        Platform.runLater(() -> cmdOutput.appendText("An error occurred: " + e.getMessage() + "\n"));
                    }
                }).start();
            });
        } else {
            Platform.runLater(() -> alertmsg("Error", "Unsupported OS", "This workflow only supports Mac and Linux.", ""));
        }
    }

    private void importSQLForLinux() throws IOException {
        String osType = getOsType(); // Assuming you have a method to get the OS type
        System.out.println(osType);

        String filePath = globalVariables.pwd + "/" + globalVariables.configDir + "/" + globalVariables.configFile;
        Map<String, String> hash = loadConfigToHash(filePath);
        String sqlDBPath = hash.get("db");

        if (osType.equals("Mac") || osType.equals("Linux")) {
            Platform.runLater(() -> cmdOutput.appendText("Executing MySQL commands, please wait...\n\n"));

            new Thread(() -> {
                try {
                    // Use mysql_config_editor to securely store and access credentials
                    String[] command = {
                            "/bin/bash",
                            "-c",
                            "mysql --login-path=local -h localhost --force" // Using login-path to access credentials
                    };

                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true); // Merge stdout and stderr

                    Process process = processBuilder.start();

                    // Handle input/output streams
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    // Send SQL commands after authentication, all at once
                    String[] sqlCommands = {
                            "DROP DATABASE IF EXISTS GLUE_TOOLS;",
                            "DROP USER IF EXISTS 'gluetools'@'localhost';",
                            "CREATE USER 'gluetools'@'localhost' IDENTIFIED BY 'Password123#@!';",
                            "CREATE DATABASE GLUE_TOOLS CHARACTER SET UTF8;",
                            "GRANT ALL PRIVILEGES ON GLUE_TOOLS.* TO 'gluetools'@'localhost';",
                            "USE GLUE_TOOLS;",
                            "SOURCE " + sqlDBPath + ";"
                    };

                    for (String sqlCommand : sqlCommands) {
                        writer.write(sqlCommand + "\n");
                        writer.flush();
                    }

                    writer.close(); // Close the output stream when done sending commands

                    // Start reading the MySQL output and displaying it in real-time
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String finalLine = line;
                        Platform.runLater(() -> cmdOutput.appendText(finalLine + "\n"));
                    }

                    reader.close(); // Close the reader when done

                    // Wait for process to exit and capture exit code
                    int exitCode = process.waitFor();
                    if (exitCode != 0) {
                        Platform.runLater(() -> cmdOutput.appendText("MySQL exited with error code: " + exitCode + "\n"));
                    } else {
                        Platform.runLater(() -> cmdOutput.appendText("MySQL commands executed successfully.\n"));
                    }

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> cmdOutput.appendText("An error occurred: " + e.getMessage() + "\n"));
                }
            }).start();
        } else {
            Platform.runLater(() -> alertmsg("Error", "Unsupported OS", "This workflow only supports Mac and Linux.", ""));
        }
    }


    @FXML
    private void importSQL(ActionEvent event) throws IOException {
        if (getOsType().equals("Mac")) {
            importSQLForMac();
        }
        else if (getOsType().equals("Linux")) {
            importSQLForLinux();
        }
        else {
            alertmsg("Os not supported", "Limited OS support", "This is " + getOsType() + " is not supported currently" , "");
        }
    }


    @FXML
    private void loadDB(ActionEvent event) throws IOException {
        String osType = getOsType();
        if (osType.equals("Mac")) {
            loadDBForMac();
        }
        else if (osType.equals("Linux")) {
            loadDBForLinux();
        }
        else {
            alertmsg("Os not supported", "Limited OS support", "This is " + osType + " is not supported currently" , "");
        }
    }

    @FXML
    private void browseFasta() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        //FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fasta file", ".*.fasta", "*.fa", "*.fna");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Fasta files (*.fasta, *.fa, *.fna)", "*.fasta", "*.fa", "*.fna");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            browseFastaPath.setText(file.getAbsolutePath());
        } else {
            browseFastaPath.setText("Error loading Fasta file");
            GlobalFileLogger.getInstance("application.log").write("Error loading Fasta file" + "\n");
        }
    }

    @FXML
    private void browseFastaDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");

        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            browseFastaDirPath.setText(selectedDirectory.getAbsolutePath());
        } else {
            browseFastaDirPath.setText("Error loading directory");
            GlobalFileLogger.getInstance("application.log").write("Error loading directory" + "\n");
        }
    }

    @FXML
    private void browseTemplate() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Submission file", "*.sbt");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            browseSbtPath.setText(file.getAbsolutePath());
        } else {
            browseSbtPath.setText("Error loading Fasta directory");
            GlobalFileLogger.getInstance("application.log").write("Error loading Fasta directory" + "\n");
        }
    }

    @FXML
    private void browseTabular() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Submission file", "*.tsv", "*.txt");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            browseTabularPath.setText(file.getAbsolutePath());
        } else {
            browseTabularPath.setText("Error loading tabular directory");
            GlobalFileLogger.getInstance("application.log").write("Error loading tabular directory" + "\n");
        }
    }

    @FXML
    private void browseOutput() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");

        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            browseOutputPath.setText(selectedDirectory.getAbsolutePath());
        } else {
            browseOutputPath.setText("Error loading directory");
            GlobalFileLogger.getInstance("application.log").write("Error loading directory" + "\n");
        }
    }

    //_________________________________________________________________________________________________________________________________________________________________
    // Glue path setting form begins
    public void browseGluePath() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Jar file", "*.jar");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            gluePath.setText(file.getAbsolutePath());
        } else {
            gluePath.setText("Error loading Jar file");
            GlobalFileLogger.getInstance("application.log").write("Error loading Jar file" + "\n");
        }
    }

    public void browseGlueConfig() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XML file", "*.xml");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            glueConfigPath.setText(file.getAbsolutePath());
        } else {
            glueConfigPath.setText("Error loading XML file");
            GlobalFileLogger.getInstance("application.log").write("Error loading XML file" + "\n");
        }
    }

    public void browseGlueDb() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("SQL file", "*.sql");
        fileChooser.getExtensionFilters().add(filter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            glueDatabase.setText(file.getAbsolutePath());
        } else {
            glueDatabase.setText("Error loading SQL file");
            GlobalFileLogger.getInstance("application.log").write("Error loading SQL file" + "\n");
        }
    }

    public void createConfigDir() {
        Path path = Paths.get(globalVariables.pwd + "/" + globalVariables.configDir);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Directory created successfully.");
                GlobalFileLogger.getInstance("application.log").write("Directory created successfully.");
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
                GlobalFileLogger.getInstance("application.log").write("Failed to create directory: " + e.getMessage());
            }
        } else {
            System.out.println("Directory already exists.");
            GlobalFileLogger.getInstance("application.log").write(globalVariables.configDir + " directory already exists.");
        }
    }

    public void glueSaveSetting(ActionEvent event) {
        //check for Settings directory
        createConfigDir();

        Path writeFilePath = Paths.get(globalVariables.pwd + "/" + globalVariables.configDir
                + "/" + globalVariables.configFile);
        System.out.println(writeFilePath);
        File glueJarFile = new File(gluePath.getText());
        File glueConfigFile = new File(glueConfigPath.getText());
        File glueDatabaseFile = new File(glueDatabase.getText());

        List<String> pathToSave = Arrays.asList("jar_file:" + gluePath.getText(),
                "conf_file:" + glueConfigPath.getText(),
                "db:" + glueDatabase.getText());

        if (glueJarFile.exists() && glueConfigFile.exists() && glueDatabaseFile.exists()) {
            try {
                // Write the list of paths to the file
                Files.write(writeFilePath, pathToSave);
            } catch (IOException e) {
                System.out.println("Failed to write paths to file: " + e.getMessage());
                GlobalFileLogger.getInstance("application.log").write("Failed to write paths to file: " + e.getMessage() + "\n");
                alertmsg("Save setting", "Settings", "Failed to write paths to file:",
                        e.getMessage());
            }
            // If success, below code to execute
            String content = "File saved to: " + globalVariables.pwd + "/" + globalVariables.configDir + "/" + "settings.config";
            GlobalFileLogger.getInstance("application.log").write("File saved to: " + globalVariables.pwd + "/" + "settings.config" + "\n");
            alertmsg("Save setting", "Settings", content, "");

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

        } else {
            alertmsg("Save setting", "Settings", "Please make sure all the paths are correct",
                    "");
            GlobalFileLogger.getInstance("application.log").write("Please make sure all the paths are correct" + "\n");
        }
    }

    public void glueSettingsCloseButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    //End of Glue path setting form
    //___________________________________________________________________________________________________________________________________________________________________
    @FXML
    private void menuAbout() {
        alertmsg("About", "RABV-GUI",
                "RABV-GUI v1.0", "");
    }

    //_______________________________________________________________________________________________________________________________________________________________
    //Execute GLUE workflow

    private List<String> glueSelectedModuleFilePath() {
        List<String> moduleInfo = new ArrayList<>();
        String selectedModule = loadModule.getValue();
        if (selectedModule == null) {
            alertmsg("Warning", "Select any of the module from the module list", "", "");
        }
        Map<String, String> modules = loadModuleToHash(globalVariables.pwd + "/" + globalVariables.moduleDir + "/" + globalVariables.moduleFile);

        String moduleFilePath = globalVariables.pwd + "/" + globalVariables.commandsDir + "/" + selectedModule + ".txt";
        moduleInfo.add(moduleFilePath);
        moduleInfo.add(modules.get(selectedModule));
        return moduleInfo;
    }

    //takes care of the {} in the line and replace it with realtime values such as project_name and fasta_file
    private String convertGlueCommand() throws IOException {

        List<String> moduleInfo = glueSelectedModuleFilePath();
        String command = moduleInfo.get(0);
        if ("Single".equals(moduleInfo.get(1))) {
            command = readCommand(moduleInfo.get(0), browseFastaPath.getText(), globalVariables.pwd + "/" + globalVariables.outputDir);
        }
        return command.toString();
    }


    public static String readCommand(String filePath, String fastaFile, String outputDir) throws IOException {
        StringBuilder commandBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().startsWith("#")) {
                    line = line.replace("{fastaFile}", fastaFile);
                    line = line.replace("{outputDir}", outputDir);
                    commandBuilder.append(line).append(System.lineSeparator());
                }
            }
        }
        return commandBuilder.toString();
    }

    private static Integer checkCondaStatus() {
        String[] command = {"bash", "-c", "command -v conda"};
        Integer status = 0;
        try {
            // Execute the command
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Read the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean isCondaInstalled = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains("conda")) {
                    isCondaInstalled = true;
                    break;
                }
            }
            // Check the exit status of the command
            int exitCode = process.waitFor();
            if (exitCode == 0 && isCondaInstalled) {
                status = 1;
                System.out.println("Conda is installed.");
            } else {
                status = 0;
                System.out.println("Conda is not installed.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }

    @FXML
    private void updateModuleStatus(ActionEvent event) {
        String selectedItem = loadModule.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (selectedItem.equals("maxLikeHoodGenotyper")) {
                //browseFastaDirPath.setDisable(true);
                //browseFastaDir.setDisable(true);

                //browseFastaDirPath.setDisable(false);
                //browseFastaDir.setDisable(false);

            } if(selectedItem.equals("genBankSubmission")) {
                //browseFastaPath.setDisable(true);
                //browseFasta.setDisable(true);

                //browseFastaDirPath.setDisable(false);
                //browseFastaDir.setDisable(false);
            }
        }
    }

    public String getOsType() {
        String os = System.getProperty("os.name").toLowerCase();
        String osType = "";
        if (os.contains("win")) {
            osType = "Windows";
        } else if (os.contains("mac")) {
            osType = "Mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            osType = "Linux";
        } else if (os.contains("sunos")) {
            osType = "Solaris";
        } else {
            osType = "Unknown OS";
        }
        return osType;
    }

    public void workflowExecuteForMac() {
        Integer condaStatus = checkCondaStatus();
        String osType = getOsType();
        System.out.println(osType);

        if (condaStatus == 1) {
            String fastaFilePath = browseFastaPath.getText();
            String selectedProject = loadPrj.getValue();
            String selectedModule = loadModule.getValue();
            if (Files.exists(Paths.get(fastaFilePath)) && selectedProject != null && selectedModule != null && osType.equals("Mac") || osType.equals("Linux")) {
                Platform.runLater(() -> cmdOutput.appendText("Executing workflow, please wait...\n\n"));

                Map<String, String> hash = loadConfigToHash(globalVariables.pwd + "/"
                        + globalVariables.configDir + "/" + globalVariables.configFile);



                new Thread(() -> {
                    String[] command = null;
                    //String[] command = {
                    //       "bash", "-c",
                    //       "source activate gluetools && java -jar " + hash.get("jar_file") + " -c " + hash.get("conf_file")
                    //};


                    command = new String[]{
                            "bash", "-c",
                            "source activate gluetools && java -jar " + hash.get("jar_file") + " -c " + hash.get("conf_file")
                    };

                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true);
                    System.out.println("Selected command");
                    System.out.println(Arrays.toString(command));

                    try {
                        Process process = processBuilder.start();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                        String glueCommandFilePath = convertGlueCommand();
                        System.out.println(glueCommandFilePath);

                        //moduleManager(String projectName, String fastaFile, String outputDir, String glueModule)
                        System.out.println("selected module is: " + loadModule.getValue());
                        List<String> items = moduleManager(loadPrj.getValue(), browseFastaPath.getText(), browseOutputPath.getText(), browseTabularPath.getText(), browseSbtPath.getText(), loadModule.getValue(), browseFastaDirPath.getText());
                        System.out.println(items);
                        //List<String> items = readGlueWF(glueCommandFilePath);

                        for (String each_command : items) {
                            writer.write(each_command + "\n");
                            writer.flush();
                        }

                        String line;
                        StringBuilder sb = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            String finalLine = line;
                            sb.append(line + "\n");
                            Platform.runLater(() -> cmdOutput.appendText(finalLine + "\n"));
                        }

                        while ((line = errorReader.readLine()) != null) {
                            System.err.println("Error: " + line);
                            sb.append(line + "\n");
                            String finalLine = line;
                            Platform.runLater(() -> cmdOutput.appendText(finalLine + "\n"));
                        }

                        logWriter(globalVariables.pwd, globalVariables.logDirName, sb.toString());

                        int exitCode = process.waitFor();
                        if (exitCode != 0) {
                            System.err.println("GlueTools exited with error code: " + exitCode);
                            GlobalFileLogger.getInstance("application.log").write("GlueTools exited with error code: " + exitCode + "\n");
                            alertmsg("Error", "Execution error:", "GlueTools exited with error code: ", "");
                            Platform.runLater(() -> cmdOutput.appendText("GlueTools exited with error code: " + exitCode + "\n"));
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        GlobalFileLogger.getInstance("application.log").write(e + "\n");
                    }
                }).start();
            } else {
                alertmsg("Error", "Either fasta file or project or module is not set", "", "");
            }
        }
        else {
            alertmsg("Unable to detect Conda", "Conda not found", "Unable to detect Conda. Please install conda and try again", "");
        }
    }

    public void workflowExecuteForLinux() {
        Integer condaStatus = checkCondaStatus();
        String osType = getOsType();
        System.out.println(osType);

        if (condaStatus == 1) {
            String fastaFilePath = browseFastaPath.getText();
            String selectedProject = loadPrj.getValue();
            String selectedModule = loadModule.getValue();
            if (Files.exists(Paths.get(fastaFilePath)) && selectedProject != null && selectedModule != null && osType.equals("Mac") || osType.equals("Linux")) {
                Platform.runLater(() -> cmdOutput.appendText("Executing workflow, please wait...\n\n"));

                Map<String, String> hash = loadConfigToHash(globalVariables.pwd + "/"
                        + globalVariables.configDir + "/" + globalVariables.configFile);


                new Thread(() -> {
                    String[] command = null;
                    //String[] command = {
                    //       "bash", "-c",
                    //       "source activate gluetools && java -jar " + hash.get("jar_file") + " -c " + hash.get("conf_file")
                    //};

                    //
                    //List<String> command = new ArrayList<>();
                    //String draftCommand = "source ~/miniconda3/etc/profile.d/conda.sh && conda activate gluetools && java -jar " + globalPath.glueFilePath + " -c " + globalPath.glueConfigFilePath + " -i " + commands.loadDataBase; // Replace with the command you want to execute

                    //command.add("/bin/bash");
                    //command.add("-c");
                    //command.add(draftCommand);
                    //.out.println("testing command");
                    //System.out.println(draftCommand);
                    // ProcessBuilder processBuilder = new ProcessBuilder(command);
                    //processBuilder.redirectErrorStream(true);

                    //Process process = processBuilder.start();
                    //
                    command = new String[] {
                            "/bin/bash",
                            "-c",
                            "source ~/miniconda3/etc/profile.d/conda.sh && conda activate gluetools && java -jar " + hash.get("jar_file") + " -c " + hash.get("conf_file")
                    };

                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true);

                    System.out.println("Selected command");
                    System.out.println(Arrays.toString(command));

                    try {
                        Process process = processBuilder.start();
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                        String glueCommandFilePath = convertGlueCommand();
                        System.out.println(glueCommandFilePath);

                        //moduleManager(String projectName, String fastaFile, String outputDir, String glueModule)
                        System.out.println("selected module is: " + loadModule.getValue());
                        List<String> items = moduleManager(loadPrj.getValue(), browseFastaPath.getText(), browseOutputPath.getText(), browseTabularPath.getText(), browseSbtPath.getText(), loadModule.getValue(), browseFastaDirPath.getText());
                        System.out.println(items);
                        //List<String> items = readGlueWF(glueCommandFilePath);

                        for (String each_command : items) {
                            writer.write(each_command + "\n");
                            writer.flush();
                        }

                        String line;
                        StringBuilder sb = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            System.out.println(line);
                            String finalLine = line;
                            sb.append(line + "\n");
                            Platform.runLater(() -> cmdOutput.appendText(finalLine + "\n"));
                        }

                        while ((line = errorReader.readLine()) != null) {
                            System.err.println("Error: " + line);
                            sb.append(line + "\n");
                            String finalLine = line;
                            Platform.runLater(() -> cmdOutput.appendText(finalLine + "\n"));
                        }

                        logWriter(globalVariables.pwd, globalVariables.logDirName, sb.toString());

                        int exitCode = process.waitFor();
                        if (exitCode != 0) {
                            System.err.println("GlueTools exited with error code: " + exitCode);
                            GlobalFileLogger.getInstance("application.log").write("GlueTools exited with error code: " + exitCode + "\n");
                            alertmsg("Error", "Execution error:", "GlueTools exited with error code: ", "");
                            Platform.runLater(() -> cmdOutput.appendText("GlueTools exited with error code: " + exitCode + "\n"));
                        }

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        GlobalFileLogger.getInstance("application.log").write(e + "\n");
                    }
                }).start();
            } else {
                alertmsg("Error", "Either fasta file or project or module is not set", "", "");
            }
        }
        else {
            alertmsg("Unable to detect Conda", "Conda not found", "Unable to detect Conda. Please install conda and try again", "");
        }
    }

    @FXML
    void cmdExecute(ActionEvent event) throws InterruptedException, IOException {
        if (getOsType().contains("Mac")) {
            workflowExecuteForMac();
        } else if (getOsType().contains("Linux")) {
            workflowExecuteForLinux();
        }
    }

    public static Map<String, String> loadModuleToHash(String filePath) {
        Map<String, String> resultMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Ignore lines starting with #
                if (line.startsWith("#")) {
                    continue;
                }

                // Split the line by tab
                String[] columns = line.split("\t");

                // Ensure there are at least two columns
                if (columns.length >= 2) {
                    String key = columns[0];
                    String value = columns[1];
                    resultMap.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            GlobalFileLogger.getInstance("application.log").write("An error occurred: " + e.getMessage() + "\n");
        }
        return resultMap;
    }

    public void alertmsg(String title, String HeaderText, String contentText, String errorMessage) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(HeaderText);
        alert.setContentText(contentText + " " + errorMessage);
        alert.showAndWait();
    }

    public static String glueFilePath() {
        String jarFilePath = "";
        String configFilePath = globalVariables.pwd + "/" + globalVariables.configDir + "/" + globalVariables.configFile;
        if (HelloController.checkConfigStatus(configFilePath)) {
            Map<String, String> hash = loadConfigToHash(configFilePath);
            jarFilePath = hash.get("jar_file");
        }
        return jarFilePath;
    }

    public static String glueConfigFilePath() {
        String settingsFilePath = "";
        String configFilePath = globalVariables.pwd + "/" + globalVariables.configDir + "/" + globalVariables.configFile;
        if (HelloController.checkConfigStatus(configFilePath)) {
            Map<String, String> hash = loadConfigToHash(configFilePath);
            settingsFilePath = hash.get("conf_file");
        }
        return settingsFilePath;
    }

    public static String glueDbPath() {
        String dbFilePath = "";
        String configFilePath = globalVariables.pwd + "/" + globalVariables.configDir + "/" + globalVariables.configFile;
        if (HelloController.checkConfigStatus(configFilePath)) {
            Map<String, String> hash = loadConfigToHash(configFilePath);
            dbFilePath = hash.get("db");
        }
        return dbFilePath;
    }

    public class globalPath {
        public static String configFilePath = globalVariables.pwd + "/" + globalVariables.configDir + "/" + globalVariables.configFile;
        public static String glueFilePath = glueFilePath(); //fileCheck.get("jar_file");
        public static String glueConfigFilePath = glueConfigFilePath(); //fileCheck.get("config_file");
        public static String glueDbPath = glueDbPath(); //fileCheck.get("db");
    }

    public class globalVariables {
        public static String pwd = System.getProperty("user.dir");
        public static String configFile = "settings.config";
        public static String configDir = "Settings";
        public static String logDirName = "Logs";
        public static String moduleDir = "Modules";
        public static String moduleFile = "Modules.tsv";
        public static String commandsDir = "Commands";
        public static String outputDir = "results";
        public static String currentStage = "";
    }

    public class commands {
        public static String loadDataBase = "list project";
        public String loadTestAnalysis = browseFastaPath.toString();
    }

    public static List<String> readProjectFile(String projectFile) {
        GlobalFileLogger.getInstance("application.log").write(
                "Reading Project file from: " + globalVariables.pwd +
                        "/" + "Settings/Projects.txt");

        String filePath = projectFile;
        List<String> projects = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line by commas and add all items to the list
                projects.addAll(Arrays.asList(line.split(",")));
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            GlobalFileLogger.getInstance("application.log").write("An error occurred while reading the file: " + e.getMessage() + "\n");
        }
        System.out.println(projects);
        return projects;
    }

    /* May be to delete once project finish*/
    public static List<String> readGlueWF(String inFile) {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    commands.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            GlobalFileLogger.getInstance("application.log").write(e + "\n");
        }
        return commands;
    }

    public static void logWriter(String dirPath, String baseFileName, String text) {

        Path path = Paths.get(dirPath + "/" + baseFileName);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Dir created: " + path);
                GlobalFileLogger.getInstance("application.log").write("Dir created: " + path + "\n");
                writeLogFile(dirPath, baseFileName, text);

            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
                GlobalFileLogger.getInstance("application.log").write("Failed to create directory: " + e.getMessage() + "\n");
            }
        } else {
            writeLogFile(dirPath, baseFileName, text);
        }
    }

    public static void writeLogFile(String dirPath, String baseFileName, String content) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
        LocalDateTime currentDate = LocalDateTime.now();
        String fileName = baseFileName + "/" + dtf.format(currentDate) + ".txt";
        GlobalFileLogger.getInstance("application.log").write("Writing log file to: " + fileName + "\n");
        String fullPath = Paths.get(dirPath, fileName).toString();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath, true))) {
            writer.write(content);
            writer.newLine(); // Optionally add a newline after the text
        } catch (IOException e) {
            e.printStackTrace();
            GlobalFileLogger.getInstance("application.log").write(e.toString() + "\n");
        }
    }

    public static List<String> modules() {
        List<String> modules = new ArrayList<>();
        modules.add("maxLikeHoodGenotyper");
        modules.add("maxLikeHoodGenotyperWithOutputExtracted");
        modules.add("sequenceReporter");
        modules.add("genBankSubmission");

        return modules;
    }

    public static List<String> moduleManager(String projectName, String fastaFile, String outputDir, String tabularFile, String templateFile, String glueModule, String fastaDir) {
        List<String> moduleCommands;
        //System.out.println("selected module in switch statement: " + glueModule);
        switch (glueModule) {
            case "maxLikeHoodGenotyper":
                moduleCommands = glueCommands.maxLikelihoodGenotyper(projectName, fastaFile);
                break;

            case "maxLikeHoodGenotyperWithOutputExtracted":
                moduleCommands = glueCommands.maxLikelihoodGenotyperWithOutputExtracted(projectName, fastaFile, outputDir);
                break;

            case "sequenceReporter":
                moduleCommands = glueCommands.sequenceReporter(projectName, fastaFile, outputDir);
                break;

            case "genBankSubmission":
                moduleCommands = glueCommands.gbSubmission(projectName, fastaDir, tabularFile, templateFile, outputDir);
                break;

            default:
                moduleCommands = new ArrayList<>();
                break;
        }
        return moduleCommands;
    }

    public void initialize() {
        String configFilePath = globalVariables.pwd + "/" + globalVariables.configDir + "/" + globalVariables.configFile;
        if (!Files.exists(Paths.get(configFilePath))) {
            alertmsg("Warning", "Settings file is empty", "You can set the Setting file from Menu-->Settings-->Set glue and DB", "");
        }
    }

    public static class viewTable {
        private final String sequence_id;
        private final String gb_create_date;
        private final String gb_update_date;
        private final String gb_length;
        private final String isolate;
        private final String gb_country;
        private final String host;
        private final String collection_year;
        private final String gb_pubmed_id;
        private final String maddog_lineage;
        private final String major_clade;
        private final String minor_clade;

        public viewTable(String sequence_id, String gb_create_date, String gb_update_date, String gb_length, String isolate, String gb_country, String host, String collection_year, String gb_pubmed_id, String maddog_lineage, String major_clade, String minor_clade) {
            this.sequence_id = sequence_id;
            this.gb_create_date = gb_create_date;
            this.gb_update_date = gb_update_date;
            this.gb_length = gb_length;
            this.isolate = isolate;
            this.gb_country = gb_country;
            this.host = host;
            this.collection_year = collection_year;
            this.gb_pubmed_id = gb_pubmed_id;
            this.maddog_lineage = maddog_lineage;
            this.major_clade = major_clade;
            this.minor_clade = minor_clade;
        }

        public String getSequence_id() {
            return sequence_id;
        }

        public String getGb_create_date() {
            return gb_create_date;
        }

        public String getGb_update_date() {
            return gb_update_date;
        }

        public String getGb_length() {
            return gb_length;
        }

        public String getIsolate() {
            return isolate;
        }

        public String getGb_country() {
            return gb_country;
        }

        public String getHost() {
            return host;
        }

        public String getCollection_year() {
            return collection_year;
        }

        public String getGb_pubmed_id() {
            return gb_pubmed_id;
        }

        public String getMaddog_lineage() {
            return maddog_lineage;
        }

        public String getMajor_clade() {
            return major_clade;
        }

        public String getMinor_clade() {
            return minor_clade;
        }
    }

    public void filterAllSeqCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void filterAllSeqClear(ActionEvent event) {
        alertmsg("Warning", "Function not avilable", "Clear function is under development, please use the latest verion", "");
    }

    public void viewCompleteTable() {
        ObservableList<viewTable> data = FXCollections.observableArrayList();
        int totalSequences = 0;
        try {
            ResultSet rs = dbConnection.fetchAllSequences();
            // System.out.print(rs.getString("sequence_id"));
            while (rs.next()) {
                data.add(new viewTable(rs.getString("sequence_id"),
                        rs.getString("gb_create_date"),
                        rs.getString("gb_update_date"),
                        rs.getString("gb_length"),
                        rs.getString("isolate"),
                        rs.getString("gb_country"),
                        rs.getString("host"),
                        rs.getString("collection_year"),
                        rs.getString("gb_pubmed_id"),
                        rs.getString("maddog_lineage"),
                        rs.getString("major_clade"),
                        rs.getString("minor_clade")));
                totalSequences++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertmsg("ERROR connecting", "SQLException", "", e.toString());
        }

        tableView.setItems(data);
        column1.setCellValueFactory(new PropertyValueFactory<>("sequence_id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("gb_create_date"));
        column3.setCellValueFactory(new PropertyValueFactory<>("gb_update_date"));
        column4.setCellValueFactory(new PropertyValueFactory<>("gb_length"));
        column5.setCellValueFactory(new PropertyValueFactory<>("isolate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("gb_country"));
        column7.setCellValueFactory(new PropertyValueFactory<>("host"));
        column8.setCellValueFactory(new PropertyValueFactory<>("collection_year"));
        column9.setCellValueFactory(new PropertyValueFactory<>("gb_pubmed_id"));
        column10.setCellValueFactory(new PropertyValueFactory<>("maddog_lineage"));
        column11.setCellValueFactory(new PropertyValueFactory<>("major_clade"));
        column12.setCellValueFactory(new PropertyValueFactory<>("minor_clade"));
        totalEntriesOnTable.setText(String.valueOf("Total entries: " + totalSequences));
        //tableView.setItems(data);
    }

    public void populateViewClades() {
        if (cladeMap.size() < 1) {
            ObservableList<String> clades = FXCollections.observableArrayList();
            List<String> allCladesList = new ArrayList<>();

            try {
                ResultSet rs = dbConnection.fetchDistinctClades();
                while (rs.next()) {
                    allCladesList.add(rs.getString("display_name"));
                }
                for (String clade : allCladesList) {
                    if (clade != null && !clade.trim().isEmpty()) {
                        String[] splitClade = clade.split(" ", 2);
                        String key = splitClade[0];
                        String value = splitClade.length > 1 ? splitClade[1] : "";
                        if (cladeMap.containsKey(key)) {
                            String existingValue = cladeMap.get(key);
                            if (existingValue.length() > 1) {
                                cladeMap.put(key, existingValue + ", " + value);
                            } else {
                                cladeMap.put(key, value);
                            }
                        } else {
                            cladeMap.put(key, value);
                        }
                    }
                }
                for (Map.Entry<String, String> entry : cladeMap.entrySet()) {
                    System.out.println(entry.getKey());
                    clades.add(entry.getKey());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                alertmsg("ERROR connecting", "SQLException", "", e.toString());
            }
            viewClades.setItems(clades);
        } else {
            //do nothing
        }
    }

    @FXML
    private void handleComboBoxClade(ActionEvent event) {
        viewSubClades.getItems().clear();
        String selectedItem = viewClades.getSelectionModel().getSelectedItem();
        viewSubClades.getItems().clear();
        //System.out.println("Selected item: " + selectedItem);
        updateViewSubClades(selectedItem);
    }

    @FXML
    private void handleComboBoxSubClade(ActionEvent event) {
        String clade = viewClades.getSelectionModel().getSelectedItem();
        String subClade = viewSubClades.getSelectionModel().getSelectedItem();
        String mergeCladeSubClade = "AL_" + clade + "_" + subClade;
        viewTableByClade(mergeCladeSubClade);
        //alertmsg("", "", "", mergeCladeSubClade);
    }

    private void updateViewSubClades(String selectedClade) {
        ObservableList<String> subClades = FXCollections.observableArrayList();
        String subCladesString = cladeMap.get(selectedClade);
        System.out.println(cladeMap.get(selectedClade));
        if (subCladesString != null && !subCladesString.trim().isEmpty()) {
            viewSubClades.setDisable(false);
            String[] subCladesArray = subCladesString.split(", ");
            subClades.addAll(Arrays.asList(subCladesArray));
            viewSubClades.setItems(subClades);
        } else {
            viewSubClades.setDisable(true);
            viewTableByClade("AL_" + viewClades.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void showQuery() {
        if (viewSequences.getValue().equals("All sequences")) {
            viewCompleteTable();
            viewClades.setDisable(true);
            viewSubClades.setDisable(true);
        } else {
            viewClades.setDisable(false);
            tableView.getItems().clear();
            populateViewClades();
        }
    }

    @FXML
    public void viewSqlTable(ActionEvent event) throws Exception {
        viewCompleteTable();
    }

    public void viewTableByClade(String alignment) {
        ObservableList<viewTable> data = FXCollections.observableArrayList();
        int totalSequences = 0;
        try {
            ResultSet rs = dbConnection.fetchByClades(alignment);
            // System.out.print(rs.getString("sequence_id"));
            while (rs.next()) {
                data.add(new viewTable(rs.getString("sequence_id"),
                        rs.getString("gb_create_date"),
                        rs.getString("gb_update_date"),
                        rs.getString("gb_length"),
                        rs.getString("isolate"),
                        rs.getString("gb_country"),
                        rs.getString("host"),
                        rs.getString("collection_year"),
                        rs.getString("gb_pubmed_id"),
                        rs.getString("maddog_lineage"),
                        rs.getString("major_clade"),
                        rs.getString("minor_clade")));
                totalSequences++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertmsg("ERROR connecting", "SQLException", "", e.toString());
        }

        tableView.setItems(data);
        column1.setCellValueFactory(new PropertyValueFactory<>("sequence_id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("gb_create_date"));
        column3.setCellValueFactory(new PropertyValueFactory<>("gb_update_date"));
        column4.setCellValueFactory(new PropertyValueFactory<>("gb_length"));
        column5.setCellValueFactory(new PropertyValueFactory<>("isolate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("gb_country"));
        column7.setCellValueFactory(new PropertyValueFactory<>("host"));
        column8.setCellValueFactory(new PropertyValueFactory<>("collection_year"));
        column9.setCellValueFactory(new PropertyValueFactory<>("gb_pubmed_id"));
        column10.setCellValueFactory(new PropertyValueFactory<>("maddog_lineage"));
        column11.setCellValueFactory(new PropertyValueFactory<>("major_clade"));
        column12.setCellValueFactory(new PropertyValueFactory<>("minor_clade"));
        totalEntriesOnTable.setText(String.valueOf("Total entries: " + totalSequences));
        //tableView.setItems(data);
    }

    public static Map<String, List<String>> loadM49Countries() {
        String filePath = "meta_data/rabv_m49_country.tsv";
        //Map<String, List<String>> regionSubRegionMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                if (values.length >= 2) {
                    String m49RegionId = values[8];
                    String m49SubRegionId = values[9];
                    globalRegionMap.computeIfAbsent(m49RegionId, k -> new ArrayList<>()).add(m49SubRegionId);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return globalRegionMap;
    }

    private List<List<String>> filterAllSequences() {

        //add the actual column name and add <stringToSQLCode> (completed) function which converts the regular language to SQL language
        String query = "testing";
        Map<String, String> sqlCodeMap =  stringToSQLCode();
        List<List<String>> queryConditions = new ArrayList<>();
        if (filterAllSeqNucID.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqNucID.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqNucID.getText() != null && !allSeqNucID.getText().isEmpty()) {

            String columnName = "sequence_id";
            String condition = sqlCodeMap.get(filterAllSeqNucID.getSelectionModel().getSelectedItem());
            String value = allSeqNucID.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }
        if (filterAllSeqSeqLen.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqSeqLen.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqSeqLen.getText() != null &&
                !allSeqSeqLen.getText().isEmpty()) {

            String columnName = "gb_length";
            String condition = sqlCodeMap.get(filterAllSeqSeqLen.getSelectionModel().getSelectedItem());
            String value = allSeqSeqLen.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }
        if (filterAllSeqEntryCreationDate.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqEntryCreationDate.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqEntryCreationDateDay.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqEntryCreationDateDay.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqEntryCreationDateMonth.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqEntryCreationDateMonth.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqEntryCreationDateYear.getText() != null &&
                !filterAllSeqEntryCreationDateYear.getText().isEmpty()) {

            String columnName = "gb_create_date";
            String condition = sqlCodeMap.get(filterAllSeqEntryCreationDate.getSelectionModel().getSelectedItem());
            String valueDay = filterAllSeqEntryCreationDateDay.getSelectionModel().getSelectedItem();
            String valueMonth = filterAllSeqEntryCreationDateMonth.getSelectionModel().getSelectedItem();
            String valueYear = filterAllSeqEntryCreationDateYear.getText();
            String value = valueDay + "-" + valueMonth + "-" + valueYear;
            queryConditions.add(List.of(columnName, condition, value));
        }

        if (filterAllSeqLastUpdateDate.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqLastUpdateDate.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqLastUpdateDateDay.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqLastUpdateDateDay.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqLastUpdateDateMonth.getSelectionModel().getSelectedItem() != null &&
                !filterAllSeqLastUpdateDateMonth.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqLastUpdateDateYear.getText() != null &&
                !filterAllSeqLastUpdateDateYear.getText().isEmpty()) {

            String columnName = "gb_update_date";
            String condition = sqlCodeMap.get(filterAllSeqLastUpdateDate.getSelectionModel().getSelectedItem());
            String valueDay = filterAllSeqLastUpdateDateDay.getSelectionModel().getSelectedItem();
            String valueMonth = filterAllSeqLastUpdateDateMonth.getSelectionModel().getSelectedItem();
            String valueYear = filterAllSeqLastUpdateDateYear.getText();
            String value = valueDay + "-" + valueMonth + "-" + valueYear;
            queryConditions.add(List.of(columnName, condition, value));
        }

        if (filterAllSeqEarliestCollectionYear.getSelectionModel().getSelectedItem() !=null &&
            !filterAllSeqEarliestCollectionYear.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqEarliestCollectionYear.getText() !=null &&
                !allSeqEarliestCollectionYear.getText().isEmpty()) {

            String columnName = "earliest_collection_year";
            String condition = sqlCodeMap.get(filterAllSeqEarliestCollectionYear.getSelectionModel().getSelectedItem());
            String value = allSeqEarliestCollectionYear.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }

        if (filterAllSeqLatestCollectionYear.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqLatestCollectionYear.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqLatestCollectionYear.getText() !=null &&
                !allSeqLatestCollectionYear.getText().isEmpty()) {

            String columnName = "latest_collection_year";
            String condition = sqlCodeMap.get(filterAllSeqLatestCollectionYear.getSelectionModel().getSelectedItem());
            String value = allSeqLatestCollectionYear.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }
        if (filterAllSeqCountryOfOrigin.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqCountryOfOrigin.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqCountryOfOrigin.getText() !=null &&
                !allSeqCountryOfOrigin.getText().isEmpty()) {

            String columnName = "gb_country";
            String condition = sqlCodeMap.get(filterAllSeqCountryOfOrigin.getSelectionModel().getSelectedItem());
            String value = allSeqCountryOfOrigin.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }

        // might need to write different SQL query to check its continent and area
        if (filterAllSeqGlobalRegion.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqGlobalRegion.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqGlobalRegionContinent.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqGlobalRegionContinent.getSelectionModel().getSelectedItem().equals("select") &&
                filterAllSeqGlobalRegionArea.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqGlobalRegionArea.getSelectionModel().getSelectedItem().equals("select")) {

            String columnName = "m49_region_id";
            String condition = sqlCodeMap.get(filterAllSeqGlobalRegion.getSelectionModel().getSelectedItem());
            String valueContinent = filterAllSeqGlobalRegionContinent.getSelectionModel().getSelectedItem();
            String valueArea = filterAllSeqGlobalRegionArea.getSelectionModel().getSelectedItem();
            queryConditions.add(List.of(columnName, condition, valueContinent, valueArea));
        }

        if (filterAllSeqClade.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqClade.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqClade.getText() !=null &&
                !allSeqClade.getText().isEmpty()) {

            String columnName = "major_clade";
            String condition = sqlCodeMap.get(filterAllSeqClade.getSelectionModel().getSelectedItem());
            String value = allSeqClade.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }


        if (filterAllSeqCountryOfDevelopmentStatus.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqCountryOfDevelopmentStatus.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqCountryOfDevelopmentStatus.getSelectionModel().getSelectedItem() !=null &&
                !allSeqCountryOfDevelopmentStatus.getSelectionModel().getSelectedItem().equals("select")) {

            // to be checked again and added
            String columnName = "gb_country";
            String condition = sqlCodeMap.get(filterAllSeqCountryOfDevelopmentStatus.getSelectionModel().getSelectedItem());
            String value = allSeqCountryOfDevelopmentStatus.getSelectionModel().getSelectedItem();
            queryConditions.add(List.of(columnName, condition, value));
        }

        if (filterAllSeqPlacedSampled.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqPlacedSampled.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqPlacedSampled.getText() !=null &&
                !allSeqPlacedSampled.getText().isEmpty()) {

            String columnName = "gb_place_sampled";
            String condition = sqlCodeMap.get(filterAllSeqPlacedSampled.getSelectionModel().getSelectedItem());
            String value = allSeqPlacedSampled.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }

        if (filterAllSeqHostSpecies.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqHostSpecies.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqHostSpecies.getText() !=null &&
                !allSeqHostSpecies.getText().isEmpty()) {

            String columnName = "host";
            String condition = sqlCodeMap.get(filterAllSeqHostSpecies.getSelectionModel().getSelectedItem());
            String value =  allSeqHostSpecies.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }

        if (filterAllSeqPatent.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqPatent.getSelectionModel().getSelectedItem().equals("select")) {

            String columnName = "patent_related";
            String condition = sqlCodeMap.get(filterAllSeqPatent.getSelectionModel().getSelectedItem());
            queryConditions.add(List.of(columnName, condition));
        }

        if (filterAllSeqPubmedID.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqPubmedID.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqPubmedID.getText() !=null &&
                !allSeqPubmedID.getText().isEmpty()) {

            String columnName = "gb_pubmed_id";
            String condition = sqlCodeMap.get(filterAllSeqPubmedID.getSelectionModel().getSelectedItem());
            String value =  allSeqPubmedID.getText();
            queryConditions.add(List.of(columnName, condition, value));

        }

        if (filterAllSeqIsolateID.getSelectionModel().getSelectedItem() !=null &&
                !filterAllSeqIsolateID.getSelectionModel().getSelectedItem().equals("select") &&
                allSeqIsolateID.getText() !=null &&
                !allSeqIsolateID.getText().isEmpty()) {

            String columnName = "isolate";
            String condition = sqlCodeMap.get(filterAllSeqIsolateID.getSelectionModel().getSelectedItem());
            String value =   allSeqIsolateID.getText();
            queryConditions.add(List.of(columnName, condition, value));
        }

        return queryConditions;

    }

    @FXML
    public void  queryAllSeqFilters(ActionEvent event) {
        System.out.println("Query sort testing " + sortQueryList(filterAllSequences()));
        String query = processQueries(filterAllSequences()); //filterAllSequences();
        if (mainController != null && query != null && !query.isEmpty()) {
            mainController.setFilterLabel(query);
            mainController.setFilterTableView(query);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setFilterLabel(String text) {
        if (filtersApplied != null) {
            filtersApplied.setText(text);
            }
        }

    public void setFilterTableView(String text) {
        System.out.println("testing " + text);
        if (tableView != null) {
            if (text.contains("m49_region_id")) {
                viewFilteredTableGbCountry(text);
            }
            else {
                viewFilteredTable(text);
            }
         }
    }

    public static Map<String, String> stringToSQLCode () {
        Map<String, String> sqlMap = new HashMap<>();
        sqlMap.put("contains", "LIKE");
        sqlMap.put("does not contains" , "NOT LIKE");
        sqlMap.put("matches", "=");
        sqlMap.put("does not match", "NOT LIKE"); // confirm
        sqlMap.put("is null", "IS NULL");
        sqlMap.put("is not null", "IS NOT NULL");
        sqlMap.put("date equals", "=");
        sqlMap.put("date does not equals", "<>");
        sqlMap.put("after", ">");
        sqlMap.put("on or after" ,">=");
        sqlMap.put("on or before", "<=");
        sqlMap.put("equals", "=");
        sqlMap.put("does not equals", "!=");
        sqlMap.put(">", ">");
        sqlMap.put("<", "<");
        sqlMap.put("<=", "<=");
        sqlMap.put(">=", ">=");
        for (int i = 1; i <= 31; i++) {
            sqlMap.put(String.valueOf(i), String.valueOf(i));
        }
        sqlMap.put("Jan", "January");
        sqlMap.put("Feb", "February");
        sqlMap.put("Mar", "March");
        sqlMap.put("Apr", "April");
        sqlMap.put("May", "May");
        sqlMap.put("June", "June");
        sqlMap.put("Jul", "July");
        sqlMap.put("Aug", "August");
        sqlMap.put("Sep", "September");
        sqlMap.put("Oct", "October");
        sqlMap.put("Nov", "November");
        sqlMap.put("Dec", "December");
        sqlMap.put("is true", "1");
        sqlMap.put("is false", "0");
        return sqlMap;
    }

    public String processQueries(List<List<String>> queryConditions) {
        StringBuilder queryBuilder = new StringBuilder();
        String condition = "";
        String column = "";
        String value = "";
        String extendedValue = "";
        List<List<String>> sortedQueryCondition = sortQueryList(queryConditions);
        System.out.println("Sorted query " + sortedQueryCondition);

        for (int i = 0; i < sortedQueryCondition.size(); i++) {
            List<String> arrayItems = sortedQueryCondition.get(i);

            column = arrayItems.get(0);
            System.out.println(column);
            // comment from here if you want to show old working code
            if (column.equals("m49_region_id")) {
                condition = arrayItems.get(1);
                value = arrayItems.get(2);
                extendedValue = arrayItems.get(3);

                queryBuilder.append(column).append("=").append("'");
                queryBuilder.append(value).append("").append("' ");
                queryBuilder.append("AND").append(" ");
                queryBuilder.append("m49_sub_region_id").append("=").append("'");
                queryBuilder.append(extendedValue).append("").append("'").append(")").append(" ");
            }
            else if (column.equals("patent_related")) {

                column = arrayItems.get(0);
                condition = arrayItems.get(1);

                queryBuilder.append(column).append(" ").append("=");
                queryBuilder.append(condition).append(" ");

            }
            else {
                condition = arrayItems.get(1);
                value =  "'" + arrayItems.get(2) + "'";

                queryBuilder.append(column).append(" ");
                queryBuilder.append(condition).append(" ");
                queryBuilder.append(value).append(" ");
            }

            if (i < queryConditions.size() - 1) {
                queryBuilder.append("AND").append(" ");
            }
        }
        return queryBuilder.toString();
        }

        public List<List<String>> sortQueryList(List<List<String>> queryList) {
            Collections.sort(queryList, new Comparator<List<String>>() {
                @Override
                public int compare(List<String> o1, List<String> o2) {
                    boolean containsM49RegionId1 = o1.contains("m49_region_id");
                    boolean containsM49RegionId2 = o2.contains("m49_region_id");

                    // If o1 contains "m49_region_id" and o2 doesn't, o1 comes first
                    if (containsM49RegionId1 && !containsM49RegionId2) {
                        return -1;
                    }
                    // If o2 contains "m49_region_id" and o1 doesn't, o2 comes first
                    else if (!containsM49RegionId1 && containsM49RegionId2) {
                        return 1;
                    }
                    // Otherwise, maintain the original order (no swap)
                    else {
                        return 0;
                    }
                }
            });

            // Print the sorted list
            for (List<String> list : queryList) {
                System.out.println(list);
            }
            return queryList;
        }


    public void viewFilteredTable(String filters) {

        ObservableList<viewTable> data = FXCollections.observableArrayList();
        int totalSequences = 0;
        try {
            ResultSet rs = dbConnection.filterAllSequences(filters);
            // System.out.print(rs.getString("sequence_id"));
            while (rs.next()) {
                data.add(new viewTable(rs.getString("sequence_id"),
                        rs.getString("gb_create_date"),
                        rs.getString("gb_update_date"),
                        rs.getString("gb_length"),
                        rs.getString("isolate"),
                        rs.getString("gb_country"),
                        rs.getString("host"),
                        rs.getString("collection_year"),
                        rs.getString("gb_pubmed_id"),
                        rs.getString("maddog_lineage"),
                        rs.getString("major_clade"),
                        rs.getString("minor_clade")));
                totalSequences++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertmsg("ERROR connecting", "SQLException", "", e.toString());
        }

        tableView.setItems(data);
        column1.setCellValueFactory(new PropertyValueFactory<>("sequence_id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("gb_create_date"));
        column3.setCellValueFactory(new PropertyValueFactory<>("gb_update_date"));
        column4.setCellValueFactory(new PropertyValueFactory<>("gb_length"));
        column5.setCellValueFactory(new PropertyValueFactory<>("isolate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("gb_country"));
        column7.setCellValueFactory(new PropertyValueFactory<>("host"));
        column8.setCellValueFactory(new PropertyValueFactory<>("collection_year"));
        column9.setCellValueFactory(new PropertyValueFactory<>("gb_pubmed_id"));
        column10.setCellValueFactory(new PropertyValueFactory<>("maddog_lineage"));
        column11.setCellValueFactory(new PropertyValueFactory<>("major_clade"));
        column12.setCellValueFactory(new PropertyValueFactory<>("minor_clade"));
        totalEntriesOnTable.setText(String.valueOf("Total entries: " + totalSequences));
        //tableView.setItems(data);
    }

    public void viewFilteredTableGbCountry(String filters) {

        ObservableList<viewTable> data = FXCollections.observableArrayList();
        int totalSequences = 0;
        try {
            ResultSet rs = dbConnection.filterAllForGlobalRegion(filters);
            // System.out.print(rs.getString("sequence_id"));
            while (rs.next()) {
                data.add(new viewTable(rs.getString("sequence_id"),
                        rs.getString("gb_create_date"),
                        rs.getString("gb_update_date"),
                        rs.getString("gb_length"),
                        rs.getString("isolate"),
                        rs.getString("gb_country"),
                        rs.getString("host"),
                        rs.getString("collection_year"),
                        rs.getString("gb_pubmed_id"),
                        rs.getString("maddog_lineage"),
                        rs.getString("major_clade"),
                        rs.getString("minor_clade")));
                totalSequences++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            alertmsg("ERROR connecting", "SQLException", "", e.toString());
        }

        tableView.setItems(data);
        column1.setCellValueFactory(new PropertyValueFactory<>("sequence_id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("gb_create_date"));
        column3.setCellValueFactory(new PropertyValueFactory<>("gb_update_date"));
        column4.setCellValueFactory(new PropertyValueFactory<>("gb_length"));
        column5.setCellValueFactory(new PropertyValueFactory<>("isolate"));
        column6.setCellValueFactory(new PropertyValueFactory<>("gb_country"));
        column7.setCellValueFactory(new PropertyValueFactory<>("host"));
        column8.setCellValueFactory(new PropertyValueFactory<>("collection_year"));
        column9.setCellValueFactory(new PropertyValueFactory<>("gb_pubmed_id"));
        column10.setCellValueFactory(new PropertyValueFactory<>("maddog_lineage"));
        column11.setCellValueFactory(new PropertyValueFactory<>("major_clade"));
        column12.setCellValueFactory(new PropertyValueFactory<>("minor_clade"));
        totalEntriesOnTable.setText(String.valueOf("Total entries: " + totalSequences));
        //tableView.setItems(data);
    }

    @FXML
    public void queryTextBox(ActionEvent event)  {
        /*
        System.out.println("Query sort testing " + sortQueryList(filterAllSequences()));
        String query = processQueries(filterAllSequences()); //filterAllSequences();
        if (mainController != null && query != null && !query.isEmpty()) {
            mainController.setFilterLabel(query);
            mainController.setFilterTableView(query);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();*/
        alertmsg("Function disabled", "This function is disabled", "This function will be available in next version", "");
    }

    public void saveTableViewToFile(TableView<viewTable> tableView, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the headers
            writer.write("sequence_id\tgb_create_date\tgb_update_date\tgb_length\tisolate\tgb_country\thost\tcollection_year\tgb_pubmed_id\tmaddog_lineage\tmajor_clade\tminor_clade");
            writer.newLine();

            // Write the data rows
            for (viewTable item : tableView.getItems()) {
                writer.write(item.getSequence_id() + "\t" +
                        item.getGb_create_date() + "\t" +
                        item.getGb_update_date() + "\t" +
                        item.getGb_length() + "\t" +
                        item.getIsolate() + "\t" +
                        item.getGb_country() + "\t" +
                        item.getHost() + "\t" +
                        item.getCollection_year() + "\t" +
                        item.getGb_pubmed_id() + "\t" +
                        item.getMaddog_lineage() + "\t" +
                        item.getMajor_clade() + "\t" +
                        item.getMinor_clade());
                writer.newLine();
            }

            // Inform user that the file was saved successfully
            alertmsg("Data saved", "Data saved successfully", "Data saved to: output.txt", "");
            System.out.println("Data has been saved to " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
            alertmsg("ERROR saving file", "IOException", "", e.toString());
        }
    }

    @FXML
    public void downloadMetaData() {
        if (tableView != null && !tableView.getItems().isEmpty()) {
            saveTableViewToFile(tableView, "output.txt");
        } else {
            alertmsg("Empty table", "No data to save", "Table view is empty", "");
        }
    }

    }
