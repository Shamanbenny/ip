package nightcoder.javafx;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import nightcoder.NightCoder;

/**
 * The Main class initializes and starts the JavaFX application.
 *
 * @author ShamanBenny
 * @version 10
 */
public class Main extends Application {
    private NightCoder nightCoder = new NightCoder();
    private Image fevicon = new Image(this.getClass().getResourceAsStream("/images/fevicon.png"));

    /**
     * Initializes and starts the main JavaFX application.
     * Loads the FXML layout and sets up the stage properties such as title, icons, and window size.
     * Also injects the NightCoder instance into the main window.
     *
     * @param stage The primary stage of the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set property
            stage.setTitle("NightCoder");
            stage.getIcons().add(fevicon);
            stage.setResizable(false);
            stage.setMinHeight(800.0);
            stage.setMaxHeight(800.0);
            stage.setMinWidth(1000.0);
            stage.setMaxWidth(1000.0);

            // Set on window close event
            stage.setOnCloseRequest((event) -> {
                this.nightCoder.saveTasksOnClose();
            });

            fxmlLoader.<MainWindow>getController().setNightCoder(this.nightCoder); // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
