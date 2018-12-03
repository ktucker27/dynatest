package cmds;

import java.util.List;
import java.util.Properties;

import params.RunParams;

public interface CommandBuilder {
    
    public enum CommandType {MCWF};
    
    /**
     * Initializes the builder. Must be called successfully before commands
     * can be generated
     * 
     * @param props configuration settings TODO - Replace with protobuffs?
     * 
     * @return true if initialization was successful
     */
    public boolean init(Properties props);
    
    /**
     * Takes run parameters and returns an executable command in the form of
     * a list of strings to be fed into a ProcessBuilder
     * 
     * @param params parameter values for the run
     * @param props run specific properties TODO - Replace with protobuffs?
     * 
     * @return List of strings that comprise the command that can be used by a
     *         ProcessBuilder
     */
    public List<String> getCommand(RunParams params, Properties props);
}
