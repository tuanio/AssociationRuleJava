package ar.pkg;

import java.io.File;
import java.io.IOException;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.NominalToBinary;

public class Data {
	public DataSource source;
	public Instances dataset;
	
	public void load_data(String filename) throws Exception {
		this.source = new DataSource(filename);
		this.dataset = this.source.getDataSet();
	}
	
	public Instances NumericToNominal(String filterOptions, boolean saving) throws Exception {
		String[] listOptions = weka.core.Utils.splitOptions(filterOptions);
		
		NumericToNominal filter = new NumericToNominal();
		filter.setOptions(listOptions);
		filter.setInputFormat(this.dataset);
		
		Instances filteredData = Filter.useFilter(this.dataset, filter);
	
		if (saving == true) {
			this.dataset = filteredData;
		}
		
		return filteredData;
	}
	
	public Instances NominalToBinary(String filterOptions, boolean saving) throws Exception {
		String[] listOptions = weka.core.Utils.splitOptions(filterOptions);
		
		NominalToBinary filter = new NominalToBinary();
		filter.setOptions(listOptions);
		filter.setBinaryAttributesNominal(true); // set to binary nominal
		filter.setInputFormat(this.dataset);
		
		Instances filteredData = Filter.useFilter(this.dataset, filter);
	
		if (saving == true) {
			this.dataset = filteredData;
		}
		
		return filteredData;
	}
	
	public void toCSV(String filename) throws IOException {
		CSVSaver saver = new CSVSaver();
		saver.setInstances(this.dataset);
		saver.setFile(new File(filename + ".csv"));
		saver.writeBatch();
	}
	
	public void toArff(String filename) throws IOException {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(this.dataset);
		saver.setFile(new File(filename + ".arff"));
		saver.writeBatch();
	}
	
	public String sumary() {
		return this.dataset.toSummaryString();
	}
	
	@Override
	public String toString() {
		return this.dataset.toString();
	}
}
