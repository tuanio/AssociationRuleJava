package ar.pkg;

import weka.associations.FPGrowth;

public class MyFPGrowth extends Data {

	FPGrowth fpgrowth = new FPGrowth();
	String[] modelOptions;
	
	public void setModelOptions(String options) throws Exception {
		this.modelOptions = weka.core.Utils.splitOptions(options);
		this.fpgrowth.setOptions(this.modelOptions);
	}
	
	public String getRules() throws Exception {
		this.fpgrowth.buildAssociations(this.dataset);
		return this.fpgrowth.toString();
	}
}
