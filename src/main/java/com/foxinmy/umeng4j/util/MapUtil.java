package com.foxinmy.umeng4j.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.foxinmy.weixin4j.model.Consts;

/**
 * 签名工具类
 * 
 * @className MapUtil
 * @author jy
 * @date 2014年10月31日
 * @since JDK 1.7
 * @see
 */
public class MapUtil {
	public static String toJoinString(Object object, boolean encoder,
			boolean lowerCase, Map<String, String> extra) {
		String text = JSON.toJSONString(object);
		Map<String, String> map = new TreeMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});

		map.putAll(JSON.parseObject(text,
				new TypeReference<Map<String, String>>() {
				}));
		if (extra != null && !extra.isEmpty()) {
			map.putAll(extra);
		}
		return toJoinString(map, encoder, lowerCase);
	}

	public static String toJoinString(Map<String, String> map, boolean encoder,
			boolean lowerCase) {
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry<String, String>> set = map.entrySet();
		try {
			if (encoder && lowerCase) {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey().toLowerCase())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(),
									Consts.UTF_8.name())).append("&");
				}
			} else if (encoder) {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(),
									Consts.UTF_8.name())).append("&");
				}
			} else if (lowerCase) {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey().toLowerCase()).append("=")
							.append(entry.getValue()).append("&");
				}
			} else {
				for (Map.Entry<String, String> entry : set) {
					if (StringUtil.isBlank(entry.getValue())) {
						continue;
					}
					sb.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
				}
			}
		} catch (UnsupportedEncodingException e) {
			;
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static String concatComma(String... params) {
		StringBuilder sb = new StringBuilder();
		sb.append(params[0]);
		if (params.length > 1) {
			for (int i = 1; i < params.length; i++) {
				sb.append(",").append(params[i]);
			}
		}
		return sb.toString();
	}
}
