package model;

// system imports

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JFrame;

// project imports
import exception.InvalidPrimaryKeyException;
import database.*;
import impresario.IView;
import userinterface.View;
import userinterface.ViewFactory;

/* Book class
 *
 */
public class Book extends EntityBase implements IView {
    private static final String myTableName = "Book";   // used to talk to Book table

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    /* Class constructor using bookId as parameter (primary key for Book)
     * Stores new Book in database
     */
    public Book(String bookId)
            throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        // Write a query to zero in on row, execute query, and store results given back by query
        String query = "SELECT * FROM " + myTableName + " WHERE (bookId = " + bookId + ")";

        // Store results in Vector object since query may return multiple rows of data
        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one book result at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be exactly ONE book with this bookId
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple books matching id : " + bookId + " found.");
            } else {
                // Copy all the retrieved data into persistent state
                Properties retrievedBookData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                // Store keys and their values in database
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
    }

    //-----------------------------------------------------------------------------------
    /* Class constructor using Properties object as parameter
     * Stores new Book in database
     */
    public Book(Properties props) {
        super(myTableName);

        setDependencies();
        // Create new Properties object to copy retrieved data into
        persistentState = new Properties();

        Enumeration allKeys = props.propertyNames();
        // Store keys and their values in database
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    /* Parameter-less constructor
     * Initializes empty Properties object
     */
    public Book() {
        super(myTableName);

        setDependencies();

        // Create new empty Properties object
        persistentState = new Properties();
    }

    //-----------------------------------------------------------------------------------
    public void processNewBook(Properties props) {
        Enumeration allKeys = props.propertyNames();
        // Store keys and their values in database
        while (allKeys.hasMoreElements() == true) {
            String nextKey = (String) allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null) {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
        this.save();
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies() {
        dependencies = new Properties();

        myRegistry.setDependencies(dependencies);
    }

    //-----------------------------------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;
        return persistentState.getProperty(key);
    }

    //-----------------------------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {
        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    // Called via IView relationship
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    public void save() {      //
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("bookId") != null) {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("bookId", persistentState.getProperty("bookId"));
                updatePersistentState(mySchema, persistentState, whereClause);

            } else {
                // insert
                Integer bookId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("bookId", "" + bookId.intValue());
                updateStatusMessage = "Book data for new book : " + persistentState.getProperty("bookId") +
                        "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing book data into database!";
        }

        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    //-----------------------------------------------------------------------------------
    public static int compare(Book a, Book b) {
        String aNum = (String) a.getState("bookId");
        String bNum = (String) b.getState("bookId");

        return aNum.compareTo(bNum);
    }

    //-----------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Book Information:"
                + "\nTitle: " + persistentState.getProperty("bookTitle")
                + "\nAuthor: " + persistentState.getProperty("author")
                + "\nPublication Date: " + persistentState.getProperty("pubYear");
    }

    //-----------------------------------------------------------------------------------
    public void display() { System.out.println(toString()); }

    /**
     * This method is needed solely to enable the Book information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("bookTitle"));
        v.addElement(persistentState.getProperty("author"));
        v.addElement(persistentState.getProperty("pubYear"));

        return v;
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
