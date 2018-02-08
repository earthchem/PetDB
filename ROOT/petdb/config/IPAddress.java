package petdb.config;

import javax.servlet.http.*;

public class IPAddress
{
    private String[] agent = {"baidu","msnbot","googlebot","spider","yahoo","bjtelecom"};
    private String[] IPignorelist = {"142.104.247.1","68.180.230.178","136.243.36.92","136.243.36.88","10.0.2.15"};
    
	public static String getIpAddr(HttpServletRequest request)
	{		
		String ip = request.getHeader("X-Real-IP");
		if (null != ip && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) 
		{
		    return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (null != ip && !"".equals(ip.trim()) && !"unknown".equalsIgnoreCase(ip)) 
		{
		    // get first ip from proxy ip
		    int index = ip.indexOf(',');
		    if (index != -1) 
		    {
		        return ip.substring(0, index);
		    } else {
		        return ip;
		    }
		}
		return request.getRemoteAddr();
	}
    
    public String getIpAddrWithFilter(HttpServletRequest request)
	{	
        String user_agent = request.getHeader("User-Agent");
        for(int i = 0; i < agent.length; i++)
            if(user_agent.indexOf(agent[i]) == 1) return null;
        String myIP = getIpAddr(request);
        for(int i=0;i<IPignorelist.length; i++)
        {
        	if(IPignorelist[i].equals(myIP)) return null;
        }
      	return myIP;
	}
}


