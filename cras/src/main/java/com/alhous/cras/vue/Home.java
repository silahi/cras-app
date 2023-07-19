package com.alhous.cras.vue;

import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Alhoussaine
 */
public class Home extends BorderPane {

    private ImageView logo;
    private ImageView searchIcon;
    private HBox header;
    private final int logo_dim = 64;
    private TextField searchInput;
    private Button filterBtn;
    private ListMembres membres;
    private Label lblTitle = new Label("CRAS : Base de données");

    public Home() {
        setStyle("-fx-background-color: #ffffff; -fx-text-fill: black;");
        var root = new BorderPane();
        header = new HBox(10);
        header.setAlignment(Pos.CENTER_RIGHT);
        lblTitle.setFont(Font.font(null, FontWeight.BOLD, 24));
        lblTitle.setStyle("-fx-text-fill: #007BFF;");
        root.setLeft(lblTitle);

//        Border headerBorder = new Border(
//                new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
//                        new BorderWidths(1), new Insets(0)));
        header.setPadding(new Insets(5));
        setPadding(new Insets(5));

        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER); 
         
        searchBox.setPadding(new Insets(5));
        searchBox.setStyle("-fx-background-color: #ffffff;");

        var searchIco = new ImageView(getLocalImage("/images/search.png"));
        searchIco.setFitWidth(20); // Set the width of the icon (adjust as needed)
        searchIco.setFitHeight(20);

        //searchBox.getChildren().add(searchIco);
        searchInput = new TextField();
        searchInput.setPrefSize(300, 20); 
        
        searchBox.getChildren().add(searchInput);

        var optionIco = new ImageView(getLocalImage("/images/option.png"));
        var userIco = new ImageView(getLocalImage("/images/user.png"));
        var menuBox = new HBox(20);
        menuBox.setAlignment(Pos.CENTER);
        Arrays.asList(optionIco, userIco).forEach(e -> {
            e.setFitWidth(logo_dim / 2);
            e.setFitHeight(logo_dim / 2);
            menuBox.getChildren().add(e);
        });

        try {
            membres = new ListMembres(header, searchInput);
            setCenter(membres);
        } catch (IOException | CsvValidationException ex) {
            System.out.println(ex.getMessage());
        }

        header.getChildren().add(searchBox);
        header.getChildren().add(menuBox);
        root.setCenter(header);
        setTop(root);

    }

    public final Image getLocalImage(String imagePath) {
        return new Image(getClass().getResourceAsStream(imagePath));
    }

}
