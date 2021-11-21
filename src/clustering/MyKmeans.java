package clustering;

import ar.pkg.Data;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.clusterers.ClusterEvaluation;
import weka.core.EuclideanDistance;
import weka.core.Instance;

public class MyKmeans extends Data {
	public SimpleKMeans kmeans = new SimpleKMeans();
	public Instances trainset;
	Instances unlabelset;
	
	public void load_train_data(String filename) throws Exception {
		DataSource source = new DataSource(filename);
		this.trainset = source.getDataSet();
	}
	
	public String getClusters(int k) throws Exception {
		System.out.println("======= KMeans training on trainset ======");
		this.kmeans.setNumClusters(k);
		this.kmeans.setDistanceFunction(new EuclideanDistance());
		this.kmeans.buildClusterer(this.trainset);
		return this.kmeans.toString();
	}
	
	public String getEvaluation() throws Exception {
		System.out.println("====== KMeans Evaluation on Training dataset =====");
		ClusterEvaluation evaluator = new ClusterEvaluation();
		evaluator.setClusterer(this.kmeans);
		evaluator.evaluateClusterer(this.trainset);
		return evaluator.clusterResultsToString();
	}
	
	public Instances getPrediction(String filename, boolean isSave) throws Exception {
		DataSource source = new DataSource(filename);
		this.unlabelset = source.getDataSet();
		this.unlabelset.setClassIndex(unlabelset.numAttributes() - 1);
		
		for (Instance instance : unlabelset) {
			double label = this.kmeans.clusterInstance(instance);
			instance.setClassValue(label);
		}
		if (isSave) {
			this.dataset = this.unlabelset;
			this.toArff("kmeans_unlabel_predicted");
		}
		return this.unlabelset;
	}
}
