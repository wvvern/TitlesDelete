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

import java.lang.reflect.Field;

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
                // Try to clear any active titles using reflection
                if (Minecraft.getMinecraft().ingameGUI != null) {
                    // Access GuiIngame fields to clear titles
                    Field titleField = GuiIngame.class.getDeclaredField("displayedTitle");
                    Field subTitleField = GuiIngame.class.getDeclaredField("displayedSubTitle");

                    titleField.setAccessible(true);
                    subTitleField.setAccessible(true);

                    // Clear any displayed titles
                    titleField.set(Minecraft.getMinecraft().ingameGUI, "");
                    subTitleField.set(Minecraft.getMinecraft().ingameGUI, "");
                }
            } catch (Exception ignored) {
            }
        }
    }
}
