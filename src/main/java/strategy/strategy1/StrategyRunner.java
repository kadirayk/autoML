package strategy.strategy1;

import java.io.File;
import java.nio.file.Files;

import core.AutoMLClassifier;
import model.Interview;
import util.SerializationUtil;
import weka.classifiers.trees.RandomForest;

public class StrategyRunner {

	public static void main(String[] args) throws Exception {

		Interview interview = SerializationUtil.readAsJSON("../../interview_data/");

		String trainFilePath = interview.getQuestionByPath("step1.q1").getAnswer();
		int relativePathIndex = trainFilePath.indexOf("interview_data");
		trainFilePath = trainFilePath.substring(relativePathIndex);
		trainFilePath = "../../" + trainFilePath;

		System.out.println("trainFilePath: " + trainFilePath);

		// "../../interview_data/interview_resources/train.csv";

		File trainFile = new File(trainFilePath);

		if (!trainFile.exists()) {
			System.out.println("can't find training file in: " + trainFile.getAbsolutePath());
			System.exit(1);
		}

		int indexOfExtension = trainFilePath.lastIndexOf(".");
		String fileExtension = trainFilePath.substring(indexOfExtension);

		File trainFileInStrategy = new File("output/" + "train" + fileExtension);

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

	private static String renameTrainFile(String filePath) {
		int indexOfExtension = filePath.lastIndexOf(".");
		String fileExtension = filePath.substring(indexOfExtension);
		int indexOfLastFolder = filePath.lastIndexOf("/") > filePath.lastIndexOf("\\") ? filePath.lastIndexOf("/")
				: filePath.lastIndexOf("\\");
		String fullPath = filePath.substring(0, indexOfLastFolder);
		return fullPath + "/train" + fileExtension;
	}

}
