<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<body>
	<h2>채팅방</h2>

	<input type="text" name="memId" name = "memId" id = "memId" value ="${sessionScope.SessionInfo.memName }" readonly/>
	<input type="text" id="msg" />
	<input type="file" id="inputFile"/>
	<input type="button" id="sendBtn" value="전송"/><br/>
	<div id="messageArea"></div>

</body>	
<script type="text/javascript">
$(function(){
var memId = $("#memId");
var sendBtn = $("#sendBtn");
var msg = $("#msg");
var inputFile = $("#inputFile");	

inputFile.on("change", function(event){
	console.log("change event,,");
	
	var files = event.target.files;
	var file = files[0];
	
	console.log(file);
	
		
		// ajax로 파일을 컨트롤 시 formData를 이용한다.
		// append() 이용
		var formData = new FormData();
		// key : value 형태로 값이 추가된다.
		formData.append("file", file);
		
		// formData는 key/value 형식으로 데이터가 저장된다.
		// dataType : 응답(response) 데이터를 내보낼 때 보내줄 데이터 타입이다.
		// processData : 데이터 파라미터를 data라는 속성으로 넣는데 제이쿼리 내부적으로 쿼리스트링을 구성한다.
		// 				파일 전송의 경우 쿼리 스트링을 사용하지 않으므로 해당 설정을 false한다.
		// contentType : content-Type 설정 시 사용하는데 해당 설정의 기본값은 "application/x-www-form-urlencoded; charset=utf-8"이다
		// request 요청에서 content-Type을 확인해보면 "multipart/form-data; boundary=---WebkitFormBoundary7Taxt434B
		// 과 같은 값을 전송되는 걸 확인할 수 있다.
		
		$.ajax({
			type : "post",
			url : "/ajax/uploadAjax",
			data : formData,
			dataType : "text",
			processData : false,
			contentType : false,
			success : function(data){
				//alert(data);
				var str = "";
				if(checkImageType(data)) { // 이미지이면 이미지 태그를 이용한 출력형태
					str += "<div>";
					str += "	<a href = '/ajax/displayFile?fileName="+ data + "'>";
					str += "	<img src = '/ajax/displayFile?fileName=" + getThumbnailName(data)+ "'/>";
					str += "	</a>";
					str += "</div>";
				} else { // 파일이면 파일명에 대한 링크로만 출력
					str += "<div>";
					str += "	<a href ='/ajax/displayFile?fileName="+data+"'>" +getOriginalName(data) + "</a>";
					str += "</div>";
				}
				$("#messageArea").prepend(str);
			}
		});
});

function getOriginalName(fileName){
	if(checkImageType(fileName)){
		return;
	}		
	var idx = fileName.indexOf("_") + 1;
	return fileName.substr(idx);
}
function getThumbnailName(fileName){
	var front = fileName.substr(0,12);
	var end = fileName.substr(12);
	
	console.log("front : " + front);
	console.log("end : " + end);
	
	return front + "s_" + end;
}

function checkImageType(fileName){
	var pattern = /jpg|gif|png|jpeg/i;
	return fileName.match(pattern); // 패턴과 일치하면 true (너 이미지 맞구나?)
}


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


