package project.csc207.lightsoutgame;

class LightsOutTestHelpMethods {

    /**
     * Turn all the lights on for a board
     * @param board lights out board
     */
    static void getLightsAllOn(LightsOutBoard board){
        for (Light l: board) {
            l.setLight(true);
        }
    }

    /**
     * Turn all the lights off for a board
     * @param board lights out board
     */
    static void getLightsAllOff(LightsOutBoard board){
        for (Light l: board) {
            l.setLight(false);
        }
    }
}
