package com.esgi.donjons;

import com.esgi.donjons.quete.dao.QueteDAO;
import com.esgi.donjons.quete.dao.QueteDAOImpl;
import com.esgi.donjons.quete.dto.QueteResponseDTO;
import com.esgi.donjons.quete.seeder.QueteSeeder;
import com.esgi.donjons.quete.service.QueteService;
import com.esgi.donjons.quete.service.QueteServiceImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        QueteDAO queteDAO = new QueteDAOImpl(connection);
        QueteService queteService = new QueteServiceImpl(queteDAO);
        QueteSeeder queteSeeder = new QueteSeeder(queteDAO);

        queteSeeder.seed();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Le Grand Registre de la Guilde ===");
            System.out.println("1 - Lister toutes les quêtes");
            System.out.println("2 - Lister les quêtes actives");
            System.out.println("3 - Voir une quête par ID");
            System.out.println("0 - Quitter");
            System.out.print("Ton choix : ");

            while (!scanner.hasNextInt()) {
                System.out.print("Veuillez entrer un nombre valide : ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    List<QueteResponseDTO> quetes = queteService.getAllQuetes();
                    if (quetes.isEmpty()) {
                        System.out.println("Aucune quête trouvée.");
                    } else {
                        quetes.forEach(System.out::println);
                    }
                }
                case 2 -> {
                    List<QueteResponseDTO> quetes = queteService.getQuetesActives();
                    if (quetes.isEmpty()) {
                        System.out.println("Aucune quête active trouvée.");
                    } else {
                        quetes.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("Entrez l'UUID de la quête : ");
                    String idStr = scanner.nextLine();
                    try {
                        UUID id = UUID.fromString(idStr);
                        System.out.println(queteService.getQueteById(id));
                    } catch (IllegalArgumentException e) {
                        System.out.println("UUID invalide.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case 0 -> System.out.println("Fermeture du registre...");
                default -> System.out.println("Choix invalide.");
            }
        } while (choice != 0);

        scanner.close();
    }
}