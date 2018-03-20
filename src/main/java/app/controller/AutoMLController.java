package app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.model.AutoMLModel;
import core.AutoMLClassifier;
import util.SerializationUtil;
import weka.classifiers.trees.RandomForest;

@Controller
public class AutoMLController {
	private static final String UPLOADED_FOLDER = "Data/";

	@GetMapping("/")
	public String greetingForm(Model model) {
		model.addAttribute("autoMLModel", new AutoMLModel());
		return "autoML";
	}

	@PostMapping("/train")
	public String submitTrain(@ModelAttribute AutoMLModel model, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws Exception {

		String id = UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
		model.setId(id);

		if (file.isEmpty()) {
			return "result";
		}

		try {
			String folderPath = UPLOADED_FOLDER + id + File.separator;

			File directory = new File(folderPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}

			byte[] bytes = file.getBytes();
			Path path = Paths.get(folderPath + "train.csv");
			Files.write(path, bytes);

			AutoMLClassifier aml = new AutoMLClassifier();
			RandomForest classifier = aml.train(path.toString());

			SerializationUtil.write(folderPath, classifier);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "autoML";
	}

	@PostMapping("/test")
	public String submitTest(@ModelAttribute AutoMLModel model, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) throws Exception {

		if (file.isEmpty()) {
			return "result";
		}

		try {

			String folderPath = "grounding/";

			File directory = new File(folderPath);
			if (!directory.exists()) {
				return "result";
			}

			byte[] bytes = file.getBytes();
			Path path = Paths.get(folderPath + "test.csv");
			Files.write(path, bytes);

			RandomForest classifier = SerializationUtil.read(folderPath);
			AutoMLClassifier aml = new AutoMLClassifier();
			List<String> result = aml.predict(classifier, folderPath + "train.csv", folderPath + "test.csv");
			model.setHasResult(true);
			model.setResult(result);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "autoML";
	}

	@GetMapping("/test/{id}")
	public String displayTest(@ModelAttribute AutoMLModel model, RedirectAttributes redirectAttributes)
			throws Exception {

		return "autoML";
	}

}
