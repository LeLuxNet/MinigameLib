package net.minigamelib.test;

import net.lelux.minigamelib.utils.ChestUtils;
import org.junit.Assert;
import org.junit.Test;

public class Utils {

    @Test
    public void test() {
        Assert.assertEquals(ChestUtils.calcSize(0), 0);
        Assert.assertEquals(ChestUtils.calcSize(2), 9);
        Assert.assertEquals(ChestUtils.calcSize(5), 9);
        Assert.assertEquals(ChestUtils.calcSize(8), 9);
        Assert.assertEquals(ChestUtils.calcSize(10), 18);
        Assert.assertEquals(ChestUtils.calcSize(13), 18);
        Assert.assertEquals(ChestUtils.calcSize(18), 18);
        Assert.assertEquals(ChestUtils.calcSize(23), 27);
        Assert.assertEquals(ChestUtils.calcSize(32), 36);
        Assert.assertEquals(ChestUtils.calcSize(44), 45);
    }
}
