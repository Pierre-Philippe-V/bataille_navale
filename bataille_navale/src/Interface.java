import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Interface extends Application {

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage plateau) throws Exception {
        Group racine = new Group();
        Scene scene = new Scene(racine,700,700, Color.rgb(99, 107, 194));

        Image logo = new Image("logo.png");
        plateau.getIcons().add(logo);
        plateau.setTitle("Bataille Navale");
        plateau.setScene(scene);
        plateau.show();
    }
}
