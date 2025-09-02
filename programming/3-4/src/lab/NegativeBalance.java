package lab;

public class NegativeBalance extends Exception {
    Personage personage;
    public NegativeBalance(Personage obj) {
        this.personage = obj;
    }
    @Override
    public String getMessage() {
        return String.format("У %s получился отрицательный баланс.", personage.getName());
    }
}