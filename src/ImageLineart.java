

import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
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
		imagePane = new AnchorPane();
		Slider slider = new Slider();
		AnchorPane.setLeftAnchor(slider, 5.0);
		AnchorPane.setRightAnchor(slider, 0.0);
		AnchorPane.setBottomAnchor(slider, 10.0);

		imagePane.getChildren().add(slider);

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

		//Image image = new Image(filename, MAXWIDTH, MAXHEIGHT, true, true);
		ImageView iv = new ImageView();
		iv.setImage(image);

		iv.setPreserveRatio(true);


		AnchorPane.setLeftAnchor(iv, 10.0);
		//AnchorPane.setRightAnchor(iv, 10.0);
		//AnchorPane.setTopAnchor(iv, 10.0);
		AnchorPane.setBottomAnchor(iv, 30.0);

		imagePane.getChildren().add(iv);

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
			root.setPrefWidth(imagePane.getWidth());
			fileNameInput.setText("");
		});

		//table.getChildren().addAll(fileNameInput, loadButton);
		AnchorPane.setTopAnchor(panel, 10.0);
		AnchorPane.setRightAnchor(panel, 10.0);
		AnchorPane.setLeftAnchor(panel, sceneWidth-PANELWIDTH);

		root.getChildren().add(panel);

		imagePane = loadImage("civ6.jpg");


		root.getChildren().add(imagePane);

		Scene scene = new Scene(root, sceneWidth, sceneHeight);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
