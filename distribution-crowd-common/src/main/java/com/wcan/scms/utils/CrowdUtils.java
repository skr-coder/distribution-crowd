package com.wcan.scms.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.aliyun.api.gateway.demo.util.HttpUtils;

public class CrowdUtils {
	/**
	 * 生成用户登录成功后使用的token
	 * 
	 * @return
	 * @author 封捷
	 */
	public static String generateToken() {

		return CrowdConstant.REDIS_MEMBER_SING_TOKEN_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 验证集合是否有效
	 * 
	 * @param c 待验证集合
	 * @return 验证结果（true：有效，false：无效）
	 * @author 封捷
	 */
	public static <E> boolean collectionEffectiveCheck(Collection<E> c) {
		return (c != null) && (c.size() > 0);
	}

	/**
	 * 验证字符串是否有效
	 * 
	 * @param source 待验证字符串
	 * @return 验证结果（true：有效，false：无效）
	 * @author 封捷
	 */
	public static boolean strEffectiveCheck(String source) {
		return (source != null) && (source.length() > 0);
	}

	/**
	 * 生成随机验证码
	 * 
	 * @param length 验证码长度
	 * @return 生成的验证码
	 * @throws RuntimeException 验证码长度必须大于0
	 * @author 封捷
	 */
	public static String randomCode(int length) {

		if (length <= 0) {
			throw new RuntimeException(CrowdConstant.MESSAGE_RANDOM_CODE_LENGTH_INVALID);
		}

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++) {

			// 1.生成随机数
			double doubleRandom = Math.random();

			// 2.调整
			int integerRandom = (int) (doubleRandom * 10);

			// 3.拼接
			builder.append(integerRandom);
		}

		return builder.toString();
	}

	/**
	 * 发送验证码短信
	 * 
	 * @param appcode    阿里云市场中调用API时识别身份的appCode
	 * @param randomCode 验证码值
	 * @param phoneNum   接收验证码短信的手机号
	 */
	public static void sendShortMessage(String appcode, String randomCode, String phoneNum) {
		// 调用短信发送接口时的访问地址
		String host = "https://feginesms.market.alicloudapi.com";

		// 具体访问路径
		String path = "/codeNotice";

		// 请求方式
		String method = "GET";

		// 登录阿里云后，进入管理控制台->云市场->已购买服务，复制AppCode
		// String appcode = "61f2eaa3c43e42ad82c26fbbe1061311";
		Map<String, String> headers = new HashMap<String, String>();

		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);

		// 封装请求参数
		Map<String, String> querys = new HashMap<String, String>();

		// 验证码
		querys.put("param", randomCode);

		// 接收短信的手机号
		querys.put("phone", phoneNum);

		// 签名编号
		querys.put("sign", "1");

		// 模板编号
		querys.put("skin", "1");

		// JDK 1.8示例代码请在这里下载： http://code.fegine.com/Tools.zip

		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 或者直接下载： http://code.fegine.com/HttpUtils.zip 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 * 相关jar包（非pom）直接下载： http://code.fegine.com/aliyun-jar.zip
			 */
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			// System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
			// 状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
			// 获取response的body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();

			throw new RuntimeException(e.getMessage());
		}
	}

}