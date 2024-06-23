package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.*;

public class ReservationService {
    public static Reservation createReservation(Scanner input, List<Person> personList, List<Service> serviceList, List<Reservation> reservationList){
    	// validation id
    	int size = reservationList.size(); 
    	String id = null;
    	
    	if(size >= 10) {
    		id = "Rsv-";
    	} else {
    		id = "Rsv-0";
    	}
    	
    	// validation customer
    	PrintService.showAllCustomer(personList);
    	Customer cus = getCustomerByCustomerId(input, personList);
    	
    	// validation employee
    	PrintService.showAllEmployee(personList);
    	Employee emp = getEmployeeByEmployeeId(input, personList);
    	
    	// validation service
    	PrintService.showAllServices(serviceList);
    	List<Service> serv = new ArrayList<>();

    	Service servs;
    	String service;
    	String services;
    	
    	while(true) {
    		service = ValidationService.validateInput("Silahkan Masukan Service Id : ", "ID tidak dikenali!", "Serv-0\\d+");
    		servs = ValidationService.validateServiceId(serviceList, service);

			if(servs!=null) {
				serv.add(servs);
				while(true) {
					System.out.println("Ingin pilih service yang lain (Y/T)?");
					String inp = input.nextLine();
					
					if(inp.equalsIgnoreCase("Y")) {
			    		services = ValidationService.validateInput("Silahkan Masukan Service Id : ", "ID tidak dikenali!", "Serv-0\\d+");
			    		servs = ValidationService.validateServiceId(serviceList, services);
			    		if(servs!=null) {
			    			serv.add(servs);
			    		} else {
			    			System.out.println("ID tidak ditemukan!");
			    		}
					} else if(inp.equalsIgnoreCase("T")) {
						System.out.println("Booking Berhasil");
						break;
					}
				}
				break;
			} else {
				System.out.println("ID tidak ditemukan!");
			}
    	}
    	
    	// validation workstage
    	String work = null;
    	boolean inProcessFound = false;
    	
    	for(Reservation r : reservationList) {
    		if(r.getEmployee().equals(emp)) {
    			if(r.getWorkstage().equals("In process")) {
    				inProcessFound = true;
    			}
    		} 
    	}
    	
    	if (inProcessFound) {
    	    work = "Waiting";
    	} else {
    	    work = "In process";
    	}
    	
    	//build data
    	Reservation reservation = Reservation.builder()
    			.reservationId(id+(size+1))
    			.customer(cus)
    			.employee(emp)
    			.services(serv)
    			.workstage(work)
    			.build();
    	
    	return reservation;
    }

    public static Customer getCustomerByCustomerId(Scanner input, List<Person> personList){
    	// validation customer
    	Customer cus = null;
    	boolean kosong = true;
    	String customer;
    	
    	do {
    		customer = ValidationService.validateInput("Silahkan Masukan Customer Id : ", "ID tidak dikenali!", "Cust-0\\d+");
			cus = ValidationService.validateCustomerId(personList, customer);
			if(cus!=null) {
				kosong = false;
			} else {
				System.out.println("ID tidak ditemukan!");
			}
    	} while(kosong);
    	
    	return cus;
    }
    
    public static Employee getEmployeeByEmployeeId(Scanner input, List<Person> personList){
    	// validation Employee
    	Employee emp = null;
    	boolean kosong = true;
    	String employee;
    	
    	do {
    		employee = ValidationService.validateInput("Silahkan Masukan Employee Id : ", "ID tidak dikenali!", "Emp-0\\d+");
			emp = ValidationService.validateEmployeeId(personList, employee);
			if(emp!=null) {
				kosong = false;
			} else {
				System.out.println("ID tidak ditemukan!");
			}
    	} while(kosong);
    	
    	return emp;
    }

    public static Reservation editReservationWorkstage(Scanner input, List<Reservation> reservationList){
    	PrintService.showRecentReservation(reservationList);
        System.out.println("0. Back to Menu");
    	
    	if(reservationList.isEmpty() || allReservationsFinished(reservationList)) {
    		System.out.println("Tidak ada reservation saat ini");
    	} else {    		
    		while(true) {    		
    			System.out.print("Silahkan Masukan Reservation ID : ");
    			String id = input.nextLine();
    			
    			if(id.startsWith("Rsv-")) {
    				Reservation currentReservation = null;
    				for(Reservation r : reservationList) {
    					if(r.getReservationId().equalsIgnoreCase(id)) { 
    						if(r.getWorkstage().equalsIgnoreCase("Waiting")) {
    							while(true) {    				
    								System.out.print("Apakah anda ingin membatalkan reservasi ini (Cancel only) (Y/T) : ");
    								String end = input.nextLine();
    								
    								if(end.equalsIgnoreCase("Y")) {
    									r.setReservationPrice(0);
    									r.setWorkstage("Cancel");
    									System.out.println("Reservasi dibatalkan!");
    									break;
    								} else if(end.equalsIgnoreCase("T")) {
    									System.out.println("Reservasi tidak jadi dibatalkan!");
    									break;
    								} else {
    									System.out.println("Masukan input yang sesuai");
    								}
    							}
    							break;
    						} else if(r.getWorkstage().equalsIgnoreCase("Finish") || r.getWorkstage().equalsIgnoreCase("Cancel")) {
    							System.out.println("Reservation already finished !");
    							break;
    						} else if(r.getWorkstage().equalsIgnoreCase("In process")){    							
    							currentReservation = r;
    							
    							while(true) {    				
    								System.out.print("Selesaikan Reservasi (Finish / Cancel) : ");
    								String end = input.nextLine();
    								
    								if(end.equalsIgnoreCase("Finish")) {
    									r.setWorkstage(end);
    									System.out.println("Reservasi selesai!");
    									break;
    								} else if(end.equalsIgnoreCase("Cancel")) {
    									System.out.println("Reservasi dibatalkan!");
    									r.setReservationPrice(0);
    									r.setWorkstage(end);
    									break;
    								} else if(end.equalsIgnoreCase("0")) {
    									System.out.println("Mengakhiri reservasi dibatalkan!");
    									break;
    								} else {
    									System.out.println("Masukan input yang sesuai");
    								}
    							}
    						}
    					}
    				}
    				if (currentReservation != null) {
                        Employee employee = currentReservation.getEmployee();

                        for (Reservation r : reservationList) {
                            if (r.getEmployee().equals(employee) && !r.equals(currentReservation) && r.getWorkstage().equalsIgnoreCase("Waiting")) {
                                r.setWorkstage("In Process");
                                break; 
                            }
                        }
                    }
    				break;
    			} else if(id.equals("0")) {
    				System.out.println("Mengakhiri reservasi dibatalkan !");
    				break;
    			} else {
    				System.out.println("Masukan id yang benar");
    			}
    		}
    	}
    	
		return null;
    }

    private static boolean allReservationsFinished(List<Reservation> reservationList) {
        for (Reservation r : reservationList) {
            if (!r.getWorkstage().equalsIgnoreCase("Finish")) {
                return false;
            }
        }
        return true;
    }
}
