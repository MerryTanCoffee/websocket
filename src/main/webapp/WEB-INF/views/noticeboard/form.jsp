<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<section class="content-header">
	<c:set value="등록" var="name" />
	<c:if test="${status eq 'u' }">
		<c:set value="수정" var="name"></c:set>
	</c:if>
	<div class="container-fluid">
		<div class="row mb-2">
			<div class="col-sm-6">
				<h1>공지사항 ${name }</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="#">DDIT HOME</a></li>
					<li class="breadcrumb-item active">공지사항 ${name}</li>
				</ol>
			</div>
		</div>
	</div>
</section>

<!-- Main content -->
<section class="content">
	<div class="row">
		<div class="col-md-12">
			<form class="card card-primary" action="/notice/insert.do"
				method="post" id="noticeForm" enctype="multipart/form-data">
				<c:if test="${status eq 'u' }">
					<input type="hidden" name="boNo" value="${notice.boNo }">
				</c:if>
				<div class="card-header">
					<h3 class="card-title">공지사항 ${name }</h3>
					<div class="card-tools"></div>
				</div>
				<div class="card-body">
					<div class="form-group">
						<label for="boTitle">제목을 입력해주세요</label> <input type="text"
							id="boTitle" value="${notice.boTitle }" name="boTitle"
							class="form-control" placeholder="제목을 입력해주세요">
					</div>
					<div class="form-group">
						<label for="boContent">내용을 입력해주세요</label>
						<!--  -->
						<textarea id="boContent" name="boContent" class="form-control"
							rows="14">${notice.boContent }</textarea>
					</div>
					<div class="form-group">
						<div class="custom-file">

							<input type="file" class="custom-file-input" id="boFile"
								name="boFile" multiple="multiple"> <label
								class="custom-file-label" c for="boFile">파일을 선택해주세요</label>
						</div>
					</div>
				</div>
				<!--
               <i class="fas fa-file-alt"></i>
               <i class="fas fa-file-excel"></i>
               <i class="far fa-file-pdf"></i>
               <i class="far fa-file-word"></i>
               <i class="far fa-file-powerpoint"></i>
               
            -->
				<div class="card-footer bg-white">
					<ul class="mailbox-attachments d-flex align-items-stretch clearfix">
						<c:if test="${not empty notice.noticeFileList }">
							<c:forEach items="${notice.noticeFileList }" var="noticeFile">
								<li><span class="mailbox-attachment-icon"> <i
										class="far fa-file-pdf"></i>
								</span>
									<div class="mailbox-attachment-info">
										<a href="#" class="mailbox-attachment-name"> 
										<i class="fas fa-paperclip"></i> ${noticeFile.fileName }</a> 
										<span class="mailbox-attachment-size clearfix mt-1"> <span>
										${noticeFile.fileFancysize }</span> 
												<span class="btn btn-default btn-sm float-right attachmentFileDel"
											id="span_${noticeFile.fileNo } "> <i
												class="fas fa-times"></i></span>
										</span>
									</div></li>
							</c:forEach>
						</c:if>
					</ul>
				</div>
				<div class="card-footer bg-white">
					<div class="row">
						<div class="col-12">
							<!-- 
                     등록: 등록, 목록 
                     수정: 수정, 취소   
                  -->
							<input type="button" value="${name}" id="insertBtn"
								class="btn btn-primary float-right">

							<c:if test="${status eq 'u' }">
								<input type="button" value="목록" id="listBtn"
									class="btn btn-success float-right">
							</c:if>
							<c:if test="${status ne 'u' }">
								<input type="button" value="취소" id="cancleBtn"
									class="btn btn-success float-right">
							</c:if>

						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</section>
<script>
	$(function() {
		CKEDITOR
				.replace(
						'boContent',
						{
							footnotesPrefix : 'a',
							filebrowserUploadUrl : '${pageContext.request.contextPath }/imageUpload.do'
						});
		var listBtn = $("#listBtn");
		var insertBtn = $("#insertBtn");
		var noticeForm = $("#noticeForm");
		var cancleBtn = $("#cancleBtn");

		cancleBtn.on("click", function() {
			location.href = "/notice/detail.do?boNo=${notice.boNo}"
		})

		listBtn.on("click", function() {
			location.href = "/notice/list.do"
		});

		insertBtn.on("click", function() {
			//입력데이터 일반 검증

			var boTitle = $("#boTitle").val();
			var boContent = CKEDITOR.instances.boContent.getData();

			if (boTitle == "" || boTitle == null) {
				alert("제목을 입력해주세요");
				return false;
			}

			if (boContent == "" || boContent == null) {
				alert("내용을 입력해주세요");
				return false;
			}
			if ($(this).val() == "수정") {
				noticeForm.attr("action", "/notice/update.do");
			}
			noticeForm.submit();
		});

		$(".attachmentFileDel")
				.on(
						"click",
						function() {
							var id = $(this).prop("id");
							var idx = id.indexOf("_");
							var noticeFileNo = id.substring(idx + 1);
							var ptrn = "<input type='text' name = 'delNoticeNo' value ='%V' hidden='hidden'/>";
							// 삭제하면 데이터가 쌓임
							$("#noticeForm").append(
									ptrn.replace("%V", noticeFileNo));
							$(this).parents("li:first").hide();
						});
	});
</script>