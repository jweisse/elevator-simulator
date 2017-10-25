import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window implements ActionListener {

	public final int DIR_UP = 1;
	public final int DIR_DOWN = -1;
	public final int DIR_IDLE = 0;
	private final int[] FLOORY = { -1, 380, 267, 153, 40 };
	private int CLOSE_WAIT_TIME = 5000;
	private JButton[] upButtons = new JButton[5];
	private JButton[] downButtons = new JButton[5];
	private JButton[] floorButtons = new JButton[5];
	private Timer travelUpTimer, travelDownTimer, dropOffOrPickUpTimer,
			elevatorClosingTimer;
	private ElevatorCar smallElevator, bigElevator;
	private JLabel[] screens = new JLabel[5];
	private JFrame frame;
	private Queue queue = new Queue();
	private int currentDirection = DIR_IDLE;
	private boolean inUse = false;
	private final Color LIT_COLOR = Color.YELLOW;
	private final Color DORMANT_COLOR = Color.WHITE;

	// higher number = slower
	private final int MOVE_SPEED = 15;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		
		/*
		int floor = 1;
		int direction = DIR_UP;
		queue.add(new FloorRequest(3, true, false), floor, direction);
		queue.add(new FloorRequest(3, true, true), floor, direction);
		queue.add(new FloorRequest(4, true, false), floor, direction);
		*/
		
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Elevator Control Simulation");
		frame.setResizable(false);
		frame.setBounds(100, 100, 750, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JPanel building = new JPanel();
		smallElevator = new ElevatorCar(false);
		bigElevator = new ElevatorCar(true);
		bigElevator.setLocation(43, 206);
		bigElevator.setSize(216, 240);

		bigElevator.addToFrame(frame);

		JPanel window4 = new JPanel();
		window4.setSize(28, 40);
		window4.setLocation(681, FLOORY[4]);
		frame.getContentPane().add(window4);
		JPanel window3 = new JPanel();
		window3.setSize(28, 40);
		window3.setLocation(681, FLOORY[3]);
		frame.getContentPane().add(window3);
		JPanel window2 = new JPanel();
		window2.setSize(28, 40);
		window2.setLocation(681, FLOORY[2]);
		frame.getContentPane().add(window2);
		JPanel window1 = new JPanel();
		window1.setSize(28, 40);
		window1.setLocation(681, FLOORY[1]);
		frame.getContentPane().add(window1);
		smallElevator.setBackground(Color.DARK_GRAY);
		smallElevator.setSize(55, 70);
		smallElevator.setLocation(590, 380);
		smallElevator.addToFrame(frame);
		building.setBackground(new Color(210, 105, 30));
		building.setBounds(560, 11, 164, 439);
		frame.getContentPane().add(building);
		

		upButtons[1] = new JButton("\u2191");
		upButtons[1].setForeground(Color.BLACK);
		upButtons[1].setBackground(DORMANT_COLOR);
		upButtons[1].setBounds(43, 114, 50, 15);
		frame.getContentPane().add(upButtons[1]);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(43, 58, 50, 50);
		frame.getContentPane().add(panel);

		screens[1] = new JLabel("1  ");
		panel.add(screens[1]);
		screens[1].setForeground(Color.RED);
		screens[1].setFont(new Font("NSimSun", Font.PLAIN, 30));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(432, 58, 50, 50);
		frame.getContentPane().add(panel_1);

		screens[4] = new JLabel("1  ");
		screens[4].setForeground(Color.RED);
		screens[4].setFont(new Font("NSimSun", Font.PLAIN, 30));
		panel_1.add(screens[4]);

		downButtons[4] = new JButton("\u2193");
		downButtons[4].setBackground(DORMANT_COLOR);
		downButtons[4].setBounds(432, 114, 50, 15);
		frame.getContentPane().add(downButtons[4]);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(302, 58, 50, 50);
		frame.getContentPane().add(panel_2);

		screens[3] = new JLabel("1  ");
		screens[3].setForeground(Color.RED);
		screens[3].setFont(new Font("NSimSun", Font.PLAIN, 30));
		panel_2.add(screens[3]);

		upButtons[3] = new JButton("\u2191");
		upButtons[3].setForeground(Color.BLACK);
		upButtons[3].setBackground(DORMANT_COLOR);
		upButtons[3].setBounds(302, 114, 50, 15);
		frame.getContentPane().add(upButtons[3]);

		downButtons[3] = new JButton("\u2193");
		downButtons[3].setBackground(DORMANT_COLOR);
		downButtons[3].setBounds(302, 132, 50, 15);
		frame.getContentPane().add(downButtons[3]);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(170, 58, 50, 50);
		frame.getContentPane().add(panel_3);

		screens[2] = new JLabel("1  ");
		screens[2].setForeground(Color.RED);
		screens[2].setFont(new Font("NSimSun", Font.PLAIN, 30));
		panel_3.add(screens[2]);

		upButtons[2] = new JButton("\u2191");
		upButtons[2].setForeground(Color.BLACK);
		upButtons[2].setBackground(DORMANT_COLOR);
		upButtons[2].setBounds(170, 114, 50, 15);
		frame.getContentPane().add(upButtons[2]);

		downButtons[2] = new JButton("\u2193");
		downButtons[2].setBackground(DORMANT_COLOR);
		downButtons[2].setBounds(170, 132, 50, 15);
		frame.getContentPane().add(downButtons[2]);

		JLabel lblNewLabel_1 = new JLabel("Floor 1");
		lblNewLabel_1.setBounds(50, 33, 43, 14);
		frame.getContentPane().add(lblNewLabel_1);

		floorButtons[1] = new JButton("1");
		floorButtons[1].setBackground(DORMANT_COLOR);
		floorButtons[1].setBounds(286, 370, 50, 15);
		frame.getContentPane().add(floorButtons[1]);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		panel_4.setBounds(286, 250, 50, 50);
		frame.getContentPane().add(panel_4);

		screens[0] = new JLabel("1  ");
		screens[0].setForeground(Color.RED);
		screens[0].setFont(new Font("NSimSun", Font.PLAIN, 30));
		panel_4.add(screens[0]);

		floorButtons[4] = new JButton("4");
		floorButtons[4].setBackground(DORMANT_COLOR);
		floorButtons[4].setBounds(286, 310, 50, 15);
		frame.getContentPane().add(floorButtons[4]);

		floorButtons[3] = new JButton("3");
		floorButtons[3].setBackground(DORMANT_COLOR);
		floorButtons[3].setBounds(286, 330, 50, 15);
		frame.getContentPane().add(floorButtons[3]);

		floorButtons[2] = new JButton("2");
		floorButtons[2].setBackground(DORMANT_COLOR);
		floorButtons[2].setBounds(286, 350, 50, 15);
		frame.getContentPane().add(floorButtons[2]);

		JLabel lblFloor = new JLabel("Floor 2");
		lblFloor.setBounds(177, 33, 43, 14);
		frame.getContentPane().add(lblFloor);

		JLabel lblFloor_1 = new JLabel("Floor 3");
		lblFloor_1.setBounds(309, 33, 43, 14);
		frame.getContentPane().add(lblFloor_1);

		JLabel lblFloor_2 = new JLabel("Floor 4");
		lblFloor_2.setBounds(439, 33, 43, 14);
		frame.getContentPane().add(lblFloor_2);

		/**
		 * Internal elevator floor button action listeners
		 */

		floorButtons[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (floorButtons[1].getBackground().equals(DORMANT_COLOR)) {
					pressFloorButton(1);
				}
			}
		});

		floorButtons[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (floorButtons[2].getBackground().equals(DORMANT_COLOR)) {
					pressFloorButton(2);
				}
			}
		});

		floorButtons[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (floorButtons[3].getBackground().equals(DORMANT_COLOR)) {
					pressFloorButton(3);
				}
			}
		});

		floorButtons[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (floorButtons[4].getBackground().equals(DORMANT_COLOR)) {
					pressFloorButton(4);
				}
			}
		});

		/**
		 * External elevator arrow button action listeners
		 */

		upButtons[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pressUpArrow(1);
			}
		});
		upButtons[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pressUpArrow(2);
			}
		});
		upButtons[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pressUpArrow(3);
			}
		});
		downButtons[2].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pressDownArrow(2);
			}
		});
		downButtons[3].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pressDownArrow(3);
			}
		});
		downButtons[4].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pressDownArrow(4);
			}
		});

	}

	private void pressFloorButton(int floor) {
		//System.out.println(floor + " floor button pressed");
		floorButtons[floor].setBackground(LIT_COLOR);

		FloorRequest newRequest = new FloorRequest(floor, false, false);
		queue.add(newRequest, currentDirection, getCurrentFloor());

		lookInQueue();

	}

	private void pressDownArrow(int floor) {
		if (downButtons[floor].getBackground().equals(DORMANT_COLOR)) {
			downButtons[floor].setBackground(LIT_COLOR);

			FloorRequest newRequest = new FloorRequest(floor, true, false);
			queue.add(newRequest, currentDirection, getCurrentFloor());

			lookInQueue();
		}
	}

	private void pressUpArrow(int floor) {
		if (upButtons[floor].getBackground().equals(DORMANT_COLOR)) {
			upButtons[floor].setBackground(LIT_COLOR);

			FloorRequest newRequest = new FloorRequest(floor, true, true);
			queue.add(newRequest, currentDirection, getCurrentFloor());

			lookInQueue();
		}
	}

	/**
	 * looks in both queues and chooses a floor to travel to if not in use
	 */
	private void lookInQueue() {
		if (inUse) {
			//System.out.println("Debug: lookInQueue called: in use");
			return;
		}
		if (!queue.isEmpty()) {
			moveToFloor(queue.peek().floor);
		} 
		else {
			inUse = false;
			changeScreens(getCurrentFloor(), DIR_IDLE);
			currentDirection = DIR_IDLE;
		}
	}

	private void openElevators() {
		bigElevator.openDoor();
		smallElevator.openDoor();
	}

	/**
	 * Need to wait for elevator to close before moving again
	 */
	private void closeElevators() {
		bigElevator.closeDoor();
		smallElevator.closeDoor();
	}

	/**
	 * gets the current floor, based on the elevator's direction
	 * 
	 * goingUp: whether or not the elevator is traveling up
	 * 
	 * @return
	 */
	private int getCurrentFloor() {
		int currentFloor = -1;
		boolean goingUp = true;
		if (currentDirection == DIR_DOWN)
			goingUp = false;
		if (goingUp) {
			for (int i = 1; i <= 4; i++) {
				if (smallElevator.getY() <= FLOORY[i]) {
					currentFloor = i;
				}
			}
		} else {
			for (int i = 4; i >= 1; i--) {
				if (smallElevator.getY() >= FLOORY[i]) {
					currentFloor = i;
				}
			}
		}
		return currentFloor;
	}

	private void moveToFloor(int floorToMove) {
		int currentFloor = getCurrentFloor();
		inUse = true;

		// travel up
		if (floorToMove > currentFloor) {
			currentDirection = DIR_UP;
			changeScreens(currentFloor, DIR_UP);
			travelUpTimer = new Timer(MOVE_SPEED, this);
			travelUpTimer.start();
		}
		
		// travel down
		else if (floorToMove < currentFloor) {
			currentDirection = DIR_DOWN;
			changeScreens(currentFloor, DIR_DOWN);
			travelDownTimer = new Timer(MOVE_SPEED, this);
			travelDownTimer.start();
		}

		// don't travel anywhere
		else {
			arrivedAtFloor(floorToMove, DIR_IDLE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int currentFloor;
		if (e.getSource().equals(travelUpTimer)) {
			currentFloor = getCurrentFloor();

			// if floor changed
			if (screens[0].getText().charAt(0) != currentFloor + '0') {
				floorChanged(currentFloor, DIR_UP);
			}
			else {
				smallElevator.setLocation(smallElevator.getX(),
						smallElevator.getY() - 1);
			}
		}

		else if (e.getSource().equals(travelDownTimer)) {
			currentFloor = getCurrentFloor();

			// if floor changed
			if (screens[0].getText().charAt(0) != currentFloor + '0') {
				floorChanged(currentFloor, DIR_DOWN);
			}
			else {
				smallElevator.setLocation(smallElevator.getX(),
						smallElevator.getY() + 1);
			}
		}

		else if (e.getSource().equals(dropOffOrPickUpTimer)) {
			closeElevators();
			elevatorClosingTimer = new Timer(smallElevator.CLOSING_TIME, this);
			elevatorClosingTimer.setRepeats(false);
			elevatorClosingTimer.start();
		}

		else if (e.getSource().equals(elevatorClosingTimer)) {
			inUse = false;
			lookInQueue();
		}
	}

	/**
	 * method is called every time the elevator changes to a new floor check to
	 * see if we need to pick up any passengers on this floor change elevator
	 * screens
	 * 
	 * @param floorToChangeTo
	 * @param timer
	 */
	private void floorChanged(int floorToChangeTo, int travelingDirection) {
		//System.out.println("Floor changed to " + floorToChangeTo);
		changeScreens(floorToChangeTo, travelingDirection);
		
		if (queue.peek().floor == floorToChangeTo) {
			arrivedAtFloor(floorToChangeTo, travelingDirection);
		}
	}

	private void arrivedAtFloor(int floor, int travelingDirection) {
		//if same floor elevator button and arrow button arriving, remove elevator button from queue 
		if(!queue.peek().isArrow && queue.size() > 1 && queue.get(1).floor == floor){
			floorButtons[floor].setBackground(DORMANT_COLOR);
			queue.removeFirst();			
		}
		
		//System.out.println("Arrived at floor: " + floor);
		smallElevator.setLocation(smallElevator.getX(),
				FLOORY[floor]);
		if (travelingDirection == DIR_UP)
			travelUpTimer.stop();
		else if(travelingDirection == DIR_DOWN)
			travelDownTimer.stop();

		/* Change the necessary button colors */
		FloorRequest firstReqOnQueue = queue.peek();
		//changeButtonToDormant()
		if (firstReqOnQueue.isArrow) {
			if (firstReqOnQueue.isUp){
				upButtons[floor].setBackground(DORMANT_COLOR);
				changeScreens(floor, DIR_UP);
				currentDirection = DIR_UP;
			}
			else {
				downButtons[floor].setBackground(DORMANT_COLOR);
				changeScreens(floor, DIR_DOWN);
				currentDirection = DIR_DOWN;
			}
		} else{
			floorButtons[floor].setBackground(DORMANT_COLOR);
		}
		queue.removeFirst();
		letPassengersInOrOut();	
	}

	private void letPassengersInOrOut() {
		openElevators();
		dropOffOrPickUpTimer = new Timer(CLOSE_WAIT_TIME, this);
		dropOffOrPickUpTimer.setRepeats(false);
		dropOffOrPickUpTimer.start();
	}

	private void changeScreens(int currentFloor, int direction) {
		String newText = "" + currentFloor;
		if (DIR_UP == direction) {
			newText = newText + "\u2191";
		} else if (DIR_DOWN == direction) {
			newText = newText + "\u2193";
		} else {
			newText = newText + "  ";
		}
		// set all screens to newText
		for (int i = 0; i < screens.length; i++) {
			screens[i].setText(newText);
		}
	}
}
