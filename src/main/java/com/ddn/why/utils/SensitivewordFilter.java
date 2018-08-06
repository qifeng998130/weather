package com.ddn.why.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 敏感词过滤
 * @Project：test
 * @version 1.0
 */
public class SensitivewordFilter {
	@SuppressWarnings("rawtypes")
	private static Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则
	
	//构造代码块
	static{
		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
		//System.out.println(sensitiveWordMap);
	}
	
	/**
	 * 判断文字是否包含敏感字符
	 * @param txt  文字
	 * @param matchType  匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return 若包含返回true，否则返回false
	 * @version 1.0
	 */
	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = CheckSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 获取文字中的敏感词
	 * @param txt 文字
	 * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
	 * @return
	 * @version 1.0
	 */
	public static Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<String>();
		for (int i = 0; i < txt.length(); i++) {
			int length = CheckSensitiveWord(txt, i, matchType);// 判断是否包含敏感字符
			if (length > 0) {
				// 存在,加入list中
				sensitiveWordList.add(txt.substring(i, i + length));
				i = i + length - 1; // 减1的原因，是因为for会自增
			}
		}
		return sensitiveWordList;
	}
	
	/**
	 * 替换敏感字字符
	 * @author codey 
	 * @date 2018年3月7日 下午4:16:07
	 * @param txt 要过滤的内容
	 * @param matchType
	 * @param replaceChar 替换字符
	 * @version 1.0
	 */
	public static String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);//获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		//遍历敏感词
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());//拼接要替换的字符串：***，**，****
			resultTxt = resultTxt.replaceAll(word, replaceString);//替换敏感词
		}
		return resultTxt;
	}
	
	/**
	 * 拼接替换字符串
	 * @author codey
	 * @date 2018年3月7日 下午4:13:38
	 * @param replaceChar  替换的字符
	 * @param length 要替换的字的个数
	 * @return 返回拼接成的字符串
	 * @version 1.0
	 */
	private static String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		return resultReplace;
	}
	
	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return，如果存在，则返回敏感词字符的长度，不存在返回0
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes"})
	public static int CheckSensitiveWord(String txt, int beginIndex, int matchType) {
		boolean flag = false; // 敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0; // 匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		//System.out.println(nowMap);
		for (int i = beginIndex; i < txt.length(); i++) {
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word); // 获取指定key
			//System.out.println(i+">>>>>"+word+"-----"+nowMap);
			if (nowMap != null) { // 存在，则判断是否为最后一个
				matchFlag++; // 找到相应key，匹配标识+1
				if ("1".equals(nowMap.get("isEnd"))) { // 如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true; // 结束标志位为true
					if (SensitivewordFilter.minMatchTYpe == matchType) { // 最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			} else { // 不存在，直接返回
				break;
			}
		}
		if (matchFlag < 2 || !flag) { // 长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}
	
	
	//测试
	public static void main(String[] args) {
		System.out.println("敏感词的数量：" + sensitiveWordMap.size());
		String string = "我爱北京天安门，天安门上太阳升。中国人民大团结，世界人民大团结"
				+"稳住，我们能赢。过来交易，老铁，求点子弹。猥琐发育，别浪，煞笔，沙比，傻逼，shabi，你妈逼你了吗，你麻痹的，你妈逼你结婚"
				+"飞入奶粉，奶奶，爷爷，爸爸，妈妈，儿子，孙子，鳖孙，滚犊子，王八蛋，孬孙，祖宗十九代，唐人街探案，啪啪啪";
		System.out.println("待检测语句字数：" + string.length());
		long beginTime = System.currentTimeMillis();
		Set<String> set = getSensitiveWord(string, 2);
		long endTime = System.currentTimeMillis();
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("总共消耗时间为：" + (endTime - beginTime));
		System.out.println(replaceSensitiveWord(string, 2,"*"));
	}
}
