package com.github.brokenswing.comixaire;

public class TestUtil
{

    private TestUtil()
    {
    }

    public static Runnable rethrow(ThrowableRunnable r)
    {
        return () ->
        {
            try
            {
                r.run();
            }
            catch (Throwable e)
            {
                throw new RuntimeException(e);
            }
        };
    }

    public interface ThrowableRunnable
    {
        void run() throws Throwable;
    }

}
