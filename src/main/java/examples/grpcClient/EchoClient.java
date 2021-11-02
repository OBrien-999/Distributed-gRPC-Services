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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Client that requests `parrot` method from the `EchoServer`.
 */
public class EchoClient {
  private final EchoGrpc.EchoBlockingStub blockingStub;
  private final JokeGrpc.JokeBlockingStub blockingStub2;
  private final RegistryGrpc.RegistryBlockingStub blockingStub3;
  private final CalcGrpc.CalcBlockingStub blockingStub4;
  private final TipsGrpc.TipsBlockingStub blockingStub5;
  private final advancedCalcGrpc.advancedCalcBlockingStub blockingStub6;

  /** Construct client for accessing server using the existing channel. */
  public EchoClient(Channel channel, Channel regChannel) {
    // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's
    // responsibility to
    // shut it down.

    // Passing Channels to code makes code easier to test and makes it easier to
    // reuse Channels.
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

  public void getServices() {
    GetServicesReq request = GetServicesReq.newBuilder().build();
    ServicesListRes response;
    try {
      response = blockingStub3.getServices(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
    }
  }

  public void findServer(String name) {
    FindServerReq request = FindServerReq.newBuilder().setServiceName(name).build();
    SingleServerRes response;
    try {
      response = blockingStub3.findServer(request);
      System.out.println(response.toString());
    } catch (Exception e) {
      System.err.println("RPC failed: " + e);
      return;
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
    int auto = 0;
    boolean task1 = false;
    
    try {
    	
      if(args.length == 6) {
        	
    	    task1 = true;
        	auto = Integer.parseInt(args[5]);
        	
      }
    	
      port = Integer.parseInt(args[1]);
      regPort = Integer.parseInt(args[3]);
      
    } catch (NumberFormatException nfe) {
    	
      System.out.println("[Port] must be an integer");
      System.exit(2);
      
    }
    
    // Create a communication channel to the server, known as a Channel. Channels
    // are thread-safe
    // and reusable. It is common to create channels at the beginning of your
    // application and reuse
    // them until the application shuts down.
    String target = host + ":" + port;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
        // Channels are secure by default (via SSL/TLS). For the example we disable TLS
        // to avoid
        // needing certificates.
        .usePlaintext().build();

    String regTarget = regHost + ":" + regPort;
    ManagedChannel regChannel = ManagedChannelBuilder.forTarget(regTarget).usePlaintext().build();
    
    try {

      // ##############################################################################
      // ## Assume we know the port here from the service node it is basically set through Gradle
      // here.
      // In your version you should first contact the registry to check which services
      // are available and what the port
      // etc is.

      /**
       * Your client should start off with 
       * 1. contacting the Registry to check for the available services
       * 2. List the services in the terminal and the client can
       *    choose one (preferably through numbering) 
       * 3. Based on what the client chooses
       *    the terminal should ask for input, eg. a new sentence, a sorting array or
       *    whatever the request needs 
       * 4. The request should be sent to one of the
       *    available services (client should call the registry again and ask for a
       *    Server providing the chosen service) should send the request to this service and
       *    return the response in a good way to the client
       * 
       * You should make sure your client does not crash in case the service node
       * crashes or went offline.
       */
    	
      // Just doing some hard coded calls to the service node without using the
      // registry
      // create client
      EchoClient client = new EchoClient(channel, regChannel);
      
      if(!task1) {

	      // call the parrot service on the server
	      client.askServerToParrot(message);
	
	      // ask the user for input how many jokes the user wants
	      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	      // Reading data using readLine
	      System.out.println("How many jokes would you like?"); // NO ERROR handling of wrong input here.
	      String num = reader.readLine();
	
	      // calling the joked service from the server with num from user input
	      client.askForJokes(Integer.valueOf(num));
	
	      // adding a joke to the server
	      client.setJoke("I made a pencil with two erasers. It was pointless.");
	
	      // showing 6 joked
	      client.askForJokes(Integer.valueOf(6));
	
	      // ############### Contacting the registry just so you see how it can be done
	      
	      // Comment these last Service calls while in Activity 1 Task 1, they are not needed and wil throw issues without the Registry running
	      // get thread's services
	      
	      /*
	      
	      client.getServices();
			
	      // get parrot
	      client.findServer("services.Echo/parrot");
	      	
	      // get all setJoke
	      client.findServers("services.Joke/setJoke");
			
	      // get getJoke
	      client.findServer("services.Joke/getJoke");
			
	      // does not exist
	      client.findServer("random");
	
		  */
      
      }else {
    	  
    	  if(auto == 0) {
    		  
    		  // Manual
    		  Scanner input = new Scanner(System.in);
    		  String selection = "";
    		  int choice = 0;
    		  double answer = 0.0;
    		  double size = 0.0;
    		  
    		  System.out.println("\n--== Hello and welcome to the service center! ==--");
    		  
    		  inputLoop: while(true) {
    		  
	    		  System.out.println("Available services today: ");
	    		  System.out.println("\n1: Calculator\n2: Tips\n3: Advanced Calculator");
	    		  
	    		  System.out.println("\nWhich service would you like to use today? Type 4 to exit.");
	    		  selection = input.nextLine();
	    		  
	    		  if(Integer.parseInt(selection) == 1) {
	    			  
	    			  innerInputLoop: while(true) {
	    			  
		    			  System.out.println("\n--== Calculator ==--\n");
		    			  System.out.println("Select what you would like to do\n");
		    			  System.out.println("1: Add\n2: Subtract\n3: Multiply\n4: Divide\n5: Go back to previous menu\n");
		    			  selection = input.nextLine();
		    			  
		    			  choice = Integer.parseInt(selection);
		    			  
		    			  switch(choice) {
		    			  
		    			  	case 1:
		    			  		List<Double> addition = new ArrayList<>();
		    			  		answer = 0.0;
		    			  		System.out.println("\nLets add some numbers!\n");
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
		    			  	case 2:
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
		    			  	case 3:
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
		    			  	case 4:
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
		    			  	case 5:
		    			  		break innerInputLoop;
		    			  	default:
		    			  		System.out.println("\nPlease input a valid option (1-5).\n");
		    			  		input.nextLine();
		    			  		break;
		    			  			
		    			  
		    			  }
	    			  
	    			  }
	    			  
	    		  }else if(Integer.parseInt(selection) == 2) {
	    			  
	    			  innerInputLoop: while(true) {
	    			  
		    			  System.out.println("\n--== Tips ==--");
		    			  System.out.println("Select what you would like to do\n");
		    			  System.out.println("1: Read all existing tips\n2: Create a tip\n3: Go back to previous menu");
		    			  selection = input.nextLine();
		    			  
			    		  choice = Integer.parseInt(selection);
			    		  
			    		  switch(choice) {
			    		  
			    		  	case 1:
			    		  		client.read();
			    		  		break;
			    		  	case 2:
			    		  		String Name = "";
			    		  		
			    		  		System.out.println("\nLets create a tip!\n");
			    		  		System.out.println("First, what is your name?\n");
			    		  		
			    		  		Name = input.nextLine();
			    		  		
			    		  		System.out.println("\nThanks " + Name + "! " + "Now, input your tip: \n");
			    		  		
			    		  		selection = input.nextLine();
			    		  		
			    		  		System.out.println("\nWriting your tip to memory...\n");
			    		  		
			    		  		client.write(selection, Name);
			    		  		
			    		  		break;
			    		  	case 3:
			    		  		break innerInputLoop;
			    		  	default:
			    		  		System.out.println("\nPlease input a valid option (1-3).\n");
		    			  		input.nextLine();
			    		  		break;
			    		  
			    		  }
		    		  
	    			  }
	    			  
	    		  }else if(Integer.parseInt(selection) == 3) {
	    			  
	    			  innerInputLoop: while(true) {
		    			  
		    			  System.out.println("\n--== Advanced Calculator ==--\n");
		    			  System.out.println("Select what you would like to do\n");
		    			  System.out.println("1: Find the mean of a set\n2: Find the mode of a set\n3: Go back to previous menu\n");
		    			  selection = input.nextLine();
		    			  
		    			  choice = Integer.parseInt(selection);
		    			  
		    			  switch(choice) {
		    			  
		    			  	case 1:
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
		    			  	case 2:
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
		    			  	case 3:
		    			  		break innerInputLoop;
		    			  	default:
		    			  		System.out.println("\nPlease input a valid option (1-3).\n");
		    			  		input.nextLine();
		    			  		break;
		    			  			
		    			  
		    			  }
		    			  
	    			  }
	    			  
	    		  }else if(Integer.parseInt(selection) == 4) {
	    			  
	    			  break inputLoop;
	    			  
	    		  }else {
	    			  
	    			  System.out.println("\nPlease input a valid selection (1-3).\n");
	    			  
	    		  }
    		  
    		  }
    		  
    		  System.out.println("\nGoodbye!");
    		  
    		  
    	  }else if(auto == 1) {
    		  
    		  System.out.println("\nYou have selected the automatic showcase!");
    		  
    		  // Automatic
    		  List<Double> numbers = Arrays.asList(4.2, 2.0, 9.5, 1.1, 7.3, 5.8);
    		  
    		  System.out.print("\n[Calc] Your given set of numbers are: ");
    		  
    		  for(double list: numbers) {
    			  
    			  System.out.print(list + " | ");
    			  
    		  }
    		  
    		  System.out.println("\n");
    		  
    		  System.out.println("Adding the numbers...");
    		  client.add(numbers);
    		  
    		  System.out.println("\nSubtracting the numbers...");
    		  client.subtract(numbers);
    		  
    		  System.out.println("\nMultiplying the numbers...");
    		  client.multiply(numbers);
    		  
    		  System.out.println("\nDividing the numbers...");
    		  client.divide(numbers);
    		  
    		  String tip1 = "When it's cold outside, wear a jacket!";
    		  String tip2 = "When you're hungry, eat some food.";
    		  String tip3 = "If you feel sick, stay home from work and stay inside.";
    		  String name = "Test";
    		  
    		  System.out.println("\n[Tips] Your given set of tips are: ");
    		  
    		  System.out.println(tip1);
    		  System.out.println(tip2);
    		  System.out.println(tip3);
    		  
    		  System.out.println("\nWriting tip 1 to memory...");
    		  
    		  client.write(tip1, name);
    		  
    		  System.out.println("\nWriting tip 2 to memory...");
    		  
    		  client.write(tip2, name);
    		  
    		  System.out.println("\nWriting tip 3 to memory...");
    		  
    		  client.write(tip3, name);
    		  
    		  System.out.println("\nHere is your saved list of tips: ");
    		  
    		  client.read();
    		  
    		  List<Double> numbers2 = Arrays.asList(19.3, 2.4, 10.11, 27.9, 19.5, 4.0, 15.7, 1.3, 9.9);
    		  
    		  System.out.print("\n[advancedCalc] Your given set of numbers are: ");
    		  
    		  for(double list: numbers2) {
    			  
    			  System.out.print(list + " | ");
    			  
    		  }
    		  
    		  double sizeOf = (double) numbers2.size();
    		  
    		  System.out.println("\n");
    		  
    		  System.out.println("Finding the mean of the numbers...");
    		  client.mean(numbers2, sizeOf);
    		  
    		  System.out.println("\nFinding the median of the numbers...");
    		  client.median(numbers2, sizeOf);
    		  
    		  System.out.println("\nGoodbye!");
    		  
    		  
    	  }else {
    		  
    		  // Error handling
    		  System.out.println("\n[Auto] needs to be either 0 or 1. Select 0 to use manual input, or 1 to use automatic input\n");
    		  System.exit(1);
    		  
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
