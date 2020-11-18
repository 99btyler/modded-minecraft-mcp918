# modded-minecraft-mcp
Have a modded version of Minecraft 1.9.4 using mcp928

# Using this yourself
1. Own Minecraft, run Minecraft 1.9.4 at least once
2. Download this repository
3. Download mcp928 folder from http://www.modcoderpack.com/
4. Put the mcp928 folder inside this repository's folder
5. Using terminal, navigate to the mcp928 folder. Then, run decompile.sh or decompile.bat
6. Copy the src folder from this repository and paste it in mcp928/src/minecraft folder. Then, rename the copied src to mmmcp
7. Using a Java IDE, open mcp928/eclipse
8. Because mcp928 allows us to change Minecraft code, we will. Manually add this required code:

```java
// CPacketPlayer.java

// Change protected to public:
public double x;
public double y;
public double z;
public float yaw;
public float pitch;
public boolean onGround;
```
```java
// EntityPlayerSP.java

// At the start of the onLivingUpdate() method:
for (Feature feature : this.mc.getMMMCP().getFeatures()) {
  feature.tryOnEvent(new EventLivingUpdate());
}

// At the start of the attackEntityFrom() method:
if (source != DamageSource.inWall) {
  this.mc.getMMMCP().tryToggleFeatures(false, "Freecam");
}

// At the start of the respawnPlayer() method:
this.mc.getMMMCP().tryToggleFeatures(false, "Freecam", "Hold", "Jump", "Sneak", "Triggerbot", "Walk");
```
```java
// EntityRenderer.java

// At the start of the renderHand() method:
if (Minecraft.getMinecraft().getMMMCP().getFeature("Tracers").isEnabled()) {
  ((Tracers)Minecraft.getMinecraft().getMMMCP().getFeature("Tracers")).doTracers();
}
```
```java
// GuiIngame.java

// At the start of the renderPotionEffects() method:
if (this.mc.getMMMCP().getFeature("ScreenGuiIngame").isEnabled()) {
  return;
}
```
```java
// KeyBinding.java

// Change private to public:
public boolean pressed;
```
```java
// Minecraft.java

// Change private final to public:
public Session session;

// After all the other variables:
private MMMCP mmmcp;

// At the end of the startGame() method:
mmmcp = new MMMCP();

// After all the other methods:
public final MMMCP getMMMCP() {
  return mmmcp;
}

// In the runTickKeyboard() method after the this.currentScreen == null check:
for (Feature feature : mmmcp.getFeatures()) {
  feature.tryToggle(i);
}

// In the dispatchKeypresses() method after the i == this.gameSettings.keyBindScreenshot.getKeyCode() check:
ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc);

// At the start of the rightClickMouse() method:
for (Feature feature : mmmcp.getFeatures()) {
  feature.tryOnEvent(new EventRightClick());
}

// Change private to public:
public void clickMouse()
```
```java
// NetHandlerPlayClient.java

// At the start of the sendPacket() method:
for (Feature feature : Minecraft.getMinecraft().getMMMCP().getFeatures()) {

  final EventSendPacket eventSendPacket = (EventSendPacket)feature.tryOnEvent(new EventSendPacket(packetIn));
  
  if (eventSendPacket != null) {
    
    if (eventSendPacket.isCanceled()) {
      return;
    }
    
    packetIn = eventSendPacket.getPacket();
    
  }

}
```
```java
// RenderManager.java

// Change private to public:
public double renderPosX;
public double renderPosY;
public double renderPosZ;
```
```java
// RenderPlayer.java

// At the start of the renderEntityName() method:
if (Minecraft.getMinecraft().getMMMCP().getFeature("Nametags").isEnabled()) {
  ((Nametags)Minecraft.getMinecraft().getMMMCP().getFeature("Nametags")).doNametag(entityIn, entityIn.getDisplayName().getUnformattedText(), x, y, z, renderManager, getFontRendererFromRenderManager());
  return;
}
```

# Screenshots
![mcp298 screenshot](https://i.imgur.com/bau3Ouo.jpg)
