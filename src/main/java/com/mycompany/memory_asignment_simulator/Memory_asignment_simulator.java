package com.mycompany.memory_asignment_simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
public class Memory_asignment_simulator {

    
    static class Espacio{
        public int memLocation, freeSize, idProcess;
        public Espacio(int memLocation, int freeSize, int idProcess) {
            this.memLocation = memLocation;
            this.freeSize = freeSize;
            this.idProcess = idProcess;
        }
    }
    static int memSize, opSysSize, n, contFA=0, contBA=0, contWA=0;
    static int sizes[], timeRaf[];
    static List<Espacio> freeSpace = new ArrayList<>();
    static List<Espacio> ram = new ArrayList<>();
    static List<Integer> exProcess = new ArrayList<>();
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
        while(true){
            contFA++;
            for(int i:exProcess){
                timeRaf[i]--;
                if(timeRaf[i]<=0){
                    exProcess.remove(i);
                    for(Espacio sl: ram){
                        if(sl.idProcess==i){
                            freeSpace.add(new Espacio(sl.memLocation, sl.freeSize, -1));
                            sl.idProcess=-1;
                        }
                    }
                }
                    
            }
            for(int i=0; i<n; i++){
                if(timeRaf[i]>0){
                    boolean comp=false;
                    for(int j: exProcess){
                        if(j==i){
                            comp=true;
                            break;
                        }
                    }
                    if(comp==false){
                        for(Espacio es:freeSpace){
                            if(sizes[i]<=es.freeSize){
                                exProcess.add(i);
                                ram.add(new Espacio(es.memLocation, sizes[i], i));
                                if(es.freeSize==sizes[i]){
                                    freeSpace.remove(es);
                                }
                                else{
                                    es.memLocation=es.memLocation+sizes[i];
                                    es.freeSize = es.freeSize-sizes[i];
                                }
                                
                            }
                        }
                    }
                }  
                else
                    continue;
            }
            Collections.sort(freeSpace, Comparator.comparing(Espacio -> Espacio.memLocation));
            Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));
            
            for(int i=0; i<freeSpace.size(); i++){
                if((i+1)<freeSpace.size()){
                    if((freeSpace.get(i).memLocation+freeSpace.get(i).freeSize)>=freeSpace.get(i+1).memLocation){
                        freeSpace.get(i).freeSize=freeSpace.get(i).freeSize+freeSpace.get(i+1).freeSize;
                        freeSpace.remove(i+1);
                    }
                }
            }
            for(int i=0; i<ram.size(); i++){
                if((i+1)<ram.size()){
                    if(ram.get(i).idProcess==-1&&ram.get(i+1).idProcess==-1){
                        ram.get(i).freeSize=ram.get(i).freeSize+ram.get(i+1).freeSize;
                        ram.remove(i+1);
                    } 
                }
            }    
        }
    }
    
    private static void inicioPrograma() {
        System.out.print("Inserte el tamaño de la memoria: ");
        memSize=ent.nextInt();
        System.out.print("Inserte el tamaño del sistema operativo: ");
        opSysSize=ent.nextInt();
        System.out.print("Inserte el número de procesos: ");
        n=ent.nextInt();
        freeSpace.add(new Espacio(opSysSize, memSize-opSysSize, -1));
        ram.add(new Espacio(0, opSysSize, -2));
        ram.add(new Espacio(opSysSize, memSize-opSysSize, -1));
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
