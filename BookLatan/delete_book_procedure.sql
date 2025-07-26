DELIMITER //

CREATE PROCEDURE DeleteBook(IN book_id INT)
BEGIN
    -- First, delete all loan records for this book
    DELETE FROM loanbook WHERE bookID = book_id;
    
    -- Delete all reservation records for this book
    DELETE FROM reservation WHERE bookID = book_id;
    
    -- Delete all author relationships for this book
    DELETE FROM bookauthor WHERE bookID = book_id;
    
    -- Finally delete the book itself
    DELETE FROM book WHERE bookID = book_id;
    
    SELECT CONCAT('Book with ID ', book_id, ' has been successfully deleted') AS result;
END //

DELIMITER ; 