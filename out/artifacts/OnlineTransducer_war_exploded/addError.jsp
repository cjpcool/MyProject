<%@ page language="java" pageEncoding="UTF-8"%>
  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
<head>  
<title>添加失败</title>  
  
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">  
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">  
  
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
    	  <h3 style="text-align: center;">添加失败！单词输入有误</h3>  
    	  <b style="text-align: center;" ><span id="layer">3</span>秒后,转到主页。</b>  
      </div>
    <%  
        //转向语句  
        response.setHeader("Refresh", "3;URL=addVocab.jsp");  
    %>  
  
</body>  
</html> 