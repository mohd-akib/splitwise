package services;

import models.*;

import java.util.List;
import java.util.Map;

public class ExpenseCreator {

    public static Expense createEqualSplittedExpense(SplitType splitType, List<User> usersInvolved, User paidBy, Double amount) {
        EqualSplittedExpense equalSplittedExpense = new EqualSplittedExpense(paidBy,amount, splitType, usersInvolved);
        return equalSplittedExpense;
    }

    public static Expense createUnequalSplittedExpense(SplitType splitType, Map<User,Double> share, User paidBy, Double amount) {
        return new UnequalSplittedExpense(paidBy, amount, splitType, share);
    }
}
