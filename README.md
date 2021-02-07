# modded-minecraft-mcp
Have a modded version of Minecraft 1.8.8 using mcp918

# Using this yourself
1. Own Minecraft, run Minecraft 1.8.8 at least once
2. Download this repository
3. Download mcp918 folder from http://www.modcoderpack.com/
4. Put the mcp918 folder inside this repository's folder
5. Using terminal, navigate to the mcp918 folder. Then, run decompile.sh or decompile.bat
6. Copy the src folder from this repository and paste it in mcp918/src/minecraft folder. Then, rename the copied src to mmmcp
7. Using a Java IDE, open mcp918/eclipse
8. Because mcp918 allows us to change Minecraft code, we will. Manually add this required code:

```java
// ----> C03PacketPlayer.java

// Change protected to public:
public double x;
public double y;
public double z;
```
```java
// ----> EntityPlayerSP.java

// At start of the attackEntityFrom() method:
if (source != DamageSource.inWall) {
  MMMCP.getInstance().ableFeatures(false, new String[] {"Freecam"});
}

// At start of the respawnPlayer() method:
MMMCP.getInstance().ableFeatures(false, new String[] {"Freecam", "Hold", "Jump", "Sneak", "Triggerbot", "Walk"});

// At start of the onLivingUpdate() method:
final EventLivingUpdate eventLivingUpdate = new EventLivingUpdate();
MMMCP.getInstance().alertFeatures(eventLivingUpdate);
```
```java
// ----> EntityRenderer.java

// In the setupCameraTransform() method after 'if (this.mc.gameSettings.viewBobbing)':
if (!MMMCP.getInstance().getFeature("Tracers").isEnabled()) {
  this.setupViewBobbing(partialTicks);
}

// At start of the renderHand() method:
final EventRenderHand eventRenderHand = new EventRenderHand();
MMMCP.getInstance().alertFeatures(eventRenderHand);
```
```java
// ----> KeyBinding.java

// Change private to public:
public boolean pressed;
```
```java
// ----> Minecraft.java

// Change private final to public:
public Session session;

// In the runTick() method after 'if (Keyboard.getEventKeyState())' after 'if (this.currentScreen != null) { } else {':
for (Feature feature : MMMCP.getInstance().getFeatures()) {
  if (feature.getKeybind() == i) {
    feature.toggle();
  }
}

// In the dispatchKeypresses() method after the 'else if (i == this.gameSettings.keyBindScreenshot.getKeyCode())':
ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc);

// Change private to public:
public void clickMouse()

// At start of the clickMouse() method:
final EventClickLeft eventClickLeft = new EventClickLeft();
MMMCP.getInstance().alertFeatures(eventClickLeft);

// At start of the rightClickMouse() method:
final EventClickRight eventClickRight = new EventClickRight();
MMMCP.getInstance().alertFeatures(eventRightClick);
```
```java
// ----> NetHandlerPlayClient.java

// At start of the addToSendQueue() method:
final EventSendPacket eventSendPacket = new EventSendPacket(packetIn);
MMMCP.getInstance().alertFeatures(eventSendPacket);
if (eventSendPacket.isCanceled()) {
  return;
} else {
  packetIn = eventSendPacket.getPacket();
}
```
```java
// ----> RenderManager.java

// Change private to public:
public double renderPosX;
public double renderPosY;
public double renderPosZ;
```
```java
// ----> RenderPlayer.java

// At start of the renderOffsetLivingLabel() method:
final EventRenderEntityName eventRenderEntityName = new EventRenderEntityName(renderManager, entityIn, x, y, z);
MMMCP.getInstance().alertFeatures(eventRenderEntityName);
if (eventRenderEntityName.isCanceled()) {
  return;
}
```
