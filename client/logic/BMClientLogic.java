package client.logic;

import java.io.IOException;
import java.util.ArrayList;

import ocsf.client.AbstractClient;
import utility.entity.Business;
import utility.entity.BusinessClient;
import utility.entity.Client;
import utility.entity.ClientChangePermission;
import utility.entity.Supplier;
import utility.entity.User;
import utility.enums.DataType;
import utility.enums.RequestType;
import utility.enums.UserType;
import utility.message_parsers.*;


public class BMClientLogic extends AbstractClient{
	
	private Object lastDataRecieved;
	private DataType typeOfLastDataRecieved;
	private RequestType typeOfLastRequestRecieved;
	
	private User loggedInUser;
	
	public BMClientLogic(String host, int port) throws IOException {
	    super(host, port); //Call the superclass constructor
	    openConnection();
	}

	@Override
	protected void handleMessageFromServer(Object messageFromServerToClient) {
		RequestType messageRequestType = MessageParser.parseMessage_RequestType(messageFromServerToClient);
		DataType messageDataType = MessageParser.parseMessage_DataType(messageFromServerToClient);
		typeOfLastDataRecieved = messageDataType;
		typeOfLastRequestRecieved = messageRequestType;
		switch(messageDataType) {
			case USER:
				lastDataRecieved = MessageParserUser.handleMessageExtractDataType_User(messageFromServerToClient);
				break;
			case CLIENT:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataTypeResultRegistretion_Client(messageFromServerToClient);
				break;
			case SUPPLIER:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataTypeResultRegistretion_Supplier(messageFromServerToClient);
				break;
			case RESTAURANTS_LIST:
				lastDataRecieved = MessegeParserRestaurants.handleMessageExtractDataType_Restaurants(messageFromServerToClient);
				break;
			case HR_MANAGER:
				if(messageRequestType == RequestType.CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS)
                    lastDataRecieved = MessageParserHR.handleMessageFromClient_ApproveBusinessClient((ArrayList<String>)messageFromServerToClient);
                else {
                    lastDataRecieved = MessageParserHR.handleMessageExtractDataType_HRGetData(messageFromServerToClient);
                }
				break;
			case ERROR_MESSAGE:
				lastDataRecieved = MessageParserError.handleMessageExtractDataType__ErrorType(messageFromServerToClient);
				break;
			case DISHES_LIST:
				lastDataRecieved = MessegeParserDishes.handleMessageExtractDataType_Dishes(messageFromServerToClient);
				break;
			case GET_DATA_OF_BUSINESS:
				lastDataRecieved = messageFromServerToClient;
				break;
			case APPROVE_BUSINESS:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataTypeResultOfApproveBusiness(messageFromServerToClient);
				break;
			case GET_DATA_OF_CLIENT:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractDataType_GetDataOfClient(messageFromServerToClient);
				break;
			case CHANGE_PERMISSION:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractFromArrayList(messageFromServerToClient);
				break;
			case REPORT:
				lastDataRecieved = MessageParserBranchManager.handleMessageExtractFromArrayList(messageFromServerToClient);
				break;
			case RESTAURANT:
                lastDataRecieved = MessegeParserRestaurants.handleMessageExtractDataType_singleRestaurant(messageFromServerToClient);
                break;
			case DISH:
                lastDataRecieved = MessegeParserDishes.handleMessageExtractDataType_SingleDish(messageFromServerToClient);
                break;
			case ORDERS_LIST:
                lastDataRecieved = MessageParserOrder.handleMessageExtractDataType_orders(messageFromServerToClient);
                break;
			case SINGLE_TEXT_STRING:
                lastDataRecieved = MessageParserTextString.handleMessageExtractDataType_SingleTextString(messageFromServerToClient);
                
                break;
			case ARRAYLIST_STRING:
				 lastDataRecieved = MessageParserTextString.handleMessageExtractDataType_ArrayListString(messageFromServerToClient);
				 break;
			default:
				System.out.println("ERROR, UNKNOWN DATA TYPE");
		}	
	}
	
