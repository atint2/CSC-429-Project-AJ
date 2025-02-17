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

public class BookCollection extends EntityBase implements IView {
    private static final String myTableName = "Book"; // used to talk to Book table

    private Vector<Book> bookList;

    /* Class constructor that takes no parameters
     *
     */
    public BookCollection() {
        super(myTableName);
        bookList = new Vector<Book>();
    }

    //-----------------------------------------------------------------------------------
    private void processQuery(String query) throws InvalidPrimaryKeyException {
        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            for (int cnt = 0; cnt < allDataRetrieved.size(); cnt++)
            {
                Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

                Book book = new Book(nextBookData);

                if (book != null)
                {
                    addBook(book);
                }
            }

        }
        else
        {
            throw new InvalidPrimaryKeyException("No books satisfying query");
        }

    }

    //-----------------------------------------------------------------------------------
    private void addBook(Book b)
    {
        int index = findIndexToAdd(b);
        bookList.insertElementAt(b,index); // To build up a collection sorted on some key
    }

    //-----------------------------------------------------------------------------------
    private int findIndexToAdd(Book b)
    {
        int low=0;
        int high = bookList.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = bookList.elementAt(middle);

            int result = Book.compare(b,midSession);

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
    public void findBooksOlderThanDate(String year) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear < " + year + ")";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void findBooksNewerThanDate(String year) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > " + year + ")";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void findBooksWithTitleLike(String title) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE bookTitle LIKE '%" + title + "%';";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void findBooksWithAuthorLike(String author) throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE author LIKE '%" + author + "%';";

        processQuery(query);
    }

    //-----------------------------------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    //-----------------------------------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("Books"))
            return bookList;
        else if (key.equals("BookCollection"))
            return this;
        return null;
    }

    //-----------------------------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) {

        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    protected void createAndShowView() {

        Scene localScene = myViews.get("BookCollectionView");

        if (localScene == null) {
            // create our new view
            View newView = ViewFactory.createView("BookCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("BookCollectionView", localScene);
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
