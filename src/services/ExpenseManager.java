package services;

import models.EqualSplittedExpense;
import models.Expense;
import models.UnequalSplittedExpense;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    List<User> users;
    Map<User, Map<User, Double>> balanceSheet;

    public ExpenseManager() {
        this.users = new ArrayList<>();
        this.balanceSheet = new HashMap<>();
    }

    public void addUser(User user) {
        this.users.add(user);
        this.balanceSheet.put(user, new HashMap<>());
    }

    public User getUserByName(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) return user;
        }
        return null;
    }

    public void addExpense(Expense expense) {
        System.out.println("Adding expense :" + expense.toString());
        switch (expense.getSplitType()) {
            case EQUAL:
                EqualSplittedExpense equalSplittedExpense = (EqualSplittedExpense) expense;
                updateBalanceSheetForEqualSplittedExpense(equalSplittedExpense);
                break;
            case EXACT:
                UnequalSplittedExpense exactSplitted = (UnequalSplittedExpense) expense;
                updateBalanceSheetForExactSplittedExpense(exactSplitted);
                break;
            case PERCENT:
                UnequalSplittedExpense percentSplitted = (UnequalSplittedExpense) expense;
                updateBalanceSheetForPercentSplitted(percentSplitted);

        }
    }

    private void updateBalanceSheetForPercentSplitted(UnequalSplittedExpense percentSplitted) {
        System.out.println("Updating balanceSheeet for percent splitted");
        if (!percentSplitted.isValid()) {
            throw new RuntimeException("share is not adding upto 100");
        }

        for (Map.Entry<User, Double> share : percentSplitted.getShare().entrySet()) {
            if (share.getKey() != percentSplitted.getGiver()) {
                Double amount = (percentSplitted.getAmount() * share.getValue() / 100.0);
                paid(percentSplitted.getGiver(), share.getKey(), amount);
            }
        }
    }

    private void updateBalanceSheetForExactSplittedExpense(UnequalSplittedExpense unequalSplittedExpense) {
        System.out.println("Updating balanceSheet for exactsplitted");
        if (!unequalSplittedExpense.isValid()) {
            throw new RuntimeException("share is not adding upto 100");
        }
        for (Map.Entry<User, Double> share : unequalSplittedExpense.getShare().entrySet()) {
            if (share.getKey() != unequalSplittedExpense.getGiver()) {
                paid(unequalSplittedExpense.getGiver(), share.getKey(), share.getValue());
            }
        }
    }

    public void updateBalanceSheetForEqualSplittedExpense(EqualSplittedExpense equalSplittedExpense) {
        System.out.println("UpdatingBalanceSheet for EqualSplitted");
        for (User user : equalSplittedExpense.getUsersInvolved()) {
            if (user != equalSplittedExpense.getGiver()) {
                Double amount = equalSplittedExpense.getAmount() / equalSplittedExpense.getUsersInvolved().size();
                paid(equalSplittedExpense.getGiver(), user, amount);
            }
        }
    }

    public void paid(User user1, User user2, Double amount) {
        Double alreadyPaidAmount = this.balanceSheet.get(user1).get(user2);
//        Double newAmount = amount;
        if (alreadyPaidAmount != null) {
            amount += alreadyPaidAmount;
        }
        Map<User, Double> balanceSheetforUser1 = this.balanceSheet.get(user1);
        balanceSheetforUser1.put(user2, amount);
    }

    public void showBalanceSheet() {
        for (int i = 0; i < this.users.size(); i++) {
            for (int j = i + 1; j < this.users.size(); j++) {
                User user1 = this.users.get(i);
                User user2 = this.users.get(j);
                Double amountUser1PaidToUser2 = this.balanceSheet.get(user1).get(user2);
                Double amountUser2PaidToUser1 = this.balanceSheet.get(user2).get(user1);
                printAmount(amountUser1PaidToUser2, amountUser2PaidToUser1, user1, user2);
            }
        }
    }

    public Double trim(Double amount) {
//        System.out.println("Into trim method");
        int d = (int) (amount * 100);
//        System.out.println("d : " + d);
        return d / 100.0;
    }


    public void printAmount(Double amountUser1PaidToUser2, Double amountUser2PaidToUser1, User user1, User user2) {
        if (amountUser1PaidToUser2 != null && amountUser2PaidToUser1 != null) {
            Double owed = trim(Math.abs(amountUser1PaidToUser2 - amountUser2PaidToUser1));
            if (owed == 0.0) return;
            if (amountUser1PaidToUser2 > amountUser2PaidToUser1) {
                System.out.println("User " + user2.getName() + " owes: " + user1.getName() + " " + owed);
            } else {
                System.out.println("User " + user1.getName() + " owes: " + user2.getName() + " " + owed);
            }
        } else {
            if (amountUser1PaidToUser2 == null) {
                if (amountUser2PaidToUser1 != null && trim(amountUser2PaidToUser1) != 0.0) {
                    System.out.println("User " + user1.getName() + " owes: " + user2.getName() + " " + trim(amountUser2PaidToUser1));
                }
            }

            if (amountUser2PaidToUser1 == null) {
                if (amountUser1PaidToUser2 != null && trim(amountUser1PaidToUser2) != 0.0) {
                    System.out.println("User " + user2.getName() + " owes: " + user1.getName() + " " + trim(amountUser1PaidToUser2));
                }
            }
        }
    }

    public void showBalanceSheet(User user1) {

        for (User user2 : users) {
            if (user2 != user1) {
                Double amountUser1PaidToUser2 = this.balanceSheet.get(user1).get(user2);
                Double amountUser2PaidToUser1 = this.balanceSheet.get(user2).get(user1);
                printAmount(amountUser1PaidToUser2, amountUser2PaidToUser1, user1, user2);
            }

        }

    }

    public void showAll() {
        for (User user : this.users) {
            System.out.println(" printing balances for user: " + user.getName());
            for (Map.Entry<User, Double> entry : this.balanceSheet.get(user).entrySet()) {
                System.out.println("paid to user : " + entry.getKey() + " amount: " + entry.getValue());
            }
        }
    }
}
