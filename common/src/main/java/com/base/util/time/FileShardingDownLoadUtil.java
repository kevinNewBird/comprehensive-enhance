//package com.base.util.time;
//
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.SynchronousQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.regex.Pattern;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.common.util.concurrent.ThreadFactoryBuilder;
//
//import static com.trs.common.base.PreConditionCheck.checkArgument;
//import static com.trs.common.utils.StringUtils.isEmpty;
//import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
//
//public class FileShardingDownLoadUtil {
//
//    private static Logger logger = LoggerFactory.getLogger(FileShardingDownLoadUtil.class);
//
//    private final static Pattern p2 =
//        Pattern.compile("^\\s*Duration: (\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d\\d).*$", Pattern.CASE_INSENSITIVE);
//
//    /**
//     * 使用缓存线程池
//     */
//    private static final ExecutorService MULTI_DOWN_LOAD_THREAD_POOL =
//        new ThreadPoolExecutor(10, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(),
//            new ThreadFactoryBuilder().setNameFormat("VIDEO-DOWNLOAD-%d").build());
//
//    /**
//     * 线程下载成功标志
//     */
//    private static int flag = 0;
//
//    /**
//     * 服务器请求路径
//     */
//    private String serverPath;
//
//    /**
//     * 本地文件夹路径
//     */
//    private String localFolderPath;
//
//    /**
//     * 分片文件的大小，单位字节
//     */
//    private Long sizeOfPieceFile;
//
//    /**
//     * 线程计数同步辅助
//     */
//    private CountDownLatch latch;
//
//    /**
//     * 因为某些平台的分片上传机制需要该分片文件的起止字节数作为参数才能上传，故在此保存各分片文件的字节，key为分片文件的文件名（即序号）, value为该分片文件的开始字节与结束字节的拼接，之间使用":"分隔
//     */
//    private Map<Integer, String> startToEndBytesMapping = new TreeMap<>();
//
//    public FileShardingDownLoadUtil(String serverPath, String localFolderPath, Long sizeOfPieceFile) {
//        checkArgument(!isEmpty(serverPath));
//        checkArgument(!isEmpty(localFolderPath));
//        checkArgument(sizeOfPieceFile != null && sizeOfPieceFile > 0L);
//        this.serverPath = serverPath;
//        this.localFolderPath = localFolderPath;
//        this.sizeOfPieceFile = sizeOfPieceFile;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public boolean executeDownLoad() {
//        try {
//            if (!localSavePathPreCheck(localFolderPath)) {
//                logger.error("本地保存路径[{}]异常", localFolderPath);
//                return false;
//            }
//            URL url = new URL(serverPath);
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setConnectTimeout(5000);// 设置超时时间
//            conn.setRequestMethod("GET");// 设置请求方式
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Accept-Encoding", "identity");
//            int code = conn.getResponseCode();
//            if (code != 200) {
//                logger.error("请求的网络地址[{}]无效", serverPath);
//                return false;
//            }
//            long length = 0L;
//            for (int i = 1;; i++) {
//                String sHeader = conn.getHeaderFieldKey(i);
//                if (sHeader != null && sHeader.equals("Content-Length")) {
//                    length = Long.parseLong(conn.getHeaderField(sHeader));
//                    break;
//                }
//            }
//            if (length == 0L) {
//                logger.error("请求网络地址[{}]的内容为空", serverPath);
//                return false;
//            }
//
//            logger.info("文件总长度[{}]MB", length >> 20);
//            int threadCount = decideThreadCount(length, sizeOfPieceFile);
//            logger.info("分配[{}]个下载线程", threadCount);
//            // 分割文件
//            latch = new CountDownLatch(threadCount);
//
//            for (int threadId = 1; threadId <= threadCount; threadId++) {
//                // 每一个线程下载的开始位置
//                long startIndex = (threadId - 1) * sizeOfPieceFile;
//                // 每一个线程下载的开始位置
//                long endIndex = startIndex + sizeOfPieceFile - 1;
//                if (threadId == threadCount) {
//                    // 最后一个线程下载的长度稍微长一点
//                    endIndex = length;
//                }
//
//                MULTI_DOWN_LOAD_THREAD_POOL.execute(new DownLoadThread(threadId, startIndex, endIndex, latch));
//            }
//            latch.await();
//            if (flag == 0) {
//                logger.info("从源地址[{}]下载视频至本地[{}]成功", serverPath, localFolderPath);
//                return true;
//            }
//        }
//        catch (Exception e) {
//            logger.error("从源地址[{}]下载视频至本地[{}]失败,失败原因[{}]", serverPath, localFolderPath, e.getMessage());
//        }
//        return false;
//    }
//
//    /**
//     *
//     * @param localFolderPath
//     * @return
//     */
//    private boolean localSavePathPreCheck(String localFolderPath) {
//        checkArgument(!isEmpty(localFolderPath), "传入的localFolderPath不能为空");
//        // 建立文件夹
//        final File folder = new File(localFolderPath);
//        // 如果存在但是不是文件夹，则删除
//        if (folder.exists() && !folder.isDirectory()) {
//            if (!folder.delete()) {
//                logger.error("本地下载路径文件夹[{}]建立失败", localFolderPath);
//                return false;
//            }
//        }
//        // 如果不存在则新建文件夹
//        if (!folder.exists()) {
//            if (!folder.mkdirs()) {
//                logger.error("本地下载路径文件夹[{}]建立失败", localFolderPath);
//                return false;
//            }
//        }
//        // 检查文件夹是否能读写
//        if (!folder.canWrite() || !folder.canRead()) {
//            logger.error("本地下载路径文件夹[{}]读写异常,请检查一下权限", localFolderPath);
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     *
//     * @param length
//     * @param sizeOfPieceFile
//     * @return
//     */
//    private int decideThreadCount(long length, Long sizeOfPieceFile) {
//        int count = (int)(length / sizeOfPieceFile);
//        return length % sizeOfPieceFile == 0 ? count : count + 1;
//    }
//
//    /**
//     * 内部类用于实现下载
//     */
//    public class DownLoadThread implements Runnable {
//        private Logger logger = LoggerFactory.getLogger(DownLoadThread.class);
//
//        /**
//         * 线程ID
//         */
//        private int threadId;
//
//        /**
//         * 下载起始位置
//         */
//        private long startIndex;
//
//        /**
//         * 下载结束位置
//         */
//        private long endIndex;
//
//        /**
//         * 计数器
//         */
//        private CountDownLatch latch;
//
//        public DownLoadThread(int threadId, long startIndex, long endIndex, CountDownLatch latch) {
//            this.threadId = threadId;
//            this.startIndex = startIndex;
//            this.endIndex = endIndex;
//            this.latch = latch;
//        }
//
//        @Override
//        public void run() {
//            InputStream is = null;// 返回资源
//            final String name = Thread.currentThread().getName();
//            try {
//                logger.info("线程[{}]即将下载第[{}]个分片视频,{}字节~{}字节", name, threadId, startIndex, endIndex);
//                URL url = new URL(serverPath);
//                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//                conn.setRequestProperty("Connection", "Keep-Alive");
//                conn.setRequestMethod("GET");
//                // 请求服务器下载部分的文件的指定位置
//                conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
//                conn.setConnectTimeout(60000);
//                conn.setReadTimeout(60000);
//                is = conn.getInputStream();
//                // 分片文件保存在给定的文件夹中
//                String localPieceFilePath = localFolderPath + File.separator + threadId + ".mp4";
//                File pieceFile = new File(localPieceFilePath);
//                // 写入文件
//                copyInputStreamToFile(is, pieceFile);
//                final long sizeOfVideo = VideoUtil.getSizeOfVideo(pieceFile);
//                final long durationOfVideo = VideoUtil.getDurationOfVideo(pieceFile);
//                logger.info("时长[{}]毫秒", durationOfVideo);
//                logger.info("容量[{}]字节", sizeOfVideo);
//                logger.info("线程[{}]下载分片视频到本地文件[{}]完毕", threadId, localPieceFilePath);
//                startToEndBytesMapping.put(threadId, startIndex + ":" + endIndex);
//            }
//            catch (Exception e) {
//                logger.error("线程[{}]下载第[{}]个分片视频的数据出现错误,错误信息[{}]", name, threadId, e.getMessage());
//                // 线程下载出错
//                flag = 1;
//            }
//            finally {
//                // 计数值减一
//                latch.countDown();
//                if (is != null) {
//                    try {
//                        is.close();
//                    }
//                    catch (IOException e) {
//                        logger.warn("关闭流出现了问题[{}]", e.getMessage());
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 内部方法，获取远程文件大小
//     *
//     * @param remoteFileUrl
//     * @return
//     * @throws IOException
//     */
//    @Deprecated
//    private long getRemoteFileSize(String remoteFileUrl)
//        throws IOException {
//        long fileSize = 0;
//        HttpURLConnection httpConnection = (HttpURLConnection)new URL(remoteFileUrl).openConnection();
//        httpConnection.setRequestMethod("HEAD");
//        int responseCode = 0;
//        try {
//            responseCode = httpConnection.getResponseCode();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (responseCode >= 400) {
//            logger.debug("Web服务器响应错误!");
//            return 0;
//        }
//        String sHeader;
//        for (int i = 1;; i++) {
//            sHeader = httpConnection.getHeaderFieldKey(i);
//            if (sHeader != null && sHeader.equals("Content-Length")) {
//                fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));
//                break;
//            }
//        }
//        return fileSize;
//    }
//
//
//
//    public static void main(String[] args) {
//
//    }
//}
