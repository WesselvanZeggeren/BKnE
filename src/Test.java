import client.controller.ClientApplication;
import server.controller.ServerApplication;

public class Test {

    public static void main(String[] args) {

        ServerApplication serverApplication = new ServerApplication();
        serverApplication.startup();

        ClientApplication clientApplication = new ClientApplication();
        clientApplication.startup();





    }
}
