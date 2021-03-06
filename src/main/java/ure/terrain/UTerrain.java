package ure.terrain;

import ure.areas.UCell;
import ure.math.UColor;
import ure.actors.UActor;

/**
 * UTerrain defines all the public methods of terrain.  It should not be implemented directly to create new custom terrain.
 * Instead, TerrainDeco should be extended.
 */
public interface UTerrain {
    boolean isPassable();
    boolean isPassable(UActor actor);
    boolean isOpaque();
    boolean breaksLatch();
    void initialize();
    void becomeReal(UCell c);
    char glyph();
    char glyph(int x, int y);
    int glyphOffsetX();
    int glyphOffsetY();
    void moveTriggerFrom(UActor actor, UCell cell);
    boolean preventMoveFrom(UActor actor);
    float moveSpeed(UActor actor);
    void walkedOnBy(UActor actor, UCell cell);
    boolean isInteractable(UActor actor);
    float interactionFrom(UActor actor);
    void printScroll(String msg, UCell cell);
    float sunvis();
    void animationTick();
    boolean glow();
    UColor bgColor();
    UColor bgColorBuffer();
    UColor fgColor();
    UColor fgColorBuffer();
    String name();
    String bonkmsg();
}
