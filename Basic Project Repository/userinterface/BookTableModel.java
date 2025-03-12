package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class BookTableModel {
    private final SimpleStringProperty bookTitle;
    private final SimpleStringProperty author;
    private final SimpleStringProperty pubYear;

    //----------------------------------------------------------------------------
    public BookTableModel(Vector<String> bookData) {
        bookTitle =  new SimpleStringProperty(bookData.elementAt(0));
        author =  new SimpleStringProperty(bookData.elementAt(1));
        pubYear =  new SimpleStringProperty(bookData.elementAt(2));
    }

    //----------------------------------------------------------------------------
    public String getBookTitle() {
        return bookTitle.get();
    }

    //----------------------------------------------------------------------------
    public void setBookTitle(String title) {
        bookTitle.set(title);
    }

    //----------------------------------------------------------------------------
    public String getAuthor() {
        return author.get();
    }

    //----------------------------------------------------------------------------
    public void setAuthor(String a) {
        author.set(a);
    }

    //----------------------------------------------------------------------------
    public String getPubYear() {
        return pubYear.get();
    }

    //----------------------------------------------------------------------------
    public void setPubYear(String year) {
        pubYear.set(year);
    }

}
