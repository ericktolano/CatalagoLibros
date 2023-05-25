package tolano.catalagolibros;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CatalagoApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CatalagoApp.class.getResource("CatalagoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Catálago de Libros");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}