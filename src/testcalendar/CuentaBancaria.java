package testcalendar;

import java.util.Calendar;

public class CuentaBancaria {
    protected int numero;
    protected double saldo;
    protected String cliente, moneda;
    protected TipoCuenta tipo;
    protected Calendar apertura;
    protected String[] historial = new String[50];
    protected int cantidadMovimientos;

    public CuentaBancaria(int numero, String cliente, String moneda){
        this(numero, cliente, moneda, TipoCuenta.CHEQUES);
    }

    public CuentaBancaria(int numero, String cliente, String moneda, TipoCuenta tipo){
        this.numero = numero;
        this.cliente = cliente;
        this.moneda = moneda;
        this.tipo = tipo;
        saldo = 0;
        apertura = Calendar.getInstance();
        cantidadMovimientos = 0;
    }

    public void depositar(double monto){
        if(monto<=0){
            throw new IllegalArgumentException("El deposito debe ser mayor que cero");
        }
        saldo += monto;
        registrarMovimiento("DEPOSITO +"+monto+" -> saldo: "+saldo);
    }

    public boolean retirar(double monto){
        if(monto <= 0){
            throw new IllegalArgumentException("El retiro debe ser mayor que cero");
        }
        if(monto>saldo){
            return false;
        }

        saldo -= monto;
        registrarMovimiento("RETIRO -"+monto+" -> saldo: "+saldo);
        return true;
    }

    public void reintegrar(double monto){
        saldo += monto;
        registrarMovimiento("REVERSA +"+monto+" -> saldo: "+saldo);
    }

    public void registrarIntereses(){
        double interes = saldo * tipo.tasa();
        saldo += interes;
        registrarMovimiento("INTERESES +"+interes+" -> saldo: "+saldo);
    }

    protected void registrarMovimiento(String linea){
        try{
            if(cantidadMovimientos == historial.length){
                throw new IllegalStateException("Historial lleno");
            }
            historial[cantidadMovimientos] = linea;
            cantidadMovimientos++;
        }catch(IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }

    public void imprimirHistorial(){
        for(int i=0; i<cantidadMovimientos; i++){
            System.out.println(historial[i]);
        }
    }

    public double getSaldo(){
        return saldo;
    }

    public void quienSoy(){
        System.out.println("CuentaBancaria (clase base)");
    }

    public String toString(){
        return numero+" "+cliente+" "+saldo+" "+moneda+" "+tipo+" "+apertura.getTime();
    }
}
