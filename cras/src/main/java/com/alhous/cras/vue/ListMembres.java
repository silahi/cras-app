package com.alhous.cras.vue;

/**
 *
 * @author Alhoussaine
 */
import com.alhous.cras.model.Membre;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ListMembres extends StackPane {

    private final TableView<Membre> tableView;
    private final List<Membre> membres;
    private final Button viewButton;
    private final Button editButton;
    private final Button deleteButton;
    private final TextField searchField;

    public ListMembres(Pane pane, TextField searchField) throws IOException, CsvValidationException {
        // Create the TableView
        tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: #F1F1F1; -fx-text-fill:#333333 -fx-font-size: 12px;");
        this.searchField = searchField;
        searchField.setPromptText("Recherche ...");

        // Create a listener for the searchField's textProperty
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateTableView(newValue);
        });

        // Create TableColumn instances for each property of the Membre class
        TableColumn<Membre, String> horodateurColumn = new TableColumn<>("Horodateur");
        horodateurColumn.setCellValueFactory(new PropertyValueFactory<>("horodateur"));

        TableColumn<Membre, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Membre, String> prenomColumn = new TableColumn<>("Prénom");
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<Membre, String> numeroTelephoneColumn = new TableColumn<>("Numéro de téléphone");
        numeroTelephoneColumn.setCellValueFactory(new PropertyValueFactory<>("numeroTelephone"));

        TableColumn<Membre, LocalDate> dateNaissanceColumn = new TableColumn<>("Date de naissance");
        dateNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        TableColumn<Membre, String> sexeColumn = new TableColumn<>("Sexe");
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));

        TableColumn<Membre, String> professionEtudesColumn = new TableColumn<>("Profession/Études");
        professionEtudesColumn.setCellValueFactory(new PropertyValueFactory<>("professionEtudes"));

        TableColumn<Membre, String> niveauScolaireColumn = new TableColumn<>("Niveau Scolaire");
        niveauScolaireColumn.setCellValueFactory(new PropertyValueFactory<>("niveauScolaire"));

        TableColumn<Membre, String> lieuNaissanceColumn = new TableColumn<>("Lieu de naissance");
        lieuNaissanceColumn.setCellValueFactory(new PropertyValueFactory<>("lieuNaissance"));

        TableColumn<Membre, String> numeroPasseportColumn = new TableColumn<>("Numéro de passeport");
        numeroPasseportColumn.setCellValueFactory(new PropertyValueFactory<>("numeroPasseport"));

        TableColumn<Membre, Boolean> estConfirmeColumn = new TableColumn<>("CONFIRMÉE (OUI / NON)");
        estConfirmeColumn.setCellValueFactory(new PropertyValueFactory<>("estConfirme"));

        TableColumn<Membre, Integer> montantColumn = new TableColumn<>("Montant");
        montantColumn.setCellValueFactory(new PropertyValueFactory<>("montant"));

        // Add columns to the TableView
        tableView.getColumns().addAll(
                horodateurColumn, nomColumn, prenomColumn, numeroTelephoneColumn,
                dateNaissanceColumn, sexeColumn, professionEtudesColumn, niveauScolaireColumn,
                lieuNaissanceColumn, numeroPasseportColumn, estConfirmeColumn, montantColumn
        );

        // Read the CSV file and populate the TableView  
        membres = readCSVFile(getClass().getResource("/files/formulaire.csv").getFile());
        tableView.getItems().addAll(membres);

        // Create buttons for CRUD operations
        var vIco = new ImageView(getLocalImage("/images/info.png"));
        viewButton = new Button("Détails", vIco);
        var eIco = new ImageView(getLocalImage("/images/edit.png"));
        editButton = new Button("Modifier", eIco);
        var dIco = new ImageView(getLocalImage("/images/delete.png"));
        deleteButton = new Button("Supprimer", dIco);

        // Create a button to add a new member
        var addIco = new ImageView(getLocalImage("/images/add.png"));
        Button addButton = new Button("Ajouter", addIco);
        addButton.setOnAction(e -> showAddMemberForm());

        // Set the buttons initially disabled
        viewButton.setDisable(true);
        editButton.setDisable(true);
        deleteButton.setDisable(true); 

        Arrays.asList(addButton, viewButton, editButton, deleteButton).forEach(b -> {
            b.setStyle("-fx-background-color: #ffffff;");
        });

        // Set event handlers for the buttons
        viewButton.setOnAction(e -> viewMember());
        editButton.setOnAction(e -> editMember());
        deleteButton.setOnAction(e -> deleteMember());

        // Create a listener for the TableView's selection model
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                viewButton.setDisable(false);
                editButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                viewButton.setDisable(true);
                editButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });

        // Create a layout for the buttons
        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(addButton, viewButton, editButton, deleteButton);
        pane.getChildren().add(buttonLayout);
        // Create the layout for the scene with the TableView and buttons
        // Create the layout for the scene with the TableView
        getChildren().add(tableView);
    }

    public final Image getLocalImage(String imagePath) {
        return new Image(getClass().getResourceAsStream(imagePath));
    }

    private void showAddMemberForm() {
        // Create a Dialog to display the add member form
        Dialog<Membre> dialog = new Dialog<>();
        dialog.setTitle("Ajouter un membre");
        dialog.setHeaderText(null);

        // Create a GridPane to layout the form controls
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Create TextFields and other controls for each attribute of the Membre class
        TextField horodateurField = new TextField();
        TextField nomField = new TextField();
        TextField prenomField = new TextField();
        TextField numeroTelephoneField = new TextField();
        DatePicker dateNaissancePicker = new DatePicker();
        ComboBox<String> sexeComboBox = new ComboBox<>();
        sexeComboBox.getItems().addAll("Homme", "Femme");
        sexeComboBox.setValue("Homme");
        TextField professionEtudesField = new TextField();
        TextField niveauScolaireField = new TextField();
        TextField lieuNaissanceField = new TextField();
        TextField numeroPasseportField = new TextField();
        TextField estConfirmeField = new TextField();
        TextField montantField = new TextField();

        grid.add(nomField, 0, 0);
        grid.add(prenomField, 1, 0);
        grid.add(numeroTelephoneField, 0, 1);
        grid.add(dateNaissancePicker, 1, 1);
        grid.add(sexeComboBox, 0, 2);
        grid.add(professionEtudesField, 1, 2);
        grid.add(niveauScolaireField, 0, 3);
        grid.add(lieuNaissanceField, 1, 3);
        grid.add(numeroPasseportField, 0, 4);
        grid.add(montantField, 1, 4);

        // Add the controls to the GridPane
        // ...
        // Set the content of the Dialog to the GridPane
        dialog.getDialogPane().setContent(grid);

        // Add a button to the Dialog to handle the "Add" action
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Set the result converter to process the input when the "Add" button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                // Validate the fields before creating a new member
                List<TextField> fields = Arrays.asList(horodateurField, nomField, prenomField, numeroTelephoneField, professionEtudesField, niveauScolaireField,
                        lieuNaissanceField, numeroPasseportField, estConfirmeField, montantField);
                if (validateFields(fields)) {

                    // Create a new Membre object and add it to the TableView
                    Membre newMembre = createMembre(
                            horodateurField.getText(),
                            nomField.getText(),
                            prenomField.getText(),
                            numeroTelephoneField.getText(),
                            dateNaissancePicker.getValue(),
                            sexeComboBox.getValue(),
                            professionEtudesField.getText(),
                            niveauScolaireField.getText(),
                            lieuNaissanceField.getText(),
                            numeroPasseportField.getText(),
                            estConfirmeField.getText(),
                            montantField.getText()
                    );

                    tableView.getItems().add(newMembre);
                    membres.add(newMembre);

                    // Show a success message (you can customize this)
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Member added successfully.");
                    alert.showAndWait();

                    return newMembre;
                }
            }
            return null;
        });

        // Show the Dialog and wait for the user input
        dialog.showAndWait();
    }

    private boolean validateFields(List<TextField> fields) {
        boolean allFieldsValid = true;
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                field.setStyle("-fx-border-color: red;");
                allFieldsValid = false;
            } else {
                field.setStyle("");
            }
        }
        return allFieldsValid;
    }

    private Membre createMembre(String horodateur, String nom, String prenom, String numeroTelephone,
            LocalDate dateNaissance, String sexe, String professionEtudes,
            String niveauScolaire, String lieuNaissance, String numeroPasseport,
            String estConfirme, String montant) {
        // Create a new Membre object using the provided data
        // ...

        return new Membre(horodateur, nom, prenom, numeroTelephone, dateNaissance,
                sexe, professionEtudes, niveauScolaire, lieuNaissance, numeroPasseport, estConfirme.toLowerCase().contains("oui"), Integer.parseInt(montant));
    }

    // Method to update the TableView based on the search query
    private void updateTableView(String searchQuery) {
        List<Membre> filteredMembres = membres.stream()
                .filter(membre -> memberContainsSearchQuery(membre, searchQuery))
                .collect(Collectors.toList());

        tableView.getItems().clear();
        tableView.getItems().addAll(filteredMembres);
    }

    // Helper method to check if a member's properties contain the search query (case-insensitive)
    private boolean memberContainsSearchQuery(Membre membre, String searchQuery) {
        searchQuery = searchQuery.toLowerCase(); // Convert the search query to lowercase (or use .toUpperCase() for uppercase search)
        return membre.getHorodateur().toLowerCase().contains(searchQuery)
                || membre.getNom().toLowerCase().contains(searchQuery)
                || membre.getPrenom().toLowerCase().contains(searchQuery)
                || membre.getNumeroTelephone().toLowerCase().contains(searchQuery)
                || membre.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toLowerCase().contains(searchQuery)
                || membre.getSexe().toLowerCase().contains(searchQuery)
                || membre.getProfessionEtudes().toLowerCase().contains(searchQuery)
                || membre.getNiveauScolaire().toLowerCase().contains(searchQuery)
                || membre.getLieuNaissance().toLowerCase().contains(searchQuery)
                || membre.getNumeroPasseport().toLowerCase().contains(searchQuery)
                || String.valueOf(membre.isEstConfirme()).toLowerCase().contains(searchQuery)
                || String.valueOf(membre.getMontant()).toLowerCase().contains(searchQuery);
    }

    // Method to read the CSV file and return a list of Membre objects
    private List<Membre> readCSVFile(String filePath) throws IOException, CsvValidationException {
        List<Membre> allMembres = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] header = reader.readNext(); // Skip the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                Membre membre = new Membre(
                        line[0], line[1], line[2], line[3], LocalDate.parse(line[4], formatter),
                        line[5], line[6], line[7], line[8], line[9], "OUI".equals(line[10]), Integer.parseInt(line[11])
                );

                allMembres.add(membre);
            }
        }

        return allMembres;
    }

    // Method to view a member's details
    private void viewMember() {
        Membre selectedMembre = tableView.getSelectionModel().getSelectedItem();
        if (selectedMembre != null) {
            // Display the details of the selected member (e.g., in a dialog)
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Membre Details");
            alert.setHeaderText(null);
            alert.setContentText(selectedMembre.toString());
            alert.showAndWait();
        } else {
            // No member selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a member to view.");
            alert.showAndWait();
        }
    }

    // Method to edit a member's details
    private void editMember() {
        Membre selectedMembre = tableView.getSelectionModel().getSelectedItem();
        if (selectedMembre != null) {
            // Display a dialog to edit the selected member's details
            // Implement the editing logic here
            // ...
        } else {
            // No member selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a member to edit.");
            alert.showAndWait();
        }
    }

    // Method to delete a member
    private void deleteMember() {
        Membre selectedMembre = tableView.getSelectionModel().getSelectedItem();
        if (selectedMembre != null) {
            // Remove the selected member from the TableView and the list
            tableView.getItems().remove(selectedMembre);
            membres.remove(selectedMembre);

            // Update the CSV file with the modified data
            saveToCSV(getClass().getResource("/files/formulaire.csv").getFile());

            // Show a confirmation message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Member deleted successfully.");
            alert.showAndWait();
        } else {
            // No member selected, show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a member to delete.");
            alert.showAndWait();
        }
    }

    // Method to save the data to the CSV file
    private void saveToCSV(String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            writer.writeNext(new String[]{
                "Horodateur", "Nom", "Prénom", "Numéro de téléphone", "Date de naissance (Mois, jours, année)",
                "Sexe", "Profession/Études", "Niveau Scolaire", "Lieu de naissance", "Numéro de passeport",
                "CONFIRMÉE (OUI / NON)", "Montant"
            });

            for (Membre membre : membres) {
                writer.writeNext(new String[]{
                    membre.getHorodateur(), membre.getNom(), membre.getPrenom(), membre.getNumeroTelephone(),
                    membre.getDateNaissance().format(formatter), membre.getSexe(), membre.getProfessionEtudes(),
                    membre.getNiveauScolaire(), membre.getLieuNaissance(), membre.getNumeroPasseport(),
                    membre.isEstConfirme() ? "OUI" : "NON", String.valueOf(membre.getMontant())
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
