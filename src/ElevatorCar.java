import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ElevatorCar extends JPanel implements ActionListener {

	JPanel car, door;
	Timer doorOpenTimer, doorCloseTimer;
	boolean elevatorDelay = false;
	boolean isBig;
	
	//the time it takes to open & close the door
	final int CLOSING_TIME = 1500;

	public ElevatorCar(boolean big) {
		isBig = big;
		car = new JPanel();
		door = new JPanel();
		car.setBackground(Color.DARK_GRAY);
		door.setBackground(Color.LIGHT_GRAY);
	}

	public void setLocation(int x, int y) {
		car.setLocation(x, y);
		door.setLocation(x + (car.getWidth() / 2), y);
	}

	public void setSize(int width, int height) {
		car.setSize(width, height);
		if (isBig) {
			door.setSize(2, height);
		} else {
			door.setSize(1, height);
		}
		setLocation(car.getX(), car.getY());
	}

	public int getY() {
		return car.getY();
	}

	public int getX() {
		return car.getX();
	}

	public boolean isOpen() {
		if (door.getWidth() < 10) {
			return false;
		}
		return true;
	}

	public void closeDoor() {
		if (isOpen()) {
			elevatorDelay = true;
			doorCloseTimer = new Timer(CLOSING_TIME / 95, this);
			doorCloseTimer.start();
		}
	}

	public void openDoor() {
		if (!isOpen()) {
			elevatorDelay = true;
			doorOpenTimer = new Timer(CLOSING_TIME / 95, this);
			doorOpenTimer.start();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(doorOpenTimer)) {
			int openWidth = 10;
			if(!isBig){
				openWidth = 3;
			}
			if (door.getX() <= car.getX() + openWidth) {
				doorOpenTimer.stop();
			} else {
				if (isBig) {
					door.setBounds(door.getX() - 2, door.getY(),
							door.getWidth() + 4, door.getHeight());
				} else {
					if (elevatorDelay) {
						door.setBounds(door.getX() - 1, door.getY(),
								door.getWidth() + 2, door.getHeight());
						elevatorDelay = false;
					} else {
						elevatorDelay = true;
					}
				}
			}
		}

		else if (e.getSource().equals(doorCloseTimer)) {
			int closedWidth = 1;
			if (isBig) {
				closedWidth = 2;
			}
			if (door.getWidth() <= closedWidth) {
				doorCloseTimer.stop();
			} else {
				if (isBig) {
					door.setBounds(door.getX() + 2, door.getY(),
							door.getWidth() - 4, door.getHeight());
				} else {
					if (elevatorDelay && door.getWidth() > 1) {
						door.setBounds(door.getX() + 1, door.getY(),
								door.getWidth() - 2, door.getHeight());
						elevatorDelay = false;
					} else {
						elevatorDelay = true;
					}
				}
			}
		}

	}

	public void addToFrame(JFrame frame) {
		frame.getContentPane().add(door);
		frame.getContentPane().add(car);
			
	}


}
