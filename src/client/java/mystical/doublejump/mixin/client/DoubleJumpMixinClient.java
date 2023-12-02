package mystical.doublejump.mixin.client;

import io.netty.buffer.Unpooled;
import java.lang.Math;
import mystical.doublejump.DoubleJump;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class DoubleJumpMixinClient {
    @Unique
    private int jumpCount = 0;
    @Unique
    private boolean jumpedLastTick = false;

    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void tickMovement(CallbackInfo info) {
        ClientPlayerEntity player = (ClientPlayerEntity) (Object) this;
        if(player.isOnGround()) {
            jumpCount = 1;
        } else if (!jumpedLastTick && jumpCount != 0 && (player.getVelocity().y < 0)) {
            if (player.input.jumping && !player.getAbilities().flying) {
                jumpCount = 0;
                player.setVelocity(player.getVelocity().x * 2.5, Math.sqrt(player.getVelocity().y * player.getVelocity().y) * 2, player.getVelocity().z * 2);
                player.jump();
                PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
                passedData.writeUuid(player.getUuid());

                ClientPlayNetworking.send(DoubleJump.C2S_DO_DOUBLEJUMP, passedData);
            }
        }
        jumpedLastTick = player.input.jumping;
    }

}