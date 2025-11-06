import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Interface extends Application {

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage plateau) throws Exception {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.CENTER);

        int lignes = 10;
        int coln = 10;
        //Ajouter les boutons
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < coln; j++) {
                Button button = new Button(Integer.toString(i));
                gridPane.add(button, j, i);
            }
        }
        Text titre = new Text(" ");
        titre.setFont(Font.font("Arial", FontWeight.BOLD, 24));



        StackPane racine = new StackPane();
        racine.getStyleClass().add("racine");
        racine.getChildren().addAll(gridPane,titre);
        racine.setAlignment(titre,Pos.TOP_CENTER);
        racine.setMargin(titre, new Insets(10,10,10,10));
        Scene scene = new Scene(racine,700,700, Color.rgb(99, 107, 194));

        scene.getStylesheets().add("/styles.css");




        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(scene);
        plateau.show();
    }
}
