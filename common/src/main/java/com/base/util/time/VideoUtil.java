package com.base.util.time;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;


/**
 * VideoUtil
 *
 * @description: VideoUtil
 * @author: deng.youxu
 * @since: 2019-10-29 23:43
 **/
public class VideoUtil {

	private final static Logger logger = LoggerFactory.getLogger(VideoUtil.class);

	private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

	/**
	 * 检查视频文件时长是否符合抖音上传的要求
	 * @param file
	 * @param secondThreshold
	 * @return
	 */
	public static boolean checkVideoDuration(File file, Long secondThreshold) {
		final long duration = getDurationOfVideo(file);
		if (duration == 0L) {
			return false;
		}
		long durationInSeconds = duration / 1000L;
		logger.info("视频实际时长[{}]秒", durationInSeconds);
		return durationInSeconds <= secondThreshold;
	}

	/**
	 * 获取视频时长，单位毫秒
	 * @param videoFile
	 * @return
	 */
	public static long getDurationOfVideo(File videoFile) {
		MultimediaObject multimediaObject = new MultimediaObject(videoFile);
		try {
			MultimediaInfo multimediaInfo = multimediaObject.getInfo();
			return	multimediaInfo.getDuration();
		} catch (EncoderException ex) {
			logger.warn("解码中出现了某些错误[{}]", ex.getMessage());
			return 0L;
		}
	}

	/**
	 * 检查视频文件容量大小是否符合抖音上传的要求
	 * @param file
	 * @param sizeThreshold 视频文件容量大小阈值， 单位MB
	 * @return
	 */
	public static boolean checkVideoSize(File file, Long sizeThreshold) {
		final long size = getSizeOfVideo(file);
		if (size == 0L) {
			return false;
		}
		Long sizeInMB = size >> 20;
		logger.info("视频实际大小[{}]MB", sizeInMB);
		return sizeInMB <= sizeThreshold;
	}

	/**
	 * 获取视频文件容量大小，单位字节
	 * @param videoFile
	 * @return
	 */
	public static long getSizeOfVideo(File videoFile) {
		try (FileInputStream fis = new FileInputStream(videoFile); FileChannel fc = fis.getChannel()) {
			return fc.size();
		} catch (IOException ex) {
			logger.info("获取视频实际大小出现问题,[{}]", ex.getMessage());
			return 0L;
		}
	}

	/**
	 * 检查视频文件容量大小是否符合抖音上传的要求
	 * @param file 视频文件
	 * @param sizeThreshold 文件总大小阈值
	 * @param partSizeThreshold 分片阈值
	 * @param partSize 分片文件大小
	 * @return
	 */
//	public static Either<Throwable, File[]> checkVideoSizeAndPart(File file, Long sizeThreshold,
//																  Long partSizeThreshold, Long partSize) {
//		try (FileInputStream fis = new FileInputStream(file); FileChannel fc = fis.getChannel()) {
//			long size = fc.size() >> 20 ;
//			logger.info("视频实际大小[{}]MB", size);
//			if (size > sizeThreshold) {
//				return Either.left(new VideoOverSizeException(
//						String.format("视频文件实际大小%sMB,超过抖音%sMB的上传要求", size, sizeThreshold)));
//			}
//
//		} catch (IOException e) {
//			logger.error("error occurred when checkVideoSize,message:{}", e.getMessage());
//			return Either.left(e);
//		}
//	}



	/**
	 * Description:计算文件的md5值
	 *
	 * @author luo.fan
	 * @date 2019/11/14 15:40
	 * @param
	 * @return java.lang.String
	 * @throws
	 *
	 */
	public static String calcMD5(File file) {
		try (InputStream stream = Files.newInputStream(file.toPath(), StandardOpenOption.READ)) {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] buf = new byte[8192];
			int len;
			while ((len = stream.read(buf)) > 0) {
				digest.update(buf, 0, len);
			}
			return toHexString(digest.digest());
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String toHexString(byte[] data) {
		StringBuilder r = new StringBuilder(data.length * 2);
		for (byte b : data) {
			r.append(hexCode[(b >> 4) & 0xF]);
			r.append(hexCode[(b & 0xF)]);
		}
		return r.toString();
	}
}
