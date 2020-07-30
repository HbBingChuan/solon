package org.noear.solon.serialization.hession;

import com.caucho.hessian.io.Hessian2Input;
import org.noear.solon.core.XActionConverter;
import org.noear.solon.core.XContext;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Parameter;
import java.util.Map;

public class HessianConverter extends XActionConverter {

    @Override
    protected boolean matched(XContext ctx, String contextType) {
        if (contextType != null && contextType.contains("application/hessian")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Object changeBody(XContext ctx) throws Exception {
        Hessian2Input hi = new Hessian2Input(new ByteArrayInputStream(ctx.bodyAsBytes()));
        return hi.readObject();
    }

    @Override
    protected Object changeValue(XContext ctx, Parameter p, int pi, Class<?> pt, Object bodyObj) throws Exception {
        if (bodyObj == null) {
            return null;
        } else {
            if(bodyObj instanceof Map) {
                Map<String, Object> tmp = (Map<String, Object>) bodyObj;

                return tmp.get(p.getName());
            }else{
                return null;
            }
        }
    }
}
