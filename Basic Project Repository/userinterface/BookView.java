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
import model.Book;

/**
 * The class containing the Book View  for the Library application
 */
//==============================================================
public class BookView extends View {

    // GUI components
    protected TextField author;
    protected TextField bookTitle;
    protected TextField pubYear;

    protected ComboBox<String> status;

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

        populateFields();

        myModel.subscribe("InsertBookError", this);
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

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Font myFont = Font.font("Garamond", FontWeight.NORMAL, 15);

        Text prompt = new Text("BOOK INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setFont(Font.font("Garamond", FontWeight.BOLD, 17));
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text authorLabel = new Text(" Author : ");
        authorLabel.setFont(myFont);
        authorLabel.setWrappingWidth(150);
        authorLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(authorLabel, 0, 1);

        author = new TextField();
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

        Text statusLabel = new Text(" Status : ");
        statusLabel.setFont(myFont);
        statusLabel.setWrappingWidth(150);
        statusLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(statusLabel, 0, 4);

        status = new ComboBox<String>();
        status.setEditable(true);
        grid.add(status, 1, 4);

        HBox doneCont = new HBox(10);
        doneCont.setAlignment(Pos.BOTTOM_RIGHT);

        submitButton = new Button("Submit");
        submitButton.setFont(myFont);
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                processAction(e);
            }
        });
        doneCont.getChildren().add(submitButton);

        doneButton = new Button("Done");
        doneButton.setFont(myFont);
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                clearErrorMessage();
                myModel.stateChangeRequest("DoneInsertingBook", null);
            }
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;

    }

    public void populateFields() {

        status.getItems().addAll("Active", "Inactive");
        status.setValue("Active");
    }

    public void processAction(Event evt) {
        // DEBUG: System.out.println("BookView.actionPerformed()");

        clearErrorMessage();

        String authorEntered = author.getText();
        String bookTitleEntered = bookTitle.getText();
        String pubYearEntered = pubYear.getText();

        if (authorEntered == null || authorEntered.length() == 0) {
            displayErrorMessage("Please enter an author!");
            author.requestFocus();
        } else if (bookTitleEntered == null || bookTitleEntered.length() == 0) {
            displayErrorMessage("Please enter a book title!");
            bookTitle.requestFocus();
        } else if (pubYearEntered == null || pubYearEntered.length() == 0) {
            displayErrorMessage("Please enter a publication year!");
            pubYear.requestFocus();
        } else if (Integer.parseInt(pubYearEntered) < 1800 || Integer.parseInt(pubYearEntered) > 2024) {
            displayErrorMessage("Publication year must be between 1800 and 2024");
            pubYear.requestFocus();
        } else {
            Book newBook = new Book();
            Properties bookInfo = new Properties();
            bookInfo.setProperty("author", authorEntered);
            bookInfo.setProperty("bookTitle", bookTitleEntered);
            bookInfo.setProperty("pubYear", pubYearEntered);
            newBook.processNewBook(bookInfo);
        }
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
