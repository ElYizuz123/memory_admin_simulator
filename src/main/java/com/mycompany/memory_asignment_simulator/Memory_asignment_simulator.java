package com.mycompany.memory_asignment_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Memory_asignment_simulator {

    
    static class Espacio{
        int memLocation, freeSize;
        public Espacio(int memLocation, int freeSize) {
            this.memLocation = memLocation;
            this.freeSize = freeSize;
        }
    }
    static int memSize, opSysSize, n;
    static int sizes[], timeRaf[];
    static List<Espacio> freeSpace = new ArrayList<>();
    static List<Espacio> ram = new ArrayList<>();
    static Scanner ent = new Scanner(System.in);
    public static void main(String[] args) {
        inicioPrograma();
        primerAjuste();
        
    }
    private static void primerAjuste() {
        System.out.println("-------------------- MÉTODO DEL PRIMER AJUSTE --------------------\n");
        System.out.println("|-----------------------------------| -> 0\n"
                +          "|                                   |\n"
                +          "|         Sistema operativo         |\n"
                +          "|                                   |\n"
                +          "|-----------------------------------| -> "+opSysSize+"\n"
                +          "|                                   |\n"
                +          "|               Libre               |\n"
                +          "|                                   |\n"
                +          "|-----------------------------------| -> "+memSize+"\n");
    }
    private static void inicioPrograma() {
        System.out.print("Inserte el tamaño de la memoria: ");
        memSize=ent.nextInt();
        System.out.print("Inserte el tamaño del sistema operativo: ");
        opSysSize=ent.nextInt();
        System.out.print("Inserte el número de procesos: ");
        n=ent.nextInt();
        freeSpace.add(new Espacio(opSysSize, memSize-opSysSize));
        ram.add(new Espacio(0, opSysSize));
        ram.add(new Espacio(opSysSize, memSize-opSysSize));
        sizes = new int[n];
        timeRaf = new int[n];
        for(int i=0; i<n; i++){
            System.out.print("Inserte el tamaño del proceso "+i+": ");
            sizes[i]=ent.nextInt();
            System.out.print("Inserte el tiempo ráfaga del proceso "+i+": ");
            timeRaf[i]=ent.nextInt();
        }
    }
}
