package server.logic;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import server.db_logic.DBController;
import server.db_logic.OrderDBController;
import server.db_logic.UserDBController;
import server.exceptions.LogInException;
import server.gui.ServerMainWindowController;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;

public class BMServerLogic extends AbstractServer{
	
	ServerMainWindowController guiController;
	
	DBController dbController;
	OrderDBController orderDBController;
	UserDBController userDBController; 
	 
	public BMServerLogic(int port, String dbName, String dbUser, String dbPassword) throws Exception {
		super(port);
		
		//Initializing controllers used on server side:
		this.dbController = DBController.getDBControllerInstanceFor(dbName, dbUser, dbPassword);
		dbController.connectToDBServer();
		if(!dbController.isConnected()) {
			//error connecting to db, no reason to go on. TODO: change error handling
			System.out.println("Error Connecting to DB, can't init Server logic");
		    throw new Exception("Can't Connect To DB, Server not started");
		}
		this.orderDBController = new OrderDBController(dbController);
		this.userDBController = new UserDBController(dbController);
	}
	
	public void handleMessageFromClient (Object msg, ConnectionToClient client) {
//		serverPrintToGuiLog("Message From Client Recieved, client:" + client.toString(), true);
		//we are assuming message is ArrayList<String>
		RequestType actionRequired = MessageParser.parseMessageFromClient_RequestType(msg);
		if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA) {
			//if client requests data - we call the request function to handle
			handleGetRequest(actionRequired, msg, client);
		} else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_NEW_TO_DB ||
				actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB ){
			//if client wants to write data - we call the write function to handle
			handleWriteRequest(actionRequired, msg, client);
		} else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS) {
			handleConnectionRequest(actionRequired, msg, client);
		} else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_DEBUG_MESSAGE) {
			//this is a debug case
			handleDebugRequest(actionRequired, msg, client);
		}else if(actionRequired == RequestType.CLIENT_REQUEST_TO_SERVER_LOGIN_REQUEST) {
			handleLoginRequest(actionRequired, msg, client);
		}
		serverPrintToGuiLog("Message From Client Handled, action: " + actionRequired.toString(), true);
	}
	
	public void sendMessageToGivenClient(Object msg, ConnectionToClient client) {
		try {
			client.sendToClient(msg);
		} catch (IOException e) {
			// TODO: HANDLE ERROR
			e.printStackTrace();
		}
	}
	
	protected void serverStarted() {
		serverPrintToGuiLog("Server listening for connections on port " + getPort(), true);
	}

	protected void serverStopped() {
		serverPrintToGuiLog("Server has stopped listening for connections.", true);
	}
	
	protected void clientConnected(ConnectionToClient client) {
		serverPrintToGuiNumberOfClients(getNumberOfClients());
		serverPrintToGuiLog("Client Connected: " + client.toString(), true);
	}


//	synchronized protected void clientDisconnected(ConnectionToClient client) {
////		System.out.println("Client Disconnected:" + client.toString());
//		serverPrintToGuiLog("Client Disconnected:" + client.toString(), true);
//		int numOfClients = getNumberOfClients() - 1;
//		serverPrintToGuiNumberOfClients(numOfClients);
//	}
	
	protected void clientDisconnectedNonAbstract(ConnectionToClient client) {
		serverPrintToGuiLog("Client Disconnected: " + client.toString(), true);
		int numOfClients = getNumberOfClients() - 1;
		serverPrintToGuiNumberOfClients(numOfClients);
	}

	
	
	//---------------------- HELPER FUNCTIONS --------------------------
	
	private void handleWriteRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType =  MessageParser.parseMessageFromClient_DataType(msg);
		switch(messageDataType) {
			case ORDER:
//				Order orderData = MessageParser.parseMessageDataType_Order(msg);
//				if(!orderDBController.handleWriteRequestMessage(actionRequired, orderData)) {
//					String error = "Error: Failed to update given OrderNumber in DB";
//					Object response = MessageParser.createMessageToClientDataType_Error(error);
//					sendMessageToGivenClient(response,client);
//				}
				break;
			case UNKNOWN:
				//TODO: HANDLE ERROR - UNKNOWN DATA TYPE?
			default:
				//TODO: HANDE ERROR - HOW DID WE GET HERE?
		}
	}
	
	private void handleGetRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType =  MessageParser.parseMessageFromClient_DataType(msg);
		switch(messageDataType) {
			case ORDER:
//				String requestedOrder = MessageParser.parseMessageDataType_Order_GetRequestOrderID(msg);
//				Order order = orderDBController.getOrderDataFromDB(requestedOrder);
//				Object response;
//				if(order == null) {
//					//TOOD:Add error handling
//					String error = "Error: Failed to find requestedOrder in DB";
//					response = MessageParser.createMessageToClientDataType_Error(error);
//				} else {
//					response = MessageParser.createMessageToClientDataType_Order(order);
//				}
//				sendMessageToGivenClient(response,client);
				break;
			case UNKNOWN:
				//TODO: HANDLE ERROR - UNKNOWN DATA TYPE?
			default:
				//TODO: HANDE ERROR - HOW DID WE GET HERE?
		}
	}
	
	private void handleConnectionRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType =  MessageParser.parseMessageFromClient_DataType(msg);
		switch(messageDataType) {
			case SINGLE_TEXT_STRING:
				String message = MessageParser.parseMessageDataType_SingleTextString(msg);
				if(message.equals("disconnected")) {
					try {
						clientDisconnectedNonAbstract(client);
						client.close();
					} catch (IOException e) {
						//TODO: add error handling
						e.printStackTrace();
					}
				}
				break;
		default:
				//TODO: HANDE ERROR - HOW DID WE GET HERE?
		}
	}
	
	private void handleLoginRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
        User user = MessageParser.parseMessageDataType_User(msg);
        Object response;
		try {
        	User result = userDBController.authenticateAndGetFullUserData(user);
            response = MessageParser.createMessageToClientDataType_User(result, RequestType.SERVER_MESSAGE_TO_CLIENT_LOGIN_SUCCESS);
            sendMessageToGivenClient(response,client);
        }catch(LogInException e) {
            response = MessageParser.createMessageToClientDataType_Error(e.getErrorType(), e.getMessage());
            sendMessageToGivenClient(response,client);
        }
    }
	
	
	//-------------------------DEBUG FUNCTIONS
	private void handleDebugRequest(RequestType actionRequired, Object msg, ConnectionToClient client) {
		DataType messageDataType =  MessageParser.parseMessageFromClient_DataType(msg);
		if(messageDataType == DataType.SINGLE_TEXT_STRING) {
			ArrayList<String>message = (ArrayList<String>)msg;
			System.out.println( actionRequired.toString() +" "+ messageDataType.toString() + " " + message.get(2) );
		}
	}
	
	//-----------------------UI FUNCTIONS
	public void setGuiController(ServerMainWindowController guiController) {
		this.guiController = guiController;
	}
	
	private void serverPrintToGuiLog(String message, boolean addPrintToTerminal) {
		guiController.updateDataLog(message);
		if(addPrintToTerminal)
			System.out.println(message);
	}
	
	private void serverPrintToGuiNumberOfClients(int numberOfClients) {
		guiController.updateNumberOfClientsConnected(numberOfClients);
	}
	

}
