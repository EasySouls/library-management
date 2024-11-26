package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.exceptions.LoanNotFoundException;
import com.tarjanyicsanad.domain.model.Loan;

import java.util.Optional;
import java.util.Set;

/**
 * A repository for managing {@link Loan}s.
 */
public interface LoanRepository {
    /**
     * Add a new loan to the repository
     * @param loan the loan to add
     */
    void addLoan(Loan loan);

    /**
     * Returns a loan with the given id.
     * @param id the id of the loan to return
     * @return an empty optional if no loan with the given id is found
     */
    Optional<Loan> getLoan(int id);

    /**
     * Get all loans from the repository
     * @return a set of all loans
     */
    Set<Loan> findAllLoans();

    /**
     * Get all loans with the given book id from the repository
     * @param bookId the id of the book to get loans for
     * @return a set of all loans with the given book id
     */
    Set<Loan> findLoansByBookId(int bookId);

    /**
     * Remove a loan from the repository
     * @param id the id of the loan to remove
     * @throws LoanNotFoundException if the loan with the given id is not found
     */
    void removeLoan(int id) throws LoanNotFoundException;

    /**
     * Remove all loans with the given book id from the repository
     * @param bookId the id of the book to remove loans for
     */
    void removeLoansByBookId(int bookId);

    /**
     * Updates the loan with the same id as the given loan.
     * @param loan the loan to update
     * @throws LoanNotFoundException if no loan with the same id is found
     */
    void updateLoan(Loan loan) throws LoanNotFoundException;

}
