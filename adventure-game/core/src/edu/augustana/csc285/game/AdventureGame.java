package edu.augustana.csc285.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import edu.augustana.csc285.game.datamodel.ConditionOperation;
import edu.augustana.csc285.game.datamodel.Effect;
import edu.augustana.csc285.game.datamodel.EffectOperation;
import edu.augustana.csc285.game.datamodel.GameData;
import edu.augustana.csc285.game.datamodel.Gender;
import edu.augustana.csc285.game.datamodel.GenderCondition;
import edu.augustana.csc285.game.datamodel.GenderEffect;
import edu.augustana.csc285.game.datamodel.Item;
import edu.augustana.csc285.game.datamodel.ItemCondition;
import edu.augustana.csc285.game.datamodel.ItemEffect;
import edu.augustana.csc285.game.datamodel.NameEffect;
import edu.augustana.csc285.game.datamodel.Option;
import edu.augustana.csc285.game.datamodel.Player;
import edu.augustana.csc285.game.datamodel.Property;
import edu.augustana.csc285.game.datamodel.PropertyCondition;
import edu.augustana.csc285.game.datamodel.PropertyType;
import edu.augustana.csc285.game.datamodel.Slide;
import edu.augustana.csc285.game.datamodel.Story;
import edu.augustana.csc285.game.datamodel.StoryManager;

public class AdventureGame extends Game {
	public static final int GAME_SCREEN_WIDTH = 800;
	public static final int GAME_SCREEN_HEIGHT = 480;
	StoryManager manager;
	SpriteBatch batch;
	BitmapFont font;

