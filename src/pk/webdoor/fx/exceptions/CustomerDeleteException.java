package pk.webdoor.fx.exceptions;

public class CustomerDeleteException extends Exception {
	
	String msg = "";
	
	int result;
	
	public CustomerDeleteException() {
	}

	
	public CustomerDeleteException(String resultString) {
		System.out.println(" Exception result " + resultString);
		if(resultString.equals("FAILED")) {
			this.result = 0;
		}else if(resultString.equals("SUCCESS")){
			this.result = 1;
		}
		//super(str);
	}
	
	@Override
	public String toString() {	
		
		
		
		switch(result){
			case 0: msg = "Customer deletion failed!";
			      break;
			case 1: msg = "Customer deleted successful!";      
				  break;
		}
		
		return msg;
	}
}	
	