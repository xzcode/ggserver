package xzcode.ggserver.game.common.interfaces;

import xzcode.ggserver.game.common.house.House;
import xzcode.ggserver.game.common.player.Player;
import xzcode.ggserver.game.common.room.Room;

public interface PRH <
P extends Player<R, H>,
R extends Room< P, R, H>, 
H extends House<P, R, H>
>{

}
