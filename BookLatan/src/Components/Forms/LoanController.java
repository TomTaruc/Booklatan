/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.AuthorDAO;
import Model.Book;
import Model.BookDAO;
import Model.Member;
import Model.PublisherDAO;
import Model.User.UserType;
import Model.UserMemberDAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Joseph Rey
 */
public class LoanController {
    private LoanForm view;
    private UserMemberDAO memberDAO;
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private PublisherDAO pubDAO;
    private ArrayList<Book> selectedBooks = new ArrayList<>();
    
    public LoanController(UserType type) {
        this.view = new LoanForm(type);
        this.memberDAO = new UserMemberDAO();
        this.bookDAO = new BookDAO();
        this.authorDAO = new AuthorDAO();
        this.pubDAO = new PublisherDAO();
        
        view.memberNameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    Member mem = memberDAO.getMemberByName(view.memberNameField.getText().trim());
                    if(mem  == null) {
                        JOptionPane.showMessageDialog(view, "User Does Exist", "Error", JOptionPane.ERROR_MESSAGE);
                        view.memberNameField.setText("");
                        view.memberIDField.setText("");
                    }
                    else {
                        view.memberNameField.setText(mem.getName());
                        view.memberIDField.setText(String.format("%06d", mem.getMemberID()));
                    }
                }
            }
        });
        
        view.memberNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(view.memberNameField.getText().trim().isEmpty()) {
                    view.memberIDField.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(view.memberNameField.getText().trim().isEmpty()) {
                    view.memberIDField.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(view.memberNameField.getText().trim().isEmpty()) {
                    view.memberIDField.setText("");
                }
            }
        });
    }
    
    
}
