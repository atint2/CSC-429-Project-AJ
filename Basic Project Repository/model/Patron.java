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

/* Patron class
 *
 */
public class Patron extends EntityBase implements IView {
    private static final String myTableName = "Patron"; // used to talk to Patron table

    protected Properties dependencies;

    // GUI Components

    private String updateStatusMessage = "";

    /* Class constructor using patronId as parameter (primary key for Patron)
     * Stores new Patron in database
     */
    public Patron(String patronId)
            throws InvalidPrimaryKeyException {
        super(myTableName);

        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);

        // You must get one patron at least
        if (allDataRetrieved != null) {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one patron. More than that is an error
            if (size != 1) {
                throw new InvalidPrimaryKeyException("Multiple patrons matching id : "
                        + patronId + " found.");
            } else {
                // copy all the retrieved data into persistent state
                Properties retrievedPatronData = allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedPatronData.propertyNames();
                while (allKeys.hasMoreElements() == true) {
                    String nextKey = (String) allKeys.nextElement();
                    String nextValue = retrievedPatronData.getProperty(nextKey);
                    // patronId = Integer.parseInt(retrievedPatronData.getProperty("patronId"));

                    if (nextValue != null) {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no patron found for this username, throw an exception
        else {
            throw new InvalidPrimaryKeyException("No patron matching id : "
                    + patronId + " found.");
        }
    }

    //-----------------------------------------------------------------------------------
    /* Class constructor using Properties object as parameter
     * Stores new Patron in database
     */
    public Patron(Properties props) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
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
    public Patron() {
        super(myTableName);

        setDependencies();

        // Create new empty Properties object
        persistentState = new Properties();
    }

    //-----------------------------------------------------------------------------------
    public void processNewPatron(Properties props) {
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

    /**
     * Called via the IView relationship
     */
    //-----------------------------------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    public static int compare(Patron a, Patron b) {
        String aNum = (String) a.getState("patronId");
        String bNum = (String) b.getState("patronId");

        return aNum.compareTo(bNum);
    }

    //-----------------------------------------------------------------------------------
    public void save() {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase() {
        try {
            if (persistentState.getProperty("patronId") != null) {
                // update
                Properties whereClause = new Properties();
                whereClause.setProperty("patronId",
                        persistentState.getProperty("patronId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Patron data for patron id : " + persistentState.getProperty("patronId") + " updated successfully in database!";
            } else {
                // insert
                Integer patronId =
                        insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("patronId", "" + patronId.intValue());
                updateStatusMessage = "Patron data for new patron : " + persistentState.getProperty("patronId")
                        + "installed successfully in database!";
            }
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing patron data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    //-----------------------------------------------------------------------------------
    @Override
    public String toString() {
        return "Patron Information:"
                + "\nName: " + persistentState.getProperty("name")
                + "\nEmail: " + persistentState.getProperty("email")
                + "\nDOB: " + persistentState.getProperty("dateOfBirth");
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

        v.addElement(persistentState.getProperty("patronId"));
        v.addElement(persistentState.getProperty("name"));
        v.addElement(persistentState.getProperty("address"));
        v.addElement(persistentState.getProperty("city"));
        v.addElement(persistentState.getProperty("stateCode"));
        v.addElement(persistentState.getProperty("zip"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("dateOfBirth"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
