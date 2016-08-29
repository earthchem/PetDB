package petdb.data; 

public class PetdbUtil 
{ 
    
    public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    }
    return true;
}
} 
