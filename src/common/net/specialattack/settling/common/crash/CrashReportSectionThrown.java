
package net.specialattack.settling.common.crash;

public class CrashReportSectionThrown extends CrashReportSection {

    private Throwable thrown;

    public CrashReportSectionThrown(Throwable thrown) {
        super("Thrown Exception");
        this.thrown = thrown;
    }

    @Override
    public String getData() {
        StringBuilder result = new StringBuilder();

        Throwable thrown = this.thrown;
        int max = 16;

        while (thrown != null) {
            result.append(thrown.getClass().getName());

            if (thrown.getMessage() != null) {
                result.append(": ").append(thrown.getMessage());
            }

            result.append("\r\n");

            StackTraceElement[] elements = thrown.getStackTrace();

            int i = 0;

            for (i = 0; i < elements.length && i < max; i++) {
                result.append("\tat ").append(elements[i].toString()).append("\r\n");
            }

            if (i < elements.length) {
                result.append("\t... ").append(elements.length - i).append(" more").append("\r\n");
            }

            thrown = thrown.getCause();

            if (thrown != null) {
                result.append("Caused by: ");

                max = 4;
            }
        }

        return result.toString();
    }

}
