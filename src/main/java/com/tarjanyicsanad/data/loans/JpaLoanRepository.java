package com.tarjanyicsanad.data.loans;

import com.tarjanyicsanad.data.loans.entities.LoanEntity;
import com.tarjanyicsanad.domain.exceptions.LoanNotFoundException;
import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.repository.BaseRepository;
import com.tarjanyicsanad.domain.repository.LoanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * JPA implementation of the {@link LoanRepository} interface.
 */
public class JpaLoanRepository
        extends BaseRepository<LoanEntity, Integer, LoanNotFoundException>
        implements LoanRepository {

    private static final Logger logger = LogManager.getLogger(JpaLoanRepository.class);

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
    public Set<Loan> findAllLoans() {
        Set<Loan> loans = new HashSet<>();
        super.findAll().forEach(loanEntity -> loans.add(Loan.fromEntity(loanEntity)));
        return loans;
    }

    @Override
    public Set<Loan> findLoansByBookId(int bookId) {
        try {
            return entityManager.createQuery("SELECT l FROM loans l WHERE l.book.id = :bookId", LoanEntity.class)
                    .setParameter("bookId", bookId)
                    .getResultList()
                    .stream()
                    .map(Loan::fromEntity)
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            logger.error("Failed to find loans by book id", e);
            return Collections.emptySet();
        }
    }

    @Override
    public void removeLoan(int id) throws LoanNotFoundException {
        LoanEntity loanEntity = super.findById(id);
        if (loanEntity == null) {
            throw new LoanNotFoundException("Loan with " + id + " not found");
        }
        super.delete(loanEntity);
    }

    /**
     * Remove all loans with the given book id from the repository.
     * Guaranteed to be executed in a single transaction.
     * @param bookId the id of the book to remove loans for
     */
    @Override
    public void removeLoansByBookId(int bookId) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("DELETE FROM loans l WHERE l.book.id = :bookId")
                    .setParameter("bookId", bookId)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Failed to remove loans by book id", e);
        }
    }

    @Override
    public void updateLoan(Loan loan) {
        super.update(loan.toEntity());
    }
}
