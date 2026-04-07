package Core;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class GameLogFormatter extends Formatter {

    private static final SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        String date = sdf.format(new Date(record.getMillis()));
        return String.format("[%s] [%s] %s%n",
                date,
                record.getLevel().getName(),
                record.getMessage());
    }
}

