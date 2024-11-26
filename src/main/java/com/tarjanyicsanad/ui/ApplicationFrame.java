package com.tarjanyicsanad.ui;

import com.tarjanyicsanad.data.settings.UserSettings;
import com.tarjanyicsanad.di.DaggerRepositoryFactory;
import com.tarjanyicsanad.di.RepositoryFactory;
import com.tarjanyicsanad.domain.model.Author;
import com.tarjanyicsanad.domain.model.Book;
import com.tarjanyicsanad.domain.repository.AuthorRepository;
import com.tarjanyicsanad.domain.repository.BookRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import com.tarjanyicsanad.domain.repository.MemberRepository;
import com.tarjanyicsanad.ui.authors.AuthorsScreen;
import com.tarjanyicsanad.ui.books.BooksScreen;
import com.tarjanyicsanad.ui.members.MembersScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The main frame of the application.
 */
public class ApplicationFrame {
    private JFrame frame;

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;

    private static final Logger logger = LogManager.getLogger(ApplicationFrame.class);

    /**
     * Creates a new {@link ApplicationFrame} with the given title, width, height and repositories.
     *
     * @param title            the title of the frame
     * @param width            the width of the frame
     * @param height           the height of the frame
     * @param bookRepository   the repository for {@link Book}s
     * @param authorRepository the repository for {@link Author}s
     * @param memberRepository the repository for {@link com.tarjanyicsanad.domain.model.Member}s
     * @param loanRepository   the repository for {@link com.tarjanyicsanad.domain.model.Loan}s
     */
    public ApplicationFrame(String title,
                            int width,
                            int height,
                            BookRepository bookRepository,
                            AuthorRepository authorRepository,
                            MemberRepository memberRepository,
                            LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.memberRepository = memberRepository;
        this.loanRepository = loanRepository;

        /// If the user wants to load the data from the file,
        /// and the preferred storage mode is not database, then load it
        if (UserSettings.getLoadFromFile() && UserSettings.getStorageMode() == UserSettings.StorageMode.IN_MEMORY) {
            readFromFile();
        }

        initComponents(title, width, height);

        /// If the user wants to save the data to the file, it will be saved when the window is closed
        if (UserSettings.getSaveToFile()) {
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    saveToFile();
                }
            });
        }

        frame.setLocationRelativeTo(null);
        frame.pack();
    }

    /**
     * Sets the visibility of the frame to true.
     */
    public void show() {
        frame.setVisible(true);
    }

    private void initComponents(String title, int width, int height) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width, height);

        CardLayout screensLayout = new CardLayout();
        screensLayout.addLayoutComponent(new BooksScreen(bookRepository, authorRepository, memberRepository, loanRepository), Screens.BOOKS);
        screensLayout.addLayoutComponent(new AuthorsScreen(authorRepository), Screens.AUTHORS);
        screensLayout.addLayoutComponent(new MembersScreen(memberRepository), Screens.MEMBERS);

        JPanel screens = new JPanel(screensLayout);
        screens.add(new BooksScreen(bookRepository, authorRepository, memberRepository, loanRepository), Screens.BOOKS);
        screens.add(new AuthorsScreen(authorRepository), Screens.AUTHORS);
        screens.add(new MembersScreen(memberRepository), Screens.MEMBERS);

        frame.setJMenuBar(new MenuBar(
                layout -> screensLayout.show(screens, layout),
                this::saveToFile,
                this::readFromFile
        ));
        frame.add(screens);
    }

    /**
     * Reads the data from the files and adds it to the repositories
     */
    private void readFromFile() {
        try(ObjectInputStream bookStream = new ObjectInputStream(new FileInputStream("books.dat"));
            ObjectInputStream authorStream = new ObjectInputStream(new FileInputStream("authors.dat"))) {

            Object readObject;
            while ((readObject = bookStream.readObject()) != null) {
                logger.info(readObject);
                Book book = (Book) readObject;
                bookRepository.addBook(book);
                logger.info(book);
            }

            while ((readObject = authorStream.readObject()) != null) {
                logger.info(readObject);
                Author author = (Author) readObject;
                authorRepository.addAuthor(author);
                logger.info(author);
            }
        } catch (Exception e) {
            logger.error("Error while reading from file", e);
        }
    }

    /**
     * Saves the data inside the repositories to the files
     */
    private void saveToFile() {
        try(ObjectOutputStream bookStream = new ObjectOutputStream(new FileOutputStream("books.dat"));
            ObjectOutputStream authorStream = new ObjectOutputStream(new FileOutputStream("authors.dat"))) {
            bookRepository.findAllBooks().forEach(book -> {
                try {
                    bookStream.writeObject(book);
                } catch (Exception e) {
                    logger.error("Error while saving book to file", e);
                }
            });
            authorRepository.findAllAuthors().forEach(author -> {
                try {
                    authorStream.writeObject(author);
                } catch (Exception e) {
                    logger.error("Error while saving author to file", e);
                }
            });
        } catch (Exception e) {
            logger.error("Error while saving to file", e);
        }
    }
}