	public void create() {
		Story story = new Story(0);
		Slide s0 = new Slide("GameData/SlideImages/slide0.jpg",
				"You’re a young Swedish immigrant to America in 1880. You’ve made the tough decision to leave your family and life in Sweden behind. Will you survive and prosper in America?\n",
				null, null, 0);
		s0.addOption(new Option("Take the Journey", "You have embarked on a journey!", null, 1, null, null));
		Slide s1 = new Slide("GameData/SlideImages/slide1.jpg",
				"It’s the 1880s in Sweden. The opportunities in America, such as cheap land and a stable economy, make migration attractive to many Europeans who face problems related to overpopulation. Millions have migrated from their homelands for a new life. You now wish to join them. \nWho are you?",
				null, null, 1);
		Option temp = new Option("Anders Bengtsson ", "You choose Anders Bengtsson", null, 2, null, null);
		Effect effect = new GenderEffect(Gender.MALE);
		temp.addEffect(effect);
		effect = new NameEffect("Anders Bengtsson");
		temp.addEffect(effect);
		s1.addOption(temp);
		temp = new Option("Lovisa Eriksdotter", "You choose Lovisa Eriksdotter", null, 3, null, null);
		effect = new GenderEffect(Gender.FEMALE);
		temp.addEffect(effect);
		effect = new NameEffect("Lovisa Eriksdotter");
		temp.addEffect(effect);
		s1.addOption(temp);

		Slide s2 = new Slide("GameData/SlideImages/slide2.jpg",
				"You are an 18 year-old male from Mellby Parish, Småland. You are the youngest of 4 brothers and will not have any farmland to work. There are too many men in your family and not enough farmland. Additionally, the crop failures of 1867-1868 hit your family farm hard. You would also like to avoid Swedish military conscription. You are coming to America to own your own farmland or find a good job. ",
				null, null, 2);
		s2.addOption(new Option("Continue", null, null, 4, null, null));

		Slide s3 = new Slide("GameData/SlideImages/slide3.jpg",
				"You are an 18 year-old female maid from Stockholm. You dislike your job as maid and can’t seem to find a better one because there is too much competition (population has been booming in the city and countryside). You decide to seek better employment in America.",
				null, null, 3);
		s3.addOption(new Option("Continue", null, null, 4, null, null));

		Slide s4 = new Slide("GameData/SlideImages/slide4.jpg",
				"Receiving letters from those who journeyed to America prompted many of your family friends to pursue their own journey over. Life must be treating them well in America— economic opportunities, building their own legacy and family. The American Dream is a strong pull for Swedes, yourself included. \n"
						+ "\n" + "Choose a letter to read:",
				null, null, 4);

		Option johnDeereLetter = new Option("John Deere Letter", null, null, 5, null, null);
		johnDeereLetter.addEffect(new ItemEffect(
				new Item("John Deere Letter", "A letter from John Deere", 1, 5, "GameData/SlideImages/item5.jpg"),
				EffectOperation.PLUS));
		johnDeereLetter.addVisibleCondition(new GenderCondition(Gender.MALE));
		s4.addOption(johnDeereLetter);

		Option illinoisLetter = new Option("Illinois Letter", null, null, 6, null, null);
		illinoisLetter.addEffect(new ItemEffect(
				new Item("Illinois Letter", "A letter from Illinois", 1, 6, "GameData/SlideImages/item6.jpg"),
				EffectOperation.PLUS));
		s4.addOption(illinoisLetter);

		Option washingtonLetter = new Option("Washington Letter", null, null, 7, null, null);
		washingtonLetter.addEffect(new ItemEffect(
				new Item("Washington Letter", "A letter from Washington", 1, 7, "GameData/SlideImages/item7.jpg"),
				EffectOperation.PLUS));
		washingtonLetter.addVisibleCondition(new GenderCondition(Gender.FEMALE));
		s4.addOption(washingtonLetter);

		Option minnesotaLetter = new Option("Minnesota Letter", null, null, 8, null, null);
		minnesotaLetter.addEffect(new ItemEffect(
				new Item("Minnesota Letter", "A letter from Minnesota", 1, 8, "GameData/SlideImages/item8.jpg"),
				EffectOperation.PLUS));
		minnesotaLetter.addVisibleCondition(new GenderCondition(Gender.MALE));
		s4.addOption(minnesotaLetter);

		Slide s5 = new Slide("GameData/SlideImages/slide5.jpg",
				"To Whom It May Concern,\n"
						+ "We hope you find this letter in good spirits. On behalf of the John Deere Company, we thank you for meeting with us concerning about jobs within our factories. We are pleased to inform you you have been recruited for a position at the Moline factory site. We are looking forward to seeing you again and have safe travels. Please bring along this notarized letter with you to our recruitment office.\n"
						+ "God speed,\n" + "From the Office of John Deere, President of John Deere Co.",
				null, null, 5);
		s5.addOption(new Option("Continue", null, null, 9, null, null));

		Slide s6 = new Slide("GameData/SlideImages/slide6.jpg",
				"Dear Cousin,\n"
						+ "May God find you well, dear friend! God is good and God is plentiful. He has shown me the way to a brighter future here in America. My journey has led myself and my travelling companions to a quaint town in the state of Illinois called Moline. It may be quaint now but we are fortunate to have factories and farming jobs available for the people. I have friends all over Illinois including Rockford and Chicago! We stay in touch through correspondence and the occasional social visit when we can. My piece of advice for you, should you make the journey, is to find peace with yourself and our loving God. For He is the way and will guide you! The Christian faith has given me hope and salvation along my journey. My fellow companions and myself attend the Lutheran service held by a pastor who is also from Sweden. It is wonderful to have familiarity in a strange place. Services are something to look forward to as it brings us closer to our Maker and builds a strong sense of community. I pray for all the success in the world for you as you make your own journey. \n"
						+ "Lovingly,\n" + "Erika Leiffson",
				null, null, 6);
		s6.addOption(new Option("Continue", null, null, 9, null, null));

		Slide s7 = new Slide("GameData/SlideImages/slide7.jpg",
				"Dear Cousin,\n"
						+ "I have been blessed to have found a place I can call home. My journeys were long, and at times I had felt grief for leaving my beautiful Sweden. I remember so clearly how it felt to be a stranger in a strange place. Only having a small trunk and broken English left me apprehensive of my surroundings. I am thankful that I have experienced no ill wills on my passage to America. I had heard some of my kin had travelled to the far west of this vast country, in a place called Washington state. My family settled near Tacoma and are now prosperous farmers and fishermen! I decided to try my hand for this place. I took the Walla Walla railroad instead of the Northern Pacific, as it was the cheaper option. I was taken to Seattle and found a boardinghouse for Swedes. Within a month I had found employment as a maid to one of the founding families. They treat me well despite some language barrier, but I am becoming more proficient and feel more confident in my new home.\n"
						+ "I do wish you well and hope someday to meet you again in my new home! Give my love to my family and friends!\n"
						+ "\n" + "Linnea Parsson\n",
				null, null, 7);
		s7.addOption(new Option("Continue", null, null, 9, null, null));

		Slide s8 = new Slide("GameData/SlideImages/slide8.jpg",
				"Dear Cousin,\n"
						+ "I am content to report that I have found steady employment as my own master. I had arrived only two months in St. Paul, working odds and ends in the town when I noticed an advertisement of purchasable land not far from my lodgings. Land is so cheap here in America! I saved enough to buy my own lot and have begun to till and grind the land. My hope is to grow peas and potatoes and sell them at market. Let us pray for a good harvest this next season!\n"
						+ "Life has treated me well. The journey is hard on everyone, harder for others. My companion on the trip to America arrived well but I have received word that he has fallen into poverty. He was swindled by a false recruiter at the docks. He is struggling to find employment in the city but, God willing, I hope he will find something soon!\n"
						+ "As you make the journey, make sure to bring plenty of food. They treat you well on the ships and trains but the journey alone can make one homesick. A little piece of home can appease the qualms of the stomach and might make you a friend or two!\n"
						+ "Godspeed dear cousin! \n" + "Arvid Lothbrok\n",
				null, null, 8);
		s8.addOption(new Option("Continue", null, null, 9, null, null));

		Slide s9 = new Slide("GameData/SlideImages/slide9.jpg",
				"To start your journey, you need a few provisions. The first and foremost being your papers. You have $70 USD worth of Kronor (Swedish money)  right now. Do you:\n",
				null, null, 9);
		temp = new Option("Buy official papers from your home parish $20 or 80 SEK", null, null, 10, null, null);
		temp.addFeasibleCondition(
				new PropertyCondition(new Property(PropertyType.GOLD, 20), ConditionOperation.GREATER_OR_EQUAL));
		temp.addEffect(new ItemEffect(new Item("Official papers", null, 1, 10, null), EffectOperation.PLUS));
		s9.addOption(temp);
		temp = new Option("Buy forged papers $10 or 40 SEK", null, null, 10, null, null);
		temp.addFeasibleCondition(
				new PropertyCondition(new Property(PropertyType.GOLD, 10), ConditionOperation.GREATER_OR_EQUAL));
		temp.addEffect(new ItemEffect(new Item("Forged papers", null, 1, 11, null), EffectOperation.PLUS));
		s9.addOption(temp);

		Slide s10 = new Slide("GameData/SlideImages/slide10.jpg",
				"Excellent. You are the first in your family to take on this journey. They are counting on your success in America. Your family has pooled enough money for passage, thankfully. You cannot take too much with you, so you must choose carefully which items to bring. Some items are more useful than others but even the smallest, trivial item can lead to unexpected use. Remember, you already have some money left after purchasing papers.  \n",
				null, null, 10);
		Option bible = new Option("A Bible", null, null, 10, null, null);
		bible.addVisibleCondition(new ItemCondition(new Item("Bible", null, 1, 12, null), ConditionOperation.EQUAL));
		bible.addEffect(new ItemEffect(new Item("Bible", null, 1, 12, null), EffectOperation.PLUS));
		s10.addOption(bible);
		Option familyHeirloom = new Option("Family Heirloon", null, null, 10, null, null);
		familyHeirloom.addVisibleCondition(
				new ItemCondition(new Item("Family Heirloom", null, 1, 13, null), ConditionOperation.EQUAL));
		familyHeirloom.addEffect(new ItemEffect(new Item("Family Heirloom", null, 1, 13, null), EffectOperation.PLUS));
		s10.addOption(familyHeirloom);
		Option sewingMachine = new Option("sewingMachine", null, null, 10, null, null);
		sewingMachine.addVisibleCondition(
				new ItemCondition(new Item("Sewing Machine", null, 1, 14, null), ConditionOperation.EQUAL));
		sewingMachine.addEffect(new ItemEffect(new Item("Sewing Machine", null, 1, 14, null), EffectOperation.PLUS));
		s10.addOption(sewingMachine);
		Option medicine = new Option("Medicine", null, null, 10, null, null);
		medicine.addVisibleCondition(
				new ItemCondition(new Item("Medicine", null, 1, 15, null), ConditionOperation.EQUAL));
		medicine.addEffect(new ItemEffect(new Item("Medicine", null, 1, 15, null), EffectOperation.PLUS));
		s10.addOption(medicine);
		Option curedMeat = new Option("Cured Meat", null, null, 10, null, null);
		curedMeat.addVisibleCondition(
				new ItemCondition(new Item("Cured Meat", null, 1, 16, null), ConditionOperation.EQUAL));
		curedMeat.addEffect(new ItemEffect(new Item("Cured Meat", null, 1, 16, null), EffectOperation.PLUS));
		s10.addOption(curedMeat);

		story.addSlide(s0);
		story.addSlide(s1);
		story.addSlide(s2);
		story.addSlide(s3);
		story.addSlide(s4);
		story.addSlide(s5);
		story.addSlide(s6);
		story.addSlide(s7);
		story.addSlide(s8);
		story.addSlide(s9);
		story.addSlide(s10);

		manager = new StoryManager(story, "Unknown", story.getStartingSlideIndex());
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
