package service;

import java.util.List;

import models.*;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public static String printServices(List<Service> serviceList){
        String result = "";
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }
    
    public static void showAllServices(List<Service> serviceList){
        int num = 1;
        System.out.println("+======================================================================+");
    	System.out.printf("| %-4s | %-7s | %-33s | %-15s |\n",
                "No.", "ID", "Nama Service", "Harga");
        System.out.println("+======================================================================+");
        for (Service service : serviceList) {
        	System.out.printf("| %-4s | %-7s | %-33s | %-15s |\n",
        			num, service.getServiceId(), service.getServiceName(), service.getPrice());
        	num++;
        }
        System.out.println("+======================================================================+");
    }

    public static void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.println("+========================================================================================================================================+");
        System.out.printf("| %-4s | %-7s | %-15s | %-50s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-7s | %-15s | %-50s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        System.out.println("+========================================================================================================================================+");
    }

	public static void showAllCustomer(List<Person> personList) {
        int num = 1;
        String member;
        System.out.println("+======================================================================+");
        System.out.printf("| %-4s | %-7s | %-15s | %-15s | %-15s |\n",
                "No.", "ID", "Nama Customer", "Alamat", "Membership");
        System.out.println("+======================================================================+");
        for (Person p : personList) {
        	if(p instanceof Customer) {
        		member = ((Customer) p).getMember().getMembershipName();
        		System.out.printf("| %-4s | %-7s | %-15s | %-15s | %-15s |\n",
        		num, p.getId(), p.getName(), p.getAddress(), member);
        		num++;
        	}
        }
        System.out.println("+======================================================================+");
	}
	
    public static void showAllEmployee(List<Person> personList){
        int num = 1;
        int exp;
        System.out.println("+======================================================================+");
        System.out.printf("| %-4s | %-7s | %-15s | %-15s | %-15s |\n",
                "No.", "ID", "Nama Customer", "Alamat", "Pengalaman");
        System.out.println("+======================================================================+");
        for (Person p : personList) {
        	if(p instanceof Employee) {
        		exp = ((Employee) p).getExperience();
        		System.out.printf("| %-4s | %-7s | %-15s | %-15s | %-15s |\n",
        		num, p.getId(), p.getName(), p.getAddress(), exp);
        		num++;
        	}
        }
        System.out.println("+======================================================================+");
    }

    public static void showHistoryReservation(List<Reservation> reservationList){
        int num = 1;
        double total = 0;
        System.out.println("+========================================================================================================================================+");
        System.out.printf("| %-4s | %-7s | %-15s | %-50s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish") || reservation.getWorkstage().equalsIgnoreCase("Cancel")) {
                System.out.printf("| %-4s | %-7s | %-15s | %-50s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
                total += reservation.getReservationPrice();
            }
        }
        System.out.println("+========================================================================================================================================+");
        System.out.printf("| %-85s | %-46s |\n", "Total Biaya", total);
        System.out.println("+========================================================================================================================================+");
        
    }

}
