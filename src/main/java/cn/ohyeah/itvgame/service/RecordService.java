package cn.ohyeah.itvgame.service;

import java.io.IOException;

import cn.ohyeah.stb.util.ConvertUtil;

public class RecordService extends AbstractHttpService {

	public RecordService(String url) {
		super(url);
	}

	/**
	 * ������Ϸ��¼
	 * @param userid
	 * @param username
	 * @param product
	 * @param index ������ţ�ȡֵ��ΧΪ1-6
	 * @param datas ��Ϸ���ݵ�����(65535�ֽ�)
	 */
	public void saveRecord(String userid, String username, String product, int index, String datas){
		String sendCmd = null;
		serviceLocation += addr_saveRecord;
		
		sendCmd = "userid=" + HURLEncoder.encode(userid) + 
				  "&username="+ HURLEncoder.encode(username) + 
				  "&product="+ HURLEncoder.encode(product) +
				  "&index="+ index +
				  "&datas="+ HURLEncoder.encode(datas);

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if(info[1].equals("0")){
				result = 0;
			}else{
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
	 * �������л���
	 * @param userid
	 * @param username
	 * @param product
	 * @param score
	 */
	public void saveScore(String userid, String username, String product, int score){
		String sendCmd = null;
		serviceLocation += addr_saveScore;
		
		sendCmd = "userid=" + HURLEncoder.encode(userid) + 
				  "&username="+ HURLEncoder.encode(username) + 
				  "&product="+ HURLEncoder.encode(product) +
				  "&score="+ score;

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if(info[1].equals("0")){
				result = 0;
			}else{
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
	 * ������Ϸ��¼
	 * @param userid
	 * @param username
	 * @param product
	 * @param index ������ţ�ȡֵ��ΧΪ1-6
	 * @return
	 */
	public String loadRecord(String userid, String username, String product, int index){
		String sendCmd = null;
		serviceLocation += addr_loadRecord;
		
		sendCmd = "userid=" + HURLEncoder.encode(userid) + 
				  "&username="+ HURLEncoder.encode(username) + 
				  "&product="+ HURLEncoder.encode(product) +
				  "&index="+ index;

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if(info[1].equals("0")){
				result = 0;
				System.out.println("success info:"+info[2]);
				return info[2];
			}else{
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
	 * ��ѯ����
	 * @param userid
	 * @param username
	 * @param product
	 * @param type 0Ϊ�����У�1Ϊ�����У�2Ϊ�����У�3Ϊ������
	 * @return userid1�� username1�� ������ ����|userid2�� username2������,����|...|myRank:�Լ�������,�Լ���߷���
	 */
	public String queryRank(String userid, String username, String product, int type){
		String sendCmd = null;
		serviceLocation += addr_queryRank;
		
		sendCmd = "userid=" + HURLEncoder.encode(userid) + 
				  "&username="+ HURLEncoder.encode(username) + 
				  "&product="+ HURLEncoder.encode(product) +
				  "&type="+ type;

		try {
			String str = postViaHttpConnection(serviceLocation, sendCmd);
			String info[] = ConvertUtil.split(str, "#");
			if(info[1].equals("0")){
				result = 0;
				return info[2];
			}else{
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
}
