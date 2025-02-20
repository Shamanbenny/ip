package nightcoder.javafx;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

/**
 * The DialogBox class represents a custom dialog box containing text and an image.
 * It is used to display user and bot messages with respective images and format.
 *
 * @author ShamanBenny
 * @version 10
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image.
     *
     * @param text The text to be displayed in the dialog box.
     * @param img  The image to be displayed alongside the text.
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.dialog.setText(text);
        this.dialog.setAlignment(Pos.TOP_RIGHT);
        this.dialog.setTextAlignment(TextAlignment.RIGHT);
        this.displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView appears on the left, and the text appears on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        this.dialog.setAlignment(Pos.TOP_LEFT);
        this.dialog.setTextAlignment(TextAlignment.LEFT);
        this.getStyleClass().add("bot-dialog");
    }

    /**
     * Creates a DialogBox instance for a user message.
     *
     * @param text The user's message text.
     * @param img  The user's profile image.
     * @return A DialogBox displaying the user's message and image.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a DialogBox instance for a bot message and flips it.
     *
     * @param text The bot's response text.
     * @param img  The bot's profile image.
     * @return A DialogBox displaying the bot's message and image, flipped to align left.
     */
    public static DialogBox getBotDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
