package example.grpcclient;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import service.*;
import test.TestProtobuf;
import java.util.List;
import java.util.ArrayList;
import java.util.*;
import java.util.Arrays;
import java.lang.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {
	
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final CalcGrpc.CalcBlockingStub blockingStub4;
  private final TipsGrpc.TipsBlockingStub blockingStub5;
  private final advancedCalcGrpc.advancedCalcBlockingStub blockingStub6;

  public Client(Channel channel, Channel regChannel) {

    blockingStub = EchoGrpc.newBlockingStub(channel);
    blockingStub2 = JokeGrpc.newBlockingStub(channel);
    blockingStub3 = RegistryGrpc.newBlockingStub(regChannel);
    blockingStub4 = CalcGrpc.newBlockingStub(channel);
    blockingStub5 = TipsGrpc.newBlockingStub(channel);
    blockingStub6 = advancedCalcGrpc.newBlockingStub(channel);
    
  }

  public void askServerToParrot(String message) {
    ClientRequest request = ClientRequest.newBuilder().setMessage(message).build();
    ServerResponse response;
    try {
      response = blockingStub.parrot(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e.getMessage());
      return;
    }
    System.out.println("Received from server: " + response.getMessage());
  }

  public void askForJokes(int num) {
    JokeReq request = JokeReq.newBuilder().setNumber(num).build();
    JokeRes response;

    try {
      response = blockingStub2.getJoke(request);
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
    System.out.println("Your jokes: ");
    for (String joke : response.getJokeList()) {
      System.out.println("--- " + joke);
    }
  }

  public void setJoke(String joke) {
    JokeSetReq request = JokeSetReq.newBuilder().setJoke(joke).build();
    JokeSetRes response;

    try {
      response = blockingStub2.setJoke(request);
      System.out.println(response.getOk());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public String[] getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    String[] empty = new String[0];
    
    try {
    	
      response = blockingStub3.getServices(request);
      
      int size = response.getServicesCount();
      int stopper = 0;
      String service = "";
      String[] services = new String[response.getServicesCount()];
      
      for(int i = 0; i < size; i++) {
    	  
    	  services[i] = response.getServices(i);
    	  stopper = response.getServices(i).indexOf("/");
    	  service = response.getServices(i).substring(stopper + 1, response.getServices(i).length());
    	  
    	  System.out.println((i + 1) + ": " + service);
    	  
      }
      
      return services;
      
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return empty;
    }
    
  }

  public String findServer(String name) {
	  
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    String connection = "";
    
    try {
    	
      response = blockingStub3.findServer(request);
      
      connection = response.getConnection().getUri() + ":" + response.getConnection().getPort();
      
      return connection;
      
    } catch (Exception e) {
    	
      System.err.println("RPC failed: " + e);
      return "localhost:8000";
      
    }
    
  }

  public void findServers(String name) {
    FindServersReq request = FindServersReq.newBuilder().setServiceName(name).build();
    ServerListRes response;
    try {
      response = blockingStub3.findServers(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }
  
  public void add(List<Double> nums) {
	  
	  CalcRequest request = CalcRequest.newBuilder().addAllNum(nums).build();
	  CalcResponse response;
	  
	  try {
		  
		  response = blockingStub4.add(request);
	      System.out.println("Answer: " + response.getSolution());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }
  
  public void subtract(List<Double> nums) {
	  
	  CalcRequest request = CalcRequest.newBuilder().addAllNum(nums).build();
	  CalcResponse response;
	  
	  try {
		  
		  response = blockingStub4.subtract(request);
		  System.out.println("Answer: " + response.getSolution());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }
  
  public void multiply(List<Double> nums) {
	  
	  CalcRequest request = CalcRequest.newBuilder().addAllNum(nums).build();
	  CalcResponse response;
	  
	  try {
		  
		  response = blockingStub4.multiply(request);
		  System.out.println("Answer: " + response.getSolution());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }

  public void divide(List<Double> nums) {
	  
	  CalcRequest request = CalcRequest.newBuilder().addAllNum(nums).build();
	  CalcResponse response;
	  
	  try {
		  
		  response = blockingStub4.divide(request);
		  System.out.println("Answer: " + response.getSolution());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }
  
  public void read() {
	  
	  Empty request = Empty.newBuilder().build();
	  TipsReadResponse response;
	  
	  try {
		  
		  response = blockingStub5.read(request);
		  System.out.println(response.getTipsList());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }
  
  public void write(String tip, String name) {
	  
	  Tip x = Tip.newBuilder().setName(name).setTip(tip).build();
	  TipsWriteRequest request = TipsWriteRequest.newBuilder().setTip(x).build();
	  TipsWriteResponse response;
	  
	  try {
		  
	      response = blockingStub5.write(request);
	      
	      if(response.getIsSuccess()) {
	    	  
	    	  System.out.println("Tip successfully added.");
	    	  
	      }else {
	    	  
	    	  System.out.println("Failed to add tip.");
	    	  
	      }
	      
	    } catch (Exception e) {
	    	
	      System.err.println("RPC failed: " + e);
	      return;
	      
	    }
	  
  }
  
  public void mean(List<Double> nums, double elements) {
	  
	  advancedCalcRequest request = advancedCalcRequest.newBuilder().addAllNum(nums).setElements(elements).build();
	  advancedCalcResponse response;
	  
	  try {
		  
		  response = blockingStub6.mean(request);
		  System.out.println("Answer: " + response.getSolution());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }
  
  public void median(List<Double> nums, double elements) {
	  
	  advancedCalcRequest request = advancedCalcRequest.newBuilder().addAllNum(nums).setElements(elements).build();
	  advancedCalcResponse response;
	  
	  try {
		  
		  response = blockingStub6.median(request);
		  System.out.println("Answer: " + response.getSolution());
	      
	  }catch (Exception e) {
	    	
		  System.err.println("RPC failed: " + e);
	      return;
	      
	  }
	  
  }
  
  public static void main(String[] args) throws Exception {
	  
    if (args.length != 5 && args.length != 6) {
    	
      System.out.println("Expected arguments: <host(String)> <port(int)> <regHost(string)> <regPort(int)> <message(String)>");
      System.exit(1);
      
    }
    
    int port = 9099;
    int regPort = 9003;
    String host = args[0];
    String regHost = args[2];
    String message = args[4];
    boolean task1 = false;
    
    try {
    	
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
      
    } catch (NumberFormatException nfe) {
    	
      System.out.println("[Port] must be an integer");
      System.exit(2);
      
    }

    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    
    try {

      Client client = new Client(channel, regChannel);
    	  
    	  String[] services;
    	  Scanner input = new Scanner(System.in);
		  String selection = "";
		  int choice = 0;
		  String regServer = "";
		  String service = "";
		  double answer = 0.0;
		  double size = 0.0;

	      // call the parrot service on the server
	      client.askServerToParrot(message);
	
	      System.out.println("\n--== Service Center ==--");
	      
	      inputLoop: while(true) {
		
		      System.out.println("\nWhich service would you like to use today?");
		      
		      System.out.println("NOTE: parrot, getServices, findServer, findServers and register are not available for regular clients. Sorry!\n");
		      
		      System.out.println("Type 16 to quit.\n");
		      
		      services = client.getServices();
		      
		      System.out.println("");
		      
		      selection = input.nextLine();
		      
		      try {
		    	  
		    	  choice = Integer.parseInt(selection);
		    	  
		      }catch(NumberFormatException ex) {
		    	  
		    	  System.out.println("\nPlease input an integer value.");
		    	  continue;
		    	  
		      }
		      
		      if(choice != 16) {
		      
			      service = services[choice - 1];
			      regServer = client.findServer(service);
			      regChannel = ManagedChannelBuilder.forTarget(regServer).usePlaintext().build();
			      client = new Client(channel, regChannel);
		      
		      }
		      
    		  switch(choice) {
		     
		     	case 2:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		System.out.println("Type the joke you would like to add: ");
		     		
		     		selection = input.nextLine();
		     		
		     		client.setJoke(selection);
		     		
		     		break;
		     	case 3:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		System.out.println("How many jokes would you like?\n");
		     		
		     		selection = input.nextLine();
		     		
		     		try {
		     			
		     			client.askForJokes(Integer.parseInt(selection));
		     			
		     		}catch(NumberFormatException ex) {
		     			
		     			System.out.println("\nPlease input a valid number.\n");
	  				    input.nextLine();
		     			
		     		}
		     		
		     		break;
		     	case 4:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		List<Double> subtraction = new ArrayList<>();
			  		answer = 0.0;
			  		System.out.println("\nLets subtract some numbers!\n");
			  		System.out.println("Input 1 number at a time, when you would like to stop, type stop.");
			  		
			  		while(!selection.equals("stop")) {
			  			
			  			selection = input.nextLine();
			  			
			  			if(!selection.equals("stop")) {
			  				
			  				try{
			  					
			  					answer = Double.parseDouble(selection);
			  				    
			  				    subtraction.add(answer);
			  				    
			  				}catch (NumberFormatException ex) {
			  					
			  				    System.out.println("\nPlease input a valid number.\n");
			  				    input.nextLine();
			  				    break;
			  					
			  				}
			  				
			  			}
			  			
			  		}
			  		
			  		System.out.println("\nSubtracting the numbers...\n");
		    		client.subtract(subtraction);
		     		
		     		break;
		     	case 5:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		List<Double> division = new ArrayList<>();
			  		answer = 0.0;
			  		System.out.println("\nLets divide some numbers!\n");
			  		System.out.println("Input 1 number at a time, when you would like to stop, type stop.");
			  		
			  		while(!selection.equals("stop")) {
			  			
			  			selection = input.nextLine();
			  			
			  			if(!selection.equals("stop")) {
			  				
			  				try{
			  					
			  					answer = Double.parseDouble(selection);
			  				    
			  				    division.add(answer);
			  				    
			  				}catch (NumberFormatException ex) {
			  					
			  				    System.out.println("\nPlease input a valid number.\n");
			  				    input.nextLine();
			  				    break;
			  					
			  				}
			  				
			  			}
			  			
			  		}
			  		
			  		System.out.println("\nDividing the numbers...\n");
		    		client.divide(division);
		     		
		     		break;
		     	case 6:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		List<Double> addition = new ArrayList<>();
			  		answer = 0.0;
			  		System.out.println("Lets add some numbers!\n");
			  		System.out.println("Input 1 number at a time, when you would like to stop, type stop.");
			  		
			  		while(!selection.equals("stop")) {
			  			
			  			selection = input.nextLine();
			  			
			  			if(!selection.equals("stop")) {
			  				
			  				try{
			  					
			  				    answer = Double.parseDouble(selection);
			  				    
			  				    addition.add(answer);
			  				    
			  				}catch (NumberFormatException ex) {
			  					
			  				    System.out.println("\nPlease input a valid number.\n");
			  				    input.nextLine();
			  				    break;
			  					
			  				}
			  				
			  			}
			  			
			  		}
			  		
			  		System.out.println("\nAdding the numbers...\n");
		    		client.add(addition);
		     		
		     		break;
		     	case 7:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		List<Double> multiplication = new ArrayList<>();
			  		answer = 0.0;
			  		System.out.println("\nLets multiply some numbers!\n");
			  		System.out.println("Input 1 number at a time, when you would like to stop, type stop.");
			  		
			  		while(!selection.equals("stop")) {
			  			
			  			selection = input.nextLine();
			  			
			  			if(!selection.equals("stop")) {
			  				
			  				try{
			  					
			  					answer = Double.parseDouble(selection);
			  				    
			  				    multiplication.add(answer);
			  				    
			  				}catch (NumberFormatException ex) {
			  					
			  				    System.out.println("\nPlease input a valid number.\n");
			  				    input.nextLine();
			  				    break;
			  					
			  				}
			  				
			  			}
			  			
			  		}
			  		
			  		System.out.println("\nMultiplying the numbers...\n");
		    		client.multiply(multiplication);
		     		
		     		break;
		     	case 8:
		     		String Name = "";
    		  		
    		  		System.out.println("\nLets create a tip!\n");
    		  		System.out.println("First, what is your name?\n");
    		  		
    		  		Name = input.nextLine();
    		  		
    		  		System.out.println("\nThanks " + Name + "! " + "Now, input your tip: \n");
    		  		
    		  		selection = input.nextLine();
    		  		
    		  		System.out.println("\nWriting your tip to memory...\n");
    		  		
    		  		client.write(selection, Name);
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		break;
		     	case 9:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		client.read();
		     		break;
		     	case 10:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		List<Double> median = new ArrayList<>();
			  		answer = 0.0;
			  		System.out.println("\nLets find the median of a data set!\n");
			  		System.out.println("Input 1 number at a time, when you would like to stop, type stop.");
			  		
			  		while(!selection.equals("stop")) {
			  			
			  			selection = input.nextLine();
			  			
			  			if(!selection.equals("stop")) {
			  				
			  				try{
			  					
			  					answer = Double.parseDouble(selection);
			  				    
			  				    median.add(answer);
			  				    
			  				}catch (NumberFormatException ex) {
			  					
			  				    System.out.println("\nPlease input a valid number.\n");
			  				    input.nextLine();
			  				    break;
			  					
			  				}
			  				
			  			}
			  			
			  		}
			  		
			  		size = (double) median.size();
			  		
			  		System.out.println("\nFinding the median of the numbers...\n");
		    		client.median(median, size);
		     		
		     		break;
		     	case 11:
		     		System.out.println("\nConnecting you to a server that has that service...\n");
		     		
		     		List<Double> mean = new ArrayList<>();
			  		answer = 0.0;
			  		System.out.println("\nLets find the mean of a data set!\n");
			  		System.out.println("Input 1 number at a time, when you would like to stop, type stop.");
			  		
			  		while(!selection.equals("stop")) {
			  			
			  			selection = input.nextLine();
			  			
			  			if(!selection.equals("stop")) {
			  				
			  				try{
			  					
			  					answer = Double.parseDouble(selection);
			  				    
			  				    mean.add(answer);
			  				    
			  				}catch (NumberFormatException ex) {
			  					
			  				    System.out.println("\nPlease input a valid number.\n");
			  				    input.nextLine();
			  				    break;
			  					
			  				}
			  				
			  			}
			  			
			  		}
			  		
			  		size = (double) mean.size();
			  		
			  		System.out.println("\nFinding the mean of the numbers...\n");
		    		client.mean(mean, size);
		     		
		     		break;
		     	case 16:
		     		System.out.println("\nGoodbye!\n");
		     		break inputLoop;
		     	default:
		     		System.out.println("\nPlease choose a valid selection (2-11).");
		     		continue;
		     
		     }
		      
	      }

    } finally {
      // ManagedChannels use resources like threads and TCP connections. To prevent
      // leaking these
      // resources the channel should be shut down when it will no longer be used. If
      // it may be used
      // again leave it running.
      channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
      regChannel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
    }
  }
}
