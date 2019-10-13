package com.hellofit.kidozone.puzzleGame.game;

/***
 *  This class is the interface for puzzle game
 *
 *  Created by Weiqiang Li on 09/13/19.
 *  Copyright @ 2019 Weiqiang Li. All right reserved
 *
 *  @author Weiqiang Li
 *  @version 3.1
 */

public interface Game {

    /**
     * Add level
     */
    public void addLevel();

    /**
     * Reduce level
     */
    public void reduceLevel();

    /**
     * Change image
     *
     * @param res
     */
    public void changeImage(int res);

}
