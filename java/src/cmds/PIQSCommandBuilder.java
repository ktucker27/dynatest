package cmds;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import params.RunParams;
import utils.TestUtils;

public class PIQSCommandBuilder implements CommandBuilder {
    private String myExeDir;
    private String myOutdir;
    
    public PIQSCommandBuilder() {
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

        cmdList.add("python");
        cmdList.add(myExeDir + "/run_piqs.py");
        
        cmdList.add("-n");
        cmdList.add(String.format("%d", params.getN()));
        
        cmdList.add("-o");
        cmdList.add(String.format("%.2f", params.getO()));
        
        cmdList.add("-chi");
        cmdList.add(String.format("%.2f", params.getChi()));
        
        cmdList.add("-gamma");
        cmdList.add(String.format("%.2f", params.getGamma()));
        
        cmdList.add("-gs");
        cmdList.add(String.format("%.2f", params.getGammaS()));
        
        //cmdList.add("-dt");
        //cmdList.add(String.format("%.2g", params.getDeltaT()));
        
        cmdList.add("-tmax");
        cmdList.add(String.format("%.2f", params.getTMax()));
        
        // Method specific options
        String prefix = params.getPrefix();
        if(!prefix.isEmpty()) {
            prefix += ".";
        }
        prefix += "piqs.";
        
        // TODO - Incorporate initial conditions into run parameters
        cmdList.add("-it");
        cmdList.add(String.format("%.2f", Double.parseDouble(TestUtils.getRequiredProp(props, prefix + "iz"))));
        
        cmdList.add("-ip");
        cmdList.add(String.format("%.2f", Double.parseDouble(TestUtils.getRequiredProp(props, prefix + "ip"))));
        
        cmdList.add(myOutdir + "/" + params.getDir() + "/piqs/piqs_out.txt");
        
        return cmdList;
    }

}
