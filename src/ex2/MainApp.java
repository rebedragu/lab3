package ex2;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

class Produs {
    private String denumire;
    private double pret;
    private int cantitate;
    private LocalDate dataExpirare;

    public Produs(String denumire, double pret, int cantitate, LocalDate dataExpirare) {
        this.denumire = denumire;
        this.pret = pret;
        this.cantitate = cantitate;
        this.dataExpirare = dataExpirare;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public LocalDate getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(LocalDate dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", cantitate=" + cantitate +
                ", dataExpirare=" + dataExpirare +
                '}';
    }
}

public class MainApp {
    public static void main(String[] args) {
        List<Produs> produse = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/ex2/produse.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 4) {
                    String denumire = tokens[0];
                    double pret = Double.parseDouble(tokens[1]);
                    int cantitate = Integer.parseInt(tokens[2]);
                    LocalDate dataExpirare = LocalDate.parse(tokens[3]);
                    Produs produs = new Produs(denumire, pret, cantitate, dataExpirare);
                    produse.add(produs);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int incasari = 0;

        while (true) {
            System.out.println("Meniu:");
            System.out.println("1. Afișare produse");
            System.out.println("2. Afișare produse expirate");
            System.out.println("3. Vânzare produs");
            System.out.println("4. Afișare produse cu preț minim");
            System.out.println("5. Salvare produse cu cantitate mică în fișier");
            System.out.println("0. Ieșire");

            int option;
            try {

                option = Integer.parseInt(br.readLine());
            } catch (IOException | NumberFormatException e) {
                option = -1;
            }

            switch (option) {
                case 1:
                    System.out.println("Produse disponibile:");
                    for (Produs produs : produse) {
                        System.out.println(produs);
                    }
                    break;

                case 2:
                    System.out.println("Produse expirate:");
                    LocalDate currentDate = LocalDate.now();
                    for (Produs produs : produse) {
                        if (produs.getDataExpirare().isBefore(currentDate)) {
                            System.out.println(produs);
                        }
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Introduceți denumirea produsului de vândut: ");
                        String denumireVanduta = br.readLine();
                        for (Produs produs : produse) {
                            if (produs.getDenumire().equals(denumireVanduta) && produs.getCantitate() > 0) {
                                System.out.print("Introduceți cantitatea de vândut: ");
                                int cantitateVanduta = Integer.parseInt(br.readLine());
                                if (cantitateVanduta <= produs.getCantitate()) {
                                    produs.setCantitate(produs.getCantitate() - cantitateVanduta);
                                    incasari += cantitateVanduta * produs.getPret();
                                    System.out.println("Produs vândut cu succes!");
                                    if (produs.getCantitate() == 0) {
                                        produse.remove(produs);
                                    }
                                } else {
                                    System.out.println("Nu există suficientă cantitate pe stoc.");
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    double pretMinim = Double.MAX_VALUE;
                    System.out.println("Produse cu preț minim:");
                    for (Produs produs : produse) {
                        if (produs.getPret() < pretMinim) {
                            pretMinim = produs.getPret();
                        }
                    }
                    for (Produs produs : produse) {
                        if (produs.getPret() == pretMinim) {
                            System.out.println(produs);
                        }
                    }
                    break;

                case 5:
                    try{
                    System.out.print("Introduceți cantitatea minimă pentru salvare: ");
                    int cantitateMinima = Integer.parseInt(br.readLine());
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src/ex2/produse_cantitate_mica.txt"));
                        for (Produs produs : produse) {
                            if (produs.getCantitate() < cantitateMinima) {
                                writer.write(produs.toString() + "\n");
                            }
                        }
                        writer.close();
                        System.out.println("Produsele cu cantitate mică au fost salvate în fișierul 'produse_cantitate_mica.txt'.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 0:
                    System.out.println("incasari totale: " + incasari);
                    return;

                default:
                    System.out.println("Optiune invalida. Vă rugam să alegeți o optiune valida.");
            }
        }
    }
}