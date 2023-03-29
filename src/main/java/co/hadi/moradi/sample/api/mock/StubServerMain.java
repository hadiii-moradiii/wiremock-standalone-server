package co.hadi.moradi.sample.api.mock;

public class StubServerMain {

    public static final Stubs stubs = new Stubs();

    public static void main(String[] args) {
        stubs.setUp()
           // .setupEbMockServer()
            .status();
    }
}