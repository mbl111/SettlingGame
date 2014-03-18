
package net.specialattack.settling.server;

import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.crash.CrashReport;
import net.specialattack.settling.common.crash.CrashReportSectionThrown;

public class SettlingServer extends Settling {

    public static SettlingServer instance;

    public SettlingServer() {
        instance = this;
    }

    @Override
    protected void runGameLoop() {
        while (this.isRunning() && !this.isShuttingDown()) {
            this.timer.update();

            for (int i = 0; i < this.timer.remainingTicks; i++) {
                this.tick();
            }
        }
    }

    @Override
    protected boolean startup() {
        return true;
    }

    @Override
    protected void shutdown() {
        System.exit(0);
    }

    @Override
    public void handleError(Throwable thrown) {
        this.attemptShutdownCrash();

        CrashReport report = new CrashReport();
        report.addSection(new CrashReportSectionThrown(thrown));

        System.err.println(report.getData());
    }

    private void tick() {

    }

}
