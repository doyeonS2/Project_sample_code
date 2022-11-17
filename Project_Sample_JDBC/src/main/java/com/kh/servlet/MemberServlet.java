package com.kh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.kh.common.Common;
import com.kh.dao.MemberDAO;
import com.kh.vo.MemberVO;

@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	// CORS 처리
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Common.corsResSet(response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		Common.corsResSet(response);
		StringBuffer sb = Common.reqStringBuff(request);
		JSONObject jsonObj = Common.getJsonObj(sb);
		
		String reqCmd = (String)jsonObj.get("cmd");
		String reqId = (String)jsonObj.get("id");
		System.out.println("전달 받은 ID : " + reqId);
		
		
		PrintWriter out = response.getWriter();
		if(!reqCmd.equals("MemberInfo")) {
			JSONObject resJson = new JSONObject();
			resJson.put("result", "NOK");
			out.print(resJson);
			return;
		} 
		
		MemberDAO dao = new MemberDAO();
		List<MemberVO> list = dao.memberSelect(reqId);
		
		JSONArray memberArray = new JSONArray();
		for (MemberVO e : list) {
			JSONObject memberInfo = new JSONObject();
			memberInfo.put("id", e.getId());
			memberInfo.put("pwd", e.getPwd());
			memberInfo.put("name", e.getName());
			memberInfo.put("email", e.getEmail());
			DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
			String dateToStr = dateFormat.format(e.getJoin());
			memberInfo.put("join", dateToStr);
			memberArray.add(memberInfo);
		}
		System.out.println(memberArray);
		out.print(memberArray);
	}
}
