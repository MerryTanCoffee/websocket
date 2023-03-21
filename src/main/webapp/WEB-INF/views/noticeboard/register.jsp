<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="register-box">
	<div class="card card-outline card-primary">
		<div class="card-header text-center">
			<a href="" class="h1"><b>DDIT</b>BOARD</a>
		</div>
		<div class="card-body">
			<p class="login-box-msg">회원가입</p>
	
			<form action="/notice/signup.do" method="post" id="signupForm" enctype="multipart/form-data">
				<div class ="input-group mb-3 text-center">
					<img class="profile-user-img img-fluid img-circle" alt ="User Profile"
					src="/resources/dist/img/AdminLTELogo.png"
					id="profileImg" style="width:150px;">
				</div>
				<div class="input-group mb-3">
					<label for = "inputDescription">프로필 이미지</label>
				</div>				
				<div class="input-group mb-3">
					<div class="custom-file">
						<input type="file" class="custom-file-input" name="imgFile"
						id="imgFile"/>
						<label class="custom-file-label" for="imgFile">
							프로필 이미지 선택
						</label>
					</div>
				</div>		
				<div class="input-group mb-3">
					<label for = "inputDescription">프로필 정보</label>
				</div>			
				<font color="red" class="mt-4 mb-2" id="id">${errors.memId }</font>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memId }" id="memId" name="memId" placeholder="아이디를 입력해주세요"> 
					<span class="input-group-append">
						<button type="button" class="btn btn-secondary btn-flat" id="idCheckBtn">중복확인</button>
					</span>
				</div>
				<font color="red" class="mt-4 mb-2">${errors.memPw }</font>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="memPw" name="memPw" placeholder="비밀번호를 입력해주세요">
				</div>
				<font color="red" class="mt-4 mb-2">${errors.memName }</font>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memName }" id="memName" name="memName" placeholder="이름을 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<div class="form-group clearfix">
						<div class="icheck-primary d-inline">
							<input type="radio" id="memGenderM" name="memGender" value="M" <c:if test = "${member.memGender eq 'M' }">checked</c:if>>
							<label for="memGenderM">남자</label>
						</div>
						<div class="icheck-primary d-inline">
							<input type="radio" id="memGenderF" name="memGender" value="F" <c:if test = "${member.memGender eq 'F' }">checked</c:if>>
							<label for="memGenderF">여자</label>
						</div>
					</div>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memEmail }" id="memEmail" name="memEmail" placeholder="이메일을 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memPhone }" id="memPhone" name="memPhone" placeholder="전화번호를 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memPostCode }" id="memPostCode" name="memPostCode"> 
					<span class="input-group-append">
						<button type="button" class="btn btn-secondary btn-flat" onclick="DaumPostcode()">우편번호 찾기</button>
					</span>
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memAddress1 }"  id="memAddress1" name="memAddress1" placeholder="주소를 입력해주세요">
				</div>
				<div class="input-group mb-3">
					<input type="text" class="form-control" value="${member.memAddress2 }"  id="memAddress2" name="memAddress2" placeholder="상세주소를 입력해주세요">
				</div>
				<div class="row">
					<div class="col-8">
						<div class="icheck-primary">
							<input type="checkbox" checked="checked" id="memAgree" name="memAgree" value="Y">
							<label for="memAgree"> 개인정보 사용을 동의해주세요 <a href="#">개인정보방침</a></label>
						</div>
					</div>
					<div class="col-4">
						<button type="button" class="btn btn-primary btn-block" id="signupBtn">가입하기</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type ="text/javascript">
$(function(){
	var idCheckBtn = $("#idCheckBtn");
	var signupForm = $("#signupForm");
	var signupBtn = $("#signupBtn");
	var idCheckFlag = false; // 중복확인 flag
	
	
	idCheckBtn.on("click",function(){
	
		$.ajax({
			type : "post",
			url : "/notice/idCheck.do",
			data : {memId : $("#memId").val()},
			// 키 : 밸류
			success : function(res){
				// 결과로 넘어오는 데이터가 ServiceResult인데
				// EXIST, NOTEXIST 에 따라서
				// "사용 가능한 아이디입니다 또는 사용 중인 아이디입니다." 출력
				// 중복확인 시 idCheckFlag 라는 스위쳐가 발동(true로 변환)
				var text = "사용 가능한 아이디입니다.";	
				if(res = "NOTEXIST") { // 없는 아이디어서 사용 가능
					alert(text);
					$("#id").html(text).css("color","green");
					idCheckFlag = true; // 중복확인 했다는 Flag 설정
					
				} else {
					text = "이미 사용중인 아이디입니다.";
					alert(text);
					$("#id").html(text);
				}
			
			}
		});
	});
	
	$("#imgFile").on("change",function(event){
		var file = event.target.files[0];
		
		if(isImageFile(file)){
			var reader = new FileReader();
			reader.onload = function(e) {
				$("#profileImg").attr("src",e.target.result);
			}
			reader.readAsDataURL(file);
		} else {
			alert("이미지 파일 선택");
		}
	});
	
	signupBtn.on("click",function(){
		// 조건
		// 아이디 비밀번호 이름까지만 일반적인 데이터 검증
		// 개인정보 처리방침 동의를 체크했을 때만 가입하기가 가능하도록
		// 중복확인 처리가 true일 때 
	
	var memId = $("#memId").val();
	var memPw = $("#memPw").val();
	var memName = $("#memName").val();
	var memAgree = $("#memAgree:checked").val();
	var agreeFlag = false; // 개인정보 처리방침 동의 여부(false : x, true : o)
	
// 	if(memId == null || memId == "") {
// 		alert("아이디 입력 필");
// 		return false;
// 	}
	
// 	if(memPw == null || memPw  == "") {
// 		alert("비밀번호 입력 필");
// 		return false;
// 	}
	
// 	if(memName == null || memName == "") {
// 		alert("이름 입력 필");
// 		return false;
// 	}
	
	// 개인 정보 처리 방침을 동의하게 되면 Y값이 넘어오므로 동의여부는 true
	if(memAgree == "Y") {
		agreeFlag = true;
	}
	
	// 개인정보 처리방침에 동의하셨습니까?
	// 아이디 중복체크 하셨습니까?
	// > 모두 진행 했으면 가입 가능
	// > 안했으면 가입 불가
	
	if(agreeFlag) { // 개인정보 처리 방침 동의
		if(idCheckFlag) { // 아이디 중복체크 완
			signupForm.submit();
		} else { // 중복 체크 미완
			alert("중복 체크 필");
		}
	} else {
		alert("개인정보 동의를 체크해주세요~~");
	}
	
	});
});


function isImageFile(file){
	var ext = file.name.split(".").pop().toLowerCase();
	return ($.inArray(ext, ["jpg", "jpeg", "gif", "png"]) === -1) ? false : true;
}

function DaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
            } 

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('memPostCode').value = data.zonecode;
            document.getElementById("memAddress1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("memAddress2").focus();
        }
    }).open();
}
</script>