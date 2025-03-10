// specify the package
package model;

// system imports

import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/**
 * The class containing the Librarian for the Library application
 */
//==============================================================
public class Librarian implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private AccountHolder myAccountHolder;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage myStage;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public Librarian() {
        myStage = MainStageContainer.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("Librarian");
        if (myRegistry == null) {
            new Event(Event.getLeafLevelClassName(this), "Librarian",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowLibrarianView();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies() {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param    key    Name of database column (field) for which the client wants the value
     * @return Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key) {
        return "";
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);

        if (key.equals("InsertBook") == true) {
            createAndShowInsertBookView();
        } else if (key.equals("Done") == true) {
            myViews.remove("BookView");

            createAndShowLibrarianView();
        } else if (key.equals("InsertPatron") == true) {
            createAndShowInsertPatronView();
        } else if (key.equals("SearchBook") == true) {
            createAndShowSearchBookView();
        } else if (key.equals("SearchPatron") == true) {
            createAndShowSearchPatronView();
        }

        myRegistry.updateSubscribers(key, this);
    }

    /**
     * Called via the IView relationship
     */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        // DEBUG System.out.println("Teller.updateState: key: " + key);
        stateChangeRequest(key, value);
    }

    public void createNewBook() {
        Book newBook = new Book();
        updateState("InsertBook", null);
    }

    public void createNewPatron() {
        Patron newPatron = new Patron();
        updateState("InsertPatron", null);
    }

    //------------------------------------------------------------
    private void createAndShowInsertBookView() {
        Scene currentScene = (Scene) myViews.get("BookView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("BookView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("BookView", currentScene);
        }

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    //------------------------------------------------------------
    private void createAndShowInsertPatronView() {
        Scene currentScene = (Scene) myViews.get("PatronView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("PatronView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("PatronView", currentScene);
        }

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    //------------------------------------------------------------
    private void createAndShowSearchBookView() {
        Scene currentScene = (Scene) myViews.get("SearchBookView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchBookView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchBookView", currentScene);
        }

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    //------------------------------------------------------------
    private void createAndShowSearchPatronView() {
        Scene currentScene = (Scene) myViews.get("SearchPatronView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("SearchPatronView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("SearchPatronView", currentScene);
        }

        // make the view visible by installing it into the frame
        swapToView(currentScene);
    }

    //------------------------------------------------------------
    private void createAndShowLibrarianView() {
        Scene currentScene = (Scene) myViews.get("LibrarianView");

        if (currentScene == null) {
            // create our initial view
            View newView = ViewFactory.createView("LibrarianView", this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put("LibrarianView", currentScene);
        }

        swapToView(currentScene);
    }


    /**
     * Register objects to receive state updates.
     */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber) {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /**
     * Unregister previously registered objects.
     */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber) {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }


    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene) {


        if (newScene == null) {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }
}

