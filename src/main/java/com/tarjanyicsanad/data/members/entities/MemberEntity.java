package com.tarjanyicsanad.data.members.entities;

import com.tarjanyicsanad.data.loans.entities.LoanEntity;
import com.tarjanyicsanad.data.loans.entities.LoanEntity_;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String email;

    @Basic(optional = false)
    private LocalDate joinedAt;

    @OneToMany(mappedBy = LoanEntity_.MEMBER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanEntity> loans = new ArrayList<>();

    public MemberEntity() {}

    public MemberEntity(Integer id, String name, String email, LocalDate joinedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public List<LoanEntity> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanEntity> loans) {
        this.loans = loans;
    }
}
