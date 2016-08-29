package petdb.registration;

import petdb.registration.*;
import javax.mail.*;



public class test{

	public static void main(String[] a) 
	{
		petdb.registration.Emailer em = new petdb.registration.Emailer();
                try {
			em.sendEmail("petdb@ciesin.columbia.edu",
                            	"ncelo@ciesin.columbia.edu",
                            	"petdb@ciesin.columbia.edu",
         	             	"Testing Email confirmation",
   	                     	"Thank you for registering");

		}
		catch (Exception e ) { System.out.println("Error = " + e.getMessage()); }
	}
}



