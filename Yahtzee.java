package Yahtzee;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Yahtzee {

	static Scanner sc = new Scanner(System.in);
	static Random rand = new Random();
	
	public static void main(String[] args) {
		YahtzeeSpel yahtzeespel = new YahtzeeSpel();	
		yahtzeespel.spelen();	
	}
}

class YahtzeeSpel {
	static ArrayList <Dobbelsteen> dobbelstenen = new ArrayList<>();
	static ArrayList<Speler> spelers = new ArrayList<>();
	boolean doorgaan = true;
	int [] blokkeerarray = new int [5];
	int aantalvervolgworpen = 0;
	
	YahtzeeSpel() {
		genereerdobbelstenen(5);
	}
	
	void spelen() {
			spelerinvoer();
		while (doorgaan) {		
			for (int h = 0 ; h < spelers.size() ; h++) {
				aantalvervolgworpen = 0;
				System.out.println("De beurt is aan " + spelers.get(h).naam);
				System.out.println();
				System.out.println("Druk op enter om de dobbelstenen te werpen");
				String invoer = Yahtzee.sc.nextLine();
				if (invoer.length() == 0) {
					gooimetdobbelstenen();
					vervolgstappen(spelers.get(h));
				}
			} 
			eindespel();	
		} 
	}
	
	void spelerinvoer () {
		boolean nameninvoeren = false;
		
		do {
			System.out.println("Voer uw naam in:");
			String invoer = Yahtzee.sc.nextLine();
			spelers.add(maakSpeler (invoer));
			System.out.println("Wil je nog een speler toevoegen? 'j' voor ja, 'n' voor nee");
			String invoer2 = Yahtzee.sc.nextLine();
			nameninvoeren = spelerinvoermenu (invoer2);						
		} while (nameninvoeren);

		System.out.print("Het spel begint. ");
		System.out.print("De deelnemers zijn: ");		
		for (int x = 0 ; x < spelers.size() ; x++) {
			if (x == spelers.size()-1) {
				System.out.println(spelers.get(x).naam);			
			} else {				
				System.out.print(spelers.get(x).naam + ", ");			
			}					
		}
	}
	
	boolean spelerinvoermenu (String s)  {
		
		boolean nameninvoeren;
		if (s.equalsIgnoreCase("j")) {
			nameninvoeren = true;
		} else if (s.equalsIgnoreCase("n")) {				
			nameninvoeren = false;
		} else {
			nameninvoeren = false;
			System.out.println("Wil je nog een speler toevoegen? 'j' voor ja, 'n' voor nee");
			String invoer = Yahtzee.sc.nextLine();
			spelerinvoermenu(invoer);
		}		
		return nameninvoeren;
	}
	
	Speler maakSpeler (String n) {
		Speler speler = new Speler();
		speler.naam = n;
		return speler;
	}
		
	void genereerdobbelstenen (int u) {
		for (int y = 0 ; y < u ; y++) {
			Dobbelsteen dobbelsteen = new Dobbelsteen();
			dobbelstenen.add(dobbelsteen);		
			}
	}

	void gooimetdobbelstenen () {
		for (int i = 0 ; i < 5 ; i++) {
			if (blokkeerarray[i] == 0) { 
				dobbelstenen.get(i).waarde = dobbelstenen.get(i).werpen();
			}
		}	
		
		for (Dobbelsteen x  : dobbelstenen ) {
			System.out.print(x.waarde + "   ");		
		}
		System.out.println();
		System.out.println();
	}
		
	void vervolgstappen (Speler speler) {
	
		if (aantalvervolgworpen < 2) {
			System.out.println(speler.naam + ", druk 'b' om deze uitkomst te behouden, 'n' om nogmaals te gooien");
			String invoer = Yahtzee.sc.nextLine();	
			if (invoer.equalsIgnoreCase("b")) {
				speler.worpgeschiedenis.add(Worp.uitslag(speler));
			} else if (invoer.equalsIgnoreCase("n")) {
				aantalvervolgworpen++;
				verwerkinvoer(speler);
			} else {
				System.out.println("Ik begrijp niet wat je bedoeld");
				vervolgstappen (speler);
			}
		} else {
			speler.worpgeschiedenis.add(Worp.uitslag(speler));
		}		
	}

	void verwerkinvoer (Speler speler) {
		System.out.println("");
		System.out.println("Welke dobbelstenen wil je laten liggen?");
		String invoer = Yahtzee.sc.nextLine();
		
		for (int d = 0 ; d < invoer.length() ; d++) {
			int los = Character.getNumericValue(invoer.charAt(d)-1);
			for (int u = 0 ; u < blokkeerarray.length ; u++) {
				if (los == u) {
					blokkeerarray[u] = 1;
				}
			}			
		}			
		gooimetdobbelstenen();	
		Arrays.fill(blokkeerarray, 0);
		vervolgstappen (speler);		
	}
	
	void eindespel() {
		System.out.println("Wil je nog een potje? enter voor ja, q om te stoppen");
		String invoer  = Yahtzee.sc.nextLine();
		if (invoer.equalsIgnoreCase("q")) {
			System.out.println("Dit is jullie worpgeschiedenis:");
			for (int h = 0 ; h < spelers.size() ; h++) {
				System.out.println(spelers.get(h).naam + ":");
				for (int k = 0 ; k < spelers.get(h).worpgeschiedenis.size() ; k++) {
					for (int j = 0 ; j < spelers.get(h).worpgeschiedenis.get(k).uitslag.length ; j++) {
						System.out.print(spelers.get(h).worpgeschiedenis.get(k).uitslag[j] + "  ");
					}
					System.out.println();				
				}				
			}				
			System.out.println("Exit");
			doorgaan = false;
		} else if (invoer.length() == 0) {
			System.out.print("Een nieuwe ronde begint. ");
		} else {
			System.out.println("Verkeerde input");	
			eindespel();
		}
	}
}

class Dobbelsteen {
	int waarde = 0;
	
	int werpen() {	
		waarde = Yahtzee.rand.nextInt(6) +1;
		return waarde;
	}
}

class Worp {
	int [] uitslag = new int [5]; 
	
	static Worp uitslag (Speler speler) {
		Worp worp = new Worp();
		for (int b = 0 ; b < 5 ; b++) {
			worp.uitslag[b] = YahtzeeSpel.dobbelstenen.get(b).waarde;
		}
		System.out.println(speler.naam + ", dit is je definitieve uitslag van deze beurt");
		System.out.println(); 
		return worp;		 
	 }	 
}

class Speler {
	ArrayList<Worp> worpgeschiedenis = new ArrayList<>();	
	String naam;
}