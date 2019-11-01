package params;

import java.util.Properties;

import utils.TestUtils;

public class RunParams {
    private int n;
    private double o;
    private double w;
    private double chi;
    private double gamma;
    private double gammaS;
    private double deltaT;
    private double tmax;
    private String prefix;
    
    public static RunParams parseProps(Properties props) {
        return new RunParams(TestUtils.getRequiredIntProp(props, "N"),
                             TestUtils.getRequiredDoubleProp(props, "Omega"),
                             TestUtils.getRequiredDoubleProp(props, "W"),
                             TestUtils.getRequiredDoubleProp(props, "Chi"),
                             TestUtils.getRequiredDoubleProp(props, "Gamma"),
                             TestUtils.getRequiredDoubleProp(props, "GammaS"),
                             TestUtils.getRequiredDoubleProp(props, "DeltaT"),
                             TestUtils.getRequiredDoubleProp(props, "TMax"));
    }
    
    public RunParams() {
        this.n = 0;
        this.o = 0;
        this.w = 0;
        this.chi = 0;
        this.gamma = 0;
        this.gammaS = 0;
        this.deltaT = 0;
        this.tmax = 0;
        
        this.prefix = "";
    }
    
    public RunParams(int n, double o, double w, double chi, double gamma, double gammaS, double deltaT, double tmax) {
        this.n = n;
        this.o = o;
        this.w = w;
        this.chi = chi;
        this.gamma = gamma;
        this.gammaS = gammaS;
        this.deltaT = deltaT;
        this.tmax = tmax;
        
        this.prefix = "";
    }

    public int getN() {
        return n;
    }

    public double getO() {
        return o;
    }

    public double getW() {
        return w;
    }

    public double getChi() {
        return chi;
    }

    public double getGamma() {
        return gamma;
    }

    public double getGammaS() {
        return gammaS;
    }
    
    public double getDeltaT() {
        return deltaT;
    }
    
    public double getTMax() {
        return tmax;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getDir() {
        String dirname = String.format("N%d/o%.1f/w%.1f/chi%.1f/Gamma%.1f/gammaS%.1f", n, o, w, chi, gamma, gammaS).replace('.', 'p');
        
        return dirname;
    }
    
    public String toString() {
        String out = "N = " + n + "\n";
        out += "Omega = " + o + "\n";
        out += "W = " + w + "\n";
        out += "chi = " + chi + "\n";
        out += "Gamma = " + gamma + "\n";
        out += "gammaS = " + gammaS + "\n";
        out += "deltaT = " + deltaT + "\n";
        out += "tmax = " + tmax + "\n";

        return out;
    }
}
