package project.csc207.lightsoutgame;

import android.content.Context;
import android.widget.Toast;


class SwitchController {

    private LightsOutBoardManager lightsOutBoardManager;

    SwitchController() {
    }

    /**
     * Set lights out board manager
     * @param lightsOutBoardManager lights out board manager
     */
    void setLightsOutBoardManager(LightsOutBoardManager lightsOutBoardManager) {
        this.lightsOutBoardManager = lightsOutBoardManager;
    }

    /**
     * Process the tap, switching the light and displaying a win message if the player wins.
     * @param context the context
     * @param position position on the board
     */
    void processTapSwitch(Context context, int position) {
            lightsOutBoardManager.touchToSwitch(position);
            if (lightsOutBoardManager.allLightsOut()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        }
}
