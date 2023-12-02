package mystical.doublejump;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleJump implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("doublejump");
	public static final String MOD_ID = "doublejump";
	public static final String MOD_NAME = "Double jump mod";

	public static final Identifier C2S_DO_DOUBLEJUMP = new Identifier("doublejump", "request_effects");

	@Override
	public void onInitialize() {

	}



}