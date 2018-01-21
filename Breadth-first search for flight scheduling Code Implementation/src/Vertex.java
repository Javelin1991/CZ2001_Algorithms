

public class Vertex {

	private final String city;
	private boolean marked;
	
	
	public Vertex(String city) {
		this.city = city;
		this.marked = false;
	}
	
	public String getCity () {
		return this.city;
	}
	
	public boolean getMarkedStatus() {
		return this.marked;
	}
	
	public void mark() { 
		this.marked = true; 
	}
	public void unMark() {
		this.marked = false;
	}
	
	 
	

}
