<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.lang.Integer" %>
<%@ page import="petdb.query.PetDBCounters"%>
<%
PetDBCounters pc=new PetDBCounters();
int[] nums = new int[8];
nums[0]= pc.getCounterNum(1);
nums[1] = pc.getCounterNum(2);
nums[2] = pc.getCounterNum(6);
nums[3] = pc.getCounterNum(5);
nums[4] = pc.getCounterNum(7);
nums[5] = pc.getCounterNum(8);
nums[6] = pc.getCounterNum(4);
nums[7] = pc.getCounterNum(3);
pc.closeQuery();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PetDB Statistics</title>
</head>
<p>
References: <%=nums[0]%>
<br>
Samples: &nbsp;<%=nums[1]%>
<br>
Bulk Rocks:&nbsp; <%=nums[2] %>
<br>
Minerals: <%=nums[3] %>
<br>
Volcanic glasses: <%=nums[4] %>
<br>
Melt inclusions:&nbsp; <%=nums[5] %>
<br>
Total individual values:&nbsp; <%=nums[6] %>
<br>
Number of stations: <%=nums[7] %>
</p>

<body>

</body>
</html>