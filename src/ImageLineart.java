

import java.io.FileNotFoundException;
import java.io.IOException;

import application.ImageProcessing;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Shape;


public class ImageLineart extends Application {
	double sceneWidth = 800;
	double sceneHeight = 600;
	double MAXWIDTH = 800;
	double MAXHEIGHT = 600;
	double PANELWIDTH = 200;
	private AnchorPane imagePane;

	public AnchorPane loadImage(String filename){
		//Soopi kood. PS! Ta ei tea mis ta teeb.
		try {
			ImageProcessing a = new ImageProcessing(filename);
			a.gaussianFilter();
			a.sobelFilter();
			a.saveGrayImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Soopi kood lõppeb
		
		Image modImage = new Image("grad_angle.jpg");
		
		
		imagePane = new AnchorPane();
		
		Image imgPreview = new Image(filename);
		double x = imgPreview.getWidth();
		double y = imgPreview.getHeight();
		double ratio = y/x;
		Image image;
		if (ratio > MAXHEIGHT/MAXWIDTH){
			if (y > MAXHEIGHT){
				image = new Image(filename, 0, MAXHEIGHT, true, true);
			} else {
				image = new Image(filename);
			}
		} else {
			if (x > MAXWIDTH){
				image = new Image(filename, MAXWIDTH, 0, true, true);
			} else {
				image = new Image(filename);
			}
		}

	
		
		Slider slider = new Slider(0, image.getWidth(), 0);
		AnchorPane.setLeftAnchor(slider, 5.0);
		AnchorPane.setRightAnchor(slider, 0.0);
		AnchorPane.setBottomAnchor(slider, 10.0);

		imagePane.getChildren().add(slider);

		
		//Image image = new Image(filename, MAXWIDTH, MAXHEIGHT, true, true);
		//ImageView iv = new ImageView();
		//iv.setImage(image);

		//iv.setPreserveRatio(true);
		
		Canvas canvas = new Canvas(image.getWidth(), image.getHeight());

		
		AnchorPane.setLeftAnchor(canvas, 10.0);
		//AnchorPane.setRightAnchor(iv, 10.0);
		//AnchorPane.setTopAnchor(iv, 10.0);
		AnchorPane.setBottomAnchor(canvas, 30.0);
		
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight());
		
		imagePane.getChildren().add(canvas);
		
		
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
				gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, 0, image.getWidth(), image.getHeight());
				gc.drawImage(modImage, 0, 0, slider.getValue(), modImage.getHeight(), 0, 0, slider.getValue(), modImage.getHeight());
			}
		});
		//slider.prefWidth(imagePane.getWidth());


		imagePane.setPrefWidth(image.getWidth());
		imagePane.setPrefHeight(image.getHeight());

		AnchorPane.setLeftAnchor(imagePane, 10.0);
		AnchorPane.setRightAnchor(imagePane, PANELWIDTH);
		AnchorPane.setTopAnchor(imagePane, 10.0);
		AnchorPane.setBottomAnchor(imagePane, 10.0);
		return imagePane;
	}

	@Override
	public void start(Stage primaryStage) {


		AnchorPane root = new AnchorPane();

		GridPane panel = new GridPane();
		TextField fileNameInput = new TextField();
		fileNameInput.setPromptText("File name");
		fileNameInput.getText();
		Button loadButton = new Button("Load file");

		panel.setVgap(8);
		panel.setPadding(new Insets(10, 20, 0, 20));
		panel.add(fileNameInput, 0, 0);
		panel.add(loadButton, 0, 1);

		fileNameInput.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent ke){
				if (ke.getCode().equals(KeyCode.ENTER)){ //Ma ei tea, kuidas seda koodi saaks teise meetodisse panna (et seda ei peaks mitmes kohas kopeerima)
					root.getChildren().remove(imagePane);
					imagePane = loadImage(fileNameInput.getText());
					root.getChildren().add(imagePane);
					System.out.println(imagePane.getPrefWidth());
					primaryStage.setWidth(imagePane.getPrefWidth()+PANELWIDTH+30);
					primaryStage.setHeight(imagePane.getPrefHeight()+90);
					AnchorPane.setLeftAnchor(panel, imagePane.getPrefWidth()+20);

					fileNameInput.setText("");
				}
			}
		});

		loadButton.setOnMouseClicked(e -> {
			root.getChildren().remove(imagePane);			
			imagePane = loadImage(fileNameInput.getText());
			root.getChildren().add(imagePane);
			System.out.println(imagePane.getPrefWidth());
			primaryStage.setWidth(imagePane.getPrefWidth()+PANELWIDTH+30);
			primaryStage.setHeight(imagePane.getPrefHeight()+90);
			AnchorPane.setLeftAnchor(panel, imagePane.getPrefWidth()+20);

			fileNameInput.setText("");
		});
		
		

		//table.getChildren().addAll(fileNameInput, loadButton);
		AnchorPane.setTopAnchor(panel, 10.0);
		AnchorPane.setRightAnchor(panel, 10.0);
		AnchorPane.setLeftAnchor(panel, sceneWidth-PANELWIDTH);

		root.getChildren().add(panel);

		//imagePane = loadImage("civ6.jpg");


		//root.getChildren().add(imagePane);

		Scene scene = new Scene(root, sceneWidth, sceneHeight);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
