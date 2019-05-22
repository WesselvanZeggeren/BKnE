import client.controller.Controller;
import server.controller.Server;

public class Test {

    public static void main(String[] args) {

        Server server = new Server();
        server.startup();

        Controller controller = new Controller();
        controller.startup();
    }
}
