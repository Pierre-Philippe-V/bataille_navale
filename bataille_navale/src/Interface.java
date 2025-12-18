import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Interface extends Application {

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage plateau) throws Exception {

        HBox select_bat = new HBox();
        GridPane grille = new GridPane();


        int lignes = 10;
        int coln = 10;
        //Ajouter la grille de boutons
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < coln; j++) {
                Button button = new Button("~~~");
                grille.add(button, j, i);
                button.setOnAction(e -> {
                    button.getStyleClass().add("bouton_select");
                    button.setText("\uD83D\uDD34");

                });
            }
        }
        grille.setHgap(10);
        grille.setVgap(10);
        grille.setAlignment(Pos.CENTER);

        int nb_type_bat = 4;
        //Ajouter la grille de boutons
//        for (int i = 0; i < nb_type_bat; i++) {

//                Button bt_type_cuirasse = new Button("Cuirassé");
//                Button bt_type_croiseur = new Button("Croiseur");
//                Button bt_type_destroyer = new Button("Destroyer");
//                Button bt_type_torpilleur = new Button("Torpilleur");
//                bt_type_cuirasse.getStyleClass().add("bt_type_bat");
//                bt_type_croiseur.getStyleClass().add("bt_type_bat");
//                bt_type_destroyer.getStyleClass().add("bt_type_bat");
//                bt_type_torpilleur.getStyleClass().add("bt_type_bat");
//                select_bat.getChildren().addAll(bt_type_torpilleur,bt_type_cuirasse,bt_type_croiseur,bt_type_destroyer);
//                bt_type_cuirasse.setOnAction(e -> {
//                    bt_type_cuirasse.getStyleClass().add("bt_type_bat_select");
//
//                });

//        }
//        select_bat.setSpacing(10);
//        select_bat.setAlignment(Pos.CENTER);
//        Image mer = new Image("vagues.png");
//        ImageView vagues = new ImageView(mer);
//        vagues.setFitHeight(800);
//        vagues.setPreserveRatio(true);
//        vagues.setX(0);
//        vagues.setY(0);

        StackPane centrer = new StackPane();
        centrer.getChildren().addAll(grille);
        VBox racine = new VBox();
        racine.getStyleClass().add("racine");
        racine.getChildren().addAll(centrer,select_bat);
        racine.setAlignment(Pos.CENTER);
        racine.setSpacing(20);
        Scene scene = new Scene(racine,700,700, Color.rgb(99, 107, 194));

        scene.getStylesheets().add("/styles.css");

        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(scene);
        plateau.show();
    }
}
