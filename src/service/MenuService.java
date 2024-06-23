package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.*;
import repositories.*;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = {"Show Data", "Create Reservation", "Complete/cancel reservation", "Exit"};
        String[] subMenuArr = {"Recent Reservation", "Show Customer", "Show Available Employee", "Show History Reservation", "Back to main menu"};
    
        int optionMainMenu;
        int optionSubMenu;

		boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        switch (optionSubMenu) {
                            case 1:
                                PrintService.showRecentReservation(reservationList);
                                break;
                            case 2:
                            	PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                            	PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                            	PrintService.showHistoryReservation(reservationList);
                                break;
                            case 0:
                                backToSubMenu = true;
                                break;
                            default :
                            	System.out.println("Input salah, masukan input yang benar !");
                            	break;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    Reservation reservation = ReservationService.createReservation(input, personList, serviceList, reservationList);
                    reservation.calculateReservationPrice();
                    reservationList.add(reservation);
                    break;
                case 3:
                	ReservationService.editReservationWorkstage(input, reservationList);
                    break;
                case 0:
                    backToMainMenu = true;
                    break;
                default :
                	System.out.println("Input salah, masukan input yang benar !");
                	break;
            }
        } while (!backToMainMenu);
		
	}
}
