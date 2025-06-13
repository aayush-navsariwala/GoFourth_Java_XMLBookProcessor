public class Book {
    //Fields to store book information
    private String title, author, publisher;
    private int year;

    //Constructor to initialise a Book object with its details
    public Book(String title, String author, String publisher, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    //Getter methods for title, author, publisher and year
    public String getTitle() { return title; }
    public String getAuthor() { return author;}
    public String getPublisher() { return publisher;}
    public int getYear() { return year;}

    //Overrides the default toString() method to provide a readable string
    @Override
    public String toString() {
        return String.format(
            "Title: %s\nAuthor: %s\nPublisher: %s\nYear: %d\n",
            title, author, publisher, year);
    }
}