
package net.specialattack.settling.common.crash;

import java.util.ArrayList;

public class CrashReport {

    private ArrayList<CrashReportSection> sections;

    public CrashReport() {
        sections = new ArrayList<CrashReportSection>();
    }

    public void addSection(CrashReportSection section) {
        sections.add(section);
    }

    public String getData() {
        StringBuilder result = new StringBuilder();

        result.append("==================================================").append("\r\n");
        result.append("  Settling has crashed!").append("\r\n").append("\r\n").append("\r\n");

        for (CrashReportSection section : sections) {
            result.append("==============================").append("\r\n");
            result.append("==  ").append(section.name).append("\r\n");
            result.append("==============================").append("\r\n");
            result.append(section.getData()).append("\r\n");

            result.append("\r\n");
        }

        return result.toString();
    }

}
