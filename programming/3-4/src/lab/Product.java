package lab;

public record Product(String name, boolean spoiled) {

    public boolean getSpoiled(){
        return this.spoiled;
    }
    public String getName(){
        return this.name;
    }
}
