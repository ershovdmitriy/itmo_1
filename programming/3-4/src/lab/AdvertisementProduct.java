package lab;

public class AdvertisementProduct extends Article{

    private String product;
    private Shop shop;

    protected AdvertisementProduct(String name, String  product, Shop shop){
        this.product = product;
        this.shop = shop;
        this.name = name;
        System.out.printf("Созданна реклама %s из магазина %s \"%s\"\n", product, shop.getName(), name);
    }

    @Override
    public void advertise(Personage personage){
        countReaders++;
        if(countReaders == 3){
            System.out.printf("Большой интерес вызвала реклама, призывающая жителей покупать %s в %s\n", product, shop.getName());
        }
        personage.go(shop.getAddress());
        shop.service(personage, (int) (Math.random() * 5) + 3);
    }
}
