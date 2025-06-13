import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Try to ensure the scanner is closed automatically
        try (Scanner scanner = new Scanner(System.in)) 
        {
            //Initialise BookManager with the path to the XML file
            BookManager manager = new BookManager("books.xml");

            //Menu loop to keep the program running until the user exits
            while (true) 
            {
                //Displays menu options
                System.out.println("\n--- Book XML Manager ---");
                System.out.println("1. View All Books");
                System.out.println("2. Search Books");
                System.out.println("3. Add New Book");
                System.out.println("4. Exit");
                System.out.print("Choose option: ");

                //Read user's menu choice
                int choice = scanner.nextInt();
                //Consume new line
                scanner.nextLine();

                //Perform actions based on the user's choice
                switch (choice) 
                {
                    //Read and display all books from the XML
                    case 1:
                        List<Book> books = manager.readBooks();
                        for (Book b : books) System.out.println(b);
                        break;
                    
                    //Search for books using a keyword
                    case 2:
                        System.out.print("Enter keyword: ");
                        String keyword = scanner.nextLine();
                        List<Book> results = manager.searchBooks(keyword);
                        //Display matching results or message if none found
                        if (results.isEmpty()) System.out.println("No matches found.");
                        else for (Book b : results) System.out.println(b);
                        break;
                    
                    //Add a new book with user input
                    case 3:
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Publisher: ");
                        String publisher = scanner.nextLine();
                        System.out.print("Year: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();

                        //Add the book to the XML file
                        manager.addBook(new Book(title, author, publisher, year));
                        break;
                    
                    //Exit the program    
                    case 4:
                        System.out.println("Exiting program.");
                        return;

                    //Handles invalid menu inputs
                    default:
                        System.out.println("Invalid option.");
                }
            }
        }
    }
}