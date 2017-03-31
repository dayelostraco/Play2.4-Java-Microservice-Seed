import play.Application;
import play.GlobalSettings;

import java.util.TimeZone;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {

        // Set default system time to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public void onStop(Application app) {

    }
}
