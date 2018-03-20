package strategy.strategy1;

import java.io.File;
import java.nio.file.Files;

import core.AutoMLClassifier;
import util.SerializationUtil;
import weka.classifiers.trees.RandomForest;

public class StrategyRunner {

	public static void main(String[] args) throws Exception {

		String trainFilePath = "../../interview_data/interview_resources/train.csv";

		File trainFile = new File(trainFilePath);

		if (!trainFile.exists()) {
			System.out.println("can't find training file in: " + trainFile.getAbsolutePath());
			System.exit(1);
		}

		File trainFileInStrategy = new File("output/train.csv");

		Files.copy(trainFile.toPath(), trainFileInStrategy.toPath());

		AutoMLClassifier aml = new AutoMLClassifier();
		RandomForest classifier = aml.train(trainFilePath);

		SerializationUtil.write("output", classifier);

		for (int i = 0; i < 5; i++) {
			System.out.println("Running strategy1 for AutoML");
			Thread.sleep(1000);
		}
		System.out.println("Strategy is ready");
	}

}