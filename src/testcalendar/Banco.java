package testcalendar;

public class Banco {
    private CuentaBancaria[] cuentas = new CuentaBancaria[20];
    private int cantidadCuentas = 0;

    public void agregarCuenta(CuentaBancaria c){
        for(int i=0; i<cantidadCuentas; i++){
            if(cuentas[i].numero == c.numero){
                throw new IllegalArgumentException("Numero de cuenta repetido");
            }
        }
        if(cantidadCuentas == cuentas.length){
            throw new IllegalStateException("El banco ya tiene 20 cuentas");
        }
        cuentas[cantidadCuentas] = c;
        cantidadCuentas++;
    }

    public CuentaBancaria buscarPorNumero(int numero){
        return buscarPorNumero(numero,0);
    }

    private CuentaBancaria buscarPorNumero(int numero, int indice){
        if(indice == cantidadCuentas){
            throw new IllegalArgumentException("No existe la cuenta #"+numero);
        }
        if(cuentas[indice].numero == numero){
            return cuentas[indice];
        }
        return buscarPorNumero(numero, indice+1);
    }

    public void ordenarPorSaldo(){
        ordenarPorSaldo(cantidadCuentas-1,0);
    }

    private void ordenarPorSaldo(int limite, int indice){
        if(limite<=0){
            return;
        }
        if(indice<limite){
            if(cuentas[indice].getSaldo() > cuentas[indice+1].getSaldo()){
                CuentaBancaria aux = cuentas[indice];
                cuentas[indice] = cuentas[indice+1];
                cuentas[indice+1] = aux;
            }
            ordenarPorSaldo(limite, indice+1);
        }else{
            ordenarPorSaldo(limite-1,0);
        }
    }

    public void transferir(int numeroOrigen, int numeroDestino, double monto){
        if(numeroOrigen == numeroDestino){
            throw new IllegalArgumentException("Las cuentas deben ser diferentes");
        }
        CuentaBancaria origen = buscarPorNumero(numeroOrigen);
        CuentaBancaria destino = buscarPorNumero(numeroDestino);

        if(!origen.retirar(monto)){
            throw new IllegalStateException("No se pudo retirar del origen");
        }
        try{
            destino.depositar(monto);
        }catch(RuntimeException e){
            origen.reintegrar(monto);
            throw e;
        }
    }

    public void aplicarInteresesGlobal(){
        for(int i=0; i<cantidadCuentas; i++){
            try{
                cuentas[i].registrarIntereses();
            }catch(RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public double saldoTotalDelBanco(){
        return saldoTotalDelBanco(0);
    }

    private double saldoTotalDelBanco(int indice){
        if(indice == cantidadCuentas){
            return 0;
        }
        return cuentas[indice].getSaldo() + saldoTotalDelBanco(indice+1);
    }

    public void generarReporte(){
        ordenarPorSaldo();
        for(int i=0; i<cantidadCuentas; i++){
            System.out.println(cuentas[i].toString());
        }
    }
}
