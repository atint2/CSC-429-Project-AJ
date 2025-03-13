package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

public class PatronTableModel {
    private final SimpleStringProperty patronId;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty city;
    private final SimpleStringProperty stateCode;
    private final SimpleStringProperty zip;
    private final SimpleStringProperty email;
    private final SimpleStringProperty dateOfBirth;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public PatronTableModel(Vector<String> accountData)
    {
        patronId =  new SimpleStringProperty(accountData.elementAt(0));
        name =  new SimpleStringProperty(accountData.elementAt(1));
        address =  new SimpleStringProperty(accountData.elementAt(2));
        city =  new SimpleStringProperty(accountData.elementAt(3));
        stateCode =  new SimpleStringProperty(accountData.elementAt(4));
        zip =  new SimpleStringProperty(accountData.elementAt(5));
        email =  new SimpleStringProperty(accountData.elementAt(6));
        dateOfBirth =  new SimpleStringProperty(accountData.elementAt(7));
        status =  new SimpleStringProperty(accountData.elementAt(8));
    }

    //----------------------------------------------------------------------------
    public String getPatronId() {
        return patronId.get();
    }

    //----------------------------------------------------------------------------
    public void setPatronId(String id) {
        patronId.set(id);
    }

    //----------------------------------------------------------------------------
    public String getName() {
        return name.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String n) {
        name.set(n);
    }

    //----------------------------------------------------------------------------
    public String getAddress() { return address.get(); }

    //----------------------------------------------------------------------------
    public void setAddress(String addr) {
        address.set(addr);
    }

    //----------------------------------------------------------------------------
    public String getCity() {
        return city.get();
    }

    //----------------------------------------------------------------------------
    public void setCity(String c)
    {
        city.set(c);
    }

    //----------------------------------------------------------------------------
    public String getStateCode() {
        return stateCode.get();
    }

    //----------------------------------------------------------------------------
    public void setStateCode(String sc)
    {
        stateCode.set(sc);
    }

    //----------------------------------------------------------------------------
    public String getZip() {
        return zip.get();
    }

    //----------------------------------------------------------------------------
    public void setZip(String z)
    {
        zip.set(z);
    }

    //----------------------------------------------------------------------------
    public String getEmail() {
        return email.get();
    }

    //----------------------------------------------------------------------------
    public void setEmail(String e)
    {
        email.set(e);
    }

    //----------------------------------------------------------------------------
    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    //----------------------------------------------------------------------------
    public void setDateOfBirth(String dob)
    {
        dateOfBirth.set(dob);
    }

    //----------------------------------------------------------------------------
    public String getStatus() {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String s)
    {
        status.set(s);
    }

}
