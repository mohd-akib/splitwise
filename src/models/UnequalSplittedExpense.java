package models;

import java.util.Map;

public class UnequalSplittedExpense extends Expense {
    Map<User, Double> share;

    public Map<User, Double> getShare() {
        return share;
    }

    public void setShare(Map<User, Double> share) {
        this.share = share;
    }

    public UnequalSplittedExpense(User giver, Double amount, SplitType splitType, Map<User, Double> share) {
        super(giver, amount, splitType);
        this.share = share;
    }

    public boolean isValid() {
        if(this.splitType == SplitType.PERCENT) {
            Double tot = 0.0;
            for (Map.Entry<User, Double> share:this.share.entrySet()) {
                tot += share.getValue();
            }
            return (100 - tot) == 0;
        } else {
            Double tot = 0.0;
            for (Map.Entry<User, Double> share:this.share.entrySet()) {
                tot += share.getValue();
            }
            return (this.amount - tot) == 0;
        }

    }

    public String toString() {
        return "{ giver: " + this.giver
                + " amount: " + this.amount
                + " splitType : " + this.splitType
                + " share: " + this.share
                + ": }";
    }
}

