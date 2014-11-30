package example.javafx.ui.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import org.apache.felix.dm.annotation.api.Component;
import org.apache.felix.dm.annotation.api.ServiceDependency;
import org.apache.felix.dm.annotation.api.Start;
import org.osgi.framework.ServiceReference;

import example.javafx.launcher.StageService;
import example.javafx.ui.AppScreen;

@Component
public class UI {

	@ServiceDependency
	private volatile StageService m_stageService;
	private volatile TabPane tabPane;

	private final Map<ServiceReference, AppScreen> screens = new ConcurrentHashMap<>();

	@Start
	public void start() {
		Platform.runLater(() -> {

			Stage primaryStage = m_stageService.getStage();
			primaryStage.setTitle("Tabs example!");
			tabPane = new TabPane();

			screens.values().forEach(this::createTab);

			primaryStage.setScene(new Scene(tabPane, 300, 250));
			primaryStage.show();

		});
	}

	private void createTab(AppScreen s) {
		Tab tab = new Tab(s.getName());
		tab.setContent(s.getContent());
		if(s.getPosition() < tabPane.getTabs().size()) {
			tabPane.getTabs().add(s.getPosition(), tab);
		} else {
			tabPane.getTabs().add(tab);
		}
		tabPane.getSelectionModel().select(tabPane.getTabs().size()-1);
	}

	@ServiceDependency(removed = "removeScreen")
	public void addScreen(ServiceReference sr, AppScreen screen) {
		if (tabPane != null) {
			Platform.runLater(() -> {
				createTab(screen);
			});
		}

		screens.put(sr, screen);

	}

	public void removeScreen(ServiceReference sr) {
		Platform.runLater(() -> {
			AppScreen remove = screens.remove(sr);
			Optional<Tab> findAny = tabPane.getTabs().stream()
					.filter(t -> t.getText().equals(remove.getName()))
					.findAny();
			if (findAny.isPresent()) {
				tabPane.getTabs().remove(findAny.get());
			}
		});
	}
}
