package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== CLIENT CALCOLATRICE IN RETE ===");

        String serverIP = "10.22.10.8"; 
        int serverPorta = 3000;

        System.out.println("Tentativo di connessione al server su [" + serverIP + ":" + serverPorta + "]...");
        
        try (Socket socket = new Socket(serverIP, serverPorta);
             Scanner scanner = new Scanner(System.in);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Connesso con successo al server!");
            
            while (true) {
                System.out.println("\n--- MENU OPERAZIONI ---");
                System.out.println("1) Somma");
                System.out.println("2) Sottrazione");
                System.out.println("3) Moltiplicazione");
                System.out.println("4) Divisione");
                System.out.println("Digita 'exit' per uscire.");
                System.out.print("Scegli un'opzione : ");
                
                // 1. Chiediamo l'input dell'operazione (la stringa di scelta)
                String scelta = scanner.nextLine().trim();
                
                if ("exit".equalsIgnoreCase(scelta)) {
                    out.println("exit"); 
                    System.out.println("Chiusura in corso...");
                    break;
                }
              
                if (!scelta.equals("1") && !scelta.equals("2") && !scelta.equals("3") && !scelta.equals("4")) {
                    System.out.println("Opzione non valida! Scegli tra 1, 2, 3 o 4.");
                    continue;
                }
            
                System.out.print("Inserisci il primo numero: ");
                String num1 = scanner.nextLine().trim();
          
                System.out.print("Inserisci il secondo numero: ");
                String num2 = scanner.nextLine().trim();
                
                String stringaDaMandare = scelta + ";" + num1 + ";" + num2;
                
                out.println(stringaDaMandare);
                System.out.println("[INFO] Inviato al server: " + stringaDaMandare);
                
                String rispostaServer = in.readLine();
                
                if (rispostaServer == null) {
                    System.out.println("Il server ha chiuso improvvisamente la connessione.");
                    break;
                }
                
                System.out.println("\12n-------------------------------------");
                System.out.println("RISULTATO DAL SERVER > " + rispostaServer);
                System.out.println("-------------------------------------");
            }
            
        } catch (Exception e) {
            System.err.println("\n[ERRORE DI RETE]: Impossibile comunicare con il server.");
            System.err.println("Dettaglio errore: " + e.getMessage());
        }
        
        System.out.println("Client terminato.");
    }
}

