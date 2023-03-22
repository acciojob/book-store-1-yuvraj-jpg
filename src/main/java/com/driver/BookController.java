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
    //own
    private HashMap<Integer,Book> bookDataBase;

    public HashMap<Integer, Book> getBookDataBase() {
        return bookDataBase;
    }

    public void setBookDataBase(HashMap<Integer, Book> bookDataBase) {
        this.bookDataBase = bookDataBase;
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

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
        this.bookDataBase=new HashMap<Integer,Book>();
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
    public ResponseEntity<Book> getBookById(@PathVariable("id") String id){
        int n=Integer.parseInt(id);
        Book book=bookDataBase.get(n);
        return new ResponseEntity<>(book,HttpStatus.FOUND);
    }


    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("delete-book-by-id/{id}")
    public ResponseEntity<String>deleteBookById(@PathVariable("id") String id){
        int n=Integer.parseInt(id);
        if(bookDataBase.containsKey(n)){
            Book book=bookDataBase.get(n);
            bookDataBase.remove(n);
            bookList.remove(book);
            String response="deleted SucessFully";
            return new ResponseEntity<>(response,HttpStatus.FOUND);
        }
        return new ResponseEntity<>("Database is empty",HttpStatus.NOT_FOUND);
    }


    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>>getAllBooks(){


        return new ResponseEntity<>(getBookList(),HttpStatus.FOUND);
    }



    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){

        bookList.clear();
        bookDataBase.clear();
        return new ResponseEntity<>("Deleted All books Sucessfully",HttpStatus.FOUND);
    }


    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()

    @GetMapping("/get-books-by-author")
    public ResponseEntity<List<Book>>getBooksByAuthor(@RequestParam("name") String authorName){
        List<Book> getbook=new ArrayList<>();
        for (Book book:bookList){
            if(book.getAuthor().equals(authorName)){
                getbook.add(book);

            }
        }
        return new ResponseEntity<>(getbook,HttpStatus.FOUND);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<List<Book>>getBooksByGenre(@RequestParam("genre") String genreName){
        List<Book> getbook=new ArrayList<>();
        for (Book book:bookList){
            if(book.getGenre().equals(genreName)){
                getbook.add(book);

            }
        }
        return new ResponseEntity<>(getbook,HttpStatus.FOUND);
    }

}