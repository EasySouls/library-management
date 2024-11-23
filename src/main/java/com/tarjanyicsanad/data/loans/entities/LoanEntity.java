package com.tarjanyicsanad.data.loans.entities;

import com.tarjanyicsanad.data.books.entities.BookEntity;
import com.tarjanyicsanad.data.books.entities.BookEntity_;
import com.tarjanyicsanad.data.members.entities.MemberEntity;
import com.tarjanyicsanad.data.members.entities.MemberEntity_;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @Basic(optional = false)
    private LocalDate loanedAt;

    @Basic(optional = false)
    private LocalDate returnDate;

    public LoanEntity() {}

    public LoanEntity(Integer id, BookEntity book, MemberEntity member, LocalDate loanedAt, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.loanedAt = loanedAt;
        this.returnDate = returnDate;
    }

    public Integer getId() {
        return id;
    }

    public BookEntity getBook() {
        return book;
    }

    public MemberEntity getMember() {
        return member;
    }

    public LocalDate getLoanedAt() {
        return loanedAt;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }
}
