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

import buffers.RequestProtos.Request;
import buffers.RequestProtos.Request.RequestType;
import buffers.ResponseProtos.Response;

class CalcImpl extends CalcGrpc.CalcImplBase {
	
	public CalcImpl() {
		
		super();
		
	}
	
	@Override
	public void add(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
		
		System.out.println("Received from client: ");
		
		int size = req.getNumCount();
		double answer = 0.0;
		
		for(int i = 0; i < size; i++) {
			
			System.out.println(req.getNum(i));
			answer = answer + req.getNum(i);
			
		}

		CalcResponse.Builder response = CalcResponse.newBuilder();
		response.setSolution(answer);
		
		CalcResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
	@Override
	public void subtract(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
		
		System.out.println("Received from client: ");
		
		int size = req.getNumCount();
		double answer = req.getNum(0);
		
		for(int i = 1; i < size; i++) {
			
			System.out.println(req.getNum(i));
			answer = answer - req.getNum(i);
			
		}
		
		CalcResponse.Builder response = CalcResponse.newBuilder();
		response.setSolution(answer);
		
		CalcResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
	@Override
	public void multiply(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
		
		System.out.println("Received from client: ");
		
		int size = req.getNumCount();
		double answer = req.getNum(0);
		
		for(int i = 1; i < size; i++) {
			
			System.out.println(req.getNum(i));
			answer = answer * req.getNum(i);
			
		}
		
		CalcResponse.Builder response = CalcResponse.newBuilder();
		response.setSolution(answer);
		
		CalcResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
	@Override
	public void divide(CalcRequest req, StreamObserver<CalcResponse> responseObserver) {
		
		System.out.println("Received from client: ");
		
		int size = req.getNumCount();
		double answer = req.getNum(0);
		
		for(int i = 1; i < size; i++) {
			
			System.out.println(req.getNum(i));
			answer = answer / req.getNum(i);
			
		}
		
		CalcResponse.Builder response = CalcResponse.newBuilder();
		response.setSolution(answer);
		
		CalcResponse res = response.build();
		responseObserver.onNext(res);
        responseObserver.onCompleted();
		
	}
	
}