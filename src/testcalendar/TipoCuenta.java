package testcalendar;

public class TipoCuenta {
    private String nombre;
    private double tasa;

    public static final TipoCuenta AHORRO = new TipoCuenta("AHORRO",0.03);
    public static final TipoCuenta CHEQUES = new TipoCuenta("CHEQUES", 0.00);
    public static final TipoCuenta PLAZO = new TipoCuenta("PLAZO",0.05);

    private TipoCuenta(String nombre, double tasa){
        this.nombre = nombre;
        this.tasa = tasa;
    }

    public double tasa(){
        return tasa;
    }

    public String toString(){
        return nombre;
    }
}
