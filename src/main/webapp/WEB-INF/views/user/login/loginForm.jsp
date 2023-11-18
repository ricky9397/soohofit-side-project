<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../common/inc/header_inc.jsp"%>



<!--  html 전체 영역을 지정하는 container -->
<div id="container">
    <!--  login 폼 영역을 : loginBox -->
    <div id="loginBox">

        <!-- 로그인 페이지 타이틀 -->
        <div id="loginBoxTitle">SOOHOFIT Login</div>
        <!-- 아이디, 비번, 버튼 박스 -->
        <div id="inputBox">
            <div class="input-form-box"><span>아이디 </span><input type="text" name="uid" class="form-control"></div>
            <div class="input-form-box"><span>비밀번호 </span><input type="password" name="upw" class="form-control"></div>
            <div>

                <a href="<c:url value="/user/join/joinForm.do"/>"><span>회원가입 /</span></a>
                <a href="javascrit:(0)"><span>아이디찾기 /</span></a>
                <a href="javascrit:(0)"><span>비밀번호찾기 </span></a>
            </div>
            <div class="button-login-box" >
                <button type="button" class="btn btn-primary btn-xs" style="width:100%">로그인</button>
            </div>
        </div>

    </div>
</div>


<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<script>




</script>

<%@ include file="../../common/inc/footer_inc.jsp"%>


