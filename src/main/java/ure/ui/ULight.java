package ure.ui;

import ure.areas.UArea;
import ure.math.UColor;
import ure.ui.UCamera;

import java.lang.Math;
import java.util.Random;

/**
 * ULight represents a light source placed in an area cell.
 *
 */

public class ULight {
    public static final int FLICKER_NONE = 0;
    public static final int FLICKER_FIRE = 1;
    public static final int FLICKER_PULSE = 2;
    public static final int FLICKER_FRITZ = 3;
    public static final int FLICKER_BLINK = 4;
    public static final int FLICKER_COMPULSE = 5;

    public UColor color;
    public int falloff = 1;
    public int range;
    int flickerStyle = 0;
    float flickerSpeed = 0f;
    float flickerIntensity = 0f;
    int flickerOffset = 0;

    Random random;
    UArea area;
    public int x,y;

    public ULight(int[] thecolor, int therange, int thefalloff) {
        color = new UColor(thecolor[0],thecolor[1],thecolor[2]);
        range = therange;
        falloff = thefalloff;
    }
    public ULight(UColor thecolor, int therange, int thefalloff) {
        color = thecolor;
        range = therange;
        falloff = thefalloff;
    }

    public void setFlicker(int style, float speed, float intensity, int offset) {
        flickerStyle = style;
        flickerSpeed = speed;
        flickerIntensity = intensity;
        flickerOffset = offset;
        random = new Random();
    }

    public void close() {
        removeFromArea();
    }

    public void moveTo(UArea thearea, int thex, int they) {
        x = thex;
        y = they;
        if (area != thearea) {
            if (area != null) {
                area.removeLight(this);
            }
            area = thearea;
            area.addLight(this);
        }
    }

    public void removeFromArea() {
        if (area != null) {
            area.removeLight(this);
        }
        area = null;
    }

    public boolean canTouch(UCamera camera) {
        int circleDistX = Math.abs(x - camera.getCenterColumn());
        int circleDistY = Math.abs(y - camera.getCenterRow());
        if (circleDistX > (camera.columns /2 + range)) return false;
        if (circleDistY > (camera.rows /2 + range)) return false;
        if (circleDistX <= (camera.columns /2)) return true;
        if (circleDistY <= (camera.rows /2)) return true;
        double cornerDistSq = Math.pow(circleDistX - camera.columns /2, 2) + Math.pow(circleDistY - camera.rows /2, 2);
        if (cornerDistSq <= Math.pow(range,2)) return true;
        return false;
    }
    public boolean canTouch(int tx, int ty) {
        if (intensityAtOffset(x - tx, y - ty) > 0.01f)
            return true;
        return false;
    }

    public float intensityAtTime(int time) {
        if (flickerStyle == FLICKER_NONE) return 1f;
        float i = 0f;
        time = (int)((float)(time + flickerOffset) * (flickerSpeed));
        switch (flickerStyle) {
            case FLICKER_FIRE: i =  intensityFlickerFire(time); break;
            case FLICKER_PULSE: i = intensityFlickerPulse(time); break;
            case FLICKER_FRITZ: i = intensityFlickerFritz(time); break;
            case FLICKER_BLINK: i = intensityFlickerBlink(time); break;
            case FLICKER_COMPULSE: i = intensityFlickerCompulse(time); break;
        }
        return 1f - (i * flickerIntensity);
    }

    float intensityFlickerFire(int time) {
        float f = (float)time / 6.0f;
        f = .75f + Math.max((float)(Math.sin(f * 2.7777)), (float)(Math.sin(f * 3.1117))) * .25f;
        return f;
    }
    float intensityFlickerPulse(int time) {
        float i = (float)Math.sin((double)time * 0.05);
        return i;
    }
    float intensityFlickerFritz(int time) {
        return 0f;
    }
    float intensityFlickerBlink(int time) {
        return 0f;
    }
    float intensityFlickerCompulse(int time) {
        float i = (float)Math.sin((double)time*0.05) + (float)Math.sin((double)time*0.03);
        return i;
    }
    public float intensityAtOffset(int xoff, int yoff) {
        xoff = (int)Math.pow(Math.abs(xoff),2);
        yoff = (int)Math.pow(Math.abs(yoff),2);
        double dist = Math.sqrt((double)xoff + (double)yoff);
        if (dist < falloff) {
            return 1f;
        } else if (dist > range) {
            return 0f;
        } else {
            float scale = ((float)dist - falloff) / ((float)range - falloff);
            double root = Math.sqrt(scale);
            return 1f - (float)root;
        }
    }
}
