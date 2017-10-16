package edu.augustana.csc285.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import edu.augustana.csc285.game.datamodel.Option;
import edu.augustana.csc285.game.datamodel.Slide;

public class SlideScreen implements Screen {
	public static final HashSet<Integer> KEY_SET = new HashSet<Integer>(
			Arrays.asList(8, 9, 10, 11, 12, 13, 14, 15, 16)); // 1
	public static final Skin DEFAULT_SKIN = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
	// 9
	private final int WIDTH_BUFFER = AdventureGame.GAME_SCREEN_WIDTH / 100;
	private final int HEIGHT_BUFFER = AdventureGame.GAME_SCREEN_HEIGHT / 100;
	private final AdventureGame game;
	private Slide slide;
	private ArrayList<Option> visibleOptions;
	private Texture image;
	private Button inventoryBtn;
	private Button playerStatBtn;
	private Button settingsBtn;
	private Texture backgroundImage;
	private OrthographicCamera camera;
	private Stage stage;
	private boolean popUp;
	private String rejectMessage;

	public SlideScreen(AdventureGame game, String rejectMessage) {
		this(game);
		this.popUp = true;
		this.rejectMessage = rejectMessage;
	}

	public SlideScreen(AdventureGame game) {
		// Set up data fields
		this.popUp = false;
		this.game = game;
		slide = game.manager.getCurrentSlide();
		image = new Texture(Gdx.files.internal(slide.getImage()));
		backgroundImage = new Texture("art/background.jpg");

		inventoryBtn = this.addTextureRegion("icons/inventory.jpg", new InventoryScreen(game), 3);
		// inventoryBtn.debug();
		playerStatBtn = this.addTextureRegion("icons/player-stat.jpg", new PlayerStatScreen(game), 2);
		// playerStatBtn.debug();
		settingsBtn = this.addTextureRegion("icons/settings.jpg", new SettingsScreen(game), 1);
		// settingsBtn.debug();

		visibleOptions = (ArrayList<Option>) slide.getVisibleOptions(game.manager.getPlayer());

		// Set up camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, AdventureGame.GAME_SCREEN_WIDTH, AdventureGame.GAME_SCREEN_HEIGHT);
		// Set up stage and table for buttons
		stage = new Stage(new ScreenViewport());
		stage.addActor(inventoryBtn);
		stage.addActor(playerStatBtn);
		stage.addActor(settingsBtn);
		Table buttonTable = new Table();

		buttonTable.setPosition((6 * AdventureGame.GAME_SCREEN_WIDTH) / 16 + WIDTH_BUFFER,
				AdventureGame.GAME_SCREEN_HEIGHT - ((6 * AdventureGame.GAME_SCREEN_HEIGHT) / 9) - (HEIGHT_BUFFER * 12)
						- AdventureGame.GAME_SCREEN_HEIGHT / 24);

		// Create and add buttons for ActionChoices
		for (int i = 0; i < visibleOptions.size(); i++) {
			Option option = visibleOptions.get(i);
			String displayString = (i + 1) + ".  " + option.getDesc();
			TextButton button = new TextButton(displayString, DEFAULT_SKIN);
			button.getLabel().setWrap(true);
			button.getLabel().setFontScale((float) 0.8);
			button.getLabel().setAlignment(Align.left);
			button.addListener(new InputListener() {
				public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
					if (option.getNextSlideIndex() != "-1") {
						if (option.isFeasible(game.manager.getPlayer())) {
							game.manager.applyOption(option);
							game.setScreen(new SlideScreen(game));
							dispose();
						} else {
							game.setScreen(new SlideScreen(game, option.getRejectMessage()));
						}
					} else {
						game.setScreen(new EndScreen(game));
						dispose();
					}
				}

				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
				}
			});
			// for now not measuring the width of the option string but assume
			// they are the same

			buttonTable.add(button).width((6 * AdventureGame.GAME_SCREEN_WIDTH) / 8)
					.height(AdventureGame.GAME_SCREEN_HEIGHT / 18).padTop(HEIGHT_BUFFER).row();

		}

		stage.addActor(buttonTable);

		Label description = new Label(slide.getDesc(), DEFAULT_SKIN);
		description.setWrap(true);
		ScrollPane scroll = new ScrollPane(description, DEFAULT_SKIN);
		scroll.setPosition((WIDTH_BUFFER * 2) + (23 * AdventureGame.GAME_SCREEN_HEIGHT) / 32,
				AdventureGame.GAME_SCREEN_HEIGHT - (HEIGHT_BUFFER * 2) - (AdventureGame.GAME_SCREEN_HEIGHT / 10)
						- ((3 * AdventureGame.GAME_SCREEN_HEIGHT) / 8));
		scroll.setSize(AdventureGame.GAME_SCREEN_WIDTH / 2, AdventureGame.GAME_SCREEN_HEIGHT / 3);
		scroll.setScrollingDisabled(true, false);
		stage.addActor(scroll);

		// create buttons for top right of screen (Inventory, Player Stats,
		// Settings)
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		// Draw background image
		game.batch.draw(backgroundImage, 0, 0, AdventureGame.GAME_SCREEN_WIDTH, AdventureGame.GAME_SCREEN_HEIGHT);
		game.batch.end();

		// tell the camera to update its matrices.
		camera.update();
		stage.act();
		stage.draw();

		// Draw ActionChoices
		for (int temp : KEY_SET) {
			if (Gdx.input.isKeyJustPressed(temp)) {
				int index = temp - 8;
				if (index < visibleOptions.size()) {
					Option option = visibleOptions.get(index);
					if (option.getNextSlideIndex() != "-1") {
						if (option.isFeasible(game.manager.getPlayer())) {
							game.manager.applyOption(option);
							game.setScreen(new SlideScreen(game));
							dispose();
						} else {
							game.setScreen(new SlideScreen(game, option.getRejectMessage()));
						}
					} else {
						game.setScreen(new EndScreen(game));
						dispose();
					}
				}
			}
		}

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

		// Draw the slide images
		game.batch.draw(image, WIDTH_BUFFER,
				Math.round(0.3583333333 * AdventureGame.GAME_SCREEN_HEIGHT * (image.getWidth() / image.getHeight())),

				Math.round(0.625 * AdventureGame.GAME_SCREEN_HEIGHT),
				Math.round(0.625 * AdventureGame.GAME_SCREEN_HEIGHT * (image.getWidth() / image.getHeight())));

		game.batch.end();
	}

	public Button addTextureRegion(String skinLocation, Screen screen, int locationInt) {
		Texture textureImage = new Texture(skinLocation);

		TextureRegion textureRegion = new TextureRegion(textureImage);
		TextureRegionDrawable textureRegionDrawable = new TextureRegionDrawable(textureRegion);
		Button button = new ImageButton(textureRegionDrawable);
		button.setSize(50, 50);
		button.setPosition(AdventureGame.GAME_SCREEN_WIDTH - (button.getWidth() + WIDTH_BUFFER) * locationInt,
				AdventureGame.GAME_SCREEN_HEIGHT - HEIGHT_BUFFER - button.getHeight());
		button.addListener(new InputListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(screen);
			}

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});
		return button;

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		if (popUp) {
			new Dialog("You cannot do this", DEFAULT_SKIN) {

				{
					text(rejectMessage);
					button("Back", game);
				}

				@Override
				protected void result(final Object object) {
					game.setScreen(new SlideScreen(game));
				}
			}.show(stage);
		}
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
	public void dispose() {
		image.dispose();
		backgroundImage.dispose();
		stage.dispose();
	}

}
