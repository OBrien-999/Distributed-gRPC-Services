package example.grpcclient;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerMethodDefinition;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import service.*;
import java.util.Stack;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.*;

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;

class advancedCalcImpl extends advancedCalcGrpc.advancedCalcImplBase {
	
	public advancedCalcImpl() {
		
		super();
		
	}
	
	@Override
	public void mean(advancedCalcRequest req, StreamObserver<advancedCalcResponse> responseObserver) {
		
		System.out.println("Received from client: ");
		
		int iterate = (int) req.getElements();
		double answer = 0.0;
		double size = req.getElements();
		
		for(int i = 0; i < iterate; i++) {
			
			System.out.println(req.getNum(i));
			answer += req.getNum(i);
			
		}
		
		answer = answer / size;
		
		advancedCalcResponse.Builder response = advancedCalcResponse.newBuilder();
		response.setSolution(answer);
		
		advancedCalcResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
	@Override
	public void median(advancedCalcRequest req, StreamObserver<advancedCalcResponse> responseObserver) {
		
		System.out.println("Received from client: ");
		
		int iterate = (int) req.getElements();
		double answer = 0.0;
		List<Double> numbers = new ArrayList<>();
		
		for(int i = 0; i < iterate; i++) {
			
			System.out.println(req.getNum(i));
			numbers.add(req.getNum(i));
			
		}
		
		Collections.sort(numbers);
		
		if((iterate % 2) != 0) {
			
			answer = numbers.get(iterate / 2);
			
		}else {
			
			answer = (numbers.get((iterate - 1) / 2) + numbers.get(iterate / 2)) / 2.0;
			
		}
		
		advancedCalcResponse.Builder response = advancedCalcResponse.newBuilder();
		response.setSolution(answer);
		
		advancedCalcResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
}