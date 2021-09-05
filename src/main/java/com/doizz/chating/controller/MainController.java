package com.doizz.chating.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.doizz.chating.vo.Room;

@Controller
public class MainController {
	
	List<Room> roomList = new ArrayList<Room>();
	static int roomNumber = 0;
	
	
	@RequestMapping("/chat")
	public ModelAndView chat() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("chat");
		return mv;
	}
	
	/**
	 * 방 페이지
	 * @return
	 */
	@RequestMapping("/room")
	public ModelAndView room() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("room");
		return mv;
	}
	
	/**
	 * 방 생성하기
	 * @param params
	 * @return
	 */
	@RequestMapping("/createRoom")
	public @ResponseBody List<Room> createRoom(@RequestParam HashMap<Object, Object> params){
		//roomName을 가져온다.
		String roomName = (String) params.get("roomName");
		//roomName이 널이 아닐경우 방번호를 생성하여 리턴시킨다.
		if(roomName != null && !roomName.trim().equals("")) {
			Room room = new Room();
			room.setRoomNumber(++roomNumber);
			room.setRoomName(roomName);
			roomList.add(room);
		}
		return roomList;
	}
	
	/**
	 * 방 정보가져오기
	 * @param params
	 * @return
	 */
	@RequestMapping("/getRoom")
	public @ResponseBody List<Room> getRoom(@RequestParam HashMap<Object, Object> params){
		return roomList;
	}
	
	/**
	 * 채팅방
	 * @return
	 */
	@RequestMapping("/moveChating")
	public ModelAndView chating(@RequestParam HashMap<Object, Object> params) {
		ModelAndView mv = new ModelAndView();
		//파라미터 넘어온 roomNumber를 변수에 대입한다.
		int roomNumber = Integer.parseInt((String) params.get("roomNumber"));
		//roomnumber를 기존에 저장되어있는 roomnumber session에서 찾는다.
		List<Room> new_list = roomList.stream().filter(o->o.getRoomNumber()==roomNumber).collect(Collectors.toList());
		//roomnumber가 존재할경우 채팅으로 이동한다.
		if(new_list != null && new_list.size() > 0) {
			mv.addObject("roomName", params.get("roomName"));
			mv.addObject("roomNumber", params.get("roomNumber"));
			mv.setViewName("chat");
		}else {
		//없을경우 방생성 화면으로 이동한다.	
			mv.setViewName("room");
		}
		return mv;
	}
	
}
