package example.javafx.otherscreen;

import org.apache.felix.dm.annotation.api.Component;

import example.javafx.ui.AppScreen;

@Component
public class OtherScreen implements AppScreen{

	@Override
	public String getName() {
		return "Other screen";
	}

}
