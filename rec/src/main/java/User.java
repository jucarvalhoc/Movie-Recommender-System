import java.util.Random;
import java.util.Scanner;



public class User {
	
	static Random rand = new Random();
	
	String name;
	int id;
	int[] ratings = new int[50];

	private Scanner in;
	
	void setData(){
		System.out.println("Digite o id do usuario:");
		in = new Scanner(System.in);
		id = in.nextInt();
		
		for(int i = 0; i < 50; i++)
			ratings[i] = -1;
		
			
		for (int i = 0; i < 50; i++) {
			int j = rand.nextInt(50);
			ratings[j] = rand.nextInt(5)+1;
			
		}
	}
	void showData(){
		for (int i = 0; i < 50; i++) {
			System.out.println("Rating[" + i + "] = " + ratings[i] + "\n" );
			
		}
	}
}
