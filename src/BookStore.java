import java.util.*;

// Abstract Book class
abstract class Book {
    protected String isbn;
    protected String title;
    protected double price;
    protected String author;

    public Book(String isbn, String title, double price, String author) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.author = author;
    }

    public abstract void displayInfo();
}

// PrintedBook class
class PrintedBook extends Book {
    private String coverType; // "hardcover" or "paperback"
    private int numberOfPages;
    private String publisher;
    private int availableCopies;

    public PrintedBook(String isbn, String title, double price, String author,
                       String coverType, int numberOfPages, String publisher, int availableCopies) {
        super(isbn, title, price, author);
        this.coverType = coverType;
        this.numberOfPages = numberOfPages;
        this.publisher = publisher;
        this.availableCopies = availableCopies;
    }

    @Override
    public void displayInfo() {
        System.out.println("Printed Book:");
        System.out.println("  Title: " + title);
        System.out.println("  Author: " + author);
        System.out.println("  ISBN: " + isbn);
        System.out.println("  Price: $" + price);
        System.out.println("  Cover: " + coverType);
        System.out.println("  Pages: " + numberOfPages);
        System.out.println("  Publisher: " + publisher);
        System.out.println("  Available Copies: " + availableCopies);
    }
}

// DigitalBook class
class DigitalBook extends Book {
    private int sizeInKB;

    public DigitalBook(String isbn, String title, double price, String author, int sizeInKB) {
        super(isbn, title, price, author);
        this.sizeInKB = sizeInKB;
    }

    @Override
    public void displayInfo() {
        System.out.println("Digital Book:");
        System.out.println("  Title: " + title);
        System.out.println("  Author: " + author);
        System.out.println("  ISBN: " + isbn);
        System.out.println("  Price: $" + price);
        System.out.println("  Size: " + sizeInKB + " KB");
    }
}

// Audiobook class
class Audiobook extends Book {
    private int durationInMinutes;
    private String narrator;

    public Audiobook(String isbn, String title, double price, String author,
                     int durationInMinutes, String narrator) {
        super(isbn, title, price, author);
        this.durationInMinutes = durationInMinutes;
        this.narrator = narrator;
    }

    @Override
    public void displayInfo() {
        System.out.println("Audiobook:");
        System.out.println("  Title: " + title);
        System.out.println("  Author: " + author);
        System.out.println("  ISBN: " + isbn);
        System.out.println("  Price: $" + price);
        System.out.println("  Duration: " + durationInMinutes + " minutes");
        System.out.println("  Narrator: " + narrator);
    }
}

// BookStore class with main method
public class BookStore {
    private List<Book> books;

    public BookStore() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayAllBooks() {
        for (Book book : books) {
            book.displayInfo();
            System.out.println("----------------------------------");
        }
    }

    public static void main(String[] args) {
        BookStore store = new BookStore();

        store.addBook(new PrintedBook("978-4-8402-5232-4", "Java Basics", 29.99, "Alice Smith",
                "paperback", 300, "TechPress", 15));

        store.addBook(new DigitalBook("978-1-0483-1418-2", "Learning Python", 19.99, "Bob Jones", 2048));

        store.addBook(new Audiobook("978-7-2405-3448-9", "Snow White and the seven dwarfs", 14.99, "Charlie Readalot", 120, "Jane Doe"));

        store.displayAllBooks();
    }
}
