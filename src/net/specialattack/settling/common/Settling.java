
package net.specialattack.settling.common;

import net.specialattack.settling.common.item.CommonItemDelegate;
import net.specialattack.settling.common.item.Items;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.OpenGLException;

public abstract class Settling implements Runnable {
    private boolean running = false;
    private boolean shuttingDown = false;
    protected static Settling instance;

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

        this.startup();

        try {
            Items.class.getName();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            this.attemptShutdown();
        }

        this.running = true;

        try {
            this.runGameLoop();
        }
        catch (OpenGLException ex) {
            ex.printStackTrace();
            this.attemptShutdown();
        }
        catch (LWJGLException ex) {
            ex.printStackTrace();
            this.attemptShutdown();
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
