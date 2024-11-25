package com.tarjanyicsanad.data.books.entities;

import com.tarjanyicsanad.data.authors.entities.AuthorEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity_;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private Set<LoanEntity> loans = new HashSet<>();

    @Basic(optional = false)
    private LocalDate publishingDate;

    public BookEntity() {}

    public BookEntity(String title,
                      String description,
                      AuthorEntity author,
                      Set<LoanEntity> loans,
                      LocalDate publishingDate) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.loans = loans;
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

    public Set<LoanEntity> getLoans() {
        return loans;
    }

    public LocalDate getPublishingDate() {
        return publishingDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLoans(Set<LoanEntity> loan) {
        this.loans = loan;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }
}