	public void sendMessageToServer(Object dataToSendToServer, DataType dataType, RequestType requestType) {
		Object message=null;
		switch(dataType) {
		case USER:
			message = MessageParserUser.prepareMessageWithDataType_User((User)dataToSendToServer, requestType);
			break;
		case CLIENT:
			message = MessageParserBranchManager.prepareMessageWithDataType_Client((Client)dataToSendToServer, requestType);
			break;
		case SUPPLIER:
			message = MessageParserBranchManager.prepareMessageWithDataType_Supplier((Supplier)dataToSendToServer, requestType);
			break;
		case SINGLE_TEXT_STRING:
			message = MessageParserTextString.prepareMessageWithDataType_SingleTextString((String)dataToSendToServer, requestType);
			break;	
		case HR_MANAGER:
			if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_GET_DATA) {
                message = MessageParserHR.prepareMessageToServer_HRDataRequest((int)dataToSendToServer,dataType, requestType);
            } else if (requestType == RequestType.CLIENT_REQUEST_TO_SERVER_WRITE_UPDATE_TO_DB){
                message = MessageParserHR.prepareMessageToServer_HRUpdateDB((BusinessClient) dataToSendToServer, dataType, requestType);
            }
            else if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_APRROVE_BUSINESS || 
                    requestType == RequestType.CLIENT_REQUEST_TO_SERVER_CHECK_APRROVE_BUSINESS) {
                message = MessageParserHR.prepareMessageToServer_HRApproveBusiness((int) dataToSendToServer, dataType, requestType);
            }
            else if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_GET_APPROVED_BUSINESS_CLIENTS) {
                message = MessageParserHR.prepareMessageToServer_HRDataRequest((int)dataToSendToServer,dataType, requestType);
            }
			break;
		case GET_DATA_OF_BUSINESS:
			message = MessageParserBranchManager.prepareMessageWithDataType_GET_DATA_OF_BUSINESS((String)dataToSendToServer, requestType);
			break;
		case APPROVE_BUSINESS:
			message = MessageParserBranchManager.prepareMessageWithDataType_Business((Business)dataToSendToServer,requestType);
			break;
		case GET_DATA_OF_CLIENT:
			message = MessageParserBranchManager.prepareMessageWithDataType_getDataOfClient((String)dataToSendToServer,requestType);
			break;
		case CHANGE_PERMISSION:
			message = MessageParserBranchManager.prepareMessageWithDataType_changePermission((ClientChangePermission)dataToSendToServer, requestType);
			break;
		case REPORT:
			System.out.println("before sending to server");
			if(requestType == RequestType.CLIENT_REQUEST_TO_SERVER_SEND_REPORT_TO_CEO) {
				message = MessageParserTextString.prepareMessageWithDataType_SingleTextString((String)dataToSendToServer, requestType);
				
			}else {
			message = dataToSendToServer;
			}
			break;
		case DISH:
            message = MessegeParserDishes.prepareMessageWithDataType_SingleDish(dataToSendToServer, requestType);
            break;
		case ARRAYLIST_STRING:
			ArrayList<String> arraylist = (ArrayList<String>)dataToSendToServer;
			message = MessageParserTextString.prepareMessageToServerDataType_ArrayListString(arraylist, requestType);
			break;
		default:
			System.out.println("sendMessageToServer: unknown dataType");
			return;
		}
		handleMessageToServer(message);
	}
	
	private void handleMessageToServer(Object msg) {
		try {
			sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not send message to server. Terminating client.");
			System.exit(1);
		}
	}
	
	public void closeConnectionWithMessageToServer() {
		sendMessageToServer("disconnected", DataType.SINGLE_TEXT_STRING, RequestType.CLIENT_REQUEST_TO_SERVER_CONNECTION_STATUS);
		try {
			closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	//----------------LOGGED-IN USER METHODS
	
	public void loginUser(User user) {
		this.loggedInUser = user;
	}
	
	public void logOutUser() {
		if(loggedInUser == null) {
			return;
		}
		sendMessageToServer(loggedInUser, DataType.USER, RequestType.CLIENT_REQUEST_TO_SERVER_LOGOUT_REQUEST);
		loggedInUser = new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "");
	}
	
	public User getLoggedUser() {
		if(loggedInUser == null)
			return new User(-1, "", "", "", "", "", UserType.USER, "", "", false, "");
		else {
			return loggedInUser;
		}
	}

	
	//----------------TO BE CHANGED WHEN MESSAGE HISTORY ADDED (POSSIBLY)
	public Object getLastDataRecieved() {
		return lastDataRecieved;
	}

	public DataType getTypeOfLastDataRecieved() {
		return typeOfLastDataRecieved;
	}

	public RequestType getTypeOfLastRequestRecieved() {
		return typeOfLastRequestRecieved;
	}
	
}