package com.myblog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;


public class IPUtils {

	private static final Logger logger = LoggerFactory.getLogger(IPUtils.class);

	public static String ipRegix = "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
	public static Pattern ipPattern = Pattern.compile(ipRegix);

	public static boolean isIp(String in) {
		if (in == null) {
			return false;
		}
		return ipPattern.matcher(in).matches();
	}

    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = request.getHeader("x-forwarded-for");
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress= inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
            if(ipAddress.indexOf(",")>0){
                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getRealIpAddr(HttpServletRequest request) {
		// just for test.
		if (request == null) {
			return "127.0.0.1";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip != null) {
			// 为了解决移动网关的问题。移动网关过来的请求，第二个ip是真实ip
			int idx = ip.indexOf(",");
			if (idx > 0 && idx < ip.length()) {
				ip = (ip.substring(ip.indexOf(",") + 1)).trim();
			}
		}
		return ip.trim();
	}

	/**
	 * Convert IP to Int.
	 *
	 * @param addr
	 * @param isSegment
	 *            true IP segment, false full IP.
	 * @return
	 */
	public static int ipToInt(final String addr, final boolean isSegment) {
		final String[] addressBytes = addr.split("\\.");
		int length = addressBytes.length;
		if (length < 3) {
			return 0;
		}
		int ip = 0;
		try {
			for (int i = 0; i < 3; i++) {
				ip <<= 8;
				ip |= Integer.parseInt(addressBytes[i]);
			}
			ip <<= 8;
			if (isSegment || length == 3) {
				ip |= 0;
			} else {
				ip |= Integer.parseInt(addressBytes[3]);
			}
		} catch (Exception e) {
			logger.warn("Warn ipToInt addr is wrong: addr=" + addr, e);
		}

		return ip;
	}

	/**
	 * 将ip转化为数字，并且保持ip的大小顺序不变 如 ipToInt("10.75.0.1") > ipToInt("10.75.0.0")
	 * 如果ip不合法则返回 0
	 *
	 * @param ipAddress
	 * @return
	 */
	public static int ipToInt(final String addr) {
		return ipToInt(addr, false);
	}

	private static long[][] intranet_ip_ranges = new long[][] { { ipToInt("10.0.0.0"), ipToInt("10.255.255.255") },
			{ ipToInt("172.16.0.0"), ipToInt("172.31.255.255") },
			{ ipToInt("192.168.0.0"), ipToInt("192.168.255.255") } };

	/**
	 * 是否为内网ip A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类
	 * 192.168.0.0-192.168.255.255 不包括回环ip
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isIntranetIP(String ip) {
		if (!isIp(ip)) {
			return false;
		}
		long ipNum = ipToInt(ip);
		for (long[] range : intranet_ip_ranges) {
			if (ipNum >= range[0] && ipNum <= range[1]) {
				return true;
			}
		}
		return false;
	}


	public static void main(String[] args) {

		System.out.println(isIp("192.168.1.*"));
	}

}
