package core;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class AutoMLClassifier {

	public RandomForest train(String trainFilePath) throws Exception {
		Instances trainData = getInstances(trainFilePath);

		String[] options = new String[1];
		options[0] = "-U"; // unpruned tree
		RandomForest randomForest = new RandomForest(); // new instance of tree
		randomForest.setOptions(options); // set the options
		randomForest.buildClassifier(trainData); // build classifier

		return randomForest;

	}

	public List<String> predict(RandomForest classifier, String trainFilePath, String testFilePath) throws Exception {
		Instances trainData = getInstances(trainFilePath);
		Instances testData = getInstances(testFilePath);

		Evaluation eval = new Evaluation(trainData);

		eval.evaluateModel(classifier, testData);

		List<String> result = new ArrayList<>();

		int i = 0;
		for (Prediction pred : eval.predictions()) {
			String resultLine = testData.get(i).toString();
			resultLine += ", " + trainData.classAttribute().value((int) pred.predicted());
			result.add(resultLine);
			i++;
		}

		return result;

	}

	private Instances getInstances(String trainFilePath) throws Exception {
		DataSource trainSource = new DataSource(trainFilePath);
		Instances trainData = trainSource.getDataSet();
		if (trainData.classIndex() == -1) {
			trainData.setClassIndex(trainData.numAttributes() - 1);
		}
		return trainData;
	}

	public static void main(String[] args) throws Exception {
		AutoMLClassifier ml = new AutoMLClassifier();
		RandomForest classifier = ml.train("Data/train.csv");
		System.out.println(ml.predict(classifier, "Data/train.csv", "Data/test.csv"));
	}

}
