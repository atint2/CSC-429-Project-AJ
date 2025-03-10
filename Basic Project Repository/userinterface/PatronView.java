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
import model.Patron;

import java.util.Properties;

/**
 * The class containing the InsertPatron View  for the Library application
 */
//==============================================================
public class PatronView extends View {

    // GUI components
    protected TextField name;
    protected TextField address;
    protected TextField city;
    protected TextField stateCode;
    protected TextField zip;
    protected TextField email;
    protected TextField dateOfBirth;

    protected Button submitButton;
    protected Button doneButton;

    // For showing error/success message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public PatronView(IModel patron) {
        super(patron, "PatronView");

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

        myModel.subscribe("InsertPatronError", this);
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

        Text prompt = new Text("PATRON INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setFont(Font.font("Garamond", FontWeight.BOLD, 17));
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text nameLabel = new Text(" Name : ");
        nameLabel.setFont(myFont);
        nameLabel.setWrappingWidth(150);
        nameLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(nameLabel, 0, 1);

        name = new TextField();
        grid.add(name, 1, 1);

        Text addressLabel = new Text(" Address : ");
        addressLabel.setFont(myFont);
        addressLabel.setWrappingWidth(150);
        addressLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(addressLabel, 0, 2);

        address = new TextField();
        grid.add(address, 1, 2);

        Text cityLabel = new Text(" City : ");
        cityLabel.setFont(myFont);
        cityLabel.setWrappingWidth(150);
        cityLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(cityLabel, 0, 3);

        city = new TextField();
        grid.add(city, 1, 3);

        Text stateCodeLabel = new Text(" State code : ");
        stateCodeLabel.setFont(myFont);
        stateCodeLabel.setWrappingWidth(150);
        stateCodeLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(stateCodeLabel, 0, 4);

        stateCode = new TextField();
        grid.add(stateCode, 1, 4);

        Text zipLabel = new Text(" ZIP : ");
        zipLabel.setFont(myFont);
        zipLabel.setWrappingWidth(150);
        zipLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(zipLabel, 0, 5);

        zip = new TextField();
        grid.add(zip, 1, 5);

        Text emailLabel = new Text(" Email : ");
        emailLabel.setFont(myFont);
        emailLabel.setWrappingWidth(150);
        emailLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(emailLabel, 0, 6);

        email = new TextField();
        grid.add(email, 1, 6);

        Text dateOfBirthLabel = new Text(" DOB : ");
        dateOfBirthLabel.setFont(myFont);
        dateOfBirthLabel.setWrappingWidth(150);
        dateOfBirthLabel.setTextAlignment(TextAlignment.RIGHT);
        grid.add(dateOfBirthLabel, 0, 7);

        dateOfBirth = new TextField();
        grid.add(dateOfBirth, 1, 7);

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
                myModel.stateChangeRequest("Done", null);
            }
        });
        doneCont.getChildren().add(doneButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(doneCont);

        return vbox;
    }

    //-------------------------------------------------------------
    public void populateFields() {
        name.setText("");
        address.setText("");
        city.setText("");
        stateCode.setText("");
        zip.setText("");
        email.setText("");
        dateOfBirth.setText("");
    }

    //-------------------------------------------------------------
    public void processAction(Event evt) {
        // DEBUG: System.out.println("PatronView.actionPerformed()");

        clearErrorMessage();

        String nameEntered = name.getText();
        String addressEntered = address.getText();
        String cityEntered = city.getText();
        String stateCodeEntered = stateCode.getText();
        String zipEntered = zip.getText();
        String emailEntered = email.getText();
        String dateOfBirthEntered = dateOfBirth.getText();

        if (nameEntered == null || nameEntered.length() == 0) {
            displayErrorMessage("Please enter a name!");
            name.requestFocus();
        } else if (addressEntered == null || addressEntered.length() == 0) {
            displayErrorMessage("Please enter an address!");
            address.requestFocus();
        } else if (cityEntered == null || cityEntered.length() == 0) {
            displayErrorMessage("Please enter a city!");
            city.requestFocus();
        } else if (stateCodeEntered == null || stateCodeEntered.length() == 0) {
            displayErrorMessage("Please enter a state code!");
            stateCode.requestFocus();
        } else if (zipEntered == null || zipEntered.length() == 0) {
            displayErrorMessage("Please enter a zip code!");
            zip.requestFocus();
        } else if (emailEntered == null || emailEntered.length() == 0) {
            displayErrorMessage("Please enter an email!");
            email.requestFocus();
        } else if (dateOfBirthEntered == null || dateOfBirthEntered.length() == 0) {
            displayErrorMessage("Please enter a date of birth!");
            email.requestFocus();
        } else if (dateOfBirthEntered.compareTo("1920-01-01") < 0 || dateOfBirthEntered.compareTo("2007-01-01") > 0) {
            displayErrorMessage("Date of birth must be between 1920-01-01 and 2007-01-01");
            dateOfBirth.requestFocus();
        } else {
            Patron newPatron = new Patron();
            Properties patronInfo = new Properties();
            patronInfo.setProperty("name", nameEntered);
            patronInfo.setProperty("address", addressEntered);
            patronInfo.setProperty("city", cityEntered);
            patronInfo.setProperty("stateCode", stateCodeEntered);
            patronInfo.setProperty("zip", zipEntered);
            patronInfo.setProperty("email", emailEntered);
            patronInfo.setProperty("dateOfBirth", dateOfBirthEntered);
            newPatron.processNewPatron(patronInfo);

            displaySuccessMessage("Patron successfully entered into the database");
            populateFields();
        }
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
