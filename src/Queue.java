import java.util.LinkedList;

public class Queue extends LinkedList<FloorRequest> {
	private static final long serialVersionUID = 1L;
	public final int DIR_UP = 1;
	public final int DIR_DOWN = -1;
	public final int DIR_IDLE = 0;

	public Queue() {
	}

	public void add(FloorRequest request, int currentDirection, int currentFloor) {
		// we need to find a place to add this request

		int indexToAddRequest = this.size();
		int counter = 0;
		for (FloorRequest tempRequest : this) {
			/*System.out.println("request = " + request.toString()
					+ ", tempRequest = " + tempRequest.toString()
					+ ", tempRequest index = " + counter
					+ ", indextoAdd = " + indexToAddRequest);*/
			if (indexToAddRequest == this.size()) {
				if (request.isAheadOf(tempRequest, currentDirection,
						currentFloor)) {
					indexToAddRequest = counter;
				}
			}
			counter++;
		}
		//System.out.println("final indexToAddRequest = " + indexToAddRequest);
		this.add(indexToAddRequest, request);
		printAll();
	}

	@Override
	public boolean add(FloorRequest request) {
		System.out.println("Please use the other add method!");
		return false;
	}

	public void printAll() {
		System.out.println("Printing Ordered Queue:");
		for (FloorRequest temp : this) {
			System.out.println(temp.toString());
		}
		System.out.println("##############################################");
	}

}
