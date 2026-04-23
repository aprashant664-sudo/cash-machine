import java.util.ArrayList;

public class AccountManager {

    private ArrayList<Account> accounts;

    public AccountManager() {

        accounts = new ArrayList<>();

        accounts.add(new SavingsAccount(1001, 1234, 1000));
        accounts.add(new CurrentAccount(1002, 1111, 1500));
        accounts.add(new SavingsAccount(1003, 2222, 2000));
        accounts.add(new CurrentAccount(1004, 3333, 500));
    }

    public Account validatePin(int pin) {

        for (Account acc : accounts) {
            if (acc.getPin() == pin) {
                return acc;
            }
        }
        return null;
    }
}
