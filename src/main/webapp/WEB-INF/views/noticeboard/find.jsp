<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
      <p href="" class="h4"><b>아이디찾기</b></p>
    </div>
    <div class="card-body">
      <p class="login-box-msg">아이디 찾기는 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
      <form action="" method="post">
        <div class="input-group mb-3">
          <input type="text" id = "memEmail" class="form-control" placeholder="이메일을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <input type="text" id = "memName" class="form-control" placeholder="이름을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <p>회원님의 아이디는 <font></font> 입니다.</p>
        </div>
        <div class="row">
          <div class="col-12">
            <button type="button" id ="idBtn" class="btn btn-primary btn-block">아이디찾기</button>
          </div>
        </div>
      </form>
   </div>
  </div>
  <br/>
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
      <p href="" class="h4"><b>비밀번호찾기</b></p>
    </div>
    <div class="card-body">
      <p class="login-box-msg">비밀번호 찾기는 아이디, 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
      <form action="" method="post">
        <div class="input-group mb-3">
          <input type="text" id = "memId" class="form-control" placeholder="아이디를 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <input type="text" id = "memEmail" class="form-control" placeholder="이메일을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <input type="text" id = "memName" class="form-control" placeholder="이름을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <p>회원님의 비밀번호는 <font></font> 입니다.</p>
        </div>
        <div class="row">
          <div class="col-12">
            <button type="button" id = "pwBtn" class="btn btn-primary btn-block">비밀번호찾기</button>
          </div>
        </div>
      </form>
    </div>
  </div>
  <div class="card card-outline card-secondary">
    <div class="card-header text-center">
      <button type="button" class="btn btn-secondary btn-block">로그인</button>
    </div>
  </div>
</div>

<script type="text/javascript">
$(function(){
	
var memId = $("#memId");
var memPw = $("#memPw");
var memName = $("#memName");
var memEmail = $("#memEmail");
var idBtn = $("#idBtn");
var pwBtn = $("#pwBtn");





idBtn.on("click",function(){
	
	$.ajax({
		type : "post",
		url : "/notice/findId.do",
		data : {memEmail : $("#memEmail").val(),
			memName : $("#memName").val()},
		// 키 : 밸류
		success : function(data){
			var emailLists = data.mem_email;
			var emailLength = emailLists.length;
			var emailFind = emailLists.substring(1, emaillenght-1)
			$("#emailList").append("<h1>"+"회원님의 정보로 등록된 이메일은 : "+emailfind+" 입니다.</h1>");
			
			}
		});
	});
});
</script>