package lab;

import java.util.ArrayList;
import java.util.List;

public class Factory extends Institution{

    private String produced;
    private List<Product> warehouse = new ArrayList<>();
    private int countSales = 5;

    protected Factory(String name, String address, Personage owner, int payment, String produced){
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.payment = payment;
        this.produced = produced;
        System.out.printf("В мире появился %s с владельцем %s\n", this.name, this.owner.getName());
        replenishWarehouse((int) (Math.random() * countSales * 2 + 1) + 1);
    }

    public String getProduced() {
        return produced;
    }

    private Product toCreate(){
        return new Product(produced, Math.random() > 0.75);
    }
    public void replenishWarehouse(int count){
        for(int i = 0; i < count; i++){
            warehouse.add(toCreate());
        }
        System.out.printf("На %s было произведено %s %s\n", name, count, produced);
    }
    public Product toSend(Shop shop){
        if (shop.getOwner().getMoney() - payment < 0) {
            try {
                throw new NegativeBalance(shop.getOwner());
            } catch (NegativeBalance e) {
                throw new RuntimeException(e);
            }
        }
        shop.getOwner().setMoney(shop.getOwner().getMoney() - payment);
        owner.setMoney(owner.getMoney() + payment);
        if (warehouse.size() == 0) {
            replenishWarehouse((int) (Math.random() * countSales * 2 + 1) + 1);
        }
        Product product = warehouse.get(warehouse.size() - 1);
        warehouse.remove(warehouse.size() - 1);
        return product;
    }
    public void increaseСountSales(int n) {
        countSales *= n;
        System.out.printf("%s увеличил выпуск %s в %s раза\n", owner.getName(), produced, n);
    }
}
