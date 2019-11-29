package com.elias.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.StringUtils;

import com.elias.enums.ResultEnum;
import com.elias.exception.EliasException;

public class FileUtil {
	// 初始化创建指定的保存路径
	public static void initSavePath(String filePath) throws EliasException {
		if (StringUtils.isEmpty(filePath)) {
			throw new EliasException(ResultEnum.PARAM_ERROR.getCode(),
					ResultEnum.PARAM_ERROR.getMessage() + ":目标地址是空值！");
		}

		// 如果是windows系统路径，判断盘符是否存在
		if (filePath.indexOf(":") == 1) {
			File disk = new File(filePath.substring(0, 3));
			if (!disk.exists()) {
				throw new EliasException(ResultEnum.PARAM_ERROR.getCode(),
						ResultEnum.PARAM_ERROR.getMessage() + ":目标盘符不存在(" + filePath.substring(0, 3) + ")");
			}
		}
		File file = new File(filePath);

		if (file.exists() && file.isDirectory()) {// 如果已存在文件夹，直接结束
			return;
		} else if (file.exists() && !file.isDirectory()) {// 如果已存在，但不是文件夹，异常
			throw new EliasException(ResultEnum.PARAM_ERROR.getCode(),
					ResultEnum.PARAM_ERROR.getMessage() + ":目标地址已存在且不是文件夹(" + filePath + ")");
		} else if (!file.exists()) {// 如果不存在
			initSavePath(file.getParent());// 先创建父目录
			file.mkdir();// 再创建文件夹
		}
	}

	public static void download(String imageUrl, String imagePath, String referer) throws Exception {
		// 构造URL
		URL url = new URL(imageUrl);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为5s
		con.setConnectTimeout(20 * 1000);
		con.setReadTimeout(20 * 1000);
		con.setRequestProperty("User-Agent", ToolsUtil.getUserAgent());
		con.setRequestProperty("Referer", referer);
		// 输入流
		InputStream is = con.getInputStream();

		// 10K的数据缓冲
		byte[] bs = new byte[10240];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = new FileOutputStream(imagePath);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}
}