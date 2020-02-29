# modded-minecraft-mcp
Have a modded version of Minecraft 1.9.4 using mcp928

# Using this yourself
1. Own Minecraft, run Minecraft 1.9.4 at least once
2. Download mcp928 from http://www.modcoderpack.com/
3. Inside the mcp928 folder, find & run the decompile.sh or decompile.bat file
4. Because mcp928 allows us to change Minecraft code, we will. Manually add this required code:
```java
// EntityPlayerSP.java

// After the onLivingUpdate() method:
for (Feature feature : this.mc.getMMMCP().getFeatures()) {
  feature.tryOnEvent(new EventLivingUpdate());
}
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

// Change private to public:
public void clickMouse()
```
```java
// RenderManager.java

// Change private to public:
public double renderPosX;
public double renderPosY;
public double renderPosZ;
```
5. Inside the mcp928 folder, find & open the eclipse folder using a Java IDE

# Screenshots
N/A
