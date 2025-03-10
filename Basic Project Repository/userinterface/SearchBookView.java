package userinterface;

// system imports

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Book;

import java.util.Properties;

/**
 * The class containing the SearchBook View  for the Library application
 */
//==============================================================

public class SearchBookView extends View {

    // GUI components
    protected TextField title;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error/success message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchBookView(IModel book) {
        super(book, "SearchBookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        myModel.subscribe("SearchBookError", this);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" LIBRARY SYSTEM ");
        titleText.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        return vbox;
    }

    //-------------------------------------------------------------
    public void populateFields() {

    }

    //-------------------------------------------------------------
    public void processAction(Event evt) {

    }

    //-------------------------------------------------------------
    public void updateState(String key, Object value) {

    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);
        return statusLog;
    }

    /**
     * Display success message
     */
    //----------------------------------------------------------
    public void displaySuccessMessage(String message) {
        statusLog.displaySuccessMessage(message);
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message) {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage() {
        statusLog.clearErrorMessage();
    }
}
