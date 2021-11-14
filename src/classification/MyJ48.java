package classification;

import java.util.Random;

import ar.pkg.Data;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MyJ48 extends Data {
	J48 tree = new J48();
	public Instances data;
	public Instances unlabelset;
	private String[] modelOptions;
	
	public void load_data(String filename) throws Exception {
		DataSource source = new DataSource(filename);
		this.data = source.getDataSet();
		this.data.setClassIndex(this.data.numAttributes() - 1);
	}
	
	public void setModelOptions(String options) throws Exception {
		this.modelOptions = weka.core.Utils.splitOptions(options);
		this.tree.setOptions(this.modelOptions);
	}
	
	public String getClassification() throws Exception {
		System.out.println("====== J48 Training on Training dataset =====");
		this.tree.buildClassifier(this.data);
		return this.tree.toString();
	}
	
	public String getEvaluationCrossValidation(int k) throws Exception {
		System.out.println("====== J48 Evaluation on cross validation =====");
		Evaluation evaluator = new Evaluation(this.data);
		
		Random rd = new Random(12);
		evaluator.crossValidateModel(this.tree, this.data, k, rd);
		return evaluator.toSummaryString();
	}
	
	public Instances getPrediction(String filename, boolean isSave) throws Exception {
		DataSource source = new DataSource(filename);
		this.unlabelset = source.getDataSet();
		this.unlabelset.setClassIndex(unlabelset.numAttributes() - 1);
		
		for (Instance instance : unlabelset) {
			double label = this.tree.classifyInstance(instance);
			instance.setClassValue(label);
		}
		if (isSave) {
			this.dataset = this.unlabelset;
			this.toArff("j48_unlabel_predicted");
		}
		return this.unlabelset;
	}
}
