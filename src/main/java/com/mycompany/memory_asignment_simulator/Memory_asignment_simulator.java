package com.mycompany.memory_asignment_simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Memory_asignment_simulator {

    static class Espacio {

        public int memLocation, freeSize, idProcess;

        public Espacio(int memLocation, int freeSize, int idProcess) {
            this.memLocation = memLocation;
            this.freeSize = freeSize;
            this.idProcess = idProcess;
        }
    }
    static int memSize, opSysSize, n, contFA = 0, contBA = 0, contWA = 0;
    static int sizes[], timeRaf[], timeRafDef[];

    static Scanner ent = new Scanner(System.in);

    public static void main(String[] args) {
        inicioPrograma();
        primerAjuste();
        mejorAjuste();
        peorAjuste();
        comparacion();
    }

    private static void comparacion() {
        System.out.println("\n\nComparación:\n");
        List<Integer> orden = new ArrayList<>();
        orden.add(contFA);
        orden.add(contBA);
        orden.add(contWA);
        Collections.sort(orden);
        System.out.print("Con " + orden.get(0) + " iteraciones: ");
        if (orden.get(0) == contFA) {
            System.out.println("Primer ajuste");
            contFA = -1;
        } else if (orden.get(0) == contBA) {
            System.out.println("Mejor ajuste");
            contBA = -1;
        } else if (orden.get(0) == contWA) {
            System.out.println("Peor ajuste");
            contWA = -1;
        }
        System.out.print("Con " + orden.get(1) + " iteraciones: ");
        if (orden.get(1) == contFA) {
            System.out.println("Primer ajuste");
            contFA = -1;
        } else if (orden.get(1) == contBA) {
            System.out.println("Mejor ajuste");
            contBA = -1;
        } else if (orden.get(1) == contWA) {
            System.out.println("Peor ajuste");
            contWA = -1;
        }
        System.out.print("Con " + orden.get(2) + " iteraciones: ");
        if (orden.get(2) == contFA) {
            System.out.println("Primer ajuste");
            contFA = -1;
        } else if (orden.get(2) == contBA) {
            System.out.println("Mejor ajuste");
            contBA = -1;
        } else if (orden.get(2) == contWA) {
            System.out.println("Peor ajuste");
            contWA = -1;
        }

    }

    private static void primerAjuste() {
        int contPE = 0;
        List<Espacio> freeSpace = new ArrayList<>();
        List<Espacio> ram = new ArrayList<>();
        List<Integer> exProcess = new ArrayList<>();
        freeSpace.add(new Espacio(opSysSize, memSize - opSysSize, -1));
        ram.add(new Espacio(0, opSysSize, -2));
        ram.add(new Espacio(opSysSize, memSize - opSysSize, -1));
        System.out.println("\n-------------------- MÉTODO DEL PRIMER AJUSTE --------------------\n");
        System.out.println("|-----------------------------------| -> 0\n"
                + "|                                   |\n"
                + "|         Sistema operativo         |\n"
                + "|                                   |\n"
                + "|-----------------------------------| -> " + opSysSize + "\n"
                + "|                                   |\n"
                + "|               Libre               |\n"
                + "|                                   |\n"
                + "|-----------------------------------| -> " + memSize + "\n"
                + "--------------------------------------\n");
        while (true) {
            boolean act = false;
            for (int i = 0; i < exProcess.size(); i++) {
                timeRaf[exProcess.get(i)]--;
                if (timeRaf[exProcess.get(i)] <= 0) {
                    for (int j = 0; j < ram.size(); j++) {
                        if (ram.get(j).idProcess == exProcess.get(i)) {
                            freeSpace.add(new Espacio(ram.get(j).memLocation, ram.get(j).freeSize, -1));
                            ram.get(j).idProcess = -1;
                        }
                    }
                    exProcess.remove(i);
                    act = true;
                    i--;
                }

            }

            Collections.sort(freeSpace, Comparator.comparing(Espacio -> Espacio.memLocation));
            Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));

            for (int i = 0; i < freeSpace.size(); i++) {
                if ((i + 1) < freeSpace.size()) {
                    if ((freeSpace.get(i).memLocation + freeSpace.get(i).freeSize) >= freeSpace.get(i + 1).memLocation) {
                        freeSpace.get(i).freeSize = freeSpace.get(i).freeSize + freeSpace.get(i + 1).freeSize;
                        freeSpace.remove(i + 1);
                        i = 0;
                    }
                }
            }
            for (int i = 0; i < ram.size(); i++) {
                if ((i + 1) < ram.size()) {
                    if (ram.get(i).idProcess == -1 && ram.get(i + 1).idProcess == -1) {
                        ram.get(i).freeSize = ram.get(i).freeSize + ram.get(i + 1).freeSize;
                        ram.remove(i + 1);
                        i = 0;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                if (timeRaf[i] > 0) {
                    boolean comp = false;
                    for (int j : exProcess) {
                        if (j == i) {
                            comp = true;
                            break;
                        }
                    }
                    if (comp == false) {
                        for (int j = 0; j < freeSpace.size(); j++) {
                            if (sizes[i] <= freeSpace.get(j).freeSize) {
                                act = true;
                                exProcess.add(i);
                                contPE++;
                                ram.add(new Espacio(freeSpace.get(j).memLocation, sizes[i], i));
                                if (freeSpace.get(j).freeSize == sizes[i]) {
                                    for (int z = 0; z < ram.size(); z++) {
                                        if (ram.get(z).memLocation == freeSpace.get(j).memLocation && ram.get(z).idProcess == -1) {
                                            ram.remove(z);
                                            break;
                                        }
                                    }
                                    freeSpace.remove(freeSpace.get(j));
                                } else {
                                    for (int z = 0; z < ram.size(); z++) {
                                        if (ram.get(z).memLocation == freeSpace.get(j).memLocation && ram.get(z).idProcess == -1) {
                                            ram.get(z).memLocation = freeSpace.get(j).memLocation + sizes[i];
                                            ram.get(z).freeSize = freeSpace.get(j).freeSize - sizes[i];
                                            break;
                                        }
                                    }
                                    freeSpace.get(j).memLocation = freeSpace.get(j).memLocation + sizes[i];
                                    freeSpace.get(j).freeSize = freeSpace.get(j).freeSize - sizes[i];

                                }
                                break;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
            if (act == true) {
                int fin = 0;
                for (int i = 0; i < n; i++) {
                    System.out.print("\nP" + i + " -> " + timeRaf[i] + " | " + sizes[i]);
                    if (timeRaf[i] == 0) {
                        fin++;
                    }
                }
                if (fin >= n) {
                    for (int i = 0; i < n; i++) {
                        timeRaf[i] = timeRafDef[i];
                    }
                    break;
                } else {
                    System.out.println("\n");
                }
                Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));
                for (Espacio pr : ram) {
                    if (pr.idProcess == -2) {
                        System.out.println("|-----------------------------------| -> 0\n"
                                + "|                                   |\n"
                                + "|         Sistema operativo         |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + opSysSize);

                    } else if (pr.idProcess == -1) {
                        System.out.println(
                                "|                                   |\n"
                                + "|               Libre               |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + (pr.memLocation + pr.freeSize));
                    } else if (pr.idProcess >= 0) {
                        String proceso = String.format("%18s", "P" + pr.idProcess);
                        System.out.println(
                                "|                                   |\n"
                                + "|" + proceso + "                 |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + (pr.memLocation + pr.freeSize));
                    }
                }
                System.out.println("\n--------------------------------------------\n");
                contFA++;
            }
            if (contPE == n) {
                for (int i = 0; i < n; i++) {
                    timeRaf[i] = timeRafDef[i];
                }
                break;
            }
        }
    }

    private static void mejorAjuste() {
        int contPE = 0;
        List<Espacio> freeSpace = new ArrayList<>();
        List<Espacio> ram = new ArrayList<>();
        List<Integer> exProcess = new ArrayList<>();
        freeSpace.add(new Espacio(opSysSize, memSize - opSysSize, -1));
        ram.add(new Espacio(0, opSysSize, -2));
        ram.add(new Espacio(opSysSize, memSize - opSysSize, -1));
        System.out.println("\n-------------------- MÉTODO DEL MEJOR AJUSTE --------------------\n");
        System.out.println("|-----------------------------------| -> 0\n"
                + "|                                   |\n"
                + "|         Sistema operativo         |\n"
                + "|                                   |\n"
                + "|-----------------------------------| -> " + opSysSize + "\n"
                + "|                                   |\n"
                + "|               Libre               |\n"
                + "|                                   |\n"
                + "|-----------------------------------| -> " + memSize + "\n"
                + "--------------------------------------\n");
        while (true) {
            boolean act = false;
            for (int i = 0; i < exProcess.size(); i++) {
                timeRaf[exProcess.get(i)]--;
                if (timeRaf[exProcess.get(i)] <= 0) {
                    act = true;
                    for (int j = 0; j < ram.size(); j++) {
                        if (ram.get(j).idProcess == exProcess.get(i)) {
                            freeSpace.add(new Espacio(ram.get(j).memLocation, ram.get(j).freeSize, -1));
                            ram.get(j).idProcess = -1;
                        }
                    }
                    exProcess.remove(i);
                    i--;
                }

            }
            Collections.sort(freeSpace, Comparator.comparing(Espacio -> Espacio.memLocation));
            Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));

            for (int i = 0; i < freeSpace.size(); i++) {
                if ((i + 1) < freeSpace.size()) {
                    if ((freeSpace.get(i).memLocation + freeSpace.get(i).freeSize) >= freeSpace.get(i + 1).memLocation) {
                        freeSpace.get(i).freeSize = freeSpace.get(i).freeSize + freeSpace.get(i + 1).freeSize;
                        freeSpace.remove(i + 1);
                        i = 0;
                    }
                }
            }
            for (int i = 0; i < ram.size(); i++) {
                if ((i + 1) < ram.size()) {
                    if (ram.get(i).idProcess == -1 && ram.get(i + 1).idProcess == -1) {
                        ram.get(i).freeSize = ram.get(i).freeSize + ram.get(i + 1).freeSize;
                        ram.remove(i + 1);
                        i = 0;
                    }
                }
            }
            Collections.sort(freeSpace, Comparator.comparing(Espacio -> Espacio.freeSize));
            for (int i = 0; i < n; i++) {
                if (timeRaf[i] > 0) {
                    boolean comp = false;
                    for (int j : exProcess) {
                        if (j == i) {
                            comp = true;
                            break;
                        }
                    }
                    if (comp == false) {
                        for (int j = 0; j < freeSpace.size(); j++) {
                            if (sizes[i] <= freeSpace.get(j).freeSize) {
                                act = true;
                                exProcess.add(i);
                                contPE++;
                                ram.add(new Espacio(freeSpace.get(j).memLocation, sizes[i], i));
                                if (freeSpace.get(j).freeSize == sizes[i]) {
                                    for (int z = 0; z < ram.size(); z++) {
                                        if (ram.get(z).memLocation == freeSpace.get(j).memLocation && ram.get(z).idProcess == -1) {
                                            ram.remove(z);
                                            break;
                                        }
                                    }
                                    freeSpace.remove(freeSpace.get(j));
                                } else {
                                    for (int z = 0; z < ram.size(); z++) {
                                        if (ram.get(z).memLocation == freeSpace.get(j).memLocation && ram.get(z).idProcess == -1) {
                                            ram.get(z).memLocation = freeSpace.get(j).memLocation + sizes[i];
                                            ram.get(z).freeSize = freeSpace.get(j).freeSize - sizes[i];
                                            break;
                                        }
                                    }
                                    freeSpace.get(j).memLocation = freeSpace.get(j).memLocation + sizes[i];
                                    freeSpace.get(j).freeSize = freeSpace.get(j).freeSize - sizes[i];

                                }
                                break;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }
            if (act == true) {
                int fin = 0;
                for (int i = 0; i < n; i++) {
                    System.out.print("\nP" + i + " -> " + timeRaf[i] + " | " + sizes[i]);
                    if (timeRaf[i] == 0) {
                        fin++;
                    }
                }
                if (fin >= n) {
                    for (int i = 0; i < n; i++) {
                        timeRaf[i] = timeRafDef[i];
                    }
                    break;
                } else {
                    System.out.println("\n");
                }
                Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));
                for (Espacio pr : ram) {
                    if (pr.idProcess == -2) {
                        System.out.println("|-----------------------------------| -> 0\n"
                                + "|                                   |\n"
                                + "|         Sistema operativo         |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + opSysSize);

                    } else if (pr.idProcess == -1) {
                        System.out.println(
                                "|                                   |\n"
                                + "|               Libre               |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + (pr.memLocation + pr.freeSize));
                    } else if (pr.idProcess >= 0) {
                        String proceso = String.format("%18s", "P" + pr.idProcess);
                        System.out.println(
                                "|                                   |\n"
                                + "|" + proceso + "                 |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + (pr.memLocation + pr.freeSize));
                    }
                }
                System.out.println("\n--------------------------------------------\n");
                contBA++;
                if (contPE == n) {
                    for (int i = 0; i < n; i++) {
                        timeRaf[i] = timeRafDef[i];
                    }
                    break;
                }
            }

        }
    }

    private static void peorAjuste() {
        int contPE = 0;
        List<Espacio> freeSpace = new ArrayList<>();
        List<Espacio> ram = new ArrayList<>();
        List<Integer> exProcess = new ArrayList<>();
        freeSpace.add(new Espacio(opSysSize, memSize - opSysSize, -1));
        ram.add(new Espacio(0, opSysSize, -2));
        ram.add(new Espacio(opSysSize, memSize - opSysSize, -1));
        System.out.println("\n-------------------- MÉTODO DEL PEOR AJUSTE --------------------\n");
        System.out.println("|-----------------------------------| -> 0\n"
                + "|                                   |\n"
                + "|         Sistema operativo         |\n"
                + "|                                   |\n"
                + "|-----------------------------------| -> " + opSysSize + "\n"
                + "|                                   |\n"
                + "|               Libre               |\n"
                + "|                                   |\n"
                + "|-----------------------------------| -> " + memSize + "\n"
                + "--------------------------------------\n");
        while (true) {
            boolean act = false;
            for (int i = 0; i < exProcess.size(); i++) {
                timeRaf[exProcess.get(i)]--;
                if (timeRaf[exProcess.get(i)] <= 0) {
                    act = true;
                    for (int j = 0; j < ram.size(); j++) {
                        if (ram.get(j).idProcess == exProcess.get(i)) {
                            freeSpace.add(new Espacio(ram.get(j).memLocation, ram.get(j).freeSize, -1));
                            ram.get(j).idProcess = -1;
                        }
                    }
                    exProcess.remove(i);
                    i--;
                }

            }

            Collections.sort(freeSpace, Comparator.comparing(Espacio -> Espacio.memLocation));
            Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));

            for (int i = 0; i < freeSpace.size(); i++) {
                if ((i + 1) < freeSpace.size()) {
                    if ((freeSpace.get(i).memLocation + freeSpace.get(i).freeSize) >= freeSpace.get(i + 1).memLocation) {
                        freeSpace.get(i).freeSize = freeSpace.get(i).freeSize + freeSpace.get(i + 1).freeSize;
                        freeSpace.remove(i + 1);
                        i = 0;
                    }
                }
            }
            for (int i = 0; i < ram.size(); i++) {
                if ((i + 1) < ram.size()) {
                    if (ram.get(i).idProcess == -1 && ram.get(i + 1).idProcess == -1) {
                        ram.get(i).freeSize = ram.get(i).freeSize + ram.get(i + 1).freeSize;
                        ram.remove(i + 1);
                        i = 0;
                    }
                }
            }
            Collections.sort(freeSpace, Comparator.comparing(Espacio -> Espacio.freeSize));
            Collections.reverse(freeSpace);
            for (int i = 0; i < n; i++) {
                if (timeRaf[i] > 0) {
                    boolean comp = false;
                    for (int j : exProcess) {
                        if (j == i) {
                            comp = true;
                            break;
                        }
                    }
                    if (comp == false) {
                        for (int j = 0; j < freeSpace.size(); j++) {
                            if (sizes[i] <= freeSpace.get(j).freeSize) {
                                act = true;
                                exProcess.add(i);
                                contPE++;
                                ram.add(new Espacio(freeSpace.get(j).memLocation, sizes[i], i));
                                if (freeSpace.get(j).freeSize == sizes[i]) {
                                    for (int z = 0; z < ram.size(); z++) {
                                        if (ram.get(z).memLocation == freeSpace.get(j).memLocation && ram.get(z).idProcess == -1) {
                                            ram.remove(z);
                                            break;
                                        }
                                    }
                                    freeSpace.remove(freeSpace.get(j));
                                } else {
                                    for (int z = 0; z < ram.size(); z++) {
                                        if (ram.get(z).memLocation == freeSpace.get(j).memLocation && ram.get(z).idProcess == -1) {
                                            ram.get(z).memLocation = freeSpace.get(j).memLocation + sizes[i];
                                            ram.get(z).freeSize = freeSpace.get(j).freeSize - sizes[i];
                                            break;
                                        }
                                    }
                                    freeSpace.get(j).memLocation = freeSpace.get(j).memLocation + sizes[i];
                                    freeSpace.get(j).freeSize = freeSpace.get(j).freeSize - sizes[i];

                                }
                                break;
                            }
                        }
                    }
                } else {
                    continue;
                }
            }

            if (act == true) {
                int fin = 0;
                for (int i = 0; i < n; i++) {
                    System.out.print("\nP" + i + " -> " + timeRaf[i] + " | " + sizes[i]);
                    if (timeRaf[i] == 0) {
                        fin++;
                    }
                }
                if (fin >= n) {
                    for (int i = 0; i < n; i++) {
                        timeRaf[i] = timeRafDef[i];
                    }
                    break;
                } else {
                    System.out.println("\n");
                }
                Collections.sort(ram, Comparator.comparing(Espacio -> Espacio.memLocation));
                for (Espacio pr : ram) {
                    if (pr.idProcess == -2) {
                        System.out.println("|-----------------------------------| -> 0\n"
                                + "|                                   |\n"
                                + "|         Sistema operativo         |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + opSysSize);

                    } else if (pr.idProcess == -1) {
                        System.out.println(
                                "|                                   |\n"
                                + "|               Libre               |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + (pr.memLocation + pr.freeSize));
                    } else if (pr.idProcess >= 0) {
                        String proceso = String.format("%18s", "P" + pr.idProcess);
                        System.out.println(
                                "|                                   |\n"
                                + "|" + proceso + "                 |\n"
                                + "|                                   |\n"
                                + "|-----------------------------------| -> " + (pr.memLocation + pr.freeSize));
                    }
                }
                System.out.println("\n--------------------------------------------\n");
                contWA++;
            }
            if (contPE == n) {
                for (int i = 0; i < n; i++) {
                    timeRaf[i] = timeRafDef[i];
                }
                break;
            }

        }
    }

    private static void inicioPrograma() {
        System.out.print("Inserte el tamaño de la memoria: ");
        memSize = ent.nextInt();
        System.out.print("Inserte el tamaño del sistema operativo: ");
        opSysSize = ent.nextInt();
        System.out.print("Inserte el número de procesos: ");
        n = ent.nextInt();
        sizes = new int[n];
        timeRaf = new int[n];
        timeRafDef = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Inserte el tamaño del proceso " + i + ": ");
            sizes[i] = ent.nextInt();
            System.out.print("Inserte el tiempo ráfaga del proceso " + i + ": ");
            timeRaf[i] = ent.nextInt();
        }
        for (int i = 0; i < n; i++) {
            timeRafDef[i] = timeRaf[i];
        }
    }
}
