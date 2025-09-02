package lab;

public abstract class Institution implements CanDoService{

    protected String name;
    protected String address;
    protected Personage owner;
    protected int payment;

    public String getAddress(){
        return address;
    }
    public String getName(){
        return name;
    }

    public void service(Personage buyer){
        buyer.setMoney(buyer.getMoney() - payment);
        owner.setMoney(owner.getMoney() + payment);
    }

    @Override
    public String toString() {
        return name + " " + owner;
    }
    @Override
    public int hashCode() {
        return name.hashCode() + owner.hashCode() + address.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Institution)) {
            return false;
        }
        Institution other = (Institution) obj;
        return this.name == other.name && this.owner == other.owner && this.address == other.address;
    }
}
