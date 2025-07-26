/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.AuthorDAO;
import Model.Book;
import Model.BookDAO;
import Model.BookStatus;
import Model.Loan;
import Model.LoanDAO;
import Model.Member;
import Model.PublisherDAO;
import Model.User;
import Model.User.UserType;
import Model.UserMemberDAO;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
    private LoanDAO loanDAO;
    private ArrayList<Book> selectedBooks = new ArrayList<>();
    private ArrayList<Book> availableBooks = new ArrayList<>();
    private Runnable updateTable;
    
    public LoanController(User user, Runnable updateTable) {
        this.view = new LoanForm(user.getType());
        this.memberDAO = new UserMemberDAO();
        this.bookDAO = new BookDAO();
        this.authorDAO = new AuthorDAO();
        this.pubDAO = new PublisherDAO();
        this.loanDAO = new LoanDAO();
        this.updateTable = updateTable;
        filterTable();
        
        if(user.getType() == UserType.LIBRARIAN) {
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
        else {
            view.memberIDField.setEditable(false);
            view.memberNameField.setEditable(false);
            Member member = memberDAO.getMemberByID(memberDAO.getMemberIDByUSerID(user.getUserId()));
            view.memberIDField.setText(String.format("%06d", member.getMemberID()));
            view.memberNameField.setText(member.getName());
        }
        
        
        this.view.booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(view, "Do you want to loan this book?", "Selected Book", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.NO_OPTION) {
                    return;
                }
                
                int rowIndex = view.booksTable.getSelectedRow();
                int columnCount = view.booksTable.getColumnCount();
                Object[] rowData = new Object[columnCount];
                
                for(int i = 0; i < columnCount; i++) {
                    rowData[i] = view.booksTable.getValueAt(rowIndex, i);
                }
                view.addedBooksModel.addRow(rowData);
                
                int bookID = Integer.parseInt(view.booksTable.getValueAt(rowIndex, 0).toString());
                selectedBooks.add(bookDAO.getBookByID(bookID));
                
                updateTable();
            }
            
        });
        
        this.view.addedBooks.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(view, "Do you want to remove this book?", "Selected Book", JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.NO_OPTION) {
                    return;
                }
                
                int rowIndex = view.addedBooks.getSelectedRow();
                Book bookToRemove = null;
                
                
                int bookID = Integer.parseInt(view.addedBooks.getValueAt(rowIndex, 0).toString());
                for(Book book : selectedBooks) {
                    if(book.getBookID() == bookID) {
                        bookToRemove = book;
                        break;
                    }
                }
                
                view.addedBooksModel.removeRow(rowIndex);
                selectedBooks.remove(selectedBooks.indexOf(bookToRemove));
                updateTable();
            }
            
        });
        
        this.view.searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        
        });
        
        this.view.loanBook.addActionListener(e -> {
            if(view.memberNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(view, "Member name and id must not be empty", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(((Date) view.dueDateField.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now())) {
                JOptionPane.showMessageDialog(view, "The Due date must not be today.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(selectedBooks.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Select a book to loan.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            Loan loan = new Loan();
            loan.setIssueDate(LocalDate.now());
            loan.setDueDate(((Date) view.dueDateField.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            loan.setMemberID(Integer.parseInt(view.memberIDField.getText()));
            loanDAO.addLoan(loan, selectedBooks);
            this.updateTable.run();
            JOptionPane.showMessageDialog(view, "Loan has been issued", "Success", JOptionPane.INFORMATION_MESSAGE);
            this.view.dispose();
        });
    }
    
    public void filterTable() {
        if(view.searchBar.getText().equalsIgnoreCase("Search book")) {
            availableBooks = bookDAO.getAllBooksByStatus(BookStatus.AVAILABLE); 
            updateTable();
        }
        else {
            availableBooks = bookDAO.getAllBooksByTitleStatus(view.searchBar.getText(), BookStatus.AVAILABLE);
            updateTable();
        }
    }
    
    
    private void updateTable() {
        view.booksTableModel.setRowCount(0);
        
        boolean skip = false;
        for(Book book: availableBooks) {
            
            for(Book xbook: selectedBooks) {
                if(xbook.getBookID() == book.getBookID()) {
                    skip = true;
                    break;
                }
            }
            
            if(skip) {
                skip = false;
                continue;
            }
            
            view.booksTableModel.addRow(new Object[] {
                String.format("%06d", book.getBookID()),
                book.getTitle(),
                book.getCategory(),
                book.getAuthors().size() >= 2 ? book.getAuthors().get(0).getName() + " et al." : book.getAuthors().get(0).getName(), 
            });
        }
    }
    
    public void clearSelected() {
        this.selectedBooks.clear();
    }
}
