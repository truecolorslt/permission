<%@page contentType="text/html; charset=utf-8"%>
<%
	//request.getContextPath()  返回站点的根目录
	//request.getRealpath("/")得到的是实际的物理路径，也就是你的项目所在服务器中的路径
	//request.getScheme() 等到的是协议名称，默认是http
	//request.getServerName() 得到的是在服务器的配置文件中配置的服务器名称 比如:localhost .baidu.com 等等
	//request.getServerPort() 得到的是服务器的配置文件中配置的端口号 比如 8080等等
	 	
	String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>