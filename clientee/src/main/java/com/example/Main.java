package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

 // =========================================================================
        // CONFIGURAZIONE RETE:
        // Se il server è sullo stesso PC, lascia "localhost".
        // Se il server è su UN ALTRO PC, metti l'IP di quel PC (es: "192.168.1.45")
        // =========================================================================
        String serverIP = "localhost"; 
        int serverPorta = 3000;

        System.out.println("Tentativo di connessione al server su [" + serverIP + ":" + serverPorta + "]...");
        
        // Apriamo il socket e gli stream di comunicazione
        try (Socket socket = new Socket(serverIP, serverPorta);
             Scanner scanner = new Scanner(System.in);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("Connesso con successo al server!");
            System.out.println("Scrivi un testo per riceverlo in MAIUSCOLO. Digita 'exit' per uscire.");
            
            // Ciclo continuo di invio e ricezione
            while (true) {
                System.out.print("\nTu > ");
                String userUtente= scanner.nextLine();
                
                // Inviamo la stringa digitata al server tramite la rete
                out.println(userUtente);
                
                // Se l'utente decide di uscire, interrompiamo il ciclo locale
                if ("exit".equalsIgnoreCase(userUtente.trim())) {
                    System.out.println("Chiusura della connessione in corso...");
                    break;
                }
                
                // Restiamo in attesa della risposta elaborata dal server
                String rispostaServer = in.readLine();
                
                if (rispostaServer == null) {
                    System.out.println("Il server ha chiuso improvvisamente la connessione.");
                    break;
                }
                
                System.out.println("Server > " + rispostaServer);
            }
            
        } catch (Exception e) {
            System.err.println("\n[ERRORE DI RETE]: Impossibile comunicare con il server.");
            System.err.println("Dettaglio errore: " + e.getMessage());
            System.err.println("Verifica che il Server sia attivo e che il Firewall non blocchi la porta " + serverPorta);
        }
        
        System.out.println("Client terminato.");



        
    }
}

