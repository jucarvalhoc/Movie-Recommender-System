import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.CSVReader;

public class Reader {

	public static void readFile(User user) {
		String csvFile = "C:/Users/Victor/Documents/GitHub/Movie-Recommender-System/rec/src/main/resources/ml-100k";
		
		try {
			FileReader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.TDF.withHeader("uID", "Movie", "Rating", "TimeStamp").parse(in);
			for (CSVRecord record : records) {
				int index = Integer.parseInt(record.get("uID"));
				if(index == user.id) {
					
					int aux = Integer.parseInt(record.get("Movie"));
					int aux2 = Integer.parseInt(record.get("Rating"));
					user.ratings[aux] = aux2;
				}
			}
			user.showData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
