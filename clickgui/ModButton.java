package ozone.gui.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import com.google.common.collect.Lists;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import ozone.gui.clickgui.setting.Component;
import ozone.gui.clickgui.setting.impl.ElementCheckBox;
import ozone.gui.clickgui.setting.impl.ElementComboBox;
import ozone.gui.clickgui.setting.impl.ElementSlider;
import ozone.gui.clickgui.utils.ColorUtils;
import ozone.mod.Mod;

public class ModButton {

	public int x, y;
	public Mod mod;
	public CategorySelector parent;
	
	public ArrayList<Component> menuelements;
	
	public boolean extended = false;
		
	public ModButton(int x, int y, Mod mod, CategorySelector parent) {
		this.x = x;
		this.y = y;
		this.mod = mod;
		this.parent = parent;
		
		this.menuelements = Lists.newArrayList();
		if (parent.gui.setmgr.getSettingsByMod(mod) != null)
			for (Setting s : parent.gui.setmgr.getSettingsByMod(mod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}
		
		for(Component c : menuelements) {
			c.update();
		}
		onMoved();
	}
	
	public void onMoved() {
		double currentY = y + 4;
		for(Component c : menuelements) {
			c.x = x + 260;
			c.y = currentY;
			currentY += c.height + 4;
		}
	}
	
	public void draw(int mouseX, int mouseY) {
		Gui.drawRect(x, y, x + 255, y + 12, Color.gray.darker().darker().getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(mod.getName(), x + 2, y + 2, mod.isEnabled() ? ColorUtils.getClickGUIColor().darker().getRGB() : Color.white.darker().getRGB());
	
		
		if(extended) {
			int width = 0;
			int height = 0;
			for(Component c : menuelements) {
				if(c.width > width) {
					width = (int) c.width;
				}
				height += c.height + 4;
			}
			
			Gui.drawRect(x + 260, y, (x + 260) + width, y + height, Color.darkGray.darker().getRGB());
		
			for(Component c : menuelements) {
				c.drawScreen(mouseX, mouseY, 0);
			}
			
		}
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isHovered(mouseX, mouseY)) {
			if(button == 0)
				mod.toggle();
			if(button == 1)
				parent.gui.closeAllSettings();
				extended = !extended;
				if(!extended) {
					parent.gui.closeAllSettings();
				}
		}
		
		
		if(extended) {
			for(Component c : menuelements) {
				c.mouseClicked(mouseX, mouseY, button);
			}
		}
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if(extended) {
			for(Component c : menuelements) {
				c.mouseReleased(mouseX, mouseY, state);
			}
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseY >= y && mouseX <= x + 255 && mouseY <= y + 12;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
