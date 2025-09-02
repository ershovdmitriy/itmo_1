package lab;

public final class A {
    private final static A a = new A();

    private A(){

    }

    public static void getA(){
        System.out.println("Hello");
    }
}
