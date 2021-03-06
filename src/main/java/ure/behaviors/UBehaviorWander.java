package ure.behaviors;

import ure.actions.UAction;
import ure.actions.UActionWalk;
import ure.actors.UNPC;

/**
 * Wander aimlessly.
 *
 */
public class UBehaviorWander extends UBehavior {

    public static String TYPE = "wander";

    @Override
    public UAction action(UNPC actor) {
        int dir = actor.random.nextInt(4);
        int wx,wy;
        if (dir == 0) {
            wx = -1; wy = 0;
        } else if (dir == 1) {
            wx = 1; wy = 0;
        } else if (dir == 2) {
            wx = 0; wy = 1;
        } else {
            wx = 0; wy = -1;
        }
        return new UActionWalk(actor, wx,wy);
    }
}
