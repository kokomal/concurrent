package yuanjun.chen.pool;

import java.util.concurrent.RecursiveAction;

public class PrintTask extends RecursiveAction {
    private static final long serialVersionUID = -1611833808051971457L;
    /** 最多只能打印50个数. */
    private static final int THRESHOLD = 5;
    private int start;
    private int end;

    public PrintTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "的i值：" + i);
            }
        } else {
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            // 并行执行两个“小任务”
            left.fork();
            right.fork();
        }
    }
}
