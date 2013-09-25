package cn.ohyeah.itvgame.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * ����Http������
 * 
 * @author jackey chan
 * @version 1.0
 */
public abstract class AbstractHttpService {

	public String addr_login = "login.do"; /* �����ַ */
	public String addr_quit = "quit.do"; /* �˳���ַ */
	public String addr_queryTime = "query_time.do"; /* ��ѯϵͳʱ���ַ */
	public String addr_saveGlobalData = "save_global_data.do"; /* ����ȫ�����ݵ�ַ */
	public String addr_loadGlobalData = "load_global_data.do"; /* ����ȫ�����ݵ�ַ */
	public String addr_saveRecord = "save_record.do"; /* �浵��ַ */
	public String addr_loadRecord = "load_record.do"; /* ������ַ */
	public String addr_saveScore = "save_score.do"; /* ������ֵ�ַ */
	public String addr_queryRank = "query_rank.do"; /* ��ѯ���е�ַ */
	public String addr_saveItem = "save_shop_item.do"; /* ������ֵ���ߵ�ַ */
	public String addr_loadItem = "load_shop_item.do"; /* ��ȡ��ֵ���ߵ�ַ */
	public String addr_log = "log.do"; /* �������д������־��ַ */
	public String addr_heartBeat = "heart_beat.do"; /* ���������ַ */
	public String addr_news = "news.do"; /* ��ѯ�����ַ */

	public String addr_add_favor = "IPTV_Advance/FavorGameServlet"; /* ����ղص�ַ */
	public String addr_goto_order_page = "goto_order_page"; /* ���������ֵ�����ַ */

	public String addr_query_coins = "query_coins"; /* Ԫ����ѯ��ַ */
	public String addr_consume_coins = "consume_coins"; /* Ԫ���۳���ַ */
	public String addr_order_coins = "order_coins"; /* Ԫ����ֵ��ַ */

	protected String serviceLocation;
	protected HttpConnection httpConnection;
	protected InputStream inputStream;
	protected OutputStream outputStream;
	protected int result;
	protected String message;

	protected AbstractHttpService(String url) {
		if (!url.endsWith("/")) {
			url += "/";
		}
		serviceLocation = url;
	}

	/**
	 * ���ط�����
	 * 
	 * @return ����ֵ<0��ʧ�ܣ�����ֵ==0���ɹ�
	 */
	public int getResult() {
		return result;
	}

	/**
	 * �жϷ����Ƿ�ɹ�
	 * 
	 * @return
	 */
	public boolean isSuccess() {
		return result == 0;
	}

	/**
	 * ������Ϣ
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * ��������
	 */
	public String postViaHttpConnection(String url, String cmd) throws IOException {
		int rc;
		try {
			url += "?" + cmd;
			System.out.println("url:" + url);
			httpConnection = (HttpConnection) Connector.open(url);
			httpConnection.setRequestMethod(HttpConnection.GET);
			// httpConnection.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			rc = httpConnection.getResponseCode();
			if (rc != HttpConnection.HTTP_OK) {
				result = -1;
				throw new IOException("HTTP response code: " + rc);
			}
			System.out.println("request state:" + rc);
			inputStream = httpConnection.openInputStream();

			int count = 0;
			while (count == 0) {
				count = inputStream.available();
			}
			byte[] bytes = new byte[count];
			int readCount = 0; // �Ѿ��ɹ���ȡ���ֽڵĸ���
			while (readCount < count) {
				readCount += inputStream.read(bytes, readCount, count - readCount);
			}

			String str = new String(bytes, "utf-8");
			System.out.println("return message:" + str);
			return str;
		} catch (ClassCastException e) {
			result = -1;
			throw new IllegalArgumentException("Not an HTTP URL");
		} finally {
			close();
		}
	}

	/**
	 * ����ղط�������
	 */
	public byte[] postViaHttpConnection_addFavor(String url, String cmd) throws IOException {
		int rc;
		try {
			url += "?" + cmd;
			System.out.println("url:" + url);
			httpConnection = (HttpConnection) Connector.open(url);
			httpConnection.setRequestMethod(HttpConnection.GET);
			rc = httpConnection.getResponseCode();
			if (rc != HttpConnection.HTTP_OK) {
				result = -1;
				throw new IOException("HTTP response code: " + rc);
			}
			System.out.println("request state:" + rc);
			inputStream = httpConnection.openInputStream();

			int count = 0;
			while (count == 0) {
				count = inputStream.available();
			}
			byte[] bytes = new byte[count];
			int readCount = 0; // �Ѿ��ɹ���ȡ���ֽڵĸ���
			while (readCount < count) {
				readCount += inputStream.read(bytes, readCount, count - readCount);
			}

			return bytes;
		} catch (ClassCastException e) {
			result = -1;
			throw new IllegalArgumentException("Not an HTTP URL");
		} finally {
			close();
		}
	}

	private void close() {
		try {
			if (inputStream != null) {
				inputStream.close();
				inputStream = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
					outputStream = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (httpConnection != null) {
						httpConnection.close();
						httpConnection = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
