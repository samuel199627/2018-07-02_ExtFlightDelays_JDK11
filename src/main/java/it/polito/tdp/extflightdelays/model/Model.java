package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	Map<Integer,Airport> aereoporti;
	ExtFlightDelaysDAO dao;
	SimpleWeightedGraph<Airport,DefaultWeightedEdge> grafo=null;
	List<Adiacenza> connessioni;
	
	public Model() {
		dao= new ExtFlightDelaysDAO();
	}
	
	
	public String creaGrafo(double distance) {
		String ritornare="GRAFO CREATO: \n\n";
		//mi importo tutti gli aereoporti che saranno i vertici
		aereoporti=new HashMap<>();
		aereoporti=dao.loadAllAirports();
		
		grafo=new SimpleWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, aereoporti.values());
		
		connessioni=new ArrayList<>();
		connessioni=dao.loadAllAdiacenze(aereoporti);
		
		for(Adiacenza a: connessioni) {
			//se ho gia' creato l'arco tra i due aereoporti niente, altrimenti vado a cercare la coppia opposta
			if(!grafo.containsEdge(a.getA1(), a.getA2())) {
				for(Adiacenza a2: connessioni) {
					//contengo i vertici ma al contrario
					if(a.getA1().equals(a2.getA2())&&a.getA2().equals(a2.getA1())) {
						int totaleVoli=0;
						double totaleDistanza=0;
						totaleVoli=a.getTotVoli()+a2.getTotVoli();
						totaleDistanza=a.getTotDist()+a2.getTotDist();
						
						double distanzaMedia= ((double) totaleDistanza)/((double) totaleVoli);
						if(distanzaMedia>distance) {
							//sono sopra la distanza richiesta quindi inserisco
							Graphs.addEdge(grafo, a.getA1(), a.getA2(), distanzaMedia);
						}
						
					}
				}
			}
		}
		ritornare="con vertici "+grafo.vertexSet().size()+" e archi "+grafo.edgeSet().size()+"\n";
		
		return ritornare;
		
	}
	
	public List<Airport> estrarreVetici(){
		List<Airport> vertici=new ArrayList<>();
		for(Airport a: grafo.vertexSet()) {
			vertici.add(a);
		}
		return vertici;
	} 
	
	public String estrarreAdiacenti(Airport a){
		String daRit="I VICINI SONO: \n";
		List<Airport> adiacenti=new ArrayList<>();
		adiacenti=Graphs.neighborListOf(grafo, a);
		List<AirportDistance> ritornare=new ArrayList<>();
		
		for(Airport ar: adiacenti) {
			ritornare.add(new AirportDistance(ar,grafo.getEdgeWeight(grafo.getEdge(a, ar))));
		}
		ritornare.sort(null);
		for(AirportDistance ar: ritornare) {
			daRit=daRit+" "+ar.getA().toString()+" con distanza "+ar.getDistanza()+"\n";
		}
		
		return daRit;
	}
	
	Airport partenza;
	double migliaDisponibili;
	List<AirportDistance> soluzione;
	int massimaLunghezza;
	
	public String itinerario(Airport a, double migliaDisponibili) {
		
		partenza=a;
		massimaLunghezza=0;
		this.migliaDisponibili=migliaDisponibili;
		soluzione=new ArrayList<>();
		List<AirportDistance> parziale=new ArrayList<>();
		parziale.add(new AirportDistance(partenza,0));
		ricorsione(parziale,0);
		String ritornare="TROVATO PERCORSO ITINERARIO CON: \n\n";
		ritornare=ritornare+partenza.toString()+"\n";
		for(AirportDistance ar: soluzione) {
			if(!ar.getA().equals(partenza)) {
				ritornare=ritornare+ar.getA().toString()+" con distanza "+ar.getDistanza()+"\n";
			}
			
		}
		return ritornare;
	}
	
	public void ricorsione(List<AirportDistance> parziale, int livello) {
		
		//ogni volta che ho la soluzione parziale con piu' vertici di quelli che avevo inserito fino ad ora aggiorno
		if(parziale.size()>massimaLunghezza) {
			soluzione=new ArrayList<>(parziale);
		}
		
		//dall'ultimo che ho inserito cerco tutti i vicini e li scorro
		List<Airport> adiacenti=new ArrayList<>();
		adiacenti=Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1).getA());
		for(Airport a: adiacenti) {
			if(!contiene(parziale,a)) {
				//aggiungo a parziale l'aereoporto con la distanza se non e' gia' in parziale in quanto devo evitare cicli
				parziale.add(new AirportDistance(a,grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1).getA(), a))));
				//se non sforo la distanza massima, allora proseguo, altrimenti tolgo l'ultimo inserito direttamente
				if(calcolaDistanza(parziale)<=migliaDisponibili) {
					ricorsione(parziale,livello+1);
				}
				parziale.remove(parziale.size()-1);
			}
			
		}
		
	}
	
	public boolean contiene(List<AirportDistance> lista,Airport a) {
		
		for(AirportDistance ad: lista) {
			if(ad.getA().equals(a)) {
				return true;
			}
		}
		
		return false;
	}
	
	public double calcolaDistanza(List<AirportDistance> lista) {
		
		double distanza=0.0;
		for(AirportDistance a:lista) {
			distanza=distanza+a.getDistanza();
		}
		
		return distanza;
		
	}

}
