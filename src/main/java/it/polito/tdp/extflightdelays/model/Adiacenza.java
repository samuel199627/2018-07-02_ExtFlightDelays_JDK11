package it.polito.tdp.extflightdelays.model;

public class Adiacenza {
	
	Airport a1;
	Airport a2;
	double totDist;
	int totVoli;
	
	public Adiacenza(Airport a1, Airport a2, double totDist, int totVoli) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.totDist = totDist;
		this.totVoli = totVoli;
	}

	public Airport getA1() {
		return a1;
	}

	public void setA1(Airport a1) {
		this.a1 = a1;
	}

	public Airport getA2() {
		return a2;
	}

	public void setA2(Airport a2) {
		this.a2 = a2;
	}

	public double getTotDist() {
		return totDist;
	}

	public void setTotDist(double totDist) {
		this.totDist = totDist;
	}

	public int getTotVoli() {
		return totVoli;
	}

	public void setTotVoli(int totVoli) {
		this.totVoli = totVoli;
	}
	
	

}
