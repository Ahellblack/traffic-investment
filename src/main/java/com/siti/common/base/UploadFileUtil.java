package com.siti.common.base;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**文件上传工具类
 * @author yaomin
 * */
public class UploadFileUtil {

	/**
	 * 接收上传的文件片，以MD5值作为文件夹名，存储文件片
	 *
	 * @param xPath   存储文件的路径
	 * @param chunk   当前上传的片
	 * @param chunks  总片数
	 * @param fileMd5 上传文件的MD5值
	 * @return 成功返回1，失败返回-1
	 */
	public static int uploadFile(MultipartFile file, String xPath, int chunk, int chunks, String fileMd5) {
		try {
			if (!file.isEmpty()) {
				File fileParent = new File(xPath + "uploadFile/" + fileMd5);
				if (!fileParent.exists()) {
					fileParent.mkdir();
				}
				String realPath = xPath + "uploadFile/" + fileMd5 + "/" + chunk;
				file.transferTo(new File(realPath));
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 非断点续传，适用于小文件
	 *
	 * @param xPath 存储文件的路径
	 * @return 成功返回上传文件的完整路径（包含文件名），失败返回error
	 */
	public static String uploadFiles(MultipartFile file, String xPath, List<Map> parameters, String size) {
		try {
			String md5 = paragraphMD5(file, parameters, size);
			if (md5.equals("error")) {
				return "error";
			}
			System.out.println(md5);
			String saveName = md5 + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String realPath = xPath + "uploadFile/" + saveName;
			File fileParent = new File(realPath);
			if (!fileParent.exists()) {
				file.transferTo(fileParent);
				if (isImage(fileParent)) {
					resize(fileParent, xPath + "uploadFile/" + md5 + "(1)" + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")), 210, 120);
				}
				if (isVideo(fileParent)) {
					screenShot(realPath.substring(1, realPath.length()), "E:/ffmpeg/ffmpeg.exe", 210, 120);
				}
			}
			return realPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 断点续传文件片全部上传完成时调用，用于合成文件
	 *
	 * @param xPath    存储文件的路径
	 * @param fileName 合成文件的名称（包括后缀名）
	 * @param fileMd5  合成文件的MD5值
	 * @return 成功返回合成文件的完整路径（包含文件名），失败返回error
	 */
	public static String mergeFile(String fileName, String fileMd5, String xPath) {
		try {
			File fileParent = new File(xPath + "uploadFile/" + fileMd5);
			String saveName = fileMd5 + fileName.substring(fileName.lastIndexOf("."));
			String uploadFileName = xPath + "uploadFile/" + saveName;
			File newfile = new File(uploadFileName);
			if (!newfile.exists()) {
				FileOutputStream outputStream = new FileOutputStream(newfile, true);
				byte[] byt = new byte[10 * 1024 * 1024];
				int len = 0;
				FileInputStream temp = null;
				for (int i = 0; i < fileParent.list().length; i++) {
					temp = new FileInputStream(new File(xPath + "uploadFile/" + fileMd5 + "/" + i));
					while ((len = temp.read(byt)) != -1) {
						outputStream.write(byt, 0, len);
					}
					temp.close();
				}
				outputStream.close();
				deleteDirectory(xPath + "uploadFile/" + fileMd5);

				System.out.println(isVideo(newfile));
				if (isVideo(newfile)) {
					screenShot(uploadFileName.substring(1, uploadFileName.length()), "E:/ffmpeg/ffmpeg.exe", 210, 120);
				}
				if (isImage(newfile)) {
					resize(newfile, xPath + "uploadFile/" + fileMd5 + "(1)" + fileName.substring(fileName.lastIndexOf(".")), 210, 120);
				}
			}
			return uploadFileName;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 断点续传时检查文件片是否上传
	 *
	 * @param xPath     存储文件片的路径
	 * @param chunk     当前上传的片
	 * @param chunkSize 当前上传片大小
	 * @param fileMd5   上传文件的MD5值
	 * @param fileName  文件的名称（包括后缀名）
	 * @return 已存在且完整返回true，否则返回false
	 */
	public static boolean checkChunk(String xPath, Integer chunk, Integer chunkSize, String fileMd5, String fileName) {
		File uploadFile = new File(xPath + "uploadFile/" + fileMd5 + fileName.substring(fileName.lastIndexOf(".")));
		if (uploadFile.exists()) {
			return true;
		}
		String realPath = xPath + "uploadFile/" + fileMd5 + "/" + chunk;
		File file = new File(realPath);
		if (file.exists()) {
			if (file.length() < chunkSize) {
				file.delete();
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断文件是否是图片类型
	 */
	public static boolean isImage(File file) {
		Image img = null;
		try {
			img = ImageIO.read(file);
			if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断文件是否是视频类型
	 */
	public static boolean isVideo(File file) {
		String fileName = file.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		String fileTypes[] = new String[]{"wmv", "asf", "asx", "rm", "rmvb", "mpg", "mpeg", "mpe", "3gp", "mov", "mp4", "m4v", "avi", "dat", "mkv", "flv", "vob"};
		for (int i = 0; i < fileTypes.length; i++) {
			if (fileType.equalsIgnoreCase(fileTypes[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 图片长宽压缩
	 *
	 * @param destFile 压缩图片的具体路径（包含文件名）
	 * @param width    压缩图片的长度
	 * @param height   压缩图片的高度
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	public static void resize(File file, String destFile, int width, int height) throws IOException {
		Image img = ImageIO.read(file);      // 构造Image对象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		image.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();
	}

	/**
	 * 图片质量压缩
	 *
	 * @param destFile 压缩图片的具体路径（包含文件名）
	 * @param quality  压缩比(0,1]
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	public static void resizePic(File file, String destFile, float quality) throws IOException {
		File resizedFile = new File(destFile);//压缩之后的文件
		System.out.println("源文件大小" + file.length());
		ImageIcon ii = new ImageIcon(file.getCanonicalPath());
		Image i = ii.getImage();
		Image resizedImage = i;
	    /*int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if(iWidth < newWidth){
            newWidth = iWidth;
        }
        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
                    / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
                    newWidth, Image.SCALE_SMOOTH);
        }*/
		Image temp = new ImageIcon(resizedImage).getImage();
		BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
				temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
		g.drawImage(temp, 0, 0, null);
		g.dispose();
		float softenFactor = 0.05f;
		float[] softenArray = {0, softenFactor, 0, softenFactor,
				1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
		Kernel kernel = new Kernel(3, 3, softenArray);
		ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		bufferedImage = cOp.filter(bufferedImage, null);
		FileOutputStream out = new FileOutputStream(resizedFile);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder
				.getDefaultJPEGEncodeParam(bufferedImage);
		param.setQuality(quality, true);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bufferedImage);
		out.close();
		System.out.println("转换后文件大小" + resizedFile.length());
	}

	/**
	 * 删除文件夹及其下所有文件
	 *
	 * @param sPath 删除文件夹的路径
	 */
	public static void deleteDirectory(String sPath) {
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return;
		}
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
		dirFile.delete();
	}

	/**
	 * 使用ffmpeg对视频截图
	 *
	 * @param veido_path  视频路径（包含文件名）
	 * @param ffmpeg_path ffmpeg位置
	 * @param width       截图的长度
	 * @param height      截图的高度
	 * @return 截取成功true，否则返回false
	 */
	public static boolean screenShot(String veido_path, String ffmpeg_path, int width, int height) {
		int time = 0;
		File file = new File(veido_path);
		if (!file.exists()) {
			System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
			return false;
		}
		List<String> commands = new ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(veido_path);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			final Process p = builder.start();
			//从输入流中读取视频信息
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			//从视频信息中解析时长
			String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
			String regexVideo = "Video: (.*?), (.*?), (.*?)[,\\s]";
			Pattern pattern = Pattern.compile(regexDuration);
			Pattern patternVideo = Pattern.compile(regexVideo);
			Matcher m = pattern.matcher(sb.toString());
			Matcher mVideo = patternVideo.matcher(sb.toString());
			if (m.find()) {
				time = getTimelen(m.group(1));
				System.out.println("视频时长：" + m.group(1) + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
			}
			if (mVideo.find()) {
				System.out.println("编码格式：" + mVideo.group(1) + ", 视频格式：" + mVideo.group(2) + ",分辨率：" + mVideo.group(3));
			}
			int shotTime = Math.round(time * 5 / 100);
			List<String> commandshots = new ArrayList<String>();
			commandshots.add(ffmpeg_path);
			commandshots.add("-ss");
			commandshots.add(String.valueOf(shotTime));//这个参数是设置截取视频多少秒时的画面
			commandshots.add("-i");
			commandshots.add(veido_path);
			commandshots.add("-f");
			commandshots.add("image2");
			commandshots.add("-y");
			commandshots.add("-s");
			String resolution = width + "*" + height;
			commandshots.add(resolution);
			commandshots.add(veido_path.substring(0, veido_path.lastIndexOf(".")).replaceFirst("vedio", "file") + ".jpg");
			try {
				ProcessBuilder buildershot = new ProcessBuilder();
				buildershot.command(commandshots);
				buildershot.start();
				System.out.println("截取成功");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 时间转换
	 */
	private static int getTimelen(String timelen) {
		int min = 0;
		String strs[] = timelen.split(":");
		if (strs[0].compareTo("0") > 0) {
			min += Integer.valueOf(strs[0]) * 60 * 60;//秒
		}
		if (strs[1].compareTo("0") > 0) {
			min += Integer.valueOf(strs[1]) * 60;
		}
		if (strs[2].compareTo("0") > 0) {
			min += Math.round(Float.valueOf(strs[2]));
		}
		return min;
	}

	/**
	 * 获取文件的MD5值
	 *
	 * @throws IOException
	 */
	public static String getFileMD5(MultipartFile file) throws IOException {
		int bufferSize = 256 * 1024;
		InputStream inputStream = null;
		DigestInputStream digestInputStream = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			inputStream = file.getInputStream();
			digestInputStream = new DigestInputStream(inputStream, messageDigest);
			byte[] buffer = new byte[bufferSize];
			while (digestInputStream.read(buffer) > 0) ;
			messageDigest = digestInputStream.getMessageDigest();
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error";
		} finally {
			try {
				digestInputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取文件的MD5值
	 *
	 * @param parameters 截取段比例，以键值对方式表示，例如截取方法：(0-size*0.1)+(size*0.45-size*0.55)+(size*0.9-size),
	 *                   则parameters：[{from=0.0, to=0.1}, {from=0.45, to=0.55}, {from=0.9, to=1.0}]
	 * @param size       上传文件的大小
	 * @return 上传文件的部分MD5值
	 * @throws IOException
	 */
	public static String paragraphMD5(MultipartFile file, List<Map> parameters, String size) throws IOException {
		try {
			List<Double> froms = new ArrayList<Double>();
			List<Double> tos = new ArrayList<Double>();
			MessageDigest md = MessageDigest.getInstance("MD5");
			InputStream fis = file.getInputStream();
			long len = file.getSize();
			for (int i = 0; i < parameters.size(); i++) {
				froms.add((Double) parameters.get(i).get("from"));
				tos.add((Double) parameters.get(i).get("to"));
				long skipFrom = (long) (len * froms.get(i));
				long skipTo = (long) (len * tos.get(i));
				byte[] buffer = new byte[(int) (skipTo - skipFrom)];
				if (i == 0) {
					fis.skip((int) skipFrom);
				} else {
					fis.skip((int) ((long) (len * froms.get(i)) - (long) (len * tos.get(i - 1))));
				}
				fis.read(buffer);
				md.update(buffer);
			}
			fis.close();
			md.update(size.getBytes("UTF-8"));
			BigInteger bigInt = new BigInteger(1, md.digest());
			return bigInt.toString(16);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

	public static String byteArrayToHex(byte[] byteArray) {
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);
	}

	public static Map<String, Double> putMap(Double from, Double to) {
		Map<String, Double> parameter = new HashMap<String, Double>();
		parameter.put("from", from);
		parameter.put("to", to);
		return parameter;
	}
}
