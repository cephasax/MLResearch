import weka.classifiers.meta.AutoWEKAClassifier;

public class TestAutoWEKA {

	public static void main(String [] args) {
		AutoWEKAClassifier.main("-t ./resources/datasets/Yeast.arff -timeLimit 1 -no-cv".split(" "));
	}
}
