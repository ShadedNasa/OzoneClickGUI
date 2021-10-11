package ozone.gui.clickgui.utils;

import java.awt.Color;

import ozone.Client;

public class ColorUtils {

	public static Color getClickGUIColor() {
		int red = (int)Client.getOzone().setmgr.getSettingByName("Red").getValDouble();
		int green = (int)Client.getOzone().setmgr.getSettingByName("Green").getValDouble();
		int blue = (int)Client.getOzone().setmgr.getSettingByName("Blue").getValDouble();
	
		return new Color(red, green, blue);
	}
}
