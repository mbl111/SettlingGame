
package net.specialattack.settling.server;

public class ServerLauncher {

    private static Thread instanceThread;

    public static void main(String[] args) {
        SettlingServer server = new SettlingServer();
        instanceThread = new Thread(server, "Settling Server Main Thread");
        instanceThread.start();
    }

}
