
package gestiondeentradas;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Javier Flores
 */

public class GestionDeEntradas {
    
    static int capacidadTotal = 100;
    static int asientosDispo = 100;
    static int asientosReserv = 0;
    static int asientosVendi = 0;     
    static double precioEntrada = 10990;
    static int numBoleta = 0;
    static ArrayList<Boleta> listaBoleta = new ArrayList<>();
    
    
    public static class Boleta {
        int numBoleta;
        int cantidadAsientos;
        String numAsiento;
        String estado;
        double totalAPagar;
        
        //constructor de boleta
        Boleta (int numBoleta, int cantidadAsientos, String numAsiento, String estado, double totalAPagar) {
            this.numBoleta = numBoleta;
            this.cantidadAsientos = cantidadAsientos;
            this.numAsiento = numAsiento;
            this.estado = estado;
            this.totalAPagar = totalAPagar;
        }
        
    }

    public static void main(String[] args) {
        
        Scanner sc = new Scanner (System.in);
        
        boolean continuar = true;
        double totalAPagar = 0;
        int numAsiento = 0;
        int cantidadAsientos = 0;
        
        while (continuar) {
                System.out.println("\nBienvenido/a al Teatro Moro!");
                System.out.println("");
                System.out.println("---- Gestion de Entradas ----");
                System.out.println("");
                System.out.println("Seleccione accion a realizar: "
                    + "\n1. Reservar asiento."
                    + "\n2. Modificar o Comprar Reserva."
                    + "\n3. Comprar entradas."
                    + "\n4. Imprimir boleta."
                    + "\n5. Salir.");
        
                    int opcion = 0;
        
                    try {
                    opcion = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Opcion invalida. Debe ingresar un numero, intente de nuevo.");
                        sc.nextLine();
                        continue;
                        
                    }
                switch (opcion) {
                    case 1: {
                        System.out.println("-- Menu de Reserva --");
                        System.out.println("");
                        System.out.println("Asientos reservados: "+asientosReserv);
                        System.out.println("");
                        System.out.println("Cuantos asientos desea reservar? (maximo 4)");
                                  
                          try {
                            cantidadAsientos = sc.nextInt();
                          } catch (Exception e) {
                          System.out.println("Opcion invalida. Debe ingresar un numero, intente de nuevo.");
                          sc.nextLine();
                          continue;                        
                          }
                        if (cantidadAsientos < 1 || cantidadAsientos > 4) {
                        System.out.println("Cantidad no valida. Debe ingresar un numero entre 1 y 4.");
                        continue;
                        }
                        //validamos disponibilidad de asientos
                        asientosDispo = (capacidadTotal - (asientosVendi + asientosReserv));
                        if (cantidadAsientos > asientosDispo) {
                            System.out.println("Cantidad insuficiente. Quedan "+ asientosDispo +" disponibles");
                        }
                        //creamos lista para guardar asientos reservados
                        int [] listaAsientosReserv = new int [cantidadAsientos];
                        //bucle para elegir asientos reservados
                        for (int c = 0; c < cantidadAsientos; c++) {//c de cantidad
                            
                        boolean asientoValido = false; 
                        
                        while (!asientoValido) {
                            System.out.println("Escoja asiento numero "+(c+1)+" a reservar:");
                            
                            try {
                                numAsiento = sc.nextInt();
                            } catch (Exception e) {
                                System.out.println("Numero de asiento invalido. Debe escojer un numero entre 1 y 100.");
                                sc.nextLine();
                                continue;
                            }
                            if (numAsiento < 1 || numAsiento > 100) {
                                System.out.println("Numero de asiento invalido. Debe escojer un numero entre 1 y 100.");
                                continue;
                            }    
                            //verificamos si asiento ya fue escogido
                            boolean repetido = false; 
                            for (int r = 0; r < c; r++) {
                                if (listaAsientosReserv [r] == numAsiento) {
                                    repetido = true;
                                    break; 
                                }
                            }
                            //verificamos si asiento ya está reservado en otra boleta
                            boolean ocupado = false;
                            for (Boleta boleta : listaBoleta) {
                                if (boleta.numAsiento.contains(String.valueOf(numAsiento))) {//String.valueOf() es para convertir datos en cadena de texto. Por ej 1 en "1"
                                    ocupado = true;
                                    break;
                                }
                            }
                            if (repetido) {
                                System.out.println("Asiento no disponible. Favor elija otro.");
                            } else if (ocupado) {
                                System.out.println("Asiento ya reservado/vendido. Favor elija otro.");
                            } else {
                               listaAsientosReserv [c] = numAsiento;
                               asientoValido = true;
                               System.out.println("Reserva Exitiosa. Asiento guardado."); 
                            }   
                            }
                        }//fin for para almacenar asientos   
                                       
                        //convertimos lista de asientos a string para mostrarla en la salida
                        StringBuilder asientosElegidos = new StringBuilder();
                        for (int e = 0; e < listaAsientosReserv.length; e++) {
                        asientosElegidos.append(listaAsientosReserv[e]);
                        if (e < listaAsientosReserv.length -1) {
                           asientosElegidos.append(", ");//si vamos agregar más cosas agrega una coma 
                        }                        
                        }
                        //actualización de datos    
                        asientosReserv += cantidadAsientos;
                        asientosDispo -= cantidadAsientos;
                        totalAPagar = precioEntrada * cantidadAsientos;
                        String estado = "Reservado";
                        //almacenamos nueva boleta
                        numBoleta++;//punto de depuración
                        Boleta nuevaBoleta = new Boleta (numBoleta, cantidadAsientos, asientosElegidos.toString(), estado, totalAPagar);
                        listaBoleta.add(nuevaBoleta);
                        System.out.println("");
                        System.out.println("Reserva exitosa. Numero de boleta: "+numBoleta);
                        break;
                    }
                    
                    case 2:
                        System.out.println("--- Modificar/Comprar Reservas ---");
                        System.out.println("");
                        if (asientosReserv == 0) {
                            System.out.println("No hay reservas para modificar. Volviendo al menu.");
                            continue;
                        } else {
                            System.out.println("Seleccione accion a realizar: "
                                + "\n1. Cambiar asiento."
                                + "\n2. Comprar reserva.");
                        int accion = 0;
                        try {
                            accion = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Accion invalida. Debe ingresar un numero, intente de nuevo.");
                            sc.nextLine();
                            continue;
                        }
                        switch (accion) {
                            case 1://comienza depuración
                                System.out.println("Indique numero de asiento a cambiar:");
                                int asientoActual = 0;
                                try {
                                    asientoActual = sc.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Entrada invalida. Debe ingresar un numero, intente de nuevo.");
                                    sc.nextLine();
                                    continue;
                                }
                                System.out.println("Indique numero de asiento nuevo:");
                                int asientoNuevo = 0;
                                try {
                                    asientoNuevo = sc.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Entrada invalida. Debe ingresar un numero, intente de nuevo.");
                                    sc.nextLine();
                                    continue;
                                }
                                
                                if (asientoActual < 1 || asientoActual > 100 || asientoNuevo < 1 || asientoNuevo > 100) {
                                    System.out.println("Error: Los asientos deben ser entre 1-100.");
                                    continue;
                                }                               
                                if (asientoActual == asientoNuevo) {
                                    System.out.println("Error: El asiento nuevo no puede ser igual al actual.");
                                    continue;
                                }//termina depuración
                                //buscamos asiento en la lista
                                boolean asientoEncontrado = false;
                                for (Boleta b : listaBoleta) {
                                    String asientoOrig = b.numAsiento;
                                    String patronViejo = "\\b" + asientoActual + "\\b";//patrón para conseguir coincidencia exacta de numeros
                                    String asientoText = asientoOrig.replaceAll(patronViejo, String.valueOf(asientoNuevo));
                                    
                                    if (!asientoText.equals(asientoOrig)) {
                                        asientoEncontrado = true;
                                        //verificamos que no se repita en la misma boleta
                                        String patronNuevo = "\\b" +asientoNuevo + "\\b";
                                        boolean repetidoEnBoleta = asientoOrig.matches(".*" +patronNuevo+ ".*");
                                        if (repetidoEnBoleta) {
                                            System.out.println("Error: El asiento "+asientoNuevo+ " ya esta registrado en esta boleta.");
                                            break;
                                        }
                                        
                                        //verificamos que nuevo asiento no esté ocupado en otra boleta
                                        boolean nuevoOcupado = false;
                                        for (Boleta otraBoleta : listaBoleta) {
                                            if (otraBoleta != b) {
                                                 if (otraBoleta.numAsiento.matches(".*" + patronNuevo + ".*")) {
                                                    nuevoOcupado = true;
                                                    break;
                                    }                                  
                                    }
                                }
                                if (nuevoOcupado) {
                                    System.out.println("Error: Asiento "+asientoText+ " ya está ocupado");
                                } else {
                                    b.numAsiento = asientoText;
                                    System.out.println("Cambio exitoso. Nuevos Asientos: "+b.numAsiento);
                                }
                                        
                                break;
                              }
                                
                              }
                                
                              if (!asientoEncontrado) {
                                  System.out.println("Error: Asiento "+asientoActual + " no encontrado.");
                              }
                              break;
                              
                            case 2:
                                System.out.println("--- Compra de Reservas ---");
                                System.out.println("");
                                break;                                                             
                                                              
                            default:
                                System.out.println("Opcion valida. Intente de nuevo.");
                                break;    
                            }
                            sc.nextLine();
                            System.out.println("Desea comprar alguna reserva existente? (S/N)");
                            String respuesta = sc.nextLine();
                            
                            if (respuesta.equalsIgnoreCase("S")) {
                                //comienza depuración
                                System.out.println("Ingrese numero de boleta a comprar:");
                                int numBoletaComprar = 0;
                                try {
                                    numBoletaComprar = sc.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Debe ingresar un numero de boleta");
                                    sc.nextLine();
                                    continue;
                                }
                                Boleta boletaComprar = null;
                                for (Boleta b : listaBoleta) {
                                    if (b.numBoleta == numBoletaComprar && b.estado.equals("Reservado")) {
                                        boletaComprar = b;
                                        break;
                                    }
                                }
                                if (boletaComprar == null) {
                                    System.out.println("Boleta no encontrada o ya comprada.");
                                } else {
                                    boletaComprar.estado = "Vendido";
                                    asientosReserv -= boletaComprar.cantidadAsientos;
                                    asientosVendi += boletaComprar.cantidadAsientos;
                                    
                                    System.out.println("Compra realizada con exito");
                                    System.out.println("Boleta Nro: "+boletaComprar.numBoleta
                                            + "\nAsientos: "+boletaComprar.numAsiento
                                            + "\nTotal a pagar: $"+boletaComprar.totalAPagar);
                                }//termina depuración
                            }
                            break;
                        }//fin else
                                       
                        case 3: {
                        System.out.println("--- Menu de Compra de Entradas ---");
                        System.out.println("");
                        System.out.println("Asientos vendidos: "+asientosVendi);
                        System.out.println("");
                        System.out.println("Cuantas entradas desea comprar? (maximo 4 por cliente)");
                        cantidadAsientos = 0;
                        try {
                            cantidadAsientos = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Error: debe ingresar un numero entre 1-4");
                            continue;
                        }
                        if (cantidadAsientos < 1 || cantidadAsientos > 4) {
                            System.out.println("Cantidad invalida. Debe ingresar un numero entre 1-4");
                            continue;
                        }
                        //validamos disponibilidad de asientos
                        asientosDispo = capacidadTotal - (asientosReserv + asientosVendi);
                        if (cantidadAsientos > asientosDispo) {
                            System.out.println("Cantidad de asientos insuficiente. Quedan "+asientosDispo+" disponibles.");
                            continue;
                        }
                        //almacenamos asientos a comprar
                        int [] listaAsientosComprados = new int [cantidadAsientos];
                        //bucle para elegir asientos reservados
                        for (int c = 0; c < cantidadAsientos; c++) {//c de cantidad
                            
                        boolean asientoValido = false; 
                        
                        while (!asientoValido) {
                            System.out.println("Escoja asiento numero "+(c+1)+" a comprar:");
                            
                            try {
                                numAsiento = sc.nextInt();
                            } catch (Exception e) {
                                System.out.println("Numero de asiento invalido. Debe escojer un numero entre 1 y 100.");
                                sc.nextLine();
                                continue;
                            }
                            if (numAsiento < 1 || numAsiento > 100) {
                                System.out.println("Numero de asiento invalido. Debe escojer un numero entre 1 y 100.");
                                continue;
                            }    
                            //verificamos si asiento ya fue escogido
                            boolean repetido = false; 
                            for (int r = 0; r < c; r++) {
                                if (listaAsientosComprados [r] == numAsiento) {
                                    repetido = true;
                                    break; 
                                }
                            }
                            //verificamos si asiento ya está reservado en otra boleta
                            boolean ocupado = false;
                            for (Boleta boleta : listaBoleta) {
                                if (boleta.numAsiento.contains(String.valueOf(numAsiento))) {//String.valueOf() es para convertir datos en cadena de texto. Por ej 1 en "1"
                                    ocupado = true;
                                    break;
                                }
                            }
                            if (repetido) {
                                System.out.println("Asiento no disponible. Favor elija otro.");
                            } else if (ocupado) {
                                System.out.println("Asiento ya reservado/vendido. Favor elija otro.");
                            } else {
                               listaAsientosComprados [c] = numAsiento;
                               asientoValido = true;
                               System.out.println("Seleccion Exitiosa. Asiento guardado."); 
                            }   
                            }
                        }//fin for para almacenar asientos   
                        //convertimos lista de asientos a string para mostrarla en la salida
                        StringBuilder asientosComprados = new StringBuilder();
                        for (int e = 0; e < listaAsientosComprados.length; e++) {
                        asientosComprados.append(listaAsientosComprados[e]);
                        if (e < listaAsientosComprados.length -1) {
                           asientosComprados.append(", ");//si vamos agregar más cosas agrega una coma 
                        }                        
                        }
                        //actualización de datos
                        asientosVendi += cantidadAsientos;
                        asientosDispo-= cantidadAsientos;
                        totalAPagar = precioEntrada * cantidadAsientos;
                        String estado = "Vendido";
                        
                        //almacenamos nueva boleta
                        numBoleta++;
                        Boleta nuevaBoleta = new Boleta (numBoleta, cantidadAsientos, asientosComprados.toString(), estado, totalAPagar);
                        listaBoleta.add(nuevaBoleta);
                        System.out.println("");
                        System.out.println("Total a pagar: "+totalAPagar);
                        System.out.println("Compra exitosa. Numero de boleta: "+numBoleta);
                        break;
                    }
                    case 4: 
                        System.out.println("-- Impresion de Boleta--");
                        System.out.println("");
                        System.out.println("Numero de boletas en el sistema: "+listaBoleta.size());
                        if (listaBoleta.isEmpty()) {
                            System.out.println("No hay boletas guardadas.");
                        } else {
                            for (Boleta b : listaBoleta) {//punto de depuración
                                System.out.println("\nBoleta Nro: "+b.numBoleta
                                        + "\nEstado: "+b.estado
                                        + "\nCantidad de asientos: "+b.cantidadAsientos
                                        + "\nAsientos: "+b.numAsiento
                                        + "\nTotal a pagar: "+b.totalAPagar);
                            }
                        }
                        break;
                        
                    case 5:
                        System.out.println("Saliendo del gestor de asientos...");
                        System.out.println("Gracias por su visita.");
                        continuar = false;
                        break;
                        
                    default:
                        System.out.println("Opcion invalida. Favor intente de nuevo");
                        break;
                        
                }//fin switch opción
            }//fin while para continuar
        sc.close(); 
    }
    }


   
       

                   


