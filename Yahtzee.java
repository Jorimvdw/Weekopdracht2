package Yahtzee;
import java.util.ArrayList;
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
	boolean doorgaan = true;
	int [] blokkeerarray = new int [5];
	Speler speler = new Speler();
	int aantalvervolgworpen = 0;
	
	YahtzeeSpel() {
		genereerdobbelstenen(5);
	}
	
	
	void spelen() {
		
		while (doorgaan) {
			System.out.println("start spel");
			
			
			laatdobbelstenenzien();
			vervolgstap();
			
		
			eindespel();
			
		} 
	}
	
	void genereerdobbelstenen (int u) {
		for (int y = 0 ; y < u ; y++) {
			Dobbelsteen dobbelsteen = new Dobbelsteen();
			dobbelstenen.add(dobbelsteen);		
			}
	}
	
	void laatdobbelstenenzien () {
		for (Dobbelsteen x  : dobbelstenen ) {
			System.out.println(x.waarde);		
		}
	}
	
	void vervolgstap () {
		Worp worp = new Worp();
		if (aantalvervolgworpen < 2) {
			System.out.println("Druk 'b' om deze uitkomst te behouden, 'n' om nogmaals te gooien");
			String invoer = Yahtzee.sc.nextLine();	
			if (invoer.equalsIgnoreCase("b")) {
				Worp done = worp.uitslag(worp);
				speler.worpgeschiedenis.add(done);
			} else if (invoer.equalsIgnoreCase("n")) {
				aantalvervolgworpen++;
				vervolgworp();
			} else {
				System.out.println("Ik begrijp niet wat je bedoeld");
				vervolgstap ();
			}
		} else {
			Worp done = worp.uitslag(worp);
			speler.worpgeschiedenis.add(done);
		}
			
		
		
	}

	void vervolgworp () {
		System.out.println("");
		System.out.println("Druk 1 als een dobbelsteen moet blijven liggen, 0 voor opnieuw gooien.");
		String invoer = Yahtzee.sc.nextLine();
		
		for (int u = 0 ; u < 5 ; u++) {				
			int los = Character.getNumericValue(invoer.charAt(u));
			blokkeerarray[u] = los;
		}
		
		for (int i = 0 ; i < 5 ; i++) {
			if (blokkeerarray[i] == 0) { 
				dobbelstenen.get(i).waarde = Yahtzee.rand.nextInt(6) +1;	
			}
		}	
			
			laatdobbelstenenzien();
			vervolgstap ();		
	}
	
	void eindespel() {
		System.out.println("Wil je nog een potje? enter voor ja, q om te stoppen");
		String invoer  = Yahtzee.sc.nextLine();
		if (invoer.equalsIgnoreCase("q")) {
			System.out.println("Exit");
			doorgaan = false;
		} else if (invoer.length() == 0) {
			System.out.println("Een nieuw spel begint");
		} else {
			System.out.println("Verkeerde input");	
			eindespel();
		}
	}


}

class Dobbelsteen {
	Dobbelsteen () {
		werpen();
	}
	int waarde = 0;
	
	int werpen() {	
		waarde = Yahtzee.rand.nextInt(6) +1;
		return waarde;
	}
}

class Worp {
	 int [] uitslag = new int [5]; 
	
	Worp uitslag (Worp worp) {
		for (int b = 0 ; b < 5 ; b++) {
			worp.uitslag[b] = YahtzeeSpel.dobbelstenen.get(b).waarde;
		}
		System.out.println("De einduitslag van deze worp is:");
		for (int a = 0 ; a < uitslag.length ; a++) {
			System.out.println(uitslag[a]);
		}
		return worp;
			
	}
}

class Speler {
	ArrayList<Worp> worpgeschiedenis = new ArrayList<>();
	
	
}