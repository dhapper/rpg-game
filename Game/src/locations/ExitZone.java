package locations;

import java.awt.Rectangle;

import gamestates.Overworld;

public class ExitZone {

	private Overworld overworld;
	Rectangle zone;
	int LocationIndex;
	int xPos, yPos;
	
	public ExitZone(Overworld overworld, Rectangle zone, int locationIndex, int xPos, int yPos) {
		this.overworld = overworld;
		this.zone = zone;
		this.LocationIndex = locationIndex;
		this.xPos = xPos;
		this.yPos = yPos;
		//System.out.println(this.overworld);
	}
	
	public void loadNewLocation(){
		overworld.loadArea(LocationIndex);
		overworld.getPlayer().setPlayerPos(xPos, yPos);
		
	}
	
	public Rectangle getZone() {
		return zone;
	}
}
