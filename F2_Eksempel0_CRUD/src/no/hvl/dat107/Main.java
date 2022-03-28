package no.hvl.dat107;

import java.util.List;
import java.sql.Date;

public class Main {
	
	private static AnsattAvdelingDAO ansAvdDAO = new AnsattAvdelingDAO();
	
	public static void main(String[] args) {
		
		Meny meny = new Meny();
		
		meny.visHovedMeny();
		
		//Create - Opprette ny(e) rad(er) i databasen
//		ansattHjelper.createAnsatt(new Ansatt("DD", "Donald", "Duck", "2020-10-10", "Onkel", 10000, 2));
//		skrivUt("Har lagt til Donald Duck");
		
		
		
		
		
//		//Read - Hente ut data fra databasen
//		//Hopper over denne
//		
//		//Update - Oppdatere data i databasen
//		dbhjelper.updateAnsatt(1004, "Mikke Mus");
//		skrivUt("Har endret navn til Mikke Mus");
//		
//		//Delete - Slette rad(er) fra databasen
//		dbhjelper.deleteAnsatt(1001);
//		skrivUt("Har slettet person med id 1001");
//		
//		//Tilbakestille til utgangspunkt
//		dbhjelper.createAnsatt(new Person(1001, "Per Viskeler"));
//		dbhjelper.deleteAnsatt(1004);
//		skrivUt("Har tilbakestilt db");
	}
}
