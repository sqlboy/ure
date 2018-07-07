package ure;

import ure.actors.UActor;
import ure.terrain.UTerrainCzar;
import ure.things.UThingCzar;

import static ure.ExampleGame.actorCzar;

public class UCartographer {

    UTerrainCzar terrainCzar;
    UThingCzar thingCzar;

    public UCartographer(UTerrainCzar theTerrainCzar, UThingCzar theThingCzar) {
        terrainCzar = theTerrainCzar;
        thingCzar = theThingCzar;
    }

    public UArea getArea(String label) {
        if ((label == null) || (label == "start")) {
            return MakeForest();
        }
        String labelname = GetLabelName(label);
        int[] labeldata = GetLabelData(label);

        System.out.println("CARTO : making area for " + labelname + " (" + labeldata[0] + ")");
        switch (labelname) {
            case "forest":
                return MakeForest();
            case "cavern":
                return MakeCavern(labeldata[0]);
            default:
                return MakeForest();
        }
    }

    public String GetLabelName(String label) {
        int i = label.indexOf(" ");
        if (i < 1) return label;
        return label.substring(0,i-1);
    }
    public int[] GetLabelData(String label) {
        int di = 0;
        int[] data = new int[20];
        int i = label.indexOf(" ");
        int lc = i;
        for (;i<=label.length();i++) {
            if (i == label.length()) {
                data[di] = Integer.parseInt(label.substring(lc+1,i-1));
            } else if (label.charAt(i) == ',') {
                data[di] = Integer.parseInt(label.substring(lc+1,i-1));
                lc = i;
                di++;
            }
        }
        return data;
    }

    public UArea MakeForest() {
        UArea area = new UArea(140, 140, terrainCzar, "grass");
        ULandscaper scaper = new ExampleForestScaper(terrainCzar, thingCzar);
        scaper.buildArea(area);
        for (int i=0;i<30;i++) {
            UActor rat = actorCzar.getActorByName("rat");
            UCell ratdest = scaper.randomOpenCell(area, rat);
            rat.moveToCell(area, ratdest.x, ratdest.y);
         }
        return area;
    }

    public UArea MakeCavern(int depth) {
        UArea area = new UArea(120, 120, terrainCzar, "wall");
        ULandscaper scaper = new ExampleCaveScaper(terrainCzar, thingCzar);
        scaper.buildArea(area);
        UActor monk = actorCzar.getActorByName("monk");
        UCell monkdest = scaper.randomOpenCell(area, monk);
        monk.moveToCell(area, monkdest.x, monkdest.y);
        return area;
    }
}