package example.javafx.ui.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
			primaryStage.setTitle("Hello World!");
			tabPane = new TabPane();

			screens.values().forEach(s -> {
				Tab tab = new Tab(s.getName());
				tab.setContent(new Rectangle(200, 200, Color.LIGHTSTEELBLUE));

				tabPane.getTabs().add(tab);
			});

			primaryStage.setScene(new Scene(tabPane, 300, 250));
			primaryStage.show();

		});

		System.out.println("Shown");
	}

	@ServiceDependency(removed = "removeScreen")
	public void addScreen(ServiceReference sr, AppScreen screen) {
		if (tabPane != null) {
			Platform.runLater(() -> {
				Tab tab = new Tab(screen.getName());
				tab.setContent(new Rectangle(200, 200, Color.LIGHTSTEELBLUE));

				tabPane.getTabs().add(tab);
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
