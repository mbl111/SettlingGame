
package net.specialattack.settling.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.specialattack.settling.common.util.ConsoleLogHandler;
import net.specialattack.settling.common.util.LogFormatter;
import net.specialattack.settling.common.util.TickTimer;

public abstract class Settling implements Runnable {

    private boolean running = false;
    private boolean shuttingDown = false;
    private boolean crashing = false;
    protected static Settling instance;
    public TickTimer timer = new TickTimer(20.0F);
    public static final Logger log = Logger.getLogger("Settling");

    public Settling() {
        instance = this;
    }

    public static Settling getInstance() {
        return instance;
    }

    public void attemptShutdownCrash() {
        this.shuttingDown = true;
        this.crashing = true;
    }

    public void attemptShutdown() {
        this.shuttingDown = true;
        this.running = false;
    }

    protected abstract void runGameLoop() throws Exception;

    protected abstract boolean startup();

    protected abstract void shutdown();

    public abstract void handleError(Throwable thrown);

    @Override
    public void run() {
        if (this.running) {
            return;
        }

        log.setUseParentHandlers(false);

        ConsoleLogHandler handler = new ConsoleLogHandler();
        handler.setFormatter(new LogFormatter());

        log.addHandler(handler);
        log.setLevel(Level.ALL);
        handler.setLevel(Level.ALL);

        this.running = true;

        boolean started = this.startup();

        if (!started) {
            this.handleError(new SettlingException("Starting returned false"));

            Thread idleThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        while (Settling.this.running) {
                            Thread.sleep(1L);
                        }
                    }
                    catch (InterruptedException e) {}
                }
            }, "Idling thread");

            idleThread.start();

            return;
        }

        try {
            this.runGameLoop();
        }
        catch (Exception e) {
            Settling.log.log(Level.SEVERE, "Error while running Settling. Shutting down", e);
            this.handleError(e);
        }

        log.info("Shutting down...");

        if (this.crashing) {
            Thread idleThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        while (Settling.this.running) {
                            Thread.sleep(1L);
                        }
                    }
                    catch (InterruptedException e) {}
                }
            }, "Idling thread");

            idleThread.start();

            return;
        }

        Thread shutdownThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10000L);
                }
                catch (InterruptedException e) {}

                System.out.println("FORCING EXIT!");
                System.exit(-1);
            }
        }, "Shutdown thread");

        shutdownThread.setDaemon(true);
        shutdownThread.start();

        this.shutdown();
    }

    public boolean isShuttingDown() {
        return this.shuttingDown;
    }

    public boolean isRunning() {
        return this.running;
    }

}
