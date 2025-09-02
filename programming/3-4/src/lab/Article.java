package lab;

public abstract class Article implements CanDoAdvertise {

    protected String name;
    protected int countReaders;

    public abstract void advertise(Personage personage);

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Article)) {
            return false;
        }
        Article other = (Article) obj;
        return this.name == other.name;
    }
}

