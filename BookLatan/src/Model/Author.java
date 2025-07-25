/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Dinel
 */
public class Author {
    private int authorID;
    private String name;
    private String bio;

    public Author(int authorID, String name, String bio) {
        this.authorID = authorID;
        this.name = name;
        this.bio = bio;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int auhorID) {
        this.authorID = authorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    } 
}