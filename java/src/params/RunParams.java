package params;

public class RunParams {
    private int n;
    private double o;
    private double w;
    private double chi;
    private double gamma;
    private double gammaS;
    
    public RunParams(int n, double o, double w, double chi, double gamma, double gammaS) {
        this.n = n;
        this.o = o;
        this.w = w;
        this.chi = chi;
        this.gamma = gamma;
        this.gammaS = gammaS;
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
    
    public String getDir() {
        String dirname = String.format("N%d/o%.1f/w%.1f/chi%.1f/Gamma%.1f/gammaS%.1f/", n, o, w, chi, gamma, gammaS).replace('.', 'p');
        
        return dirname;
    }
    
    public String toString() {
        String out = "N = " + n + "\n";
        out += "Omega = " + o + "\n";
        out += "W = " + w + "\n";
        out += "chi = " + chi + "\n";
        out += "Gamma = " + gamma + "\n";
        out += "gammaS = " + gammaS + "\n";

        return out;
    }
}
