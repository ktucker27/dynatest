package cmds;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import params.RunParams;
import utils.TestUtils;

public class MCWFCommandBuilder implements CommandBuilder {
    private String myExeDir;
    private String myOutdir;
    
    public MCWFCommandBuilder() {
        myExeDir = "";
        myOutdir = "";
    }
    
    @Override
    public boolean init(Properties props) {
        myExeDir = TestUtils.getRequiredProp(props, "exedir");
        myOutdir = TestUtils.getRequiredProp(props, "outdir");
        
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
        
        // TODO - Add gel to RunParams
        cmdList.add("-gel");
        cmdList.add(String.format("%.2g", 0.0));
        
        // Method specific options
        String prefix = params.getPrefix();
        if(!prefix.isEmpty()) {
            prefix += ".";
        }
        prefix += "mcwf.";
        
        int numTraj = Integer.parseInt(TestUtils.getRequiredProp(props, prefix + "numTraj"));
        cmdList.add("-traj");
        cmdList.add(String.format("%d", numTraj));
        
        double evt = Double.parseDouble(TestUtils.getRequiredProp(props, prefix + "evt"));
        cmdList.add("-evt");
        cmdList.add(String.format("%.2g", evt));
        
        int numThreads = Integer.parseInt(TestUtils.getRequiredProp(props, prefix + "numThreads"));
        cmdList.add("-nt");
        cmdList.add(String.format("%d", numThreads));
        
        cmdList.add(myOutdir + "/" + params.getDir() + "/mcwf");
        
        // TODO - Incorporate initial conditions into run parameters
        if(props.containsKey(prefix + "ic")) {
            cmdList.add(props.getProperty(prefix + "ic"));
        }
        
        return cmdList;
    }
}
