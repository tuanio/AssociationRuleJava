package classification;

//train model
//output data of model
//evaluate model (test, train, cross-validation)

public class ClassificationMain {

	public static void main(String[] args) throws Exception {
		String trainDataPath = "D:\\Study\\Data Mining\\76_19497581_NguyenVanAnhTuan_DHKHMT15A_Nhom02_Lab07\\iris_train_80.arff";
		String testDataPath = "D:\\Study\\Data Mining\\76_19497581_NguyenVanAnhTuan_DHKHMT15A_Nhom02_Lab07\\iris_test_20.arff";
		String unlabelDataPath = "D:\\Study\\Data Mining\\76_19497581_NguyenVanAnhTuan_DHKHMT15A_Nhom02_Lab07\\iris_unlabel.arff";
		
		MyNaiveBayes naivebayes = new MyNaiveBayes();
		naivebayes.load_train_data(trainDataPath);
		naivebayes.load_test_data(testDataPath);
		System.out.println(naivebayes.getClassification());
		System.out.println(naivebayes.getEvaluation());
		System.out.println(naivebayes.getPrediction(unlabelDataPath, true));
		
		MyJ48 j48 = new MyJ48();
		j48.load_data(trainDataPath);
		System.out.println(j48.getClassification());
		System.out.println(j48.getEvaluationCrossValidation(5));
		System.out.println(j48.getPrediction(unlabelDataPath, true));
		
		
		MyKNN knn = new MyKNN();
		knn.load_train_data(trainDataPath);
		knn.load_test_data(testDataPath);
		System.out.println(knn.getClassification());
		System.out.println(knn.getEvaluation());
		System.out.println(knn.getPrediction(unlabelDataPath, true));
		
		
	}

}
