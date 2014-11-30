package example.javafx.ui;

import javafx.scene.Node;

public interface AppScreen {
	String getName();
	Node getContent();
	int getPosition();
}
