import java.io.Serializable;

public class Agent implements Serializable {

    String name;
    int initialBalance;
    int accountID;

    public Agent(String name, int initialBalance) {
        this.name = name;
        this.initialBalance = initialBalance;
    }

    public void setAccountID(int value) {
        this.accountID = value;
    }

    public int getAccountID() {
        return accountID;
    }
}
