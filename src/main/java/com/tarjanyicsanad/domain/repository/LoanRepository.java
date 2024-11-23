package com.tarjanyicsanad.domain.repository;

import com.tarjanyicsanad.domain.exceptions.LoanNotFoundException;
import com.tarjanyicsanad.domain.model.Loan;

import java.util.List;
import java.util.Optional;

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
     * @return a list of all loans
     */
    List<Loan> findAllLoans();

    /**
     * Remove a loan from the repository
     * @param id the id of the loan to remove
     * @throws LoanNotFoundException if the loan with the given id is not found
     */
    void removeLoan(int id) throws LoanNotFoundException;

    /**
     * Updates the loan with the same id as the given loan.
     * @throws LoanNotFoundException if no loan with the same id is found
     */
    void updateLoan(Loan loan) throws LoanNotFoundException;

}
