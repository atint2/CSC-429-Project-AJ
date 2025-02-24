package model;

import exception.InvalidPrimaryKeyException;

import java.util.Properties;
import java.util.Scanner;

public class DatabaseTester {
    public static void main(String[] args) throws InvalidPrimaryKeyException {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter number to choose option:\n" +
                "1. Insert a new book into the database\n" +
                "2. Insert a new patron into the database");
        int userChoice = input.nextInt();
        input.nextLine();
        switch (userChoice) {
            case 1:
                insertBook();
                break;
            case 2:
                insertPatron();
                break;
            default:
                System.out.println("Error: Invalid option");
        }

       /* System.out.println("\nEnter part of a title of a book: ");
        String bookTitle = input.nextLine();
        System.out.println("Finding books with title like : " + bookTitle);
        printBooksWithTitleLike(bookTitle);

        System.out.println("\nEnter a year to find books published before it: ");
        String booksOlderThan = input.nextLine();
        System.out.println("Finding books published before : " + booksOlderThan);
        printBooksOlderThanDate(booksOlderThan); */

        System.out.println("\nEnter a date to find patrons younger than it (YYYY-MM-DD): ");
        String patronsyoungerThan = input.nextLine();
        System.out.println("Finding patrons younger than : " + patronsyoungerThan);
        printPatronsYoungerThan(patronsyoungerThan);

        System.out.println("\nEnter a zip code to find all patrons at that zip code: ");
        String zipCode = input.nextLine();
        System.out.println("Finding patrons at zip code : " + zipCode);
        printPatronsAtZipCode(zipCode);
    }

    //-----------------------------------------------------------------------------------
    public static void insertBook() {
        Properties newBook = new Properties();

        Scanner readBookInfo = new Scanner(System.in);
        System.out.println("Enter book title: ");
        String bookTitle = readBookInfo.nextLine();
        System.out.println("Enter author: ");
        String author = readBookInfo.nextLine();
        System.out.println("Enter publication year: ");
        String pubYear = readBookInfo.nextLine();

        newBook.setProperty("bookTitle", bookTitle);
        newBook.setProperty("author", author);
        newBook.setProperty("pubYear", pubYear);

        Book insert = new Book(newBook);
        insert.save();

    }

    //-----------------------------------------------------------------------------------
    public static void insertPatron() {
        Properties newPatron = new Properties();

        Scanner readPatronInfo = new Scanner(System.in);
        System.out.println("Enter name of patron: ");
        String name = readPatronInfo.nextLine();
        System.out.println("Enter address of patron: ");
        String address = readPatronInfo.nextLine();
        System.out.println("Enter city of patron: ");
        String city = readPatronInfo.nextLine();
        System.out.println("Enter stateCode of patron: ");
        String stateCode = readPatronInfo.nextLine();
        System.out.println("Enter zip code of patron: ");
        String zip = readPatronInfo.nextLine();
        System.out.println("Enter email of patron: ");
        String email = readPatronInfo.nextLine();
        System.out.println("Enter date of birth of patron: ");
        String dateOfBirth = readPatronInfo.nextLine();

        newPatron.setProperty("name", name);
        newPatron.setProperty("address", address);
        newPatron.setProperty("city", city);
        newPatron.setProperty("stateCode", stateCode);
        newPatron.setProperty("zip", zip);
        newPatron.setProperty("email", email);
        newPatron.setProperty("dateOfBirth", dateOfBirth);

        Patron insert = new Patron(newPatron);
        insert.save();
    }

    //-----------------------------------------------------------------------------------
    public static void printBooksOlderThanDate(String year) throws InvalidPrimaryKeyException {
        BookCollection bookCollection = new BookCollection();

        bookCollection.findBooksOlderThanDate(year);
        bookCollection.display();
    }

    public static void printBooksNewerThanDate(String year) throws InvalidPrimaryKeyException {
        BookCollection bookCollection = new BookCollection();

        bookCollection.findBooksNewerThanDate(year);
        bookCollection.display();
    }

    //-----------------------------------------------------------------------------------
    public static void printBooksWithTitleLike(String title) throws InvalidPrimaryKeyException {
        BookCollection bookCollection = new BookCollection();

        bookCollection.findBooksWithTitleLike(title);
        bookCollection.display();
    }

    public static void printBooksWithAuthorLike(String author) throws InvalidPrimaryKeyException {
        BookCollection bookCollection = new BookCollection();

        bookCollection.findBooksWithAuthorLike(author);
        bookCollection.display();
    }

    //-----------------------------------------------------------------------------------
    public static void printPatronsOlderThan(String date) throws InvalidPrimaryKeyException {
        PatronCollection patronCollection = new PatronCollection();

        patronCollection.findPatronsOlderThan(date);
        patronCollection.display();
    }

    //-----------------------------------------------------------------------------------
    public static void printPatronsYoungerThan(String date) throws InvalidPrimaryKeyException {
        PatronCollection patronCollection = new PatronCollection();

        patronCollection.findPatronsYoungerThan(date);
        patronCollection.display();
    }

    //-----------------------------------------------------------------------------------
    public static void printPatronsAtZipCode(String zipCode) throws InvalidPrimaryKeyException {
        PatronCollection patronCollection = new PatronCollection();

        patronCollection.findPatronsAtZipCode(zipCode);
        patronCollection.display();
    }

    //-----------------------------------------------------------------------------------
    public static void printPatronsWithNameLike(String name) throws InvalidPrimaryKeyException {
        PatronCollection patronCollection = new PatronCollection();

        patronCollection.findPatronsWithNameLike(name);
        patronCollection.display();
    }
}
