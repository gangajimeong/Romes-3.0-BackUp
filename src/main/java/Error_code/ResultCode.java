package Error_code;

public class ResultCode {
	public final static int SUCCESS = 0;
	public final static int NO_RESULT = 1;
	public final static int DB_CONNECT_ERROR = 2;
	public final static int REQUIRE_ELEMENT_ERROR= 3;
	public final static int AUTHORITY_ERROR=4;//권한 오류
	public final static int DATA_ALERT_ERROR = 5; // session.save,update,error 오류
	public final static int UNABLE_TO_UPDATE = 6;
	public final static int FAIL_RESULT = 7;
	public final static int NO_AUTHORITY = 80;
	public final static int UNKNOWN_ERROR = 99;
	
	public final static int TEst=1;
	public final static String genericMsg(int code) {
		return"["+code+"]";
	}
}