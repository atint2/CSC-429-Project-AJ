package userinterface;

// system imports

import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Properties;

// project imports
import impresario.IModel;

/**
 * The class containing the Book View  for the Library application
 */
//==============================================================
public class BookView extends View {

    // GUI components
    protected TextField author;
    protected TextField bookTitle;
    protected TextField pubYear;

    protected ComboBox<Text> status;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BookView(IModel book) {
        super(book, "BookView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        myModel.subscribe("UpdateStatusMessage", this);
    }

    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle() {
        Text titleText = new Text("       LIBRARY SYSTEM          ");
        titleText.setFont(Font.font("Garamond", FontWeight.BOLD, 25));
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.BLACK);

        return titleText;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent() {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Font myFont = Font.font("Garamond", FontWeight.NORMAL, 12);

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setFont(Font.font("Garamond", FontWeight.BOLD, 15));
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text authorLabel = new Text(" Author : ");
        authorLabel.setFont(myFont);
        authorLabel.setWrappingWidth(150);
        authorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLabel, 0, 1);

        author = new TextField();
        author.setEditable(true);
        grid.add(author, 1, 1);

        Text bookTitleLabel = new Text(" Title : ");
        bookTitleLabel.setFont(myFont);
        bookTitleLabel.setWrappingWidth(150);
        bookTitleLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(bookTitleLabel, 0, 2);

        bookTitle = new TextField();
        bookTitle.setEditable(true);
        grid.add(bookTitle, 1, 2);

        Text pubYearLabel = new Text(" Publication Year : ");
        pubYearLabel.setFont(myFont);
        pubYearLabel.setWrappingWidth(150);
        pubYearLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(pubYearLabel, 0, 3);

        pubYear = new TextField();
        pubYear.setEditable(true);
        grid.add(pubYear, 1, 3);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.CENTER);

        submitButton = new Button("Submit");
        submitButton.setFont(myFont);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
            }
        });
        doneCont.getChildren().add(submitButton);

        doneButton = new Button("Done");
        doneButton.setFont(myFont);
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
            }
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    public void updateState(String key, Object value) {

    }

    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage) {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

}
