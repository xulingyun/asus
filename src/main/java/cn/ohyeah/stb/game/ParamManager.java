package cn.ohyeah.stb.game;

/**
 * ���������࣬����У��jad����
 * @author maqian
 * @version 1.0
 */
final class ParamManager {
	public static final String ENTRANCE_WINSIDE = "winside";
	public static final String ENTRANCE_WINSIDEGD = "winsidegd";
	
	
	int[] rechargeAmounts;	/*��ֵ����б�*/
	
	String stbType;			/*�����������*/
	String enterURL;		/*�����������*/
	String zyUserToken;		/*�����������*/
	String myDXScore;		/*�����������*/
	String hosturl;			/*�����������, �ղز���*/
	String code;			/*�����������*/
	String timeStmp;		/*�����������*/
	
	String spid;			/*��Ӧ��ID�����κ��������ֵʱ��Ҫ*/
	String gameid;			/*��ϷID�����κ��������ֵʱ��Ҫ*/
	String buyURL;			/*��ѯԪ���Ϳ۳�Ԫ����������ַ*/
	String checkKey;		/*MD5�����ַ���*/
	
	String server;			/*��Ϸ��������ַ*/
	String userId;			/*������ID*/
	String accountName;		/*�û��ǳ�*/
	String userToken;		
	String product;		/*��Ʒ����*/
	
	private String errorMessage;
	private boolean parseSuccessful;
	private IEngine engine;
	
	public ParamManager(IEngine engine) {
		this.engine = engine;
	}
	
	public boolean parse() {
		try {
			parseSuccessful = true;
			errorMessage = "";
			parseParam();
			if (!parseSuccessful) {
				System.out.println(errorMessage);
			}
		}
		catch (Exception e) {
			parseSuccessful = false;
			errorMessage += e.getMessage()+"\n";
		}
		return parseSuccessful;
	}
	
	private void parseParam() {
		Configurations conf = Configurations.getInstance();
		System.out.println("telcomOperators:"+conf.getTelcomOperators());
		System.out.println("serviceProvider:"+conf.getServiceProvider());
		if (conf.isServiceProviderWinside()) {
			if (conf.isTelcomOperatorsTelcomgd()) {
				parseWinsidegdPlatParam();
			}
			else {
				parseWinsidePlatParam();
			}
		}
		else {
			parseSuccessful = false;
			errorMessage += "[����] ==> "+"δ֪����ڲ���"+conf.getServiceProvider()+"\n";
		}
		
		String amounts = conf.getPrice();
		if (amounts == null || "".equals(amounts)) {
			amounts = getStringParam("price");
		}
		if (amounts != null && !"".equals(amounts)) {
			parseAmounts(amounts);
		}
	}
	
	private void parseAmounts(String amounts) {
		try {
			int prevPos = 0;
			int scanPos = 0;
			int amountCount = 1;
			if (!amounts.startsWith("/") && !amounts.endsWith("/") && amounts.indexOf("//")<0) {
				while (scanPos < amounts.length()) {
					if (amounts.charAt(scanPos) == '/') {
						++amountCount;
					}
					++scanPos;
				}
				rechargeAmounts = new int[amountCount];
				
				scanPos = 0;
				amountCount = 0;
				while (scanPos < amounts.length()) {
					if (amounts.charAt(scanPos) == '/') {
						rechargeAmounts[amountCount] = Integer.parseInt(amounts.substring(prevPos, scanPos));
						++amountCount;
						prevPos = scanPos+1;
					}
					++scanPos;
				}
				rechargeAmounts[amountCount] = Integer.parseInt(amounts.substring(prevPos));
			}
			else {
				parseSuccessful = false;
				errorMessage += "[����] ==> "+"����"+"\""+"price"+"\""+"��ʽ����"+"\n";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			parseSuccessful = false;
			errorMessage += "[����] ==> "+"����"+"\""+"price"+"\""+"��ʽ����"+"\n";
		}
	}
	
	private void parseWinsidegdPlatParam() {
		server = getStringParam("w_server");
		userId = getStringParam("userid");
		accountName = getStringParam("iptvname");
		spid = getStringParam("spid");
		gameid = getStringParam("gameid");
		product = getStringParam("product");
		checkKey = getStringParam("checkKey");
		buyURL = getStringParam("buyURL");
		zyUserToken = getStringParam("zyUserToken");
		stbType = getStringParam("stbType");
		enterURL = getStringParam("enterURL");
		myDXScore = getStringParam("myDXScore");
		hosturl = getStringParam("hosturl");
		code = getStringParam("gameCode");
		timeStmp = getStringParam("timeStmp");
		userToken = zyUserToken;
	}
	
	private void parseWinsidePlatParam() {
		server = getStringParam("loginurl");
		userId = getStringParam("userid");
		accountName = getStringParam("username");
		userToken = getStringParam("userToken");
		gameid = getStringParam("gameid");
		spid = getStringParam("spid");
		product = getStringParam("product");
		checkKey = getStringParam("checkKey");
		buyURL = getStringParam("buyURL");
	}
	
	private String getStringParam(String paramName) {
		String paramValue = null;
		try {
			paramValue = engine.getAppProperty(paramName).trim();
			if ("".equals(paramValue)) {
				parseSuccessful = false;
				errorMessage += "[��Ϣ] ==> "+"��ȡ����"+"\""+paramName+"\""+"ʧ��"+"\n";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			parseSuccessful = false;
			errorMessage += "[��Ϣ] ==> "+"��ȡ����"+"\""+paramName+"\""+"ʧ��"+"\n";
		}
		return paramValue;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public boolean isParseSuccess() {
		return parseSuccessful;
	}
}
