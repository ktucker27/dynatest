package cmds;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import params.RunParams;
import utils.TestUtils;

public class SymmCommandBuilder implements CommandBuilder {
    private String myExeDir;
    private String myOutdir;
    
    public SymmCommandBuilder() {
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
        cmdList.add(myExeDir + "/RunSim.jar");
        
        cmdList.add("-n");
        cmdList.add(String.format("%d", params.getN()));
        
        cmdList.add("-o");
        cmdList.add(String.format("%.2f", params.getO()));
        
        cmdList.add("-w");
        cmdList.add(String.format("%.2f", params.getW()));

        cmdList.add("-g");
        cmdList.add(String.format("%.2f", 2*params.getChi()));
        
        // Assume we always have equal on-site interactions for now
        cmdList.add("-gaa");
        cmdList.add(String.format("%.2f", 2*params.getChi()));
        
        cmdList.add("-f");
        cmdList.add(String.format("%.2f", params.getGamma()));
        
        cmdList.add("-faa");
        cmdList.add(String.format("%.2f", params.getGamma() + params.getGammaS()));
        
        // Assume Delta = 0 for now for the sake of comparison with methods that
        // do not support disorder
        cmdList.add("-d");
        cmdList.add(String.format("%.2f", 0.0));
        
        //cmdList.add("-dt");
        //cmdList.add(String.format("%.2g", params.getDeltaT()));
        
        cmdList.add("-tmax");
        cmdList.add(String.format("%.2f", params.getTMax()));
        
        // Method specific options
        String prefix = params.getPrefix();
        if(!prefix.isEmpty()) {
            prefix += ".";
        }
        prefix += "symm.";
        
        cmdList.add("-t");
        
        int numThreads = Integer.parseInt(TestUtils.getRequiredProp(props, prefix + "numThreads"));
        cmdList.add("-nt");
        cmdList.add(String.format("%d", numThreads));
        
        cmdList.add(myOutdir + "/" + params.getDir() + "/symm");
        cmdList.add("SYMM");
        
        // TODO - Incorporate initial conditions into run parameters
        cmdList.add("-iz");
        cmdList.add(String.format("%.2f", Double.parseDouble(TestUtils.getRequiredProp(props, prefix + "iz"))));
        
        cmdList.add("-ip");
        cmdList.add(String.format("%.2f", Double.parseDouble(TestUtils.getRequiredProp(props, prefix + "ip"))));
        
        return cmdList;
    }

}
