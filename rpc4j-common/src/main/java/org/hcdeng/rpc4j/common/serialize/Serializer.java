package org.hcdeng.rpc4j.common.serialize;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by hcdeng on 2017/3/29.
 */
public class Serializer {
    public static byte[] serialize(Object t) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output oos = new Hessian2Output(bos);
        oos.writeObject(t);
        return bos.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Hessian2Input hi = new Hessian2Input(bis);
        return hi.readObject();
    }
}
