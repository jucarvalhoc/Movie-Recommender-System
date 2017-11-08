import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;

public class Reader {

	public static void whatever(){
		String csvFile = "C:\\Users\\joaov\\workspace\\rec\\bin\\ml-100k\\test.csv";

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			while ((line = reader.readNext()) != null) {
				System.out.println("Country [id= " + line[0] + ", code= " + line[1] + " , name=" + line[2] + "]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
