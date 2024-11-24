package com.tarjanyicsanad.data.loans;

import com.tarjanyicsanad.data.loans.entities.LoanEntity;
import com.tarjanyicsanad.domain.exceptions.LoanNotFoundException;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import jakarta.persistence.EntityManager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Optional;

/**
 * JPA implementation of the {@link LoanRepository} interface.
 */
public class JpaLoanRepository
        extends BaseRepository<LoanEntity, Integer, LoanNotFoundException>
        implements LoanRepository {

    @Inject
    public JpaLoanRepository(EntityManager entityManager) {
        super(entityManager, LoanEntity.class);
    }

    @Override
    public void addLoan(Loan loan) {
        super.save(loan.toEntity());
    }

    @Override
    public Optional<Loan> getLoan(int id) {
        return Optional.ofNullable(super.findById(id)).map(Loan::fromEntity);
    }

    @Override
    public ArrayList<Loan> findAllLoans() {
        ArrayList<Loan> loans = new ArrayList<>();
        super.findAll().forEach(loanEntity -> loans.add(Loan.fromEntity(loanEntity)));
        return loans;
    }

    @Override
    public void removeLoan(int id) throws LoanNotFoundException {
        LoanEntity loanEntity = super.findById(id);
        if (loanEntity == null) {
            throw new LoanNotFoundException("Loan with " + id + " not found");
        }
        super.delete(loanEntity);
    }

    @Override
    public void updateLoan(Loan loan) {
        super.update(loan.toEntity());
    }
}
