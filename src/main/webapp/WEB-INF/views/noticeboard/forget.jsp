<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="login-box">
  <div class="card card-outline card-primary">
    <div class="card-header text-center">
      <p href="" class="h4"><b>아이디찾기</b></p>
    </div>
    <div class="card-body">
      <p class="login-box-msg">아이디 찾기는 이메일, 이름을 입력하여 찾을 수 있습니다.</p>
      <form action="" method="post">
        <div class="input-group mb-3">
          <input type="text" class="form-control" id="memEmail" name="memEmail" placeholder="이메일을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <input type="text" class="form-control" id="memName" name="memName" placeholder="이름을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <p>회원님의 아이디는 <font id="id"></font> 입니다.</p>
        </div>
        <div class="row">
          <div class="col-12">
            <button type="button" class="btn btn-primary btn-block" id="idBtn">아이디찾기</button>
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
          <input type="text" class="form-control" id="memId" name="memId"  placeholder="아이디를 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <input type="text" class="form-control" id="memEmail1" name="memEmail" placeholder="이메일을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <input type="text" class="form-control" id="memName1" name="memName" placeholder="이름을 입력해주세요.">
        </div>
		<div class="input-group mb-3">
          <p>회원님의 비밀번호는 <font id="pw"></font> 입니다.</p>
        </div>
        <div class="row">
          <div class="col-12">
            <button type="button" class="btn btn-primary btn-block" id="pwBtn">비밀번호찾기</button>
          </div>
        </div>
      </form>
    </div>
  </div>
  <div class="card card-outline card-secondary">
    <div class="card-header text-center">
	  <h4>MAIN MENU</h4>
      <a href = "login.do"><button type="button" class="btn btn-secondary btn-block">로그인</button></a>
    </div>
  </div>
</div>
<script type="text/javascript">
$(function(){
	var idBtn = $("#idBtn");
	var pwBtn = $("#pwBtn");
	
	idBtn.on("click", function(){
	console.log("mail : " + $("#memEmail").val());
	console.log("name : " + $("#memName").val());
		
		$.ajax({
			type : "post",
			url : "/notice/forgetId.do",
			data : {
				memEmail : $("#memEmail").val(),
				memName : $("#memName").val()
			},
			success : function(result){
				console.log(result)
					$("#id").html(result);
			}
		});
	});
	
	pwBtn.on("click", function(){
		console.log("id : " + $("#memId").val());
		console.log("mail : " + $("#memEmail1").val());
		console.log("name : " + $("#memName1").val());
		$.ajax({
			type : "post",
			url : "/notice/forgetPw.do",
			data : {
				memId : $("#memId").val(),
				memEmail : $("#memEmail1").val(),
				memName : $("#memName1").val(),
			},
			success : function(res){
				console.log("res : " + res);
					$("#pw").html(res);
			}
		})
	});
	
});
</script>