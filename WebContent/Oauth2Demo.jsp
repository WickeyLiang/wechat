<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Oauth2 测试</title>


<script type="text/javascript" src="public_js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/Oauth2Demo.js"></script>
<script type="text/javascript">
	
	$(function(){
		var code = "<%=request.getParameter("code")%>";
		var state = "<%=request.getParameter("state")%>";
		
		getCode(code,state);
	});
	
	function getCode(code,state) {
		
		$.ajax({
			type : "POST",
			url : "getUserInfo_OAuth2Action.action",
			data : {
				code : code,
				state : state
			},
			success : function(data){
				$("#result").text("拿到了:"+code);
			}
		})
	}
	
</script>

</head>
<body>
	hello,world!
	<div>
		<span id="result"></span>
	</div>
</body>
</html>