
package net.specialattack.settling.common.util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConsoleLogHandler extends ConsoleHandler {

    @Override
    public void publish(LogRecord record) {
        if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
            System.err.println(this.getFormatter().format(record));
        }
        else {
            System.out.println(this.getFormatter().format(record));
        }
    }

}
