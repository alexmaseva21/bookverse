package com.example.demo.service;

import com.example.demo.model.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void initializeBooks() {
        if (bookRepository.count() == 0) {

            // Fantasy
            Book book1 = new Book();
            book1.setTitle("The Midnight Library");
            book1.setAuthor("Matt Haig");
            book1.setGenre("Fantasy");
            book1.setDescription("Between life and death there is a library, and within that library, the shelves go on forever.");
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setTitle("The Starless Sea");
            book2.setAuthor("Erin Morgenstern");
            book2.setGenre("Fantasy");
            book2.setDescription("A magical world of underground libraries, mystery, and ancient stories written in the stars.");
            bookRepository.save(book2);

            Book book9 = new Book();
            book9.setTitle("The Night Circus");
            book9.setAuthor("Erin Morgenstern");
            book9.setGenre("Fantasy");
            book9.setDescription("An enchanted competition unfolds between two young magicians within a mysterious circus that only opens at night.");
            bookRepository.save(book9);

            // Sci-Fi / Dystopian
            Book book3 = new Book();
            book3.setTitle("Dune");
            book3.setAuthor("Frank Herbert");
            book3.setGenre("Sci-Fi");
            book3.setDescription("A cosmic masterpiece of politics, survival, and mysticism on the desert planet Arrakis.");
            bookRepository.save(book3);

            Book book8 = new Book();
            book8.setTitle("The Hunger Games");
            book8.setAuthor("Suzanne Collins");
            book8.setGenre("Dystopian");
            book8.setDescription("In a dystopian future, teenagers are forced to participate in a televised battle to the death controlled by a ruthless Capitol.");
            bookRepository.save(book8);

            // Mystery / Thriller
            Book book4 = new Book();
            book4.setTitle("The Silent Patient");
            book4.setAuthor("Alex Michaelides");
            book4.setGenre("Mystery");
            book4.setDescription("A shocking psychological thriller about a woman's act of violence against her husband, and the therapist obsessed with uncovering her motive.");
            bookRepository.save(book4);

            // Romance
            Book book5 = new Book();
            book5.setTitle("Book Lovers");
            book5.setAuthor("Emily Henry");
            book5.setGenre("Romance");
            book5.setDescription("A sharp, sparkling romance about a literary agent and an executive editor who keep bumping into each other in a small town.");
            bookRepository.save(book5);

            // Adventure / Classics
            Book book6 = new Book();
            book6.setTitle("The Hobbit");
            book6.setAuthor("J.R.R. Tolkien");
            book6.setGenre("Adventure");
            book6.setDescription("Bilbo Baggins is whisked away from his comfortable hobbit-hole into a dangerous quest to raid the treasure hoard guarded by Smaug the Dragon.");
            bookRepository.save(book6);

            // Historical Fiction
            Book book7 = new Book();
            book7.setTitle("The Seven Husbands of Evelyn Hugo");
            book7.setAuthor("Taylor Jenkins Reid");
            book7.setGenre("Historical Fiction");
            book7.setDescription("An aging Hollywood movie icon reflects on her glamorous, scandalous life and the secret truths behind her seven marriages.");
            bookRepository.save(book7);

        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}