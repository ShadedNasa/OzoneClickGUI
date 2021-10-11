package ozone.mod.impl;

import org.lwjgl.input.Keyboard;

import de.Hero.settings.Setting;
import ozone.mod.Mod;
import ozone.mod.ModCategory;

public class ClickGUI extends Mod {

	public static ClickGUI guiSettings = new ClickGUI();
	
	public Setting blur;
	
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, ModCategory.RENDER);
		
		addSlider("Red", 0, 255, 209, true);
		addSlider("Green", 0, 255, 29, true);
		addSlider("Blue", 0, 255, 83, true);
		
		addCheckbox("Sound", true);
		this.blur = addCheckbox("Blur", true);
	}

	// Just the settings for the ClickGUI
	
	@Override
	public void onEnable() {
		toggle();
	}
	
	public void addSlider(String name, int min, int max, int defaultVal, boolean onlyIntegers) {
		Client.getOzone().setmgr.rSetting(new Setting(name, this, defaultVal, min, max, onlyIntegers));
	}
	
	public Setting addCheckbox(String name, boolean defaultVal) {
		Setting newset = new Setting(name, this, defaultVal);
		Client.getOzone().setmgr.rSetting(newset);
		return newset;
	}

}
