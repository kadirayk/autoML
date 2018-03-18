package core;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

public class ClassifierEvaluation {
	Evaluation evaluation;
	Classifier classifier;

	public ClassifierEvaluation(Evaluation evaluation, Classifier classifier) {
		this.evaluation = evaluation;
		this.classifier = classifier;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

}
