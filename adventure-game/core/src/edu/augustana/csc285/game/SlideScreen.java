package edu.augustana.csc285.game;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	private final int LEFT_BUFFER = 5;
	private final int BUFFER = 20;
	private AdventureGame game;
	private Slide slide;
	private Texture image;
	private List<String> options;
	private OrthographicCamera camera;
	private int input;
	private String continueMessage;
	private int numOptions;
	private Stage stage;

	
	public SlideScreen(AdventureGame game) {
		slide = new Slide("test_image", "You appear to have stumbled upon a testing slide!", "test url", "", 0, null);
		//can test for different image with changing test_image to test_image2
		this.game = game;
		options = new ArrayList<String>();
		
		numOptions = 4; // eventually change to slide.getOptionStringList().size();
		
		//temporary instantiation of options (for now)
		for (int i = 0; i < numOptions; i++) {
			options.add("Go west");
		}
		
		this.image = new Texture("slideImages/" + slide.getImage() + ".png");
		continueMessage = "";
		camera = new OrthographicCamera();
		camera.setToOrtho(false, AdventureGame.GAME_SCREEN_WIDTH, 
								AdventureGame.GAME_SCREEN_HEIGHT);
		stage = new Stage(new ScreenViewport());
		Button button = LibGdxUtility.createChangeScreenButton(game,new MainMenuScreen(game), "MenuButton", "skin/glassy-ui.json", 50, 50, 50, 50);
		stage.addActor(button);

	}
	
	@Override
	public void render (float delta) {	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// tell the camera to update its matrices.
		camera.update();
		stage.act();
		stage.draw();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		game.batch.setProjectionMatrix(camera.combined);
		
		if (image.getHeight() >= image.getWidth()) {
			drawScreenForPortraitImage();
		} else {
			drawScreenForPortraitImage();
		}
		
		
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose () {
		image.dispose();
	}
	
	public void drawScreenForPortraitImage() {
		game.batch.begin();
		//Draws the image
		game.batch.draw(image, BUFFER, 
				(AdventureGame.GAME_SCREEN_HEIGHT - image.getHeight()) / 2); // this centers the image height-wise 
		//Draws the url or image description
		game.font.draw(game.batch, slide.getUrl(), BUFFER,
				AdventureGame.GAME_SCREEN_HEIGHT / 2 - image.getHeight() / 2 - 5);
		//Draws the description or the directions for the current slide
		game.font.draw(game.batch, slide.getDesc() + "\n\nYou may:", image.getWidth() + 75, 350);
		//Draws the options and checks user input
		for (int i = 0; i < numOptions; i++) {
			game.font.draw(game.batch, i + 1 + ". " + options.get(i), image.getWidth() + 100, 290 - BUFFER * i);
			
		}
		//Draws the prompt and includes user input when user enters a number
		if (input > 0 && input <= numOptions) {
			game.font.draw(game.batch, "What is your choice?   " + input + "", image.getWidth() + 75, 290 - BUFFER * numOptions - 30);		
		} else { //Draw without input
			game.font.draw(game.batch, "What is your choice?   __", image.getWidth() + 75, 290 - BUFFER * numOptions - 30);		

		}
		//Draws the continue message after player presses a number
		game.font.draw(game.batch, continueMessage, 590, 30);
				
		checkUserInput();

		 
		
		game.batch.end();
	}
	
	public void drawScreenForLandscapeImage() {
		
	}
	
	//Checks user input
	public void checkUserInput() {
		if (Gdx.input.isKeyPressed(Keys.NUM_1)) {
			input = 1;
			continueMessage = "Press SPACE BAR to continue";
		} else if (Gdx.input.isKeyPressed(Keys.NUM_2)) {
			input = 2;
			continueMessage = "Press SPACE BAR to continue";
		} else if (Gdx.input.isKeyPressed(Keys.NUM_3)) {
			input = 3;
			continueMessage = "Press SPACE BAR to continue";
		} else if (Gdx.input.isKeyPressed(Keys.NUM_4)) {
			input = 4;
			continueMessage = "Press SPACE BAR to continue";
		}
		
		//Continue to next slide when space is pressed
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			if (input > 0 && input <= numOptions) {
				game.setScreen(new SlideScreen(game));
				dispose();
		}
			
		}
	}
}


