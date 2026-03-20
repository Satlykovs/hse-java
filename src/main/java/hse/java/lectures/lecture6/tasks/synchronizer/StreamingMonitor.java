package hse.java.lectures.lecture6.tasks.synchronizer;

import java.util.List;

public class StreamingMonitor {

    private final int maxTicks;
    private final List<Integer> ids;

    private int curIndex = 0;
    private int curTick = 0;

    public StreamingMonitor(List<Integer> ids, int maxTicks)
    {
        this.ids = ids;
        this.maxTicks = maxTicks;
    }


    public synchronized void waitWorker(int id) throws InterruptedException
    {
        while (curTick >= maxTicks || ids.get(curIndex) != id)
        {
            wait();
        }
    }

    public synchronized void next()
    {
        curTick++;
        curIndex = (curIndex + 1) % ids.size();
        notifyAll();
    }

    public synchronized void waitFinish() throws InterruptedException
    {
        while (curTick < maxTicks)
        {
            wait();
        }
    }
}
