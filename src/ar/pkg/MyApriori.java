package ar.pkg;

import weka.associations.Apriori;

public class MyApriori extends Data {
	Apriori apriori = new Apriori();
	String[] modelOptions;
	
	public void setModelOptions(String options) throws Exception {
		this.modelOptions = weka.core.Utils.splitOptions(options);
		this.apriori.setOptions(this.modelOptions);
	}
	
	public String getRules() throws Exception {
		this.apriori.buildAssociations(this.dataset);
		return this.apriori.toString();
	}
}
