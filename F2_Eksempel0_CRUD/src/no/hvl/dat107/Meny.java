package no.hvl.dat107;

import java.util.Scanner;

public class Meny {
	
	AnsattDAO ansDAO = new AnsattDAO();
	AvdelingDAO avdDAO = new AvdelingDAO();
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
                    ansDAO.skrivUtAlleAnsatte();
                    break;
                case 4:
                    leggInnAnsatt();
                    break;
                case 5:
                    updateAnsattStilling();
                    break;
                case 6:
                    updateAnsattLonn();
                    break;
                case 7:
                    finnAvdelingMedIdSok();
                    break;
                case 8:
                    printAvdeling();
                    break;
                case 9:
                    oppdaterAvdeling();
                    break;
                case 10:
                    updateAnsattLonn();
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
		Ansatt ansatt = ansDAO.finnAnsattMedId(id);
		if (ansatt == null) {
			System.out.println("Brukeren med id " + id + "finnes ikke.");
		}
		System.out.println("Ansatt funnet!" + "\n" + ansatt.toString());
	}
	
	public void finnAnsattMedBrukernavnSok () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn brukernavn: ");
		String brukernavn = scanner.nextLine();
		Ansatt ansatt = ansDAO.finnAnsattMedBrukernavn(brukernavn);
		if (ansatt == null) {
			System.out.println("Brukeren med brukernavn " + brukernavn + "finnes ikke.");
		}
		System.out.println("Ansatt funnet!" + "\n" + ansatt.toString());
	}
	
	public void updateAnsattLonn () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID-en til ansatte du vil oppdatere:");
		int id = sc.nextInt();
		System.out.println("Vennligst skriv inn ny lønn:");
		double lonn = scanner.nextDouble();
		ansDAO.updateLonn(id, lonn);
		System.out.println(ansDAO.finnAnsattMedId(id));
	}
	
	public void updateAnsattStilling () {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID-en til ansatte du vil oppdatere:");
		int id = sc.nextInt();
		System.out.println("Vennligst skriv inn ny stillingstittel:");
		String stilling = scanner.nextLine();
		ansDAO.updateStilling(id, stilling);
		System.out.println(ansDAO.finnAnsattMedId(id));
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
		Avdeling avdeling = avdDAO.finnAvdelingMedId(scanner.nextInt());
		Ansatt ansatt = ansAvdDAO.createNewAnsatt(brukernavn, fornavn, etternavn, ansDato, stilling, lonn, avdeling);
		System.out.println(ansatt.toString());
	}
	
	public void finnAvdelingMedIdSok() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID til avdelingen: ");
		int id = scanner.nextInt();
		System.out.println("Avdeling funnet! Avdelingen er: " + avdDAO.finnAvdelingMedId(id).toString());
	}
	
	public void printAvdeling() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID til avdelingen du vil skrive ut: ");
		int id = scanner.nextInt();
		Avdeling avdeling = avdDAO.finnAvdelingMedId(id);
		avdeling.printAvd();
	}
	
	public void oppdaterAvdeling() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Vennligst skriv inn ID til den ansatte du vil oppdatere avdelingen til: ");
		int id = scanner.nextInt();
		Ansatt ansatt = ansDAO.finnAnsattMedId(id);
		if(ansatt.getAvd().getSjef() == ansatt) {
			System.out.println("Kan ikke endre avdelingen til " + ansatt.getFornavn() + ". Sjefer kan ikke endre avdeling!");
		} else {
			Avdeling avd = ansatt.getAvd();
			avd.removeAnsatt(ansatt);
			System.out.println("Skriv inn avdeling-ID til den nye avdelingen: ");
			avdDAO.finnAvdelingMedId(scanner.nextInt()).addAnsatt(ansatt);
		}
	}
}
