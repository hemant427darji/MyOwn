package com.jsp.hibernate.actor_movies;

import com.jsp.hibernate.actor_movies.dao.ActorDao;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        ActorDao actorDao = new ActorDao();
        Scanner sc = new Scanner(System.in);
        boolean run = true;

        while (run) {
            System.out.println("\n=== Actor Management Menu ===");
            System.out.println("1. Add Actor");
            System.out.println("2. Find Actor by ID");
            System.out.println("3. Find Actor by Name (HQL)");
            System.out.println("4. Find Actors by Industry (SQL)");
            System.out.println("5. Find Actors Between Age (CriteriaBuilder)");
            System.out.println("6. Find Actors With Age Greater Than (HQL)");
            System.out.println("7. Update Actor Age by ID (CriteriaBuilder)");
            System.out.println("8. Update Actor Nationality by Industry (HQL)");
            System.out.println("9. Delete Actor by ID (CriteriaBuilder)");
            System.out.println("10. Find Actors by Movie Title (SQL)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    actorDao.addActor();
                    break;
                case 2:
                    actorDao.findActorById();
                    break;
                case 3:
                    actorDao.findActorByName();
                    break;
                case 4:
                    actorDao.findActorsByIndustry();
                    break;
                case 5:
                    actorDao.findActorsBetweenAge();
                    break;
                case 6:
                    actorDao.findActorsWithAgeGreaterThan();
                    break;
                case 7:
                    actorDao.updateActorAgeById();
                    break;
                case 8:
                    actorDao.updateActorNationalityByIndustry();
                    break;
                case 9:
                    actorDao.deleteActorById();
                    break;
                case 10:
                    actorDao.findActorsByMoviesTitle();
                    break;
                case 0:
                    run = false;
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}
