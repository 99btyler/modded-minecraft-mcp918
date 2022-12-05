# modded-minecraft-mcp918
![modded-minecraft-mcp918 gif](https://i.imgur.com/J4xnT1X.gif)

# Getting Started
Get this project set up locally
### Prerequisites
* Own a copy of Minecraft so you can create a 1.8.8 version folder
* Have a decompiled mcp918 (http://www.modcoderpack.com/)
### Setting up
* Once you have a decompiled mcp918, copy the mmmcp folder from this repository to mcp918/src/minecraft
* Using your Java IDE, open mcp918/eclipse
* Because mcp918 allows me to change Minecraft code, I did. However, I don't include any minecraft code files in this repository so you must manually add this required code to the files:

```java
// ----> inside C03PacketPlayer.java

// Change protected to public:
public double x;
public double y;
public double z;
```
```java
// ----> inside EntityPlayerSP.java

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
// ----> inside EntityRenderer.java

// In the setupCameraTransform() method after 'if (this.mc.gameSettings.viewBobbing)':
if (!MMMCP.getInstance().getFeature("Tracers").isEnabled()) {
  this.setupViewBobbing(partialTicks);
}

// At start of the renderHand() method:
final EventRenderHand eventRenderHand = new EventRenderHand();
MMMCP.getInstance().alertFeatures(eventRenderHand);
```
```java
// ----> inside KeyBinding.java

// Change private to public:
public boolean pressed;
```
```java
// ----> inside Minecraft.java

// Change private final to public:
public Session session;

// In the runTick() method after 'if (Keyboard.getEventKeyState())' after 'if (this.currentScreen != null) { } else {':
for (Feature feature : MMMCP.getInstance().getFeatures()) {
  if (feature.getKeybind() == i) {
    feature.toggle();
  }
}

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
// ----> inside NetHandlerPlayClient.java

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
// ----> inside RenderManager.java

// Change private to public:
public double renderPosX;
public double renderPosY;
public double renderPosZ;
```
```java
// ----> inside RenderPlayer.java

// At start of the renderOffsetLivingLabel() method:
final EventRenderEntityName eventRenderEntityName = new EventRenderEntityName(renderManager, entityIn, x, y, z);
MMMCP.getInstance().alertFeatures(eventRenderEntityName);
if (eventRenderEntityName.isCanceled()) {
  return;
}
```
