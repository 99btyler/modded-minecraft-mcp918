package mmmcp.feature.event.events;

import mmmcp.feature.event.Cancelable;
import mmmcp.feature.event.Event;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

public class EventRenderEntityName extends Event implements Cancelable {

    private RenderManager renderManager;

    private EntityPlayer entityPlayer;

    private double x;
    private double y;
    private double z;

    private boolean canceled;

    public EventRenderEntityName(RenderManager renderManager, EntityPlayer entityPlayer, double x, double y, double z) {

        super(renderEntityName);

        this.renderManager = renderManager;

        this.entityPlayer = entityPlayer;

        this.x = x;
        this.y = y;
        this.z = z;

    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public EntityPlayer getEntityPlayer() {
        return entityPlayer;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
