package com.green.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller // 이 클래스는 사용자의 요청을 처리하는 '컨트롤러' 임을 스프링에게 알림
public class TestController {
	
	// 모든 컨트롤러는 / 가 반드시 필요
	// http://localhost:8080 == '/'
	@RequestMapping("/")
	// '/' 를 치면은 home() 함수를 실행하여라
	public String home() {
		System.out.println("아핳핳"); // 서버 콘솔창에 출력(작동확인용)
		return "home"; // /WEB-INF/views/home.jsp
	}
	
	// 에러 발생시 java.lang.IllegalArumentException : Name for argument of type [java..... ]
	// project 에 우클릭 properties -> Java Compiler 의 오른쪽위치에서
	// 맨위 Enable project specific settings 체크 
	// 맨마지막 Store information about method parameters(usable via reflection)체크 하기
	
	// /temp주소를 찾아가서 a와 b를 인자로 받는다 a에는 hello(String) b에는 123(int) 
	// /temp?a=hello&b=123
	@RequestMapping("/temp")
	public String temp( String a, int b ) {
		System.out.println("a=" + a);
		System.out.println("b=" + (b+3) );
		                       // "123" 이 숫자로 자동변환되어 126으로 출력
		return "none"; // none.jsp 없음  404에러
						// 에러 뜻 500 : (서버에러)자바에 런타임 에러 분모가0으로 나눠 실행 에러 db 연결하다 연결이 끊김 데이터 연결하다 끊김
		               // 400 : 코드가 잘못됨(주소가 잘못됨)
	}
	
	// /temp1?a=hello&b=123
	@RequestMapping("/temp1")
	public String temp1( HttpServletRequest request,
			HttpServletResponse response
			) { 
		// request 객체에서 직접 "a"와 "b"라는 이름의 파라미터를 꺼냄
		String in_a = request.getParameter("a");
		String in_b = request.getParameter("b");
		
		System.out.println("a");
		System.out.println("b");
		
		String a    = in_a;
		int b       = Integer.parseInt(in_b); // 문자열 "123"을 숫자로 직접 변환해줘야하기때문에 Integer.parseInt 를 사용
		
		System.out.println("a=" + a);
		System.out.println("b=" + (b+7) );
		
		return "none";
	}
	
	// /temp2?a=hello&b=123
	@RequestMapping("/temp2")
	public String temp2( @RequestParam Map<String, String> map ) {
		// 모든 파라미터를 키(key)-값(value) 쌍으로 묶어서 한번에 받는다
		
		System.out.println("map=" + map); // 출력은 map={a=hello, b=123}
		String a = map.get("a");
		int b = Integer.parseInt( map.get("b") );
		
		System.out.println("a=" + a);
		System.out.println("b=" + b);
		return "none"; // none.jsp 없음  404에러				
	}
	
	// 요즘 Springboot 가 인자를 처리하는 방식
	// /temp3?a=hello&b=123
	@RequestMapping("/temp3")
	public String temp3(
			@RequestParam("a") String x, @RequestParam("b") int y)
	{		// a를 자바변수 x 에 넣어라, b를 자바변수 y에 넣어라
		System.out.println("a=" + x);
		System.out.println( "b=" + (y+3) );
		return "none";
	}
	
	// /temp4?a=hello&b=123
	@RequestMapping("/temp4")
	public String temp4( DataVo vo ) {
		String a = vo.getA(); // vo안에있는 getA를 꺼내온다 
		int    b = vo.getB();
		
		System.out.println("a=" + a);
		System.out.println("b=" + (b+10));
		return "none";
	}
	
	//-----------------------------------------------------
	// 넘겨받은 값을 처리해서 jsp 에 넘기는 방법
	// Model 을 사용한다
	// jsp 에 넘겨줄 데이터를 Model class 에 담아서 전달 -> jsp 에서는 ${} 로 값을 받는다
	// /temp5?a=hello&b=123
	@RequestMapping("/temp5")
	public String temp5( String a, int b, Model model ) {
		System.out.println("a=" + a);
		System.out.println("b=" + b);
		
		model.addAttribute("a",a);
		model.addAttribute("b",(b*3));
		model.addAttribute("c","이것도 넘어가나");
		
		return "reqdata";  // reqdata 파일로 리턴한다
	}
	
	// /temp6?a=hello&b=123
	@RequestMapping("/temp6")
	public String temp6(DataVo vo, Model model) { // ${} 로 값을 받는다
		String a = vo.getA();
		int    b = vo.getB();
		
		System.out.println("a=" +a);
		System.out.println("b=" +(b-7));
		
		model.addAttribute("a",a);
		model.addAttribute("b",b-7);
		model.addAttribute("c", "c가 들어가는지 Test");
		
		
		return "reqdata"; // reqdata 파일로 리턴한다
	}

	// /temp7?a=hello&b=123
	@RequestMapping("/temp7")
	public String temp7(
			@ModelAttribute("attrName") DataVo vo,
			Model model
			) {
		
		System.out.println( "a=" + vo.getA() );
		System.out.println( "b=" + vo.getB() );
		
		model.addAttribute("a",vo.getA());
		model.addAttribute("b",vo.getB());
		
		return "noneModel"; // noneModel.jsp 파일로 리턴한다
	}
	
	//--------------------------------------------
	// path Variable : 경로에 data를 포함시키는 방법
	// 주의!!! @PathVariable 생략하면 ERROR 발생  
	// /temp8/hello/123
	@RequestMapping("/temp8/{a}/{b}")// 주소를 넣고 주소부분에 {a},{b}를 넣는다
	public String temp8(
			@PathVariable("a") String a,
			@PathVariable("b") int b
			) {
		System.out.println("a=" + a );
		System.out.println("b=" + (b-23) );
		
		return "none";
	}
	
	// /temp9/hello/123
	// @PathVariable 생략가능 (DataVo vo) 로 받았을시
	@RequestMapping("/temp9/{a}/{b}")
	public String temp9( DataVo vo ) {
		
		System.out.println("a= " + vo.getA());
		System.out.println("a= " + vo.getB());
		
		return "none";
	}
	
	// /temp10/hello/123
	@RequestMapping("/temp10/{a}/{b}")
	public String temp10(
			@ModelAttribute("vo") DataVo vo,
			Model model
			) {
		
		System.out.println("a= " + vo.getA());
		System.out.println("a= " + vo.getB());
		
		return "noneModel";
	}
}






