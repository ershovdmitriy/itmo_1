package lab;

import java.util.ArrayList;
import java.util.List;

public class Shop extends Institution{

    private List<Product> warehouse = new ArrayList<>();
    private Factory provider;
    private int sales;

    protected Shop(String name, String address, Personage owner, int payment, Factory provider) {
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.payment = payment;
        this.provider = provider;
        System.out.printf("В мире появился %s с владельцем %s\n", this.name, this.owner.getName());
        try {
            orderProduct((int) (Math.random() * 5) + 3);
        } catch (NegativeBalance e) {
            throw new RuntimeException(e);
        }
    }

    public Personage getOwner(){
        return owner;
    }

    public void orderProduct(int count) throws NegativeBalance {
        for (int i = 0; i < count; i++) {
            warehouse.add(provider.toSend(this));
        }
        System.out.printf("В %s было отправленно %s %s\n", name, count, provider.getProduced());
    }

    public void service(Personage buyer, int count){
        for (int i = 0; i < count; i++){
            try {
                if (buyer.getMoney() - payment < 0) {
                    throw new NegativeBalance(buyer);
                }
                buyer.setMoney(buyer.getMoney() - payment);
                owner.setMoney(owner.getMoney() + payment);
                if (warehouse.size() == 0) {
                    orderProduct((int) (Math.random() * 5) + 3);
                }
                Product product = warehouse.get(warehouse.size() - 1);
                warehouse.remove(warehouse.size() - 1);
                System.out.printf("%s купил %s\n", buyer.getName(), product.getName());
                sales++;
                if (sales == 15) {
                    System.out.printf("В магазине было продано столько %s, сколько раньше не продавалось за целый месяц\n", product.getName());
                    provider.increaseСountSales((int) (Math.random() * 4) + 2);
                }
                if (product.getSpoiled()) {
                    Diseases[] values = Diseases.values();
                    int random = (int) (Math.random() * values.length);
                    buyer.fallIll(values[random]);
                }
            }
            catch (NegativeBalance e){
                System.out.printf("У %s недостаточно средств для покупки\n", buyer.getName());
            }
        }
    }

    @Override
    public void service(Personage buyer) {

    }
}
