package com.tarjanyicsanad.ui;

import com.tarjanyicsanad.data.books.InMemoryBookRepository;
import com.tarjanyicsanad.data.settings.UserSettings;
import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.ui.authors.AuthorsScreen;
import com.tarjanyicsanad.ui.authors.AuthorsTableModel;
import com.tarjanyicsanad.ui.books.BooksScreen;
import com.tarjanyicsanad.ui.books.BooksTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ApplicationFrame {
    private JFrame frame;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private static Logger logger = LogManager.getLogger(ApplicationFrame.class);

    public ApplicationFrame(String title, int width, int height) {
        /// Handle dependency injection
        RepositoryFactory repositoryFactory = DaggerRepositoryFactory.create();
        bookRepository = repositoryFactory.bookRepository();
        authorRepository = repositoryFactory.authorRepository();

        /// If the user wants to load the data from the file,
        /// and the preferred storage mode is not database, then load it
        if (UserSettings.getLoadFromFile() && UserSettings.getStorageMode() == UserSettings.StorageMode.IN_MEMORY) {
            readFromFile();
        }

        initComponents(title, width, height);

        if (UserSettings.getSaveToFile()) {
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    saveToFile();
                }
            });
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initComponents(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        BooksTableModel booksTableModel = new BooksTableModel(bookRepository);
        AuthorsTableModel authorsTableModel = new AuthorsTableModel(authorRepository);

        CardLayout screensLayout = new CardLayout();
        screensLayout.addLayoutComponent(new BooksScreen(booksTableModel), Screens.BOOKS);
        screensLayout.addLayoutComponent(new AuthorsScreen(authorsTableModel), Screens.AUTHORS);

        JPanel screens = new JPanel(screensLayout);
        screens.add(new BooksScreen(booksTableModel), Screens.BOOKS);
        screens.add(new AuthorsScreen(authorsTableModel), Screens.AUTHORS);

        frame.setJMenuBar(new MenuBar(layout -> screensLayout.show(screens, layout)));
        frame.add(screens);
    }

    private void readFromFile() {
        try(ObjectInputStream bookStream = new ObjectInputStream(getClass().getResourceAsStream("books.dat"));
            ObjectInputStream authorStream = new ObjectInputStream(getClass().getResourceAsStream("authors.dat"))) {

            List<Book> books = (List<Book>)bookStream.readObject();
            books.forEach(book -> {
                bookRepository.addBook(book);
                logger.info(book);
            });
            List<Author> authors = (List<Author>)authorStream.readObject();
            authors.forEach(author -> {
                authorRepository.addAuthor(author);
                logger.info(author);
            });
        } catch (Exception e) {
            logger.error("Error while reading from file", e);
        }
    }

    private void saveToFile() {
        try(ObjectOutputStream bookStream = new ObjectOutputStream(new FileOutputStream("books.dat"));
            ObjectOutputStream authorStream = new ObjectOutputStream(new FileOutputStream("authors.dat"))) {
            bookStream.writeObject(bookRepository.findAll());
            authorStream.writeObject(authorRepository.findAll());
        } catch (Exception e) {
            logger.error("Error while saving to file", e);
        }
    }
}
