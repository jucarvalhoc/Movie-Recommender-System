import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;



public class User {
	
	static Random rand = new Random();
	
	int id;
	int[] ratings;
	int nor; //Number of ratings;
	float[] cValues; //vector of comparison with another user X
	private Scanner in;
	
	
	public User(int id, int[] ratings) {
		this.id = id;
		this.ratings = ratings;
		Arrays.fill(ratings, -1);
		this.nor = 0;
		cValues = new float[5];
	}
	
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
		for (int i = 1; i < 51; i++) {
			System.out.println("Movie["+i+"]: " + ratings[i]);
		}
	}
	void showCmp(){
		for (int i = 0; i < cValues.length; i++) {
			System.out.println("Value:["+i+"]: " + cValues[i]);
		}
	}
	
}
