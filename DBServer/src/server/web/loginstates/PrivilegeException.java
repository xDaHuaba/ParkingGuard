package server.web.loginstates;

/**
 * Created by dahua on 27-Apr-18.
 * Class: 4AHIT
 * Project: ParkingGuardDBServer
 */
public class PrivilegeException extends Exception {
	public PrivilegeException() {
		super();
	}

	public PrivilegeException(String message) {
		super(message);
	}

	public PrivilegeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrivilegeException(Throwable cause) {
		super(cause);
	}
}
