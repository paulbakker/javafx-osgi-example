package example.javafx.otherscreen;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import org.apache.felix.dm.annotation.api.Component;

import example.javafx.ui.AppScreen;

@Component
public class OtherScreen implements AppScreen{

	@Override
	public String getName() {
		return "Other screen";
	}

	@Override
	public Node getContent() {
		VBox vbox = new VBox();
		Button button = new Button("Other screen test reload");
		button.setOnAction(e -> System.out.println("test"));
		
		vbox.getChildren().add(button);
		
		return vbox;
	}

	@Override
	public int getPosition() {
		return 1;
	}

}
