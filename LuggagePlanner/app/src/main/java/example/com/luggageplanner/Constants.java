package example.com.luggageplanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Saloni on 4/27/2016.
 */
public class Constants {

    public static final Map<String, Integer> iconMap = new HashMap<String, Integer>(){{
        put("clear-day", R.drawable.clear_day);
        put("rain", R.drawable.rain);
        put("snow", R.mipmap.ic_launcher);
        put("sleet", R.mipmap.ic_launcher);
        put("wind", R.mipmap.ic_launcher);
        put("fog", R.mipmap.ic_launcher);
        put("cloudy", R.mipmap.ic_launcher);
        put("partly-cloudy-day", R.mipmap.ic_launcher);
        put("partly-cloudy-night", R.mipmap.ic_launcher);

    }};


}
