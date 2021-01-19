package com.javaex.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Controller
@RequestMapping(value="/phone")
public class PhoneController {

	//필드
	
	//생성자
	
	//메소드 g.s
	
	//메소드 일반
	//메소드마다 기능 1개씩 --> 기능마다 url 부여
	
	//http://localhost:8088/phonebook3/phone/list
	//리스트
	@RequestMapping(value="list", method= {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model) {
		System.out.println("list");
		
		//dao를 통해 리스트를 가져온다.
		PhoneDao phoneDao = new PhoneDao();
		List<PersonVo> personList = phoneDao.getPersonList();
		System.out.println(personList.toString());
		
		//model --> data 를 보내는 방법 --> 담아 놓으면 된다.
		model.addAttribute("pList", personList);
		
		return "/WEB-INF/views/list.jsp";
	}
		
	//http://localhost:8088/phonebook3/phone/writeForm
	//등록폼
	@RequestMapping(value="/writeForm", method = {RequestMethod.GET , RequestMethod.POST })
	public String writeForm() {
		System.out.println("WriteForm");
		
		return "/WEB-INF/views/writeForm.jsp";
	}
	
	//http://localhost:8088/phonebook3/phone/writeForm?name=서웅기&hp=?&company=?
	//등록
	@RequestMapping(value="/write", method= {RequestMethod.GET,RequestMethod.POST} )
	public String write(@RequestParam("name") String name,
						@RequestParam("hp") String hp,
						@RequestParam("company") String company) {
		
		System.out.println("write");
		System.out.println(name + hp + company);
		
		PersonVo personVo = new PersonVo(name, hp, company);
		System.out.println(personVo.toString());
		
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personInsert(personVo);
		
		return "redirect:/phone/list";
	} 
	
	//수정폼
	@RequestMapping(value="/modifyForm", method= {RequestMethod.GET, RequestMethod.POST})
	public String modifyForm(Model model,
							 @RequestParam("no") int no) {
		System.out.println("modifyForm");
		
		PhoneDao phoneDao = new PhoneDao();
		PersonVo personVo = phoneDao.getPerson(no);
		
		model.addAttribute("uList" , personVo);
		
		return "/WEB-INF/views/modifyForm.jsp";
	}
	
	//수정
	public String modify() {
		System.out.println("modify");
		
		return "";
	}
	
	//삭제
	@RequestMapping(value="/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("no") int no) {
		System.out.println("delete");
		
		PhoneDao phoneDao = new PhoneDao();
		phoneDao.personDelete(no);
		
		
		return "redirect:/phone/list";
	}	
	
	
	
	
}
