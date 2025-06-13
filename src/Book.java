public class Book {
    private String title, author, publisher;
    private int year;

    public Book(String title, String author, String publisher, int year) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author;}
    public String getPublisher() { return publisher;}
    public int getYear() { return year;}

    @Override
    public String toString() {
        return String.format(
            "Title: %s\nAuthor: %s\nPublisher: %s\nYear: %d\n",
            title, author, publisher, year);
    }
}