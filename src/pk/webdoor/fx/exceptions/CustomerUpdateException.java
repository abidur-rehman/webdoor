package pk.webdoor.fx.exceptions;

@SuppressWarnings("serial")
public class CustomerUpdateException extends Exception {
	
	String msg = "";
	
	int result;
	
	public CustomerUpdateException() {
	}

	
	public CustomerUpdateException(String resultString) {
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
			case 0: msg = "Customer update failed!";
			      break;
			case 1: msg = "Customer update successful!";      
				  break;
		}
		
		return msg;
	}
	
	
	
}
