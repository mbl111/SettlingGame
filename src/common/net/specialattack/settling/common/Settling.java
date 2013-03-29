
package net.specialattack.settling.common;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Items;
import net.specialattack.settling.common.util.ConsoleLogHandler;
import net.specialattack.settling.common.util.LogFormatter;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.OpenGLException;

public abstract class Settling implements Runnable {

    private boolean running = false;
    private boolean shuttingDown = false;
    protected static Settling instance;
    public static final Logger log = Logger.getLogger("Settling");

    public Settling() {
        instance = this;
    }

    public static Settling getInstance() {
        return instance;
    }

    public void attemptShutdown() {
        this.shuttingDown = true;
    }

    protected abstract void runGameLoop() throws LWJGLException, OpenGLException;

    protected abstract boolean startup();

    protected abstract void shutdown();

    public abstract CommonItemDelegate getItemDelegate();

    public abstract void finishItems();

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

        this.startup();

        try {
            Items.class.getName();
        }
        catch (Exception e) {
            Settling.log.log(Level.SEVERE, "Failed loading items, aborting", e);
            this.attemptShutdown();
        }

        this.running = true;

        try {
            this.runGameLoop();
        }
        catch (OpenGLException e) {
            Settling.log.log(Level.SEVERE, "Error while running Settling. Shutting down", e);
            this.attemptShutdown();
        }
        catch (LWJGLException e) {
            Settling.log.log(Level.SEVERE, "Error while running Settling. Shutting down", e);
            this.attemptShutdown();
        }
        catch (Exception e) {
            Settling.log.log(Level.SEVERE, "Error while running Settling. Shutting down", e);
            this.attemptShutdown();
        }

        System.out.println("Shutting down...");

        this.running = false;

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
