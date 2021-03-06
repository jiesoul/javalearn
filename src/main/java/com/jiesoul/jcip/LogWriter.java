package com.jiesoul.jcip;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogWriter {
    private static final int CAPACTITY = 100;
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingDeque<>(CAPACTITY);
        this.logger = new LoggerThread(writer);
    }

    public void start () {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        private LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    writer.println(queue.take());
                }
            } catch (InterruptedException ignored) {

            } finally {
                writer.close();
            }
        }
    }
}
