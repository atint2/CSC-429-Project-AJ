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

public class BookCollection {
    private static final String myTableName = "Book"; // used to talk to Book table

    private Vector<Book> books;

    /* Class constructor that takes no parameters
     *
     */
    public BookCollection() {

    }
}
