/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Dinel
 */
public class Book {
    private int bookID; // maps to infobookID
    private String title;
    private List<Author> authors;
    private Publisher publisher;
    private String category;
    private Date pubDate;
    private String language;
    private BookStatus status;
    private String shelfLocation;

    public Book() {};
    
    public Book(int bookID, String title, List<Author> authors, Publisher publisher, String category, Date pubDate, String language, BookStatus status, String shelfLocation) {
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.category = category;
        this.pubDate = pubDate;
        this.language = language;
        this.status = status;
        this.shelfLocation = shelfLocation;
    }

    // Getters and setters for all fields
    public int getBookID() { return bookID; }
    public void setBookID(int bookID) { this.bookID = bookID; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<Author> getAuthors() { return authors; }
    public void setAuthors(List<Author> authors) { this.authors = authors; }
    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public Date getPubDate() { return pubDate; }
    public void setPubDate(Date pubDate) { this.pubDate = pubDate; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
    public String getShelfLocation() { return shelfLocation; }
    public void setShelfLocation(String shelfLocation) { this.shelfLocation = shelfLocation; }
}