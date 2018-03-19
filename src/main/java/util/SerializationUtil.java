package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.trees.RandomForest;

public class SerializationUtil {

	private SerializationUtil() {
	}

	public static void write(String path, RandomForest classifier) {
		String filePath = path + File.separator + "classifier";
		try (FileOutputStream f = new FileOutputStream(new File(filePath));
				ObjectOutputStream o = new ObjectOutputStream(f)) {
			o.writeObject(classifier);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static RandomForest read(String path) {
		String filePath = path + File.separator + "classifier";
		RandomForest classifier = null;
		try (FileInputStream f = new FileInputStream(new File(filePath));
				ObjectInputStream o = new ObjectInputStream(f)) {
			classifier = (RandomForest) o.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return classifier;
	}

}