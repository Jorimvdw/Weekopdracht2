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
	ArrayList <Dobbelsteen> Dobbelstenen = new ArrayList<>();
	
	YahtzeeSpel() {
		for (int y = 0 ; y < 5 ; y++) {
		Dobbelsteen dobbelsteen = new Dobbelsteen();
		Dobbelstenen.add(dobbelsteen);		
		}
	}
	
	
	void spelen() {
		boolean doorgaan = true;
		Dobbelsteen dobbelsteen = new Dobbelsteen();
		
	
		
		
		while (doorgaan) {
			System.out.println("start spel");
			for (Dobbelsteen x  : Dobbelstenen ) {
				System.out.println(x.werpen() +1);				
			}
			
			System.out.println("Wil je nog een potje? enter voor ja, q om te stoppen");
			if (Yahtzee.sc.nextLine().equalsIgnoreCase("q")) {
				System.out.println("Exit");
				doorgaan = false;
			}
		} 
	}


}

class Dobbelsteen {
	int waarde = 3;
	int werpen() {	
		waarde = Yahtzee.rand.nextInt(6);
		return waarde;
	}
}