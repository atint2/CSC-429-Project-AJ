
// specify the package
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Librarian;

/**
 * The class containing the Librarian View  for the Library application
 */
//==============================================================
public class LibrarianView extends View {

    // GUI stuff
    private Button insertBookButton;
    private Button insertPatronButton;
    private Button searchBooksButton;
    private Button searchPatronsButton;
    private Button doneButton;

    // For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LibrarianView(IModel librarian) {

        super(librarian, "LibrarianView");

        // create a container for showing the contents
        VBox container = new VBox(10);

        container.setPadding(new Insets(15, 5, 5, 5));

        // create a Node (Text) for showing the title
        container.getChildren().add(createTitle());

        // create a Node (GridPane) for showing data entry fields
        container.getChildren().add(createFormContents());

        // Error message area
        container.getChildren().add(createStatusLog("                          "));

        getChildren().add(container);

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("LoginError", this);
    }

    // Create the label (Text) for the title of the screen
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

    // Create the main form contents
    //-------------------------------------------------------------
    private GridPane createFormContents() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Font myFont = Font.font("Garamond", FontWeight.NORMAL, 15);

        insertBookButton = new Button("INSERT NEW BOOK");
        insertBookButton.setFont(myFont);
        insertBookButton.setTextAlignment(TextAlignment.CENTER);
        insertBookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(insertBookButton, 1, 0);

        insertPatronButton = new Button("INSERT NEW PATRON");
        insertPatronButton.setFont(myFont);
        insertPatronButton.setTextAlignment(TextAlignment.CENTER);
        insertPatronButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(insertPatronButton, 1, 1);

        searchBooksButton = new Button("SEARCH BOOKS");
        searchBooksButton.setFont(myFont);
        searchBooksButton.setTextAlignment(TextAlignment.CENTER);
        searchBooksButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(searchBooksButton, 1, 2);

        searchPatronsButton = new Button("SEARCH PATRONS");
        searchPatronsButton.setFont(myFont);
        searchPatronsButton.setTextAlignment(TextAlignment.CENTER);
        searchPatronsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });
        grid.add(searchPatronsButton, 1, 3);

        doneButton = new Button("DONE");
        doneButton.setFont(myFont);
        doneButton.setTextAlignment(TextAlignment.CENTER);
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });
        grid.add(doneButton, 1, 4);

        return grid;
    }


    // Create the status log field
    //-------------------------------------------------------------
    private MessageView createStatusLog(String initialMessage) {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // This method processes events generated from our GUI components.
    // Make the ActionListeners delegate to this method
    //-------------------------------------------------------------
    public void processAction(Event evt) {
        // DEBUG System.out.println("LibrarianView.actionPerformed()");
        clearErrorMessage();

        Librarian myLibrarian = new Librarian();
        if (evt.getSource().toString().contains("INSERT NEW BOOK"))
        {
            myLibrarian.createNewBook();
        }
        else if (evt.getSource().toString().contains("INSERT NEW PATRON"))
        {
            myLibrarian.createNewPatron();
        }
        else if (evt.getSource().toString().contains("SEARCH BOOKS"))
        {
            myLibrarian.updateState("SearchBook", null);
        }
        else if (evt.getSource().toString().contains("SEARCH PATRONS"))
        {
            myLibrarian.updateState("SearchPatron", null);
        }
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value) {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true) {
            // display the passed text
            displayErrorMessage((String) value);
        }

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

