package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Expense;
import com.sistema.sicaf.model.PaymentStatus;
import com.sistema.sicaf.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public List<Expense> findPending() {
        return expenseRepository.findByStatusOrderByDueDateAsc(PaymentStatus.PENDING);
    }

    public Expense save(Expense expense) {
        if (expense.getStatus() == null) {
            expense.setStatus(PaymentStatus.PENDING);
        }
        return expenseRepository.save(expense);
    }

    public void settleExpense(Long id) {
        expenseRepository.findById(id).ifPresent(expense -> {
            expense.setStatus(PaymentStatus.PAID);
            expense.setPaymentDate(LocalDate.now());
            expenseRepository.save(expense);
        });
    }

    public void deleteById(Long id) {
        expenseRepository.deleteById(id);
    }

    public Optional<Expense> findById(Long id) {
        return expenseRepository.findById(id);
    }
}
