package utility.enums;

public enum RequestType {
	//RequestTypes with name starting with "SERVER_..." are sent by server to client
	//RequestTypes with name starting with "CLIENT_..." are sent by client to server	
	CLIENT_REQUEST_TO_SERVER_GET_DATA("CLIENT_REQUEST_TO_SERVER_GET_DATA"),						//used to ask server to send some data to client
	CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB("CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB"), 		//used to ask server to write new data in server DB 
	CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB("CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB"), //used to ask server to update existing data in DB
	CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS("CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS"),	//used by client to update server on client's connection status
	CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE("CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE"), 			//used to send a debug message from client to server, should not be used
	CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST("CLIENT_REQUEST_TO_SERVER_UNKNOWN_REQUEST"), 		//used on server side for error handling, should not be sent from client
	CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST("CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST("CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_W4C_REQUEST("CLIENT_REQUEST_TO_SERVER_W4C_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT("CLIENT_REQUEST_TO_SERVER_REGISTER_CLIENT"),		//used for send data about new "client" for registration from client to server 
	CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER("CLIENT_REQUEST_TO_SERVER_REGISTER_SUPPLIER"),	//used for send data about new supplier for registration from client to server
	CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST("CLIENT_REQUEST_TO_SERVER_SEARCH_RESTAURANT_REQUEST"),	
	CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS("CLIENT_REQUEST_TO_SERVER_APPROVE_BUSINESS"),
	CLIENT_REQUEST_TO_SERVER_DECLINE_BUSINESS("CLIENT_REQUEST_TO_SERVER_DECLINE_BUSINESS"),
	CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES("CLIENT_REQUEST_TO_SERVER_GET_DATA_BUSINESSES_NAMES"),
	CLIENT_REQUEST_TO_SERVER_GET_DATA_CLIENT_INFO("CLIENT_REQUEST_TO_SERVER_GET_DATA_CLIENT_INFO"),
	CLIENT_REQUEST_TO_SERVER_CHANGE_PERMISSION("CLIENT_REQUEST_TO_SERVER_CHANGE_PERMISSION"),
	CLIENT_REQUEST_TO_SERVER_OPEN_REPORT("CLIENT_REQUEST_TO_SERVER_OPEN_REPORT"),
	CLIENT_REQUEST_TO_SERVER_OPEN_SUPPLIER_INCOME_REPORT("CLIENT_REQUEST_TO_SERVER_OPEN_SUPPLIER_INCOME_REPORT"),
	CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS("CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS"),
	CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS("CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS"),
	CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS("CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS"),
	CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER("CLIENT_REQUEST_TO_SERVER_SUPPLIER_UPDATE_ORDER"),
	CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO("CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO"),
	CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST("CLIENT_REQUEST_TO_SERVER_GET_ORDERS_BY_RESTAURANT_ID_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_GET_RESTAURANT_BY_SUPPLIER_REQUEST("CLIENT_REQUEST_TO_SERVER_GET_RESTAURANT_BY_SUPPLIER_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_ADD_DISH_TO_MENU_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_UPDATE_DISH_IN_MENU_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_SUPPLIER_CANCEL_ORDER("CLIENT_REQUEST_TO_SERVER_SUPPLIER_CANCEL_ORDER"),
	CLIENT_REQUEST_TO_SERVER_CEO_QUARTERLY_REPORT("CLIENT_REQUEST_TO_SERVER_CEO_QUARTERLY_REPORT"),
	CLIENT_REQUEST_TO_SERVER_CEO_GET_BRANCHES_REPORTS("CLIENT_REQUEST_TO_SERVER_CEO_GET_BRANCHES_REPORTS"),
	CLIENT_REQUEST_TO_SERVER_DELETE_DISH_FROM_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_DELETE_DISH_FROM_MENU_REQUEST"),
	CLIENT_REQUEST_TO_SERVER_OPEN_REPORT_FROM_BRANCH("CLIENT_REQUEST_TO_SERVER_OPEN_REPORT_FROM_BRANCH"),
	SERVER_MESSAGE_TO_CLIENT_UPDATED_DISH_SUCCESS("SERVER_MESSAGE_TO_CLIENT_UPDATED_DISH_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS("SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_DECLINE_BUSINESS_SUCCESS("SERVER_MESSAGE_TO_CLIENT_DECLINE_BUSINESS_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_ERROR("SERVER_MESSAGE_TO_CLIENT_ERROR"),							//used to send error from server to client
	SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED("SERVER_MESSAGE_TO_CLIENT_DATA_PROVIDED"),			//used to send data from server to client
	SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS("SERVER_MESSAGE_TO_CLIENT_REGISTER_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_SUPPLIER_REGISTER_SUCCESS("SERVER_MESSAGE_TO_CLIENT_SUPPLIER_REGISTER_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_REFUND_AMOUNT("SERVER_MESSAGE_TO_CLIENT_CLIENT_REFUND_AMOUNT"),
	SERVER_MESSAGE_TO_CLIENT_APPROVE_BUSINESS_SUCCESS("SERVER_MESSAGE_TO_CLIENT_APPROVE_BUSINESS_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_CHANGE_PERMISSION_SUCCESS("SERVER_MESSAGE_TO_CLIENT_CHANGE_PERMISSION_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS("SERVER_MESSAGE_TO_CLIENT_OPEN_REPORT_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_SEND_TO_CEO_SUCCESS("SERVER_MESSAGE_TO_CLIENT_SEND_TO_CEO_SUCCESS"),
	SERVER_MESSAGE_TO_CLIENT_CEO_GET_BRANCHES_REPORTS("SERVER_MESSAGE_TO_CLIENT_CEO_GET_BRANCHES_REPORTS"),
	CLIENT_REQUEST_TO_SERVER_INCOMING_FILE("CLIENT_REQUEST_TO_SERVER_INCOMING_FILE"),
	CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST("CLIENT_REQUEST_TO_SERVER_CATEGORY_RESTAURANT_REQUEST"), 
	CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED("CLIENT_REQUEST_TO_SERVER_UPDATE_REFUND_AMOUNT_AFTER_REFUNDS_USED"),
	CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA("CLIENT_REQUEST_TO_SERVER_GET_CLIENT_REFUNDS_DATA"),
	CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA("CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_DATA"), 
	CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE("CLIENT_REQUEST_TO_SERVER_BUSINESS_CLIENT_BUDGED_UPDATE"),
	CLIENT_REQUEST_TO_SERVER_MENU_REQUEST("CLIENT_REQUEST_TO_SERVER_MENU_REQUEST");			
	
	private String requestType;
	
	private RequestType(String request) {
		this.requestType = request;
	}
	
	@Override
	public String toString() {
		return requestType;
	}
}
