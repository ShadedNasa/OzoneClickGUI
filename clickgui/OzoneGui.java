package ozone.gui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

import de.Hero.settings.SettingsManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ozone.Client;
import ozone.gui.clickgui.setting.Component;
import ozone.gui.clickgui.setting.impl.ElementSlider;
import ozone.gui.clickgui.utils.ColorUtils;
import ozone.mod.ModCategory;
import ozone.mod.impl.ClickGUI;

public class OzoneGui extends GuiScreen {

	public int x = 40, y = 40, x2, y2;
	public boolean isDragging = false;
	
	public SettingsManager setmgr;
	
	public static ModCategory selected;
	public ArrayList<CategorySelector> elements = Lists.newArrayList();
	
	public OzoneGui() {
		this.setmgr = Client.getOzone().setmgr;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (this.isDragging) {
			x = x2 + mouseX;
			y = y2 + mouseY;
			
			int index = 0;
			for(CategorySelector selector : elements) {
				selector.setX(x);
				int calcY = y + (index * 12);
				selector.setY(calcY);
				selector.onMoved(mouseX, (index * 12));
				index++;
			}
		}
		
		
		// Top Panel
		Gui.drawRect(x, y - 15, x + 310, y, ColorUtils.getClickGUIColor().darker().getRGB());
		mc.fontRendererObj.drawStringWithShadow("Ozone", x + 15, y - 10, -1);
		// Window
		Gui.drawRect(x, y, x + 310, y + 220, Color.DARK_GRAY.darker().getRGB());
		Gui.drawRect(x, y, x + 55, y + 220, Color.DARK_GRAY.getRGB());
	
		for(CategorySelector selector : elements) {
			selector.draw(mouseX, mouseY);
		}
	}
	
	public void closeAllSettings() {
		for(CategorySelector selector : elements) {
			for(ModButton mb : selector.modButtons) {
				mb.extended = false;
				for(Component c : mb.menuelements) {
					c.comboextended = false;
				}
			}
		}
	}

	@Override
	public void initGui() {
		// blur
		if(ClickGUI.guiSettings.blur.getValBoolean()) {
			if (OpenGlHelper.shadersSupported && mc.getRenderViewEntity() instanceof EntityPlayer) {
				if (mc.entityRenderer.theShaderGroup != null) {
					mc.entityRenderer.theShaderGroup.deleteShaderGroup();
				}
				mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
			}
		}
		
		this.elements.clear();
		
		int x = this.x;
		int y = this.y;
		for(ModCategory category : ModCategory.values()) {
			CategorySelector selector = new CategorySelector(x, y, category, this);
			elements.add(selector);
			y += 12;
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for(CategorySelector selector : elements) {
			selector.mouseClicked(mouseX, mouseY, mouseButton);
		}
		if(mouseButton == 0 && isTopBarHovered(mouseX, mouseY)) {
			x2 = this.x - mouseX;
			y2 = this.y - mouseY;
			this.isDragging = true;
		}
	}
	
	public boolean isTopBarHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseY >= (y - 15) && mouseX <= x + 310 && mouseY <= y;
	}
	
	public boolean isGuiHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseY >= y && mouseX <= x + 310 && mouseY <= y + 220;
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if(state == 0)
			this.isDragging = false;
	}
	
	@Override
	public void onGuiClosed() {
		if (mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}
		
		// Slider Fix by HeroCode. Modified to fit this gui.
		for (CategorySelector panel : elements) {
			if (panel.modButtons != null) {
				for (ModButton b : panel.modButtons) {
					if (b.extended) {
						for (Component e : b.menuelements) {
							if(e instanceof ElementSlider){
								((ElementSlider)e).dragging = false;
							}
						}
					}
				}
			}
		}
	}
}
