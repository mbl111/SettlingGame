
package net.specialattack.settling.common.crash;

public abstract class CrashReportSection {

    public final String name;

    public CrashReportSection(String name) {
        this.name = name;
    }

    public abstract String getData();

}
