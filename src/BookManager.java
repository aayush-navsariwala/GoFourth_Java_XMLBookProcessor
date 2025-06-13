import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class BookManager {
    @SuppressWarnings("FieldMayBeFinal")
    private String filePath;

    public BookManager(String filePath) {
        this.filePath = filePath;
    }

    public List<Book> readBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Document doc = getDocument();
            NodeList list = doc.getElementsByTagName("book");

            for (int i = 0; i < list.getLength(); i++) {
                Element bookElem = (Element) list.item(i);
                String title = getTagValue(bookElem, "title");
                String author = getTagValue(bookElem, "author");
                String publisher = getTagValue(bookElem, "publisher");
                int year = Integer.parseInt(getTagValue(bookElem, "year"));

                books.add(new Book(title, author, publisher, year));
            }
        } catch (Exception e) {
            System.out.println("Error reading XML: " + e.getMessage());
        }
        return books;
    }

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
    public void addBook(Book newBook) {
        try {
            Document doc = getDocument();
            Element root = doc.getDocumentElement();

            Element bookElem = doc.createElement("book");

            bookElem.appendChild(createTag(doc, "title", newBook.getTitle()));
            bookElem.appendChild(createTag(doc, "author", newBook.getAuthor()));
            bookElem.appendChild(createTag(doc, "publisher", newBook.getPublisher()));
            bookElem.appendChild(createTag(doc, "year", Integer.toString(newBook.getYear())));

            root.appendChild(bookElem);

            saveDocument(doc);
            System.out.println("Book added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    private Document getDocument() throws Exception {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(xmlFile);
    }

    private void saveDocument(Document doc) throws Exception {
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(new File(filePath)));
    }

    private String getTagValue(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent();
    }

    private Element createTag(Document doc, String name, String value) {
        Element tag = doc.createElement(name);
        tag.appendChild(doc.createTextNode(value));
        return tag;
    }
}