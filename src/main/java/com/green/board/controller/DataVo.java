package com.green.board.controller;

public class DataVo { // VO나 DTO는 반드시 생성자, getter/setter, toString 다하기
	// Fields
	private String a;
	private int    b;
	
	// Constructor 생성자
	public DataVo() {}
	public DataVo(String a, int b) {
		super();
		this.a = a;
		this.b = b;
	}
	
	// getter / setter
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public int getB() {
		return b;
	}
	public void setB(int b) {
		this.b = b;
	}
	
	// ToString
	@Override
	public String toString() {
		return "DataVo [a=" + a + ", b=" + b + "]";
	}
	
}
