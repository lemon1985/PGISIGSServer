<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="jslib/jquery-1.8.3.min.js""></script>
	<script type="text/javascript">
		function aaa(){
			/* $.post("http://127.0.0.1:82/msg/services/testRestful/post/add/dh2",{},function(data){
				alert(data);
			},"json"); */
			$.ajax({
				url:"http://127.0.0.1:82/msg/services/testRestful/post/add/dh2",
				type:"POST",
				data:{},
				contentType: "application/json,charset=utf-8",
				success:function(data){
					alert(data);
				}
			})
		}
		function bbb(){
			$.get("http://127.0.0.1:82/msg/services/testRestful/get",{},function(data){
				alert(data);
			},"json");
		}
		
		
		
		
		function getBase64Image(img) {
            var canvas = document.createElement("canvas");
            canvas.width = img.width;
            canvas.height = img.height;
            var ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, img.width, img.height);
            var ext = img.src.substring(img.src.lastIndexOf(".")+1).toLowerCase();
            var dataURL = canvas.toDataURL("image/"+ext);
            return dataURL;
        }
        var imgLink = "http://127.0.0.1:8080/HZAJServer/images/tp1.png";
        var tempImage = new Image();
        tempImage.src = imgLink;
        tempImage.crossOrigin = "*";
        tempImage.onload = function(){
            var base64 = getBase64Image(tempImage);
            console.log(base64);
        }
	</script>

  </head>
  
  <body>
    <input type="button" onclick="aaa()" value="查询POST">
    <input type="button" onclick="bbb()" value="查询GET">
  </body>
</html>
