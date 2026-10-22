package testcalendar;

import java.util.Calendar;

public class CuentaPlazoFijo extends CuentaBancaria {
    private Calendar findePlazo;
    private double intereses;

    public CuentaPlazoFijo(int numero, String cliente, String moneda){
        super(numero, cliente, moneda, TipoCuenta.PLAZO);
    }

    public void setPlazo(int anio, int mes, int dia){
        try{
            Calendar fecha = Calendar.getInstance();
            fecha.clear();
            fecha.setLenient(false);
            fecha.set(anio, mes-1, dia);
            fecha.getTime();
            findePlazo = fecha;
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Fecha de vencimiento invalida");
        }
    }

    public boolean vencida(){
        return findePlazo != null && !Calendar.getInstance().before(findePlazo);
    }

    private void pasarIntereses(){
        if(intereses != 0){
            saldo += intereses;
            registrarMovimiento("INTERESES AL SALDO +"+intereses+" -> saldo: "+saldo);
            intereses = 0;
        }
    }

    public boolean retirar(double monto){
        if(monto<=0){
            throw new IllegalArgumentException("El retiro debe ser mayor que cero");
        }
        if(vencida()){
            pasarIntereses();
            return super.retirar(monto);
        }

        if(monto>intereses){
            return false;
        }
        intereses -= monto;
        registrarMovimiento("RETIRO DE INTERESES -"+monto+" -> intereses: "+intereses);
        return true;
    }

    public void registrarIntereses(){
        if(vencida()){
            pasarIntereses();
            super.registrarIntereses();
        }else{
            double interes = saldo * tipo.tasa();
            intereses += interes;
            registrarMovimiento("INTERESES +"+interes+" -> intereses: "+intereses);
        }
    }

    public void reintegrar(double monto){
        if(vencida()){
            super.reintegrar(monto);
        }else{
            intereses += monto;
            registrarMovimiento("REVERSA DE INTERESES +"+monto+" -> intereses: "+intereses);
        }
    }

    public void quienSoy(){
        System.out.println("Soy una CuentaPlazoFijo");
    }

    public String toString(){
        String fecha = "Sin definir";
        if(findePlazo != null){
            fecha = findePlazo.getTime().toString();
        }
        return super.toString()+" Intereses: "+intereses+" Vence: "+fecha;
    }
}
