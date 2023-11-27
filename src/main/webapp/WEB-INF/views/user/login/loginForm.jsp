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
            <div class="input-form-box"><span>아이디 </span><input type="text" id="userId" name="userId" class="form-control"></div>
            <div class="input-form-box"><span>비밀번호 </span><input type="password" id="userPwd" name="userPwd" class="form-control"></div>
            <div>

                <a href="<c:url value="/user/join/joinForm"/>"><span>회원가입 /</span></a>
                <a href="javascrit:(0)"><span>아이디찾기 /</span></a>
                <a href="javascrit:(0)"><span>비밀번호찾기 </span></a>
            </div>
            <div class="button-login-box" >
                <button type="button" class="btn btn-primary btn-xs" style="width:100%">로그인</button>
            </div>
            <div class="button-login-box" >
                <button type="button" class="btn"  style="width:100%" onclick="loginKakao();">
                    <img src="/assets/images/icons/kakao_login_medium_wide.png" alt="" style="width:100%">
                </button>
            </div>
            <div class="button-login-box" >
                <button type="button" class="btn" style="width:100%" onclick="loginGoogle();">
                    <img src="/assets/images/icons/web_neutral_sq_SI@3x.png" alt="" style="width:100%">
                </button>

            </div>
            <div class="button-login-box" >
                <button type="button" class="btn" style="width:100%" onclick="loginNaver();">
                    <img src="/assets/images/icons/btnG_완성형.png" alt="" style="width:100%">
                </button>
            </div>
        </div>

    </div>
</div>


<%@ include file="../../common/inc/footer_inc.jsp"%>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script type="text/javascript">

    // 카카오 로그인
    function loginKakao() {
        window.open('/oauth2/authorization/kakao', 'snsLogin', 'status=0,toolbar=0,Titlebar=0,width=500,height=800,resizable=1');
    }
    // 구글 로그인
    function loginGoogle() {
        window.open('/oauth2/authorization/google', 'snsLogin', 'status=0,toolbar=0,Titlebar=0,width=500,height=800,resizable=1');
    }
    // 네이버 로그인
    function loginNaver() {
        window.open('/oauth2/authorization/naver', 'snsLogin', 'status=0,toolbar=0,Titlebar=0,width=500,height=800,resizable=1');
    }

</script>

