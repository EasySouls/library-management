package com.tarjanyicsanad.data.books.entities;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity_;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    private String title;

    @Basic(optional = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @OneToMany(mappedBy = LoanEntity_.BOOK)
    private Collection<LoanEntity> loan = new HashSet<>();

    @Basic(optional = false)
    private LocalDate publishingDate;

    public BookEntity() {}

    public BookEntity(String title,
                      String description,
                      AuthorEntity author,
                      Collection<LoanEntity> loans,
                      LocalDate publishingDate) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.loan = loans;
        this.publishingDate = publishingDate;
    }

    public int getId() {
        return id;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public Collection<LoanEntity> getLoan() {
        return loan;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setLoan(Collection<LoanEntity> loan) {
        this.loan = loan;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
}
