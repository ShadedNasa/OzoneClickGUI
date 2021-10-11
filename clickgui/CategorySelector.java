package ozone.gui.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import ozone.Client;
import ozone.gui.clickgui.utils.ColorUtils;
import ozone.mod.Mod;
import ozone.mod.ModCategory;
import ozone.utils.FormatUtils;

public class CategorySelector {

	public int x, y;
	public ModCategory category;
	public OzoneGui gui;
	
	public ArrayList<ModButton> modButtons = Lists.newArrayList();
		
	public CategorySelector(int x, int y, ModCategory category, OzoneGui gui) {
		this.x = x;
		this.y = y;
		this.category = category;
		this.gui = gui;
		
		int mbX = this.x + 55;
		int mbY = gui.y;
		for(Mod m : Client.getOzone().modManager.getModsInCategory(category)) {
			ModButton mb = new ModButton(mbX, mbY, m, this);
			this.modButtons.add(mb);
			mbY += 12;
		}
	}
	
	public void onMoved(int changeX, int changeY) {
		int index = 0;
		for(ModButton mb : this.modButtons) {
			mb.setX(this.x + 55);
			mb.setY(gui.y + (index * 12));
			mb.onMoved();
			index++;
		}
	}
	
	public void draw(int mouseX, int mouseY) {
		Gui.drawRect(x, y, x + 55, y + 12, Color.gray.darker().getRGB());
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(FormatUtils.formatCategoryName(category), x + 2, y + 2, OzoneGui.selected == category ? ColorUtils.getClickGUIColor().darker().getRGB() : Color.white.darker().getRGB());
	
		if(OzoneGui.selected == category) {
			for(ModButton mb : this.modButtons) {
				mb.draw(mouseX, mouseY);
			}
		}
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(OzoneGui.selected == category) {
			for(ModButton mb : this.modButtons) {
				mb.mouseClicked(mouseX, mouseY, button);
			}
		}
		if(isHovered(mouseX, mouseY)) {
			OzoneGui.selected = category;
		}
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if(OzoneGui.selected == this.category) {
			for(ModButton mb : this.modButtons) {
				mb.mouseReleased(mouseX, mouseY, state);
			}
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseY >= y && mouseX <= x + 55 && mouseY <= y + 45;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
