package pk.webdoor.fx.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MessagesController implements Initializable {

	@FXML static Label messageBar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("MessagesController initialize called");

	}

	static FadeTransition messageTransition = null;

	public static void displayMessage(String message) {
		if (messageBar != null) {
			if (messageTransition != null) {
				messageTransition.stop();
			} else {
				messageTransition = new FadeTransition(Duration.millis(2000), messageBar);
				messageTransition.setFromValue(1.0);
				messageTransition.setToValue(0.0);
				messageTransition.setDelay(Duration.millis(1000));
				messageTransition.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						messageBar.setVisible(false);
					}
				});
			}
			messageBar.setText(message);
			messageBar.setVisible(true);
			messageBar.setOpacity(1.0);
			messageTransition.playFromStart();
		}
	}

}
