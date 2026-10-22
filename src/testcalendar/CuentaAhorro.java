package testcalendar;

import java.util.Calendar;

public class CuentaAhorro extends CuentaBancaria {
    private Calendar ultimoUso;

    public CuentaAhorro(int numero, String cliente, String moneda){
        super(numero, cliente, moneda, TipoCuenta.AHORRO);
        ultimoUso = Calendar.getInstance();
    }

    public boolean status(){
        Calendar limite = Calendar.getInstance();
        limite.add(Calendar.MONTH, -6);
        return !ultimoUso.before(limite);
    }

    public void depositar(double monto){
        if(!status()){
            throw new IllegalStateException("La cuenta de ahorro esta inactiva");
        }
        super.depositar(monto);
    }

    public boolean retirar(double monto){
        if(!status()){
            throw new IllegalStateException("La cuenta de ahorro esta inactiva");
        }
        return super.retirar(monto);
    }

    protected void registrarMovimiento(String linea){
        ultimoUso = Calendar.getInstance();
        super.registrarMovimiento(linea);
    }

    public void quienSoy(){
        System.out.println("Soy una CuentaAhorro");
    }

    public String toString(){
        return super.toString()+" Activa: "+status();
    }
}
