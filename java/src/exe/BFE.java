package exe;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import cmds.CommandBuilder;
import cmds.MCWFCommandBuilder;
import cmds.PIQSCommandBuilder;
import cmds.SymmCommandBuilder;
import params.RunParams;

/**
 * 
 * @author tuckerkj
 *
 * The Batch File Executable (a.k.a. Big F*cking Executable) runs a large
 * number of processes based on a request file using multiple executable
 * types to facilitate comparison between various solvers
 * 
 */
public class BFE {

    private static void printUsage() {
        System.out.println("java -jar BFE.jar propfile paramsfile");
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length < 2) {
            printUsage();
            return;
        }
        
        // Read the configuration file
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(args[0]);
        props.load(in);
        
        if(!props.containsKey("exes")) {
            System.err.println("Missing required property: exes");
            return;
        }
        
        // Get the list of executables to run and create a list of command builders
        String exeStr = props.getProperty("exes");
        String[] exeList = exeStr.split(",");
        ArrayList<CommandBuilder> cmds = new ArrayList<CommandBuilder>();
        for(int i = 0; i < exeList.length; ++i) {
            CommandBuilder.CommandType type;
            try {
                type = CommandBuilder.CommandType.valueOf(exeList[i].trim());
            } catch(IllegalArgumentException ex) {
                System.err.println("Unrecognized command type " + exeList[i].trim() + ". Options are:");
                for(int j = 0; j < CommandBuilder.CommandType.values().length; ++j) {
                    System.err.println(CommandBuilder.CommandType.values()[j]);
                }
                return;
            }
            switch(type) {
            case MCWF:
                cmds.add(new MCWFCommandBuilder());
                break;
            case SYMM:
                cmds.add(new SymmCommandBuilder());
                break;
            case PIQS:
                cmds.add(new PIQSCommandBuilder());
                break;
            default:
                throw new UnsupportedOperationException("Executable type " + type.toString() + " not yet supported");
            }
        }
        
        // Initialize the command builders
        for(int i = 0; i < cmds.size(); ++i) {
            if(!cmds.get(i).init(props)) {
                System.err.println("Failed to initialize command " + i + " of type " + exeList[i].trim());
                return;
            }
        }
        
        // Get the parameters
        // TODO - Read from properties
        ArrayList<RunParams> paramList = new ArrayList<RunParams>();
        //paramList.add(new RunParams(10, 5, 0, 1, 1, 1, 1e-4, 2));
        try {
            //paramList.add(RunParams.parseProps(props));
        } catch(Exception ex) {
            System.err.println("Error parsing run paremters");
            return;
        }
        
        Gson gson = new Gson();
        FileInputStream instream = new FileInputStream(args[1]);
        Type collectionType = new TypeToken<Collection<RunParams>>() {}.getType();
        Collection<RunParams> gsonParams = gson.fromJson(new JsonReader(new InputStreamReader(instream, "UTF-8")), collectionType);
        //RunParams gsonParams = gson.fromJson("{'n':1}", RunParams.class);
        Iterator<RunParams> iter = gsonParams.iterator();
        while(iter.hasNext()) {
            RunParams params = iter.next();
            System.out.println(params);
            paramList.add(params);
        }
        
        // Run the processes
        for(int paramIdx = 0; paramIdx < paramList.size(); ++paramIdx) {
            RunParams params = paramList.get(paramIdx);
            for(int cmdIdx = 0; cmdIdx < cmds.size(); ++cmdIdx) {
                CommandBuilder cmd = cmds.get(cmdIdx);
                try {
                    List<String> cmdList = cmd.getCommand(params, props);
                    for(int i = 0; i < cmdList.size(); ++i) {
                        System.out.print(cmdList.get(i) + " ");
                    }
                    System.out.print("\n");

                    ProcessBuilder pb = new ProcessBuilder(cmdList);
                    pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                    Process p = pb.start();
                    p.waitFor();
                } catch(InvalidParameterException ex) {
                    System.err.println("Error creating command from " + cmd.getClass().getName() + " for params:\n" + params.toString());
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

}
