package lab;

import java.util.ArrayList;
import java.util.List;

public class Hospital extends Institution {

    private List<Personage> turn = new ArrayList<>();

    protected Hospital(String name, String address, Personage owner, int payment){
        this.name = name;
        this.address = address;
        this.owner = owner;
        this.payment = payment;
        System.out.printf("В мире появился %s с владельцем %s\n", this.name, this.owner.getName());
    }

    @Override
    public void service(Personage ill){
        turn.add(ill);
        if(turn.size() == 3){
            System.out.printf("Все больные, которые имели еще достаточно сил, чтобы самостоятельно передвигаться, побежали к %s. У %s выстроилась большая очередь\n", owner.getName(), address);
            increasePayment((int) (Math.random() * 5) * 10 + 10);
        }
    }
    public void increasePayment(int n){
        payment += n;
        System.out.printf("%s сразу же увеличил за лечение плату на %s. Денежки рекой потекли к нему\n", owner.getName(), n);
    }
    public void heal(Personage ill){
        if(!ill.getDiseases().isEmpty()){
            try {
                ill.setMoney(ill.getMoney() - payment * ill.getDiseases().size());
                if (ill.getMoney() - payment * ill.getDiseases().size() > 0) {
                    throw new NegativeBalance(ill);
                }
                owner.setMoney(owner.getMoney() + payment * ill.getDiseases().size());
                ill.recover();
                System.out.printf("Персонаж %s был вылечен\n", ill.getName());
            }
            catch (NegativeBalance e){
                System.out.printf("У %s недостаточно средств для покупки\n", ill.getName());
            }
        }
        else{
            System.out.printf("Персонаж %s пришел здоровым в %s\n", ill.getName(), name);
        }
    }
    public void healAll(){
        for(Personage ill: turn){
            heal(ill);
        }
        System.out.printf("%s никому не отказывал в медицинской помощи и вылечил всех\n", owner.getName());
    }
}
