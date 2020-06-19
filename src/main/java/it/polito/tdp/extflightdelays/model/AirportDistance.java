package it.polito.tdp.extflightdelays.model;

public class AirportDistance implements Comparable<AirportDistance>{
	
	Airport a;
	Double distanza;
	
	public AirportDistance(Airport a, double distanza) {
		super();
		this.a = a;
		this.distanza = distanza;
	}

	public Airport getA() {
		return a;
	}

	public void setA(Airport a) {
		this.a = a;
	}

	public Double getDistanza() {
		return distanza;
	}

	public void setDistanza(Double distanza) {
		this.distanza = distanza;
	}

	@Override
	public int compareTo(AirportDistance o) {
		// TODO Auto-generated method stub
		return -this.getDistanza().compareTo(o.getDistanza());
	}
	
	

}
