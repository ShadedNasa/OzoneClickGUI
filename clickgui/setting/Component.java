package ozone.gui.clickgui.setting;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import ozone.gui.clickgui.ModButton;
import ozone.gui.clickgui.OzoneGui;

public class Component {
	public OzoneGui clickgui;
	public ModButton parent;
	public Setting set;
	public double offset;
	public double x;
	public double y;
	public double width;
	public double height;
	
	public String setstrg;
	
	public boolean comboextended;
	
	public void setup(){
		clickgui = parent.parent.gui;
	}
	
	public void update() {
		x = parent.x + 255 + 2;
		y = parent.y + offset;
		width = 255 + 10;
		height = 15;
		
		String sname = set.getName();
		if(set.isCheck()){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			double textx = x + width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(setstrg);
			if (textx < x + 13) {
				width += (x + 13) - textx + 1;
			}
		}else if(set.isCombo()){
			height = comboextended ? set.getOptions().size() * (Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2) + 15 : 15;
			
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			int longest = Minecraft.getMinecraft().fontRendererObj.getStringWidth(setstrg);
			for (String s : set.getOptions()) {
				int temp = Minecraft.getMinecraft().fontRendererObj.getStringWidth(s);
				if (temp > longest) {
					longest = temp;
				}
			}
			double textx = x + width - longest;
			if (textx < x) {
				width += x - textx + 1;
			}
		}else if(set.isSlider()){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
			String displaymax = "" + Math.round(set.getMax() * 100D)/ 100D;
			double textx = x + width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(setstrg) - Minecraft.getMinecraft().fontRendererObj.getStringWidth(displaymax) - 4;
			if (textx < x) {
				width += x - textx + 1;
			}
		}
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		return isHovered(mouseX, mouseY);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
