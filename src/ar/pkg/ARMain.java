package ar.pkg;

import java.util.Arrays;
import java.util.Scanner;

// xây dựng menu
// 1. apriori
// 2. fpgrowth
// 3. lưu file

public class ARMain {
	
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws Exception {
		
		boolean running = true;
		while (running) {
			
			System.out.println("-------------The Automatic Preprocessing Association Rule Program-----------");
			System.out.println("1. Apriori");
			System.out.println("2. FPGrowth");
			System.out.println("0. Exit!");
			
			System.out.print("----> ");
			int input = in.nextInt();
			
			switch (input) {
			case 1:
				callApriori();
				break;
			case 2:
				callFPGrowth();
				break;
			case 0:
				running = false;
				System.out.println("--------- Bye ----------");
				break;
			}
		}
	}

	public static void callApriori() throws Exception {
		MyApriori apriori = new MyApriori();
		
		System.out.print("Enter the path to your dataset: ");
		in.nextLine();
		String datasetPath = in.nextLine();		
		apriori.load_data(datasetPath);
		
		System.out.print("Enter your options to Apriori model: ");
		String options = in.nextLine();
		
		apriori.setModelOptions(options);
		apriori.NumericToNominal("-R first-last", true);
		
		int choice = saveFile();
		if (choice == -1) {
			System.out.println("There's no choice match your choice! Choose don't save instead");			
		} else {
			System.out.println(choice);
			String[] listString = datasetPath.split("\\\\");
			listString = listString[listString.length - 1].split("\\.");
			String fileName = listString[0];
			if (choice == 1 || choice == 3) {
				apriori.toCSV(fileName);
			}
			if (choice == 2 || choice == 3) {
				apriori.toArff(fileName);
			}
		}
		
		String ar = apriori.getRules();
		System.out.println(ar);
	}
	
	public static void callFPGrowth() throws Exception {
		MyFPGrowth fpgrowth = new MyFPGrowth();
		
		System.out.print("Enter the path to your dataset: ");
		in.nextLine();
		String datasetPath = in.nextLine();		
		fpgrowth.load_data(datasetPath);
		
		System.out.print("Enter your options to FPGrowth model: ");
		String options = in.nextLine();
		
		fpgrowth.setModelOptions(options);
		fpgrowth.NumericToNominal("-R first-last", true);
		fpgrowth.NominalToBinary("-R first-last", true);
		
		int choice = saveFile();
		if (choice == -1) {
			System.out.println("There's no choice match your choice! Choose don't save instead");			
		} else {
			String[] listString = datasetPath.split("\\\\");
			listString = listString[listString.length - 1].split("\\.");
			String fileName = listString[0];
			if (choice == 1 || choice == 3) {
				fpgrowth.toCSV(fileName);
			}
			if (choice == 2 || choice == 3) {
				fpgrowth.toArff(fileName);
			}
		}
		
		String ar = fpgrowth.getRules();
		System.out.println(ar);
	}
	
	public static int saveFile() {
		System.out.println("Do you want to save new preprocess data?[no/csv/arff/both]");
		System.out.print("----> Your choice: ");
		String userChoice = in.nextLine().strip();
		String[] choices = {"no", "csv", "arff", "both"};
		return Arrays.asList(choices).indexOf(userChoice);
	}
}
