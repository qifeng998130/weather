package com.ddn.why.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * @Description: 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 * @Project：test
 * @version 1.0
 */
public class SensitiveWordInit {
	private String ENCODING = "UTF-8";    //字符编码
	@SuppressWarnings("rawtypes")
	public HashMap sensitiveWordMap;
	
	public SensitiveWordInit(){
		super();
	}
	
	/**
	 * 初始化
	 * @version 1.0
	 */
	@SuppressWarnings("rawtypes")
	public Map initKeyWord() {
		try {
			// 读取敏感词库
			Set<String> keyWordSet = readSensitiveWordFile();
			// 将敏感词库加入到HashMap中
			addSensitiveWordToHashMap(keyWordSet);
			// spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}

	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
	 * 中 = {
	 *      isEnd = 0
	 *      国 = {<br>
	 *      	 isEnd = 1
	 *           人 = {isEnd = 0
	 *                民 = {isEnd = 1}
	 *                }
	 *           男  = {
	 *           	   isEnd = 0
	 *           		人 = {
	 *           			 isEnd = 1
	 *           			}
	 *           	}
	 *           }
	 *      }
	 *  五 = {
	 *      isEnd = 0
	 *      星 = {
	 *      	isEnd = 0
	 *      	红 = {
	 *              isEnd = 0
	 *              旗 = {
	 *                   isEnd = 1
	 *                  }
	 *              }
	 *      	}
	 *      }
	 * @param keyWordSet  敏感词库
	 * @version 1.0
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
		sensitiveWordMap = new HashMap(keyWordSet.size()); // 初始化敏感词容器，减少扩容操作
		String key = null;
		Map nowMap = null;//状态map，是否结束
		Map<String, String> newWorMap = null;
		// 迭代keyWordSet
		Iterator<String> iterator = keyWordSet.iterator();
		while (iterator.hasNext()) {
			key = iterator.next(); // 关键字,敏感词
			//System.out.println("构建DFA模型：" + key+"====="+sensitiveWordMap);
			//nowMap指向sensitiveWordMap
			nowMap = sensitiveWordMap;
			for (int i = 0; i < key.length(); i++) {
				char keyChar = key.charAt(i); // 转换成char型---单个字
				Object wordMap = nowMap.get(keyChar); // 获取
				if (wordMap != null) { // 如果存在该key，直接赋值
					nowMap = (Map) wordMap;
				} else { // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
					newWorMap = new HashMap<String, String>();
					newWorMap.put("isEnd", "0"); // 不是最后一个
					//嵌套map
					nowMap.put(keyChar, newWorMap);
					//nowMap指向newWorMap
					nowMap = newWorMap;
				}

				if (i == key.length() - 1) {
					nowMap.put("isEnd", "1"); // 最后一个
				}
				//System.out.println(nowMap+"---now----"+i);
				//System.out.println(sensitiveWordMap+"----"+i);
			}
		}
	}

	/**
	 * 读取敏感词库中的内容，将内容添加到set集合中
	 * @return
	 * @version 1.0
	 * @throws Exception 
	 */
	@SuppressWarnings("resource")
	private Set<String> readSensitiveWordFile() throws Exception {
		Set<String> set = null;

		//服务器地址：/data/ftpone/static/danmu/word.txt
		//本地  D:\\sensitive\\word.txt
		File file = new File("/data/ftpone/static/danmu/word.txt"); // 读取文件
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
		try {
			if (file.isFile() && file.exists()) { // 文件流是否存在
				set = new HashSet<String>();
				BufferedReader bufferedReader = new BufferedReader(read);
				String txt = null;
				while ((txt = bufferedReader.readLine()) != null) { // 读取文件，将文件内容放入到set中
					//System.out.println("敏感词："+txt);
					set.add(txt);
				}
			} else { // 不存在抛出异常信息
				throw new Exception("敏感词库文件不存在");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			read.close(); // 关闭文件流
		}
		return set;
	}
	
	/**
	 * 将敏感词库装换为一行一个词的格式
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void formateSensitiveWord() throws Exception {
		Set<String> set = null;//去重用
		
		File file = new File("D:\\sensitive\\1.txt"); // 读取文件
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
		try {
			if (file.isFile() && file.exists()) { // 文件流是否存在
				set = new HashSet<String>();
				
				BufferedReader bufferedReader = new BufferedReader(read);
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("D:\\sensitive\\word.txt")));//输出流
				String txt = null;
				while ((txt = bufferedReader.readLine()) != null) { // 读取文件，将文件内容放入到set中
					// 处理敏感词
					System.out.println("敏感词：" + txt.replace("|", "\r\n"));
					set.add(txt);
				}
				//不能在上一个循环中
				Iterator<String> iterator = set.iterator();
				while (iterator.hasNext()) {
					txt = iterator.next();
					bufferedWriter.write(txt);
					bufferedWriter.newLine();
				}
				bufferedWriter.flush();
				bufferedWriter.close();
			} else { // 不存在抛出异常信息
				throw new Exception("敏感词库文件不存在");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			read.close(); // 关闭文件流
		}
	}
}
