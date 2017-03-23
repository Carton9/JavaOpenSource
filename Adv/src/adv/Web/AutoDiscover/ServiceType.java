package adv.Web.AutoDiscover;

public enum ServiceType {
	Host(0),Cilent(1);
    private final int id;
    
	ServiceType(int id) { 
        this.id = id; 
    }
     
    public int getValue() { 
        return id; 
    }
}
