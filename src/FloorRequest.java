public class FloorRequest {
	int floor;
	boolean isArrow;
	boolean isUp;

	public final int DIR_UP = 1;
	public final int DIR_DOWN = -1;
	public final int DIR_IDLE = 0;

	public FloorRequest(int f, boolean arrow, boolean up) {
		floor = f;
		isArrow = arrow;
		isUp = up;
	}

	public boolean equals(Object o) {
		FloorRequest newReq = (FloorRequest) o;
		if (newReq.floor == this.floor && newReq.isArrow == this.isArrow
				&& newReq.isUp == newReq.isUp)
			return true;
		return false;
	}

	public boolean isAheadOf(FloorRequest newReq, int direction,
			int currentFloor) {
		
		/*
		String curDirectionString = " idle";
		if (direction == DIR_UP)
			curDirectionString = " up";
		else if (direction == DIR_DOWN)
			curDirectionString = " down";
		System.out.println("Comparing " + this.toString() + " with "
				+ newReq.toString() + ", current floor/direction = "
				+ currentFloor + curDirectionString);
		System.out.println(this.toString() + " has level = "
				+ (this.getLevelOfRequest(direction, currentFloor)));
		System.out.println(newReq.toString() + " has level = "
				+ (newReq.getLevelOfRequest(direction, currentFloor)));
		
		System.out.println("###########################");
*/

		int thisReqLevel = this.getLevelOfRequest(direction, currentFloor);
		int newReqLevel = newReq.getLevelOfRequest(direction, currentFloor);
		
		if ( thisReqLevel < newReqLevel) {
			//System.out.println(this.toString() + " is ahead of " +newReq.toString());
			return true;
		} else if (newReqLevel < thisReqLevel) {
			//System.out.println(newReq.toString() + " is ahead of " + this.toString());
			return false;
		}

		//if they are the same level, tiebreakers are based off of where the floors are
		int thisReqDistanceFromLocation = Math.abs(this.floor - currentFloor);
		int newReqDistanceFromLocation = Math.abs(newReq.floor - currentFloor);
		
		//give tiebreaker to nonArrow floor first
		if(thisReqDistanceFromLocation == newReqDistanceFromLocation)
			return !this.isArrow;
		
		if((thisReqLevel == 1 || thisReqLevel == 3)){
			if(thisReqDistanceFromLocation < newReqDistanceFromLocation)
				return true;
		}
		else{
			if(thisReqDistanceFromLocation > newReqDistanceFromLocation)
				return true;
		}
		return false;
	}

	public String toString() {
		String requestDir = "";
		if (this.isArrow) {
			if (this.isUp)
				requestDir = " UP";
			else
				requestDir = " DOWN";
		}
		return this.floor + requestDir;
	}

	/**
	 * 
	 * @param direction
	 * @param currentFloor
	 * @return 1,2,3, or 4 based on how the requested floor compares to the
	 *         current location and direction of the elevator (1 is highest
	 *         priority)
	 */
	private int getLevelOfRequest(int direction, int currentFloor) {
		boolean reqDirSameAsCurrentDir = getReqDirSameAsCurrentDir(this,
				direction, currentFloor);
		boolean reqLocatedInCurrentDir = getReqLocatedInCurrentDir(this,
				direction, currentFloor);

		//special case involving floor button direction
		if(!this.isArrow){
			if(reqLocatedInCurrentDir)
				return 1;
			return 3;
		}
		
		if (reqDirSameAsCurrentDir && reqLocatedInCurrentDir) {
			return 1;
		} else if (!reqDirSameAsCurrentDir && reqLocatedInCurrentDir) {
			return 2;
		} else if (!reqDirSameAsCurrentDir && !reqLocatedInCurrentDir) {
			return 3;
		} else if (reqDirSameAsCurrentDir && !reqLocatedInCurrentDir) {
			return 4;
		}
		return -1;
	}

	private boolean getReqLocatedInCurrentDir(FloorRequest request,
			int direction, int currentFloor) {

		boolean reqLocatedInCurrentDir = false;
		if ((request.floor > currentFloor && direction != DIR_DOWN)
				|| (request.floor < currentFloor && direction != DIR_UP))
			reqLocatedInCurrentDir = true;
		return reqLocatedInCurrentDir;
	}

	private boolean getReqDirSameAsCurrentDir(FloorRequest request,
			int direction, int currentFloor) {

		boolean reqDirSameAsCurrentDir = true;
		if ((request.isArrow && request.isUp && direction == DIR_DOWN)
				|| (request.isArrow && !request.isUp && direction == DIR_UP))
			reqDirSameAsCurrentDir = false;
		return reqDirSameAsCurrentDir;
	}

}
