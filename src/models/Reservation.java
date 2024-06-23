package models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Employee employee;
    private List<Service> services;
    private double reservationPrice;
    private String workstage;
    //   workStage (In Process, Finish, Canceled)

    public Reservation(String reservationId, Customer customer, Employee employee, List<Service> services,
            String workstage) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.employee = employee;
        this.services = services;
        this.workstage = workstage;
        calculateReservationPrice();
    };

    public void calculateReservationPrice() {
        String member = customer.getMember().getMembershipName();
        double price = 0;
        
        for(Service s: services) {
        	price += s.getPrice();
        }
        
        switch (member) {
	        case "Silver":
	            price -= price * 0.05;
	            break;
	        case "Gold":
	            price -= price * 0.1;
	            break;
	        default:
	            // No discount for non-members or other membership levels
	            break;
	    }
        
        this.reservationPrice = price;
    }
}
