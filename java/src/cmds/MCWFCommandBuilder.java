package cmds;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import params.RunParams;

public class MCWFCommandBuilder implements CommandBuilder {
    private String myExeDir;
    private String myOutdir;
    
    @Override
    public boolean init(Properties props) {
        myExeDir = getRequiredProp(props, "exedir");
        myOutdir = getRequiredProp(props, "outdir");
        
        return true;
    }

    @Override
    public List<String> getCommand(RunParams params, Properties props) {
        ArrayList<String> cmdList = new ArrayList<String>();

        cmdList.add("java");
        cmdList.add("-jar");
        cmdList.add("-Xmx10G");
        cmdList.add(myExeDir + "/MCWF.jar");
        
        cmdList.add("-n");
        cmdList.add(String.format("%d", params.getN()));
        
        cmdList.add("-o");
        cmdList.add(String.format("%.2f", params.getO()));

        cmdList.add("-chi");
        cmdList.add(String.format("%.2f", params.getChi()));
        
        cmdList.add("-f");
        cmdList.add(String.format("%.2f", params.getGamma()));
        
        cmdList.add("-faa");
        cmdList.add(String.format("%.2f", params.getGamma() + params.getGammaS()));
        
        cmdList.add("-dt");
        cmdList.add(String.format("%.2g", params.getDeltaT()));
        
        cmdList.add("-tmax");
        cmdList.add(String.format("%.2f", params.getTMax()));
        
        String prefix = params.getPrefix();
        if(!prefix.isEmpty()) {
            prefix += ".";
        }
        prefix += "mcwf.";
        
        int numTraj = Integer.parseInt(getRequiredProp(props, prefix + "numTraj"));
        cmdList.add("-traj");
        cmdList.add(String.format("%d", numTraj));
        
        double evt = Double.parseDouble(getRequiredProp(props, prefix + "evt"));
        cmdList.add("-evt");
        cmdList.add(String.format("%.2g", evt));
        
        int numThreads = Integer.parseInt(getRequiredProp(props, prefix + "numThreads"));
        cmdList.add("-nt");
        cmdList.add(String.format("%d", numThreads));
        
        cmdList.add(myOutdir + "/" + params.getDir() + "/mcwf");
        
        if(props.containsKey(prefix + "ic")) {
            cmdList.add(props.getProperty(prefix + "ic"));
        }
        
        return cmdList;
    }
    
    private String getRequiredProp(Properties props, String prop) {
        if(!props.containsKey(prop)) {
            System.err.println("Properties missing required field " + prop);
            throw new InvalidParameterException("Properties missing required field " + prop);
        }
        return props.getProperty(prop);
    }
}
