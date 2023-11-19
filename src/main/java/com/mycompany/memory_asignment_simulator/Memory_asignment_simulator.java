package com.mycompany.memory_asignment_simulator;

import java.util.Scanner;
public class Memory_asignment_simulator {

    public static void main(String[] args) {
        int mS, oSS, n;
        int sizes[], tR[];
        Scanner ent = new Scanner(System.in);
        System.out.print("Inserte el tamaño de la memoria: ");
        mS=ent.nextInt();
        System.out.print("Inserte el tamaño del sistema operativo: ");
        oSS=ent.nextInt();
        System.out.print("Inserte el número de procesos: ");
        n=ent.nextInt();
        sizes = new int[n];
        tR = new int[n];
        for(int i=0; i<n; i++){
            System.out.print("Inserte el tamaño del proceso "+i+": ");
            sizes[i]=ent.nextInt();
            System.out.print("Inserte el tiempo ráfaga del proceso "+i+": ");
            tR[i]=ent.nextInt();
        }
        
        //ent.nextLine();
    }
}
