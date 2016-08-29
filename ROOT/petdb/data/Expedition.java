package petdb.data;


public class Expedition{    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameUrl() {
		return nameUrl;
	}
	public void setNameUrl(String nameUrl) {
		this.nameUrl = nameUrl;
	}
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	public String getShipUrl() {
		return shipUrl;
	}
	public void setShipUrl(String shipUrl) {
		this.shipUrl = shipUrl;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getChiefScientists() {
		return chiefScientists;
	}
	public void setChiefScientists(String chiefScientists) {
		this.chiefScientists = chiefScientists;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
    public String getInstitutionUrl() {
		return institutionUrl;
	}
	public void setInstitutionUrl(String institutionUrl) {
		this.institutionUrl = institutionUrl;
	}
	private String name; 
    private String nameUrl; 
    private String ship; 
    private String shipUrl; 
    private String date; 
    private String chiefScientists; 
    private String institution; 
    private String institutionUrl; 
   
}
