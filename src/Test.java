import client.controller.ClientApplication;
import server.controller.ServerApplication;

public class Test {

    public static void main(String[] args) {

        ServerApplication serverApplication = new ServerApplication();
        serverApplication.startup();

        ClientApplication clientApplication1 = new ClientApplication();
        clientApplication1.startup();
    }
}
