package service;

import java.util.List;
import java.util.Scanner;

import models.*;

public class ValidationService {
    public static String validateInput(String question, String errorMessage, String regex) {
	    Scanner input = new Scanner(System.in);
	    String result;
	    boolean isLooping = true;
	    do {
	      System.out.print(question);
	      result = input.nextLine();
	
	      //validasi menggunakan matches
	      if (result.matches(regex)) {
	        isLooping = false;
	      }else {
	        System.out.println(errorMessage);
	      }
	
	    } while (isLooping);
	
	    return result;
    }

    public static Customer validateCustomerId(List<Person> personList, String id){
    	for(Person p: personList) {
    		if(p.getId().equalsIgnoreCase(id)) {
    			if(p instanceof Customer) {
    				return ((Customer) p);
    			} 
    		}
    	}
    	return null;
    }
    
    public static Employee validateEmployeeId(List<Person> personList, String id){
    	for(Person p: personList) {
    		if(p.getId().equalsIgnoreCase(id)) {
    			if(p instanceof Employee) {
    				return ((Employee) p);
    			} 
    		}
    	}
    	return null;
    }
    
    public static Service validateServiceId(List<Service> serviceList, String id){
    	for(Service p: serviceList) {
    		if(p.getServiceId().equalsIgnoreCase(id)) {
    			return p;
    		}
    	}
    	return null;
    }
}
