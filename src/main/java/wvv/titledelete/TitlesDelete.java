package wvv.titledelete;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

@Mod(
        modid = TitlesDelete.MODID,
        name = TitlesDelete.MODNAME,
        version = TitlesDelete.VERSION
)
@SideOnly(Side.CLIENT)
public class TitlesDelete {

    public static final String MODID = "titlesdelete";
    public static final String MODNAME = "Titles Delete";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null) {
            try {
                // Clear any active titles using the cached fields
                if (Minecraft.getMinecraft().ingameGUI != null) {
                    clearTitleFields();
                }
            } catch (Exception ignored) {
                // Silently ignore any errors
            }
        }
    }

    private void clearTitleFields() {
        try {
            GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
            
            // Clear title text fields using ReflectionHelper with field names
            ReflectionHelper.setPrivateValue(GuiIngame.class, gui, "", "displayedTitle", "field_175201_x");
            ReflectionHelper.setPrivateValue(GuiIngame.class, gui, "", "displayedSubTitle", "field_175200_y");
            
            // Clear timing fields to stop animations
            ReflectionHelper.setPrivateValue(GuiIngame.class, gui, 0, "titleDisplayTime", "field_175195_w");
            ReflectionHelper.setPrivateValue(GuiIngame.class, gui, 0, "titleFadeIn", "field_175199_z");
            ReflectionHelper.setPrivateValue(GuiIngame.class, gui, 0, "titleFadeOut", "field_175197_u");
        } catch (Exception ignored) {
            // Silently handle any reflection errors
        }
    }
}
