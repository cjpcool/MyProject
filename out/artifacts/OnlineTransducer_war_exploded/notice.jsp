<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提示</title>
</head>
<script type="text/javascript">  
    var time = 4;  
  
    function returnUrlByTime() {  
  
        window.setTimeout('returnUrlByTime()', 1000);  
  
        time = time - 1;  
  
        document.getElementById("layer").innerHTML = time;  
    }  
</script>  
  
</head>  
  
<body  onload="returnUrlByTime()">  
      <div  style="text-align: center; margin:0 auto; width:200px">
    	  <h3 style="text-align: center;">${note }</h3>  
    	  <b style="text-align: center;" ><span id="layer">3</span>秒后,转到主页。</b>  
      </div>
    <%  
        //转向语句  
        response.setHeader("Refresh", "3;URL="+request.getAttribute("url"));  
    %>  
  
</body>  
</body>
</html>