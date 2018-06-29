package ure.terrain;

import ure.UCell;
import ure.UColor;
import ure.UREActor;

import java.util.Random;

/**
 * Created by gilmore on 6/20/2018.
 *
 * A type of terrain which can be in a cell.
 *
 */

public abstract class URETerrain implements Cloneable {

    public static final String TYPE = "";

    public String name;
    public String type;
    public String walkmsg = "";
    public String bonkmsg = "";
    public char filechar;
    public char glyph;
    public String variants;

    public int[] fgcolor;
    public int[] bgcolor;
    public int[] bgvariance;
    public int[][] bgvariants;

    public UColor fgColor;
    public UColor bgColor;

    public UColor fgColorBuffer;
    public UColor bgColorBuffer;

    public boolean passable;
    public boolean opaque;
    public boolean glow = false;
    public float sunvis;

    public boolean isPassable() {
        return passable;
    }
    public boolean isPassable(UREActor actor) { return isPassable(); }
    public boolean isOpaque() {
        return opaque;
    }

    public void initialize() {
        fgColor = new UColor(fgcolor[0],fgcolor[1],fgcolor[2]);
        bgColor = new UColor(bgcolor[0],bgcolor[1],bgcolor[2]);
        fgColorBuffer = new UColor(0f,0f,0f);
        bgColorBuffer = new UColor(0f, 0f ,0f);
    }

    public void becomeReal() {
        initialize();
        if (bgvariants != null) {
            Random r = new Random();
            bgColor.set(bgvariants[r.nextInt(bgvariants.length - 1)]);
        }
        if (bgvariance != null) {
            Random r = new Random();
            bgColor.set(bgColor.iR() + r.nextInt(bgvariance[0]) - bgvariance[0] / 2,
                    bgColor.iG() + r.nextInt(bgvariance[1]) - bgvariance[1] / 2,
                    bgColor.iB() + r.nextInt(bgvariance[2]) - bgvariance[2] / 2);
        }
    }

    public char glyph() {
        return glyph;
    }

    public char glyph(int x, int y) {
        if (variants == null)
            return glyph();
        int seed = (x * y * 19 + 1883) / 74;
        int period = variants.length();
        return variants.charAt(seed % period);
    }

    public void moveTriggerFrom(UREActor actor, UCell cell) {
        if (isPassable(actor)) {
            actor.moveToCell(cell.areaX(), cell.areaY());
        } else {
            cell.area().commander().printScroll(bonkmsg);
        }
    }

    public boolean preventMoveFrom(UREActor actor) {
        return false;
    }

    public void walkedOnBy(UREActor actor, UCell cell) {
        printScroll(walkmsg, cell);
    }

    public void printScroll(String msg, UCell cell) {
        if (walkmsg != null)
            if (walkmsg.length() > 0)
                if (cell.area() != null)
                    if (cell.area().commander() != null)
                        cell.area().commander().printScroll(msg);
    }
    public URETerrain getClone() {
        try {
            return (URETerrain) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(" Cloning not allowed. ");
            return this;
        }
    }
}
