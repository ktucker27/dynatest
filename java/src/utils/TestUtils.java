package utils;

import java.security.InvalidParameterException;
import java.util.Properties;

public class TestUtils {
    public static String getRequiredProp(Properties props, String prop) {
        if(!props.containsKey(prop)) {
            System.err.println("Properties missing required field " + prop);
            throw new InvalidParameterException("Properties missing required field " + prop);
        }
        return props.getProperty(prop);
    } 
}
