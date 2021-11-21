package clustering;

public class ClusteringMain {

	public static void main(String[] args) throws Exception {
		String trainDataPath = "D:\\Study\\Data Mining\\76_19497581_NguyenVanAnhTuan_DHKHMT15A_Nhom02_Lab08_Lab09\\buy.arff";
		String unlabelDataPath = "D:\\Study\\Data Mining\\76_19497581_NguyenVanAnhTuan_DHKHMT15A_Nhom02_Lab08_Lab09\\buy_unlabeled.arff";
		
		MyKmeans kmeans = new MyKmeans();
		kmeans.load_train_data(trainDataPath);
		System.out.println(kmeans.getClusters(2));
		System.out.println(kmeans.getEvaluation());
		System.out.println(kmeans.getPrediction(unlabelDataPath, true));
		
		MyHierarchical hierarchical = new MyHierarchical();
		hierarchical.load_train_data(trainDataPath);
		System.out.println(hierarchical.getClusters(2));
		System.out.println(hierarchical.getEvaluation());
		System.out.println(hierarchical.getPrediction(unlabelDataPath, true));
		
	}

}
