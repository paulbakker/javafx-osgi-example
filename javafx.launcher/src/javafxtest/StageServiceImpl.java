package javafxtest;

import org.amdatu.javafx.StageService;

import javafx.stage.Stage;

public class StageServiceImpl implements StageService{

	private final Stage m_stage;
	
	public StageServiceImpl(Stage stage) {
		m_stage = stage;
	}


	@Override
	public Stage getStage() {
		return m_stage;
	}
}
