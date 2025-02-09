package nightcoder.javafx;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import nightcoder.NightCoder;
import nightcoder.ui.Ui;

/**
 * Controller for the main GUI.
 * Handles user interactions and manages the UI components.
 *
 * @author ShamanBenny
 * @version 10
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private NightCoder nightCoder;

    /* `/images/DALL-E_Guest User.png` generated by DALL-E using ChatGPT */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DALL-E_Guest User.png"));
    /* `/images/DALL-E_NightCoder Bot.png` generated by DALL-E using ChatGPT */
    private Image botImage = new Image(this.getClass().getResourceAsStream("/images/DALL-E_NightCoder Bot.png"));

    /**
     * Initializes the main window by displaying the welcome messages and binding scroll properties.
     */
    @FXML
    public void initialize() {
        ArrayList<DialogBox> welcomeDialogs = new ArrayList<>();
        welcomeDialogs.add(DialogBox.getBotDialog(Ui.getWelcomeString(1), this.botImage));
        welcomeDialogs.add(DialogBox.getBotDialog(Ui.getWelcomeString(2), this.botImage));
        this.dialogContainer.getChildren().addAll(welcomeDialogs);
        this.scrollPane.vvalueProperty().bind(this.dialogContainer.heightProperty());
    }

    /** Injects the NightCoder instance */
    public void setNightCoder(NightCoder n) {
        this.nightCoder = n;
    }

    /**
     * Handles user input by creating dialog boxes.
     * Displays the user's input and the corresponding response from NightCoder.
     * Clears the text field after processing the input.
     */
    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        String response = this.nightCoder.getResponse(input);
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, this.userImage),
                DialogBox.getBotDialog(response, this.botImage)
        );
        this.userInput.clear();
    }
}
