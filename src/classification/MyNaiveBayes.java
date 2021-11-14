package classification;

import ar.pkg.Data;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MyNaiveBayes extends Data {
	public NaiveBayes naivebayes = new NaiveBayes();
	public Instances trainset;
	public Instances testset;
	public Instances unlabelset;
	
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
	
	public String getClassification() throws Exception {
		System.out.println("====== Naive Bayes Training on Training dataset =====");
		this.naivebayes.buildClassifier(this.trainset);
		return this.naivebayes.toString();
	}
	
	public String getEvaluation() throws Exception {
		System.out.println("====== Naive Bayes Evaluation on Testing dataset =====");
		Evaluation evaluator = new Evaluation(this.testset);
		evaluator.evaluateModel(this.naivebayes, this.testset);
		return evaluator.toSummaryString();
	}
	
	public Instances getPrediction(String filename, boolean isSave) throws Exception {
		DataSource source = new DataSource(filename);
		this.unlabelset = source.getDataSet();
		this.unlabelset.setClassIndex(unlabelset.numAttributes() - 1);
		
		for (Instance instance : unlabelset) {
			double label = this.naivebayes.classifyInstance(instance);
			instance.setClassValue(label);
		}
		if (isSave) {
			this.dataset = this.unlabelset;
			this.toArff("naivebayes_unlabel_predicted");
		}
		return this.unlabelset;
	}
}
