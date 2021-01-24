package models;

import java.util.List;

public class EqualSplittedExpense extends Expense {
    List<User> usersInvolved;

    public List<User> getUsersInvolved() {
        return usersInvolved;
    }

    public void setUsersInvolved(List<User> usersInvolved) {
        this.usersInvolved = usersInvolved;
    }

    public EqualSplittedExpense(User giver, Double amount, SplitType splitType, List<User> usersInvolved) {
        super(giver, amount, splitType);
        this.usersInvolved = usersInvolved;
    }

}
