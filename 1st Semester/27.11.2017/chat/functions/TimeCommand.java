package chat.functions;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 27.11.2017
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public class TimeCommand {

    public String getExistingTime(){

        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        return LocalDateTime.now(zoneId).toString();

    }

}
