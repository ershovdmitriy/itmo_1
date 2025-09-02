package lab;

import java.util.ArrayList;
import java.util.List;

public class Personage {

    private final String name;
    private int money;
    private final String address;
    private String location;
    private List<Diseases> diseases;
    private List<Article> articles = new ArrayList<>();

    protected Personage(String name, String address, int money){
        this.name = name;
        this.address = address;
        this.money = money;
        this.location = address;
        this.diseases = new ArrayList<>();
        System.out.printf("В мире появился %s с бюджетом %s монет\n", this.name, this.money);
    }

    public String getName(){
        return this.name;
    }
    public int getMoney(){
        return this.money;
    }
    public List<Diseases> getDiseases(){
        return diseases;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void recover(){
        diseases.clear();
    }
    public void fallIll(Diseases disease){
        diseases.add(disease);
        System.out.printf("%s заболел %s\n", name, diseases.get(diseases.size() - 1));
    }

    public void go(String address){
        if (address != location){
            System.out.printf("%s идет из %s в %s\n", name, location, address);
            location = address;
        }
    }
    public void read(Newspaper newspaper){
        System.out.printf("%s читает %s\n", name, newspaper.getName());
        List<Article> articles = newspaper.getArticles();
        for(int i = 0; i < articles.size(); i++){
            if(Math.random() > 0.25){
                System.out.printf("%s заинтересовался %s\n", name, articles.get(i).getName());
                articles.get(i).advertise(this);
            }
        }
    }
    public void write(String name, Institution institution){
        System.out.printf("%s написал %s\n", this.name, name);
        articles.add(new AdvertisementInstitution(name, institution));
    }
    public void write(String name, String product, Shop shop){
        System.out.printf("%s написал %s\n", this.name, name);
        articles.add(new AdvertisementProduct(name, product, shop));
    }
    public void release(Newspaper newspaper){
        System.out.printf("%s отправляет все свои статьи в %s\n", name, newspaper.getName());
        for (Article article : articles) {
            newspaper.addArticle(article);
        }
    }

    @Override
    public String toString() {
        return name + " " + address;
    }
    @Override
    public int hashCode() {
        return name.hashCode() + address.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Personage)) {
            return false;
        }
        Personage other = (Personage) obj;
        return this.name == other.name && this.address == other.address;
    }
}
