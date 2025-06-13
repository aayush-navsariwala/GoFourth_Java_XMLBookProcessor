import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            BookManager manager = new BookManager("books.xml");

            while (true) {
                System.out.println("\n--- Book XML Manager ---");
                System.out.println("1. View All Books");
                System.out.println("2. Search Books");
                System.out.println("3. Add New Book");
                System.out.println("4. Exit");
                System.out.print("Choose option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        List<Book> books = manager.readBooks();
                        for (Book b : books) System.out.println(b);
                    }
                    case 2 -> {
                        System.out.print("Enter keyword: ");
                        String keyword = scanner.nextLine();
                        List<Book> results = manager.searchBooks(keyword);
                        if (results.isEmpty()) System.out.println("No matches found.");
                        else for (Book b : results) System.out.println(b);
                    }
                    case 3 -> {
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Publisher: ");
                        String publisher = scanner.nextLine();
                        System.out.print("Year: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();

                        manager.addBook(new Book(title, author, publisher, year));
                    }
                    case 4 -> {
                        System.out.println("Exiting program.");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        }
    }
}