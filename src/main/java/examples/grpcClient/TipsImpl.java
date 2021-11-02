package example.grpcclient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import java.util.Stack;
import java.io.*;
import java.nio.file.*;
import java.nio.channels.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;

class TipsImpl extends TipsGrpc.TipsImplBase {
	
	Path path = Paths.get("/home/noah/Desktop/gRPC/tipLog.txt");
	Stack<Tip> tips = new Stack<Tip>();
	
	public TipsImpl() {
		
		super();
		
	}
	
	public void read(Empty empty, StreamObserver<TipsReadResponse> responseObserver) {
		
		TipsReadResponse.Builder response = TipsReadResponse.newBuilder();
		FileReader in = null;
		
		try {
		
			in = new FileReader("/home/noah/Desktop/gRPC/tipLog.txt");
		
		}catch(FileNotFoundException e) {
			
			System.err.println(e);
			
		}
		Tip x;
		
		try(BufferedReader reader = new BufferedReader(in)) {
			
			String line;
			
			while((line = reader.readLine()) != null) {
				
				String[] result = line.split(" ", 2);
				
				if(result.length > 1) {
				
				    String name = result[0];
				    String rest = result[1];
				    
				    String[] con = rest.split("\\:", 2);
				    String tip = con[1];
				    
				    x = Tip.newBuilder().setName(name).setTip(tip).build();
				    
				    response.addTips(x);
			    
				}
			    
			}
			
		}catch (IOException e) {
			
		    System.err.println(e);
		    
		}
		
		TipsReadResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
	@Override
	public void write(TipsWriteRequest req, StreamObserver<TipsWriteResponse> responseObserver) {
		
		TipsWriteResponse.Builder response = TipsWriteResponse.newBuilder();
		
		if(req.hasTip()) {
		
			Tip x = req.getTip();
			
			String name = x.getName();
			String tip = x.getTip();
			String writeTo = (System.lineSeparator() + name + " submitted the following tip: " + tip);
		
			try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.APPEND))) {
				
			    out.write(writeTo.getBytes());
			    
			    response.setIsSuccess(true);
			    
			}catch (IOException e) {
				
			    System.err.println(e);
			    
			}
			
		}else {
			
			response.setIsSuccess(false);
			
		}
		
		TipsWriteResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
}