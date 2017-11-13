import java.util.Random;

public class Engine {

	public static void CompareValue(User user1, User user2) {
		float[][] aux = new float[1][5];
		
		for (int i = 1; i < user1.ratings.length; i++) {
			if(user1.ratings[i] != -1)
				user1.nor++;
		}
		for (int i = 1; i < user2.ratings.length; i++) {
			if(user2.ratings[i] != -1)
				user2.nor++;
		}
		
		int common = 0;
		for (int i = 1; i < 50; i++) {
			if(user1.ratings[i] != -1 && user2.ratings[i] != -1) {
				common++; // Number of common ratings between the users.
				int diff = Math.abs(user1.ratings[i] - user2.ratings[i]);
				
				switch (diff) {
				case 0:
					user2.cValues[0]++;
					break;
				case 1:
					user2.cValues[1]++;
					break;
				case 2:
					user2.cValues[2]++;
					break;
				case 3:
					user2.cValues[3]++;
					break;
				case 4:
					user2.cValues[4]++;
					break;
				default:
					System.out.println("ERRO: " + diff + " | " + i);
					break;
				}
			}
		}
		for (int j = 0; j < 5; j++) {
			user2.cValues[j] /= common;
		}
		user2.showCmp();
	}
	
	//receives the number of generations and a random population
	public static void GA(int generations, User[] population, User user) {
		float fvalue[] = new float[40];
		float[][] wpop = new float[population.length][5];
		int nou=50; //Number of Users;
		
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < 5; j++) {
				int sum=0;
				for (int k = 0, a=4; k < 5; a--, k++) {
					sum+=Math.pow(2,a)*population[i].cValues[j*5+k];
				}
				wpop[i][j]=2*sum/31-1;
			}
		}
		
		//Similarity
		double[][] sim = new double[population.length][nou];
		
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < nou; j++) {
				float sum = 0;
				for(int k=0;k<5;k++)	sum+=wpop[i][k]*population[i].cValues[k]*1.0;
				sim[i][j]=(1.0/5.0)*sum;
			}
		}
		//prediction
		double[][] px = new double[40][50];
		
		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 50; j++) {
				double sum1=0, sum2=0;
				for (int k = 0; k < nou; k++) {
					sum1 += sim[i][k]*(population[k].ratings[j]-population[k].ratings[50]);
					sum2 += sim[i][k];
				}
				px[i][j] = population[user.id].ratings[50] + sum1*1.0/sum2;
				
			}
		}
		
		double[] fitness[];
		for(int it=0;it<40;it++)	{
			fvalue[it]=0;
			for(int i=0;i<6;i++)	{
				float sum=0;
				int noi=0;
				for(int j=0;j<50;j++)	{
					if(population[i].ratings[j]!=-1)	{
						sum += Math.abs(px[it][j]-population[i].ratings[j]);
						noi++;
					}
				}
				sum/=noi*1.0;
				fvalue[it] += sum;
			}
		}
		
		for(int i=0;i<40;i++)	{
			population[i].ratings[25]=(int) fvalue[i];
		}
		
		if(generations>1)	{
			Random rand = new Random();

			//crossover
			for(int i=0;i<40;i+=2)		{
				int pos=rand.nextInt()%25;
				for(;pos<25;pos++)	{
					int temp = population[i].ratings[pos];
					population[i].ratings[pos] = population[i+1].ratings[pos];
					population[i+1].ratings[pos] = temp;
				}
			}

			
			//mutation
			for(int i=0;i<40;i++)	{
				for(int j=0;j<25;j++)	{
					if(population[i].ratings[j]==0)	population[i].ratings[j]=1;
					else	population[i].ratings[j]=0;
				}
			}
		}
	}
}

