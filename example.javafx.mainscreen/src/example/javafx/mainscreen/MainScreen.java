package example.javafx.mainscreen;

import org.apache.felix.dm.annotation.api.Component;

import example.javafx.ui.AppScreen;
@Component
public class MainScreen implements AppScreen{

	@Override
	public String getName() {
		return "Main";
	}

}
