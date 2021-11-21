package clustering;

import ar.pkg.Data;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.HierarchicalClusterer;
import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

public class MyHierarchical extends Data {
	HierarchicalClusterer hierachicalModel = new HierarchicalClusterer();
	Instances trainset;
	Instances unlabelset;
	
	public void load_train_data(String filename) throws Exception {
		DataSource source = new DataSource(filename);
		this.trainset = source.getDataSet();
	}
	
	public String getClusters(int k) throws Exception {
		System.out.println("======= Hierarchical Model training on trainset ======");
		this.hierachicalModel.setNumClusters(k);
		this.hierachicalModel.setDistanceFunction(new EuclideanDistance());
		this.hierachicalModel.buildClusterer(this.trainset);
		return this.hierachicalModel.toString();
	}
	
	public String getEvaluation() throws Exception {
		System.out.println("====== Hierarchical Model Evaluation on Training dataset =====");
		ClusterEvaluation evaluator = new ClusterEvaluation();
		evaluator.setClusterer(this.hierachicalModel);
		evaluator.evaluateClusterer(this.trainset);
		return evaluator.clusterResultsToString();
	}
	
	public Instances getPrediction(String filename, boolean isSave) throws Exception {
		DataSource source = new DataSource(filename);
		this.unlabelset = source.getDataSet();
		this.unlabelset.setClassIndex(unlabelset.numAttributes() - 1);
		
		for (Instance instance : unlabelset) {
			double label = this.hierachicalModel.clusterInstance(instance);
			instance.setClassValue(label);
		}
		if (isSave) {
			this.dataset = this.unlabelset;
			this.toArff("hierarchical_unlabel_predicted");
		}
		return this.unlabelset;
	}
}
