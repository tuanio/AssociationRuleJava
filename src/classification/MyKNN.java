package classification;

import ar.pkg.Data;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MyKNN extends Data {
	public IBk knn = new IBk();
	public Instances trainset;
	public Instances testset;
	public Instances unlabelset;
	private String[] modelOptions;
	
	public void load_train_data(String filename) throws Exception {
		DataSource source = new DataSource(filename);
		this.trainset = source.getDataSet();
		this.trainset.setClassIndex(this.trainset.numAttributes() - 1);
	}
	
	public void load_test_data(String filename) throws Exception {
		DataSource source = new DataSource(filename);
		this.testset = source.getDataSet();
		this.testset.setClassIndex(this.testset.numAttributes() - 1);
	}
	
	public void setModelOptions(String options) throws Exception {
		this.modelOptions = weka.core.Utils.splitOptions(options);
		this.knn.setOptions(this.modelOptions);
	}
	
	public String getClassification() throws Exception {
		System.out.println("====== K-Nearest Neighbour Training on Training dataset =====");
		this.knn.buildClassifier(this.trainset);
		return this.knn.toString();
	}
	
	public String getEvaluation() throws Exception {
		System.out.println("====== K-Nearest Neighbour Evaluation on Testing dataset =====");
		Evaluation evaluator = new Evaluation(this.testset);
		evaluator.evaluateModel(this.knn, this.testset);
		return evaluator.toSummaryString();
	}
	
	public Instances getPrediction(String filename, boolean isSave) throws Exception {
		DataSource source = new DataSource(filename);
		this.unlabelset = source.getDataSet();
		this.unlabelset.setClassIndex(unlabelset.numAttributes() - 1);
		
		for (Instance instance : unlabelset) {
			double label = this.knn.classifyInstance(instance);
			instance.setClassValue(label);
		}
		if (isSave) {
			this.dataset = this.unlabelset;
			this.toArff("knn_unlabel_predicted");
		}
		return this.unlabelset;
	}
}
