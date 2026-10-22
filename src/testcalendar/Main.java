package testcalendar;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        Banco banco = new Banco();
        int opcion=0, numero, destino, tipo;
        double monto;
        CuentaBancaria cuenta;

        while(opcion != 9){
            System.out.println("\n1 Crear cuenta\n2 Depositar\n3 Retirar\n4 Transferir"
                    +"\n5 Aplicar intereses\n6 Reporte\n7 Historial\n8 Girar cheque\n9 Salir");
            if(!s.hasNext()){
                break;
            }
            try{
                opcion = s.nextInt();
                switch(opcion){
                    case 1:
                        System.out.println("1 Ahorro, 2 Cheques, 3 Plazo fijo");
                        tipo = s.nextInt();
                        if(tipo<1 || tipo>3){
                            throw new IllegalArgumentException("Tipo de cuenta invalido");
                        }
                        System.out.print("Numero: ");
                        numero = s.nextInt();
                        s.nextLine();
                        System.out.print("Cliente: ");
                        String cliente = s.nextLine();
                        System.out.print("Moneda: ");
                        String moneda = s.nextLine();

                        if(tipo==1){
                            cuenta = new CuentaAhorro(numero,cliente,moneda);
                        }else if(tipo==2){
                            System.out.print("Sobregiro: ");
                            double limite = s.nextDouble();
                            cuenta = new CuentaCheques(numero, cliente, moneda, limite);
                        }else{
                            CuentaPlazoFijo plazo = new CuentaPlazoFijo(numero,cliente,moneda);
                            System.out.print("Vencimiento (anio mes dia): ");
                            int anio=s.nextInt(), mes=s.nextInt(), dia=s.nextInt();
                            plazo.setPlazo(anio,mes,dia);
                            cuenta = plazo;
                        }
                        banco.agregarCuenta(cuenta);
                        cuenta.quienSoy();
                        break;
                    case 2:
                    case 3:
                        System.out.print("Cuenta: ");
                        cuenta = banco.buscarPorNumero(s.nextInt());
                        System.out.print("Monto: ");
                        monto = s.nextDouble();
                        if(opcion==2){
                            cuenta.depositar(monto);
                        }else if(!cuenta.retirar(monto)){
                            System.out.println("No se pudo retirar ese monto");
                        }
                        break;
                    case 4:
                        System.out.print("Origen: ");
                        numero = s.nextInt();
                        System.out.print("Destino: ");
                        destino = s.nextInt();
                        System.out.print("Monto: ");
                        monto = s.nextDouble();
                        banco.transferir(numero,destino,monto);
                        break;
                    case 5:
                        banco.aplicarInteresesGlobal();
                        break;
                    case 6:
                        banco.generarReporte();
                        System.out.println("Total: "+banco.saldoTotalDelBanco());
                        break;
                    case 7:
                        System.out.print("Cuenta: ");
                        banco.buscarPorNumero(s.nextInt()).imprimirHistorial();
                        break;
                    case 8:
                        System.out.print("Cuenta: ");
                        cuenta = banco.buscarPorNumero(s.nextInt());
                        if(!(cuenta instanceof CuentaCheques)){
                            throw new IllegalStateException("La cuenta no es de cheques");
                        }
                        System.out.print("Monto: ");
                        monto = s.nextDouble();
                        ((CuentaCheques)cuenta).girarCheque(monto);
                        break;
                    case 9:
                        break;
                    default:
                        System.out.println("Opcion invalida");
                }
            }catch(Exception e){
                System.out.println("Error: "+e.getMessage());
                if(s.hasNextLine()){
                    s.nextLine();
                }
            }
        }
        s.close();
    }
}
