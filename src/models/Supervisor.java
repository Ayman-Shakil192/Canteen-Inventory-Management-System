package models;
import gui.SupervisorMenu;

public class Supervisor {
	public Supervisor(int dept_id) {
		new SupervisorMenu(dept_id).call();
	}
}
