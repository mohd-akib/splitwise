import models.Expense;
import models.SplitType;
import models.User;
import services.ExpenseCreator;
import services.ExpenseManager;

import java.util.*;

public class MainApplication {

    public static void main(String[] args) {
        User user1 = new User("1","u1","123","mail2");
        User user2 = new User("2", "u2", "345", "mail2");
        User user3 = new User("3", "u3", "2354534", "mail3");
        User user4 = new User("4", "u4", "243652", "mail4");
//        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));




        Scanner scanner = new Scanner(System.in);
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.addUser(user1);
        expenseManager.addUser(user2);
        expenseManager.addUser(user3);
        expenseManager.addUser(user4);
        while (true) {
            String command = scanner.nextLine();
            String[] commands = command.split(" ");
            String commandType = commands[0];

            switch (commandType) {
                case "SHOWALL":
                    expenseManager.showAll();
                    break;
                case "SHOW":
                    if (commands.length == 1) {
                        expenseManager.showBalanceSheet();
                    } else {
                        expenseManager.showBalanceSheet(expenseManager.getUserByName(commands[1]));
                    }
                    break;
                case "EXPENSE":
                    String paidBy = commands[1];
                    Double amount = Double.parseDouble(commands[2]);
                    int noOfUsers = Integer.parseInt(commands[3]);
                    String expenseType = commands[4 + noOfUsers];
                    Map<User, Double> share = new HashMap<>();
                    List<User> usersInvolved = new ArrayList<>();
                    System.out.println("ExpenseType : " + expenseType);
                    switch (expenseType) {
                        case "EQUAL":
                            System.out.println("Into EQUAL case");
                            for (int i = 0; i < noOfUsers; i++) {
                                usersInvolved.add(expenseManager.getUserByName(commands[4+i]));
                            }
                            System.out.println("Users involved: " + usersInvolved);
                            Expense expense = ExpenseCreator.createEqualSplittedExpense(
                                    SplitType.EQUAL,
                                    usersInvolved,
                                    expenseManager.getUserByName(paidBy),
                                    amount
                            );
                            expenseManager.addExpense(expense);
                            break;
                        case "EXACT":
                            System.out.println("Into EXACT case");
                            for (int i = 0; i < noOfUsers; i++) {
                                share.put(
                                        expenseManager.getUserByName(commands[4+i]),
                                        Double.parseDouble(commands[5 + noOfUsers + i])
                                );
                            }
                            Expense expense1 = ExpenseCreator.createUnequalSplittedExpense(
                                    SplitType.EXACT,
                                    share,
                                    expenseManager.getUserByName(paidBy),
                                    amount
                            );
                            expenseManager.addExpense(expense1);
                            break;
                        case "PERCENT":
                            System.out.println("Into PERCENT case");
                            for (int i = 0; i < noOfUsers; i++) {
                                share.put(
                                        expenseManager.getUserByName(commands[4+i]),
                                        Double.parseDouble(commands[5 + noOfUsers + i])
                                );
                            }

                            Expense expense2 = ExpenseCreator.createUnequalSplittedExpense(
                                    SplitType.PERCENT,
                                    share,
                                    expenseManager.getUserByName(paidBy),
                                    amount
                            );
                            expenseManager.addExpense(expense2);
                            break;
                    }
                    break;
            }
        }
    }

}

