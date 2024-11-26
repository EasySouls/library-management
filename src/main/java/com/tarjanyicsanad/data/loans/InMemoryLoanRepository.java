package com.tarjanyicsanad.data.loans;

import com.tarjanyicsanad.domain.model.Loan;
import com.tarjanyicsanad.domain.repository.LoanRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An in-memory implementation of the {@link LoanRepository} interface.
 */
public class InMemoryLoanRepository implements LoanRepository {
    private final Set<Loan> loans;

    /**
     * Creates a new {@link InMemoryLoanRepository}.
     *
     * @param loans the initial set of loans
     */
    public InMemoryLoanRepository(Set<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    @Override
    public void removeLoan(int id) {
        loans.removeIf(loan -> loan.id() == id);
    }

    @Override
    public Optional<Loan> getLoan(int id) {
        return loans.stream()
                .filter(loan -> loan.id() == id)
                .findFirst();
    }

    @Override
    public Set<Loan> findAllLoans() {
        return loans;
    }

    @Override
    public Set<Loan> findLoansByBookId(int bookId) {
        return loans.stream()
                .filter(loan -> loan.book().id() == bookId)
                .collect(Collectors.toSet());
    }

    @Override
    public void updateLoan(Loan loan) {
        loans.removeIf(l -> l.id() == loan.id());
        loans.add(loan);
    }

    @Override
    public void removeLoansByBookId(int bookId) {
        loans.removeIf(loan -> loan.book().id() == bookId);
    }
}
