import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class BookManager {
    @SuppressWarnings("FieldMayBeFinal")
    //Path to the XML file storing book data
    private String filePath;

    //Constructor to initialise file path
    public BookManager(String filePath) {
        this.filePath = filePath;
    }

    //Reads all books from the XML file and returns them as a list
    public List<Book> readBooks() {
        List<Book> books = new ArrayList<>();
        try {
            //Parse the XML document
            Document doc = getDocument();
            //Get all <book> elements
            NodeList list = doc.getElementsByTagName("book");

            //Loop through each <book> and extract data
            for (int i = 0; i < list.getLength(); i++) {
                Element bookElem = (Element) list.item(i);

                //Extract values from each sub-element
                String title = getTagValue(bookElem, "title");
                String author = getTagValue(bookElem, "author");
                String publisher = getTagValue(bookElem, "publisher");
                int year = Integer.parseInt(getTagValue(bookElem, "year"));

                //Create a new Book object and add it to the list
                books.add(new Book(title, author, publisher, year));
            }
        } 
        catch (Exception e) 
        {
            System.out.println("Error reading XML: " + e.getMessage());
        }
        return books;
    }

    //Searches for books where the keyword matches title, author or publisher
    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : readBooks()) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
                book.getPublisher().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    //Adds a new book to the XML file
    public void addBook(Book newBook) {
        try {
            //Loads the existing XML
            Document doc = getDocument();
            //Get root <library> element
            Element root = doc.getDocumentElement();
            
            //Create new <book> element with sub-elements
            Element bookElem = doc.createElement("book");

            bookElem.appendChild(createTag(doc, "title", newBook.getTitle()));
            bookElem.appendChild(createTag(doc, "author", newBook.getAuthor()));
            bookElem.appendChild(createTag(doc, "publisher", newBook.getPublisher()));
            bookElem.appendChild(createTag(doc, "year", Integer.toString(newBook.getYear())));

            //Append the new book to the root element
            root.appendChild(bookElem);

            //Save the modified document back to file
            saveDocument(doc);
            System.out.println("Book added successfully!");
        } 
        catch (Exception e) 
        {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    //Parses the XML file and returns the Document object
    private Document getDocument() throws Exception {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(xmlFile);
    }

    //Saves the modified XML Document to the file
    private void saveDocument(Document doc) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    //Gets the text content of a tag inside a parent element
    private String getTagValue(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent();
    }

    //Creates an XML tag with a given name and value
    private Element createTag(Document doc, String name, String value) {
        Element tag = doc.createElement(name);
        tag.appendChild(doc.createTextNode(value));
        return tag;
    }
}