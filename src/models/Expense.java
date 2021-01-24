package models;

public abstract class Expense {
    User giver;
    Double amount;
    SplitType splitType;

    public User getGiver() {
        return giver;
    }

    public void setGiver(User giver) {
        this.giver = giver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public SplitType getSplitType() {
        return splitType;
    }

    public void setSplitType(SplitType splitType) {
        this.splitType = splitType;
    }

    public Expense(User giver, Double amount, SplitType splitType) {
        this.giver = giver;
        this.amount = amount;
        this.splitType = splitType;
    }
}
