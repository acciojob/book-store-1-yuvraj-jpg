package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {
    private List<Book> bookList;
    private int id;
    private HashMap<Integer,Book> bookDataBase;
    public BookController() {
        this.bookList = new ArrayList<>();
        this.id=1;
        this.bookDataBase = new HashMap<>();
    }
    public List<Book> getBookList() {
        return bookList;
    }
    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public HashMap<Integer, Book> getBookDataBase() {
        return bookDataBase;
    }
    public void setBookDataBase(HashMap<Integer, Book> bookDataBase) {
        this.bookDataBase = bookDataBase;
    }
    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(getId());
        bookList.add(book);

        bookDataBase.put(id, book);
        id++;
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }
    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id){
        int x = Integer.parseInt(id);
        Book book = bookDataBase.get(x);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable String id){
        int x = Integer.parseInt(id);
        if(bookDataBase.containsKey(x)){
            Book book = bookDataBase.get(x);
            bookDataBase.remove(x);
            bookList.remove(book);
            return new ResponseEntity<>("deleted ", HttpStatus.OK);
        }
        return new ResponseEntity<>("no book with this is ", HttpStatus.OK);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }


    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks(){
        bookList.clear();
        bookDataBase.clear();
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }


    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam String author){
        List<Book> ans = new ArrayList<>();
        for(Book book : bookList){
            if(book.getAuthor().equals(author)){
                ans.add(book);
            }
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam String genre){
        List<Book> ans = new ArrayList<>();
        for(Book book : bookList){
            if(book.getGenre().equals(genre)){
                ans.add(book);
            }
        }
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
