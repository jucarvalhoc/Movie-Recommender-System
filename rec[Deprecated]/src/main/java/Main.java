import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Main {

	static final int m = 1;
	static final int M = 5;
	
	
	
	public static void main(String[] args) {
		
		
		int[] ratings = new int[300];
		User user = new User(1, ratings);
		Reader.readFile(user);
		
		User userAux = new User(2, new int[500]);
		Reader.readFile(userAux);
		
		Engine.CompareValue(user, userAux);
	}
	
}
	
	