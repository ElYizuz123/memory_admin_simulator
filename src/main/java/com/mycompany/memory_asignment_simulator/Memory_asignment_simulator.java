package com.mycompany.memory_asignment_simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Memory_asignment_simulator {
    static int memSize, opSysSize, n;
    static int sizes[], timeRaf[];
    static List<EspacioLibre> freeSpace = new ArrayList<>();
    static class EspacioLibre{
        int memLocation, freeSize;

        public EspacioLibre(int memLocation, int freeSize) {
            this.memLocation = memLocation;
            this.freeSize = freeSize;
        }
        
    }
    public static void main(String[] args) {
        Scanner ent = new Scanner(System.in);
        System.out.print("Inserte el tamaño de la memoria: ");
        memSize=ent.nextInt();
        System.out.print("Inserte el tamaño del sistema operativo: ");
        opSysSize=ent.nextInt();
        System.out.print("Inserte el número de procesos: ");
        n=ent.nextInt();
        freeSpace.add(new EspacioLibre(opSysSize, memSize-opSysSize));
        sizes = new int[n];
        timeRaf = new int[n];
        for(int i=0; i<n; i++){
            System.out.print("Inserte el tamaño del proceso "+i+": ");
            sizes[i]=ent.nextInt();
            System.out.print("Inserte el tiempo ráfaga del proceso "+i+": ");
            timeRaf[i]=ent.nextInt();
        }
        System.out.println("|-----------------------------------| -> 0\n"
                +          "|                                   |\n"
                +          "|         Sistema operativo         |\n"
                +          "|                                   |\n"
                +          "|-----------------------------------| -> "+opSysSize+"\n"
                +          "|                                   |\n"
                +          "|                                   |\n"
                +          "|                                   |\n"
                +          "|-----------------------------------| -> "+(memSize-opSysSize)+"\n");
        //ent.nextLine();
    }
}
