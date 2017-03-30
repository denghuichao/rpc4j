package org.hcdeng.rpc4j;

import junit.framework.TestCase;
import org.hcdeng.rpc4j.common.utils.ZKPathUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hcdeng on 2017/3/30.
 */
public class TestZKPathUtils extends TestCase {
    @Test
    public void testFormatProviderPath() {
        String providerPath = ZKPathUtils.formatProviderPath("helloService", "127.0.0.1", 4050);
        Assert.assertTrue("/rpc4j/service/helloService/127.0.0.1:4050".equals(providerPath));
    }

    @Test
    public void testFormatProviderPath_Fail_1() {
        try {
            ZKPathUtils.formatProviderPath(null, "127.0.0.1", 4050);
            fail();
        } catch (NullPointerException e) {
            //ok
        }
    }

    @Test
    public void testFormatProviderPath_Fail_2() {
        try {
            ZKPathUtils.formatProviderPath("helloService", null, 4050);
            fail();
        } catch (NullPointerException e) {
            //ok
        }
    }

    @Test
    public void testIsValidProviderPath_1() {
        assertTrue(ZKPathUtils.isValidProviderPath("/rpc4j/service/serviceName/127.0.0.1:88"));
    }

    @Test
    public void testIsValidProviderPath_2() {
        assertTrue(ZKPathUtils.isValidProviderPath("/rpc4j/service/serviceName/192.168.1.1:88"));
    }

    @Test
    public void testIsValidProviderPath_3() {
        assertFalse(ZKPathUtils.isValidProviderPath("/rpc4j/services/serviceName/127.0.0.1:88"));
    }

    @Test
    public void testIsValidProviderPath_4() {
        assertFalse(ZKPathUtils.isValidProviderPath("/rpc4j/service/127.0.0.1:88"));
    }

    @Test
    public void testIsValidProviderPath_5() {
        assertFalse(ZKPathUtils.isValidProviderPath("/rpc4j/service/serviceName/1922.168.1.1:88"));
    }

    @Test
    public void testIsValidProviderPath_6() {
        try {
            assertFalse(ZKPathUtils.isValidProviderPath(null));
            fail();
        } catch (NullPointerException e) {
        }
    }

    @Test
    public void testGetServiceNameFromProviderPath_1() {
        String providerPath = "/rpc4j/service/helloService/127.0.0.1:4050";
        Assert.assertTrue("helloService".equals(ZKPathUtils.getServiceNameFromProviderPath(providerPath)));
    }

    @Test
    public void testGetServiceNameFromProviderPath_2() {
        String providerPath = "/rpc4j/service/127.0.0.1:4050";
        try {
            ZKPathUtils.getServiceNameFromProviderPath(providerPath);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
}
