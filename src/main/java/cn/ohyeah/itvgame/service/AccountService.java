package cn.ohyeah.itvgame.service;

import java.io.IOException;
import java.util.Date;

import cn.ohyeah.stb.util.ConvertUtil;

/**
 * 用户服务接口
 * 
 * @author Administrator
 * 
 */
public class AccountService extends AbstractHttpService {

	public AccountService(String url) {
		super(url);
	}

	/**
	 * 登入游戏
	 * 
	 * @param userid
	 * @param username
	 * @param product
	 */
	public void cmdLogin(String userid, String username, String product, boolean isLogin) {
		String sendCmd = null;
		if (isLogin) {
			serviceLocation += addr_login;
		} else {
			serviceLocation += addr_quit;
		}

		sendCmd = "userid=" + HURLEncoder.encode(userid) + "&username="
				+ HURLEncoder.encode(username) + "&product=" + HURLEncoder.encode(product);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
			} else {
				result = -1;
				message = info[2];
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * 和游戏相关的全局数据，在服务器上只有一个备份
	 * 
	 * @param userid
	 * @param username
	 * @param product
	 * @param data
	 *            游戏数据的内容(65535字节)
	 */
	public void saveGobalData(String userid, String username, String product, String data) {
		String sendCmd = null;
		serviceLocation += addr_saveGlobalData;

		sendCmd = "userid=" + HURLEncoder.encode(userid) + "&username="
				+ HURLEncoder.encode(username) + "&product=" + HURLEncoder.encode(product)
				+ "&datas=" + HURLEncoder.encode(data);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
			} else {
				result = -1;
				message = info[2];
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * 读取全局数据
	 * 
	 * @param userid
	 * @param username
	 * @param product
	 * @param data
	 */
	public String loadGobalData(String userid, String username, String product) {
		String sendCmd = null;
		serviceLocation += addr_loadGlobalData;

		sendCmd = "userid=" + HURLEncoder.encode(userid) + "&username="
				+ HURLEncoder.encode(username) + "&product=" + HURLEncoder.encode(product);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
				return info[2];
			} else {
				result = -1;
				message = info[2];
				return null;
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 购买道具时，向服务器写购买日志
	 * 
	 * @param userid
	 * @param username
	 * @param product
	 * @param contentStr
	 *            字符串值（如：购买的物品名）
	 * @param contentVal
	 *            整数值（如：花费的元宝）
	 * @param memo
	 *            备注(65535字节)
	 */
	public void writePurchaseLog(String userid, String username, String product, String contentStr,
			int contentVal, String memo) {
		String sendCmd = null;
		serviceLocation += addr_log;

		sendCmd = "userid=" + HURLEncoder.encode(userid) + "&username="
				+ HURLEncoder.encode(username) + "&product=" + HURLEncoder.encode(product)
				+ "&contentStr=" + HURLEncoder.encode(contentStr) + "&contentVal="
				+ HURLEncoder.encode(String.valueOf(contentVal)) + "&memo="
				+ HURLEncoder.encode(memo);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
			} else {
				result = -1;
				message = info[2];
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * 心跳命令,间隔为10分钟
	 * 
	 * @param userid
	 * @param username
	 * @param product
	 */
	public void sendHeartBeat(String userid, String username, String product) {
		String sendCmd = null;
		serviceLocation += addr_heartBeat;

		sendCmd = "userid=" + HURLEncoder.encode(userid) + "&username="
				+ HURLEncoder.encode(username) + "&product=" + HURLEncoder.encode(product);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
			} else {
				result = -1;
				message = info[2];
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * 查询系统时间
	 * 
	 * @param userid
	 * @param username
	 * @param product
	 * @param format
	 *            时间的格式（当前只支持0）
	 */
	public Date querySystemTime(String userid, String username, String product, int format) {
		String sendCmd = null;
		serviceLocation += addr_queryTime;

		sendCmd = "userid=" + HURLEncoder.encode(userid) + "&username="
				+ HURLEncoder.encode(username) + "&product=" + HURLEncoder.encode(product)
				+ "&format=" + HURLEncoder.encode(String.valueOf(format));

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
				return new Date(Long.parseLong(info[2]));
			} else {
				result = -1;
				message = info[2];
				return null;
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询公告(success#返回的公告数量#开始时间1, 结果时间1,公告标题1,公告内容1\n开始时间2, 结果时间2, 公告标题2,公告内容2
	 * 
	 * @param product
	 */
	public String queryNews(String product) {
		String sendCmd = null;
		serviceLocation += addr_news;

		sendCmd = "&product=" + HURLEncoder.encode(product);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
				return info[2];
			} else {
				result = -1;
				message = info[2];
				return null;
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 广东添加收藏接口
	 * 
	 * @param userid
	 * @param username
	 * @param gameid
	 * @param spid
	 * @param gameCode
	 * @param timeStmp
	 */
	public void addFavor(String userid, String username, String gameid, String spid,
			String gameCode, String timeStmp) {
		String sendCmd = null;
		serviceLocation += addr_add_favor;

		sendCmd = "userID=" + HURLEncoder.encode(userid) + "&user=" + HURLEncoder.encode(username)
				+ "&gameID=" + HURLEncoder.encode(gameid) + "&spid=" + HURLEncoder.encode(spid)
				+ "&code=" + HURLEncoder.encode(gameCode) + "&timeStmp="
				+ HURLEncoder.encode(timeStmp);

		try {
			byte[] b = postViaHttpConnection_addFavor(serviceLocation, sendCmd);
			int r = littleEndianByteArrayToInt(b);
			if (r == 1) {
				message = "收藏成功!";
			} else {
				result = r;
				message = getAddFavoriteErrorMessage(r);
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * 进入大厅充值界面接口
	 * 
	 * @param userid
	 */
	public void gotoOrderPage(String userid) {
		String sendCmd = null;
		serviceLocation += addr_goto_order_page;

		sendCmd = "userid=" + HURLEncoder.encode(userid);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if (info[1].equals("0")) {
				result = 0;
			} else {
				result = -1;
				message = info[2];
			}
		} catch (IOException e) {
			result = -1;
			message = e.getMessage();
			e.printStackTrace();
		}
	}

	private int littleEndianByteArrayToInt(byte[] data) {
		int v = data[0] & 0XFF;
		v |= (data[1] & 0XFF) << 8;
		v |= (data[2] & 0XFF) << 16;
		v |= (data[3] & 0XFF) << 24;
		return v;
	}

	public static String getAddFavoriteErrorMessage(int errorCode) {
		String message = null;
		switch (errorCode) {
		case -1:
			message = "收藏夹已满";
			break;
		case -2:
			message = "该游戏已经收藏";
			break;
		case -11:
			message = "用户帐号不正确";
			break;
		case -12:
			message = "中游id不正确";
			break;
		case -13:
			message = "游戏ID不正确";
			break;
		case -14:
			message = "spid不正确";
			break;
		case -15:
			message = "timeStmp不正确";
			break;
		case -16:
			message = "请求超时";
			break;
		case -17:
			message = "code不正确";
			break;
		case -101:
			message = "系统异常";
			break;
		default:
			message = "位置错误";
			break;
		}
		return message;
	}
}
