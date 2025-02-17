package model;

// system imports

import java.util.Properties;
import java.util.Vector;

import javafx.scene.Scene;

// project imports
import exception.InvalidPrimaryKeyException;
import event.Event;
import database.*;

import impresario.IView;

import userinterface.View;
import userinterface.ViewFactory;

public class PatronCollection extends EntityBase implements IView {
    private static final String myTableName = "Patron"; // used to talk to Patron table

    private Vector<Patron> patronList;

    /* Class constructor that takes no parameters
     *
     */
    public PatronCollection() {
        super(myTableName);
        patronList = new Vector<Patron>();
    }

    //-----------------------------------------------------------------------------------
    private void processQuery(String query) throws InvalidPrimaryKeyException {
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

                Patron patron = new Patron(nextPatronData);

                if (patron != null)
                {
                    addPatron(patron);
                }
            }

        }
        else
        {
            throw new InvalidPrimaryKeyException("No patrons satisfying query");
        }

    }

    //-----------------------------------------------------------------------------------
    private void addPatron(Patron p)
    {
        int index = findIndexToAdd(p);
        patronList.insertElementAt(p,index); // To build up a collection sorted on some key
    }

    //-----------------------------------------------------------------------------------
    private int findIndexToAdd(Patron p)
    {
        int low=0;
        int high = patronList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Patron midSession = patronList.elementAt(middle);

            int result = Patron.compare(p,midSession);

            if (result == 0)
            {
                return middle;
            }
            else if (result < 0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }

    //-----------------------------------------------------------------------------------
    public void findPatronsOlderThan(String date) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < " + date + ")";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void findPatronsYoungerThan(String date) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth > " + date + ")";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void findPatronsAtZipCode(String zip) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (zip = " + zip + ")" ;

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void findPatronsWithNameLike(String name) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE name LIKE '%" + name + "%';";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("Patrons"))
            return patronList;
        else if (key.equals("PatronCollection"))
            return this;
        return null;
    }

    //-----------------------------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    protected void createAndShowView() {

        Scene localScene = myViews.get("PatronCollectionView");

        if (localScene == null) {
            // create our new view
            View newView = ViewFactory.createView("PatronCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("PatronCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);

    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }
}
