package lab;

public class AdvertisementInstitution extends Article{

    private Institution institution;

    protected AdvertisementInstitution(String name, Institution institution){
        this.institution = institution;
        this.name = name;
        System.out.printf("Созданна реклама %s \"%s\"\n", institution.getName(), name);
    }

    @Override
    public void advertise(Personage personage){
        countReaders++;
        if(countReaders == 4){
            System.out.printf("Большой интерес вызвала реклама %s\n", institution.getName());
        }
        personage.go(institution.getAddress());
        institution.service(personage);
    }
}
