<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<body>
	<h2>채팅방</h2>

	<input type="text" name="memId" name = "memId" id = "memId" value ="${sessionScope.SessionInfo.memName }" readonly/>
	<input type="text" id="msg" />
	<input type="button" id="sendBtn" value="전송"/>
	<div id="messageArea"></div>
</body>	
<script type="text/javascript">
$(function(){
var memId = $("#memId");
var sendBtn = $("#sendBtn");
var msg = $("#msg");
var messageArea = $("#messageArea");
	

	// websocket 생성
    const websocket = new WebSocket("ws://192.168.34.18/echo"); // IP or localhost넣기
    websocket.onmessage = onMessage;	// 소켓이 메세지를 받을 때
    websocket.onopen = onOpen;		// 소켓이 생성될때(클라이언트 접속)
    websocket.onclose = onClose;	// 소켓이 닫힐때(클라이언트 접속해제)

    //on exit chat room
    function onClose(evt) {
    $("#messageArea").append("연결 끊김");
    console.log("소켓닫힘 : " + evt);
    }

    //on entering chat room
    function onOpen(evt) {
    console.log("소켓열림 : " + evt);
    }

    // on message controller
    function onMessage(msg) {
    	
	    var data = JSON.parse(msg.data); // msg를 받으면 data 필드 안에 Json String으로 정보가 있음
	    console.log("데이터 : ", data)
	    // 필요한 정보를 Json data에서 추출
	    
	    
	    
	     var senderId = data.senderId;
	     var message = data.message;
	     var time = data.time;
	     var enterGuest = data.enterGuest; // 입장용 문장 나누기
	     
	     if(enterGuest != null) {
	    	 alert(senderId + "님이 입장 하셨습니다.");
	    	 $("#messageArea").prepend(senderId + message + "  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[" + time + "]" + "<br/>");
	     } else {
		     $("#messageArea").prepend(senderId + " : " + message + "  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[" + time + "]" + "<br/>");
// 		     console.log("메세지 쏘는중 >> " + data);
	    	 
	     }
	     
    }
    // send a message
    $("#sendBtn").click(function(){
   		    var message = $("#msg").val()
   		    var memId = $("#memId").val()
   		    
   			
   			// 입장하셨습니다
   	 		if(memId == "" || memId == null) {
   	 			alert("대화명을 입력해주세요.");
   	 		}
   			
   		    // don't send when content is empty
   		    // 채팅 입력 칸이 비어있지 않을 경우만 정보를 Json형태로 전송
   		    if(message != "") {			
   			let msg = {
    		   	'senderId' : memId,
   		    	'message' : message,
   		    	'time' : new Date().toTimeString().split(' ')[0]
   		      	}

   		    	if(message != null) {
   		    	websocket.send(JSON.stringify(msg));	// websocket handler로 전송(서버로 전송)
   		    	}
   		    	document.getElementById("msg").value = '';
   		 	  } 
    });
});
</script>


