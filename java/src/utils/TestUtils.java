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
    
    public static double getRequiredDoubleProp(Properties props, String prop) {
        String strVal = getRequiredProp(props, prop);
        double val = 0.0;
        try {
            val = Double.parseDouble(strVal);
        } catch(NumberFormatException ex) {
            System.err.println("Failed to parse string '" + strVal + "' as double for property " + prop);
            throw ex;
        }
        
        return val;
    }
    
    public static int getRequiredIntProp(Properties props, String prop) {
        String strVal = getRequiredProp(props, prop);
        int val = 0;
        try {
            val = Integer.parseInt(strVal);
        } catch(NumberFormatException ex) {
            System.err.println("Failed to parse string '" + strVal + "' as int for property " + prop);
            throw ex;
        }
        
        return val;
    }
}
