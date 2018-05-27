package net.minigamelib.test;

import net.lelux.minigamelib.utils.MathUtils;
import org.junit.Assert;
import org.junit.Test;

public class Utils {

    @Test
    public void calcChestSize() {
        Assert.assertEquals(MathUtils.calcSize(0), 0);
        Assert.assertEquals(MathUtils.calcSize(2), 9);
        Assert.assertEquals(MathUtils.calcSize(5), 9);
        Assert.assertEquals(MathUtils.calcSize(8), 9);
        Assert.assertEquals(MathUtils.calcSize(10), 18);
        Assert.assertEquals(MathUtils.calcSize(13), 18);
        Assert.assertEquals(MathUtils.calcSize(18), 18);
        Assert.assertEquals(MathUtils.calcSize(23), 27);
        Assert.assertEquals(MathUtils.calcSize(32), 36);
        Assert.assertEquals(MathUtils.calcSize(44), 45);
    }

    @Test
    public void generateRandomNumbers() {
        for (int i = 0; i < 100; i++) {
            int random = MathUtils.generateRandomInt(25, 50);
            Assert.assertTrue(random < 50);
            Assert.assertTrue(random >= 25);
        }
    }
}
