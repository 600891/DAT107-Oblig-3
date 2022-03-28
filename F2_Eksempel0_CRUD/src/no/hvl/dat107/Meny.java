package no.hvl.dat107;

import java.util.Scanner;

public class Meny {

	AnsattAvdelingDAO ansAvdDAO = new AnsattAvdelingDAO();
	Scanner sc = new Scanner(System.in);
	
	public Meny() {}
	
	public void visHovedMeny() {
        int input = 0;

        do {
            System.out.println("Velkommen");
            System.out.println("----------------------");
            System.out.println("(1) Søk etter en ansatt med ID");
            System.out.println("(2) Søk etter en ansatt med brukernavn");
            System.out.println("(3) List ut alle ansatte");
            System.out.println("(4) Legg til en ny ansatt");
            System.out.println("(5) Oppdatere en ansatt sin stilling");
            System.out.println("(6) Oppdatere en ansatt sin lønn");
            System.out.println("(7) Søk etter en avdeling med ID");
            System.out.println("(8) List ut alle ansatte på en avdeling");
            System.out.println("(9) Oppdatere avdelingen til en ansatt");
            System.out.println("(10) Legg til en ny avdeling");
            System.out.println("(11) Avslutt");
            System.out.println("----------------------");
            System.out.print("Vennligst oppgi ønsket menyvalg:");

            input = sc.nextInt();

            switch (input) {
                case 1:
                    finnAnsattMedIdSok();
                    break;
                case 2:
                    finnAnsattMedBrukernavnSok();
                    break;
                case 3:
                    ansAvdDAO.skrivUtAlleAnsatte();
                    enterTilbake();
                    break;
                case 4:
                    leggInnAnsatt();
                    break;
                case 5:
                    oppdaterAnsattStilling();
                    break;
                case 6:
                    oppdaterAnsattLonn();
                    break;
                case 7:
                    finnAvdelingMedIdSok();
                    break;
                case 8:
                    skrivUtAvdeling();
                    break;
                case 9:
                    oppdaterAvdeling();
                    break;
                case 10:
                    leggTilAvdeling();
                    break;
                default:
                    break;
            }

        } while (input != 11);

        System.exit(1);
    }
	
	public void finnAnsattMedIdSok () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID: ");
		int id = scanner.nextInt();
		Ansatt ansatt = ansAvdDAO.finnAnsattMedId(id);
		if (ansatt == null) {
			System.out.println("Brukeren med id " + id + "finnes ikke.");
		}
		System.out.println("Ansatt funnet!" + "\n" + ansatt.toString());
		enterTilbake();
	}
	
	public void finnAnsattMedBrukernavnSok () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn brukernavn: ");
		String brukernavn = scanner.nextLine().toUpperCase();
		Ansatt ansatt = ansAvdDAO.finnAnsattMedBrukernavn(brukernavn);
		if (ansatt == null) {
			System.out.println("Brukeren med brukernavn " + brukernavn + "finnes ikke.");
		}
		System.out.println("Ansatt funnet!" + "\n" + ansatt.toString());
		enterTilbake();
	}
	
	public void oppdaterAnsattLonn () {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Vennligst skriv inn ID-en til ansatte du vil oppdatere:");
		int id = sc.nextInt();
		
		System.out.println("Vennligst skriv inn ny lønn:");
		double lonn = scanner.nextDouble();
		
		ansAvdDAO.oppdaterLonn(id, lonn);
		System.out.println(ansAvdDAO.finnAnsattMedId(id));
		enterTilbake();
	}
	
	public void oppdaterAnsattStilling () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID-en til ansatte du vil oppdatere:");
		int id = sc.nextInt();
		System.out.println("Vennligst skriv inn ny stillingstittel:");
		String stilling = scanner.nextLine();
		ansAvdDAO.oppdaterStilling(id, stilling);
		System.out.println(ansAvdDAO.finnAnsattMedId(id));
		enterTilbake();
	}
	
	public void leggInnAnsatt () {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Starter prosessen med å lage ny ansatt. Vennligst skriv inn brukernavn (4 bokstaver):");
		String brukernavn = scanner.nextLine();
		
		System.out.println("Vennligst skriv inn fornavn:");
		String fornavn = scanner.nextLine();
		
		System.out.println("Vennligst skriv inn etternavn:");
		String etternavn = scanner.nextLine();
		
		System.out.println("Vennligst skriv inn ansettelsesdato (ÅÅÅÅ-MM-DD):");
		String ansDato = scanner.nextLine();
		
		System.out.println("Vennligst skriv inn stillingstittel:");
		String stilling = scanner.nextLine();
		
		System.out.println("Vennligst skriv inn lønn:");
		double lonn = scanner.nextDouble();
		
		System.out.println("Vennligst skriv inn avdelings-ID:");
		Avdeling avdeling = ansAvdDAO.finnAvdelingMedId(scanner.nextInt());
		
		Ansatt ansatt = ansAvdDAO.lagNyAnsatt(brukernavn, fornavn, etternavn, ansDato, stilling, lonn, avdeling);
		System.out.println("Ansatt lagt til! \n" + ansatt.toString());
		enterTilbake();
	}
	
	public void finnAvdelingMedIdSok() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID til avdelingen: ");
		int id = scanner.nextInt();
		if(ansAvdDAO.finnAvdelingMedId(id) != null) {
			System.out.println("Avdeling funnet! Avdelingen er: " + ansAvdDAO.finnAvdelingMedId(id).toString());
		} else {
			System.out.println("Klarte ikke å finne avdelingen.");
		}
		enterTilbake();
	}
	
	public void skrivUtAvdeling() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID til avdelingen du vil skrive ut: ");
		int id = scanner.nextInt();
		ansAvdDAO.skrivUtAlleAnsatteIAvdeling(id);
		enterTilbake();
	}
	
	public void oppdaterAvdeling() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID til den ansatte du vil oppdatere avdelingen til: ");
		int id = scanner.nextInt();
		Ansatt ansatt = ansAvdDAO.finnAnsattMedId(id);
		
		while(ansatt.erSjef()) {
			System.out.println("Kan ikke endre avdelingen til " + ansatt.getFornavn() + ". Sjefer kan ikke endre avdeling! Prøv igjen: ");
			ansatt = ansAvdDAO.finnAnsattMedId(scanner.nextInt());
		}
		System.out.println("Hvilken avdeling vil du flytte den ansatte til? (ID)");
		Avdeling avd = ansAvdDAO.finnAvdelingMedId(scanner.nextInt());
		ansAvdDAO.oppdaterAvdeling(ansatt.getAnsatt_id(), avd);
		
		System.out.println("Avdelingen er oppdatert.");
		enterTilbake();
	}
	
	public void leggTilAvdeling() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn navnet til den nye avdelingen: ");
		String navn = scanner.nextLine();
		System.out.println("Vennligst skriv inn ID-nummeret til den ansatte som skal bli sjef: ");
		Ansatt ansatt = ansAvdDAO.finnAnsattMedId(scanner.nextInt());
		while(ansatt.erSjef()) {
			System.out.println("En sjef kan ikke bytte avdeling! Prøv igjen: ");
			ansatt = ansAvdDAO.finnAnsattMedId(scanner.nextInt());
		}
		ansAvdDAO.lagNyAvdeling(navn, ansatt);
		System.out.println("Avdelingen ble lagt til.");
		enterTilbake();
	}
	
	 private void enterTilbake()
	 { 
	        System.out.println("Trykk Enter for å komme tilbake til menyen...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }
}
