import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Main {

	static final int m = 1;
	static final int M = 5;
	
	
	
	public static void main(String[] args) {
		
		readFile();
	}
		
	public static void readFile(){
		String csvFile = "C:/Users/joaov/workspace/rec/src/main/resources/ml-100k/ua.base";
		
		try {
			FileReader in = new FileReader(csvFile);
			Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
			for (CSVRecord record : records) {
				System.out.println(record.get(0));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
