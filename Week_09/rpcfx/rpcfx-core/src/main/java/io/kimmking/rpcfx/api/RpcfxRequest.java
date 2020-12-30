package io.kimmking.rpcfx.api;

import java.io.Serializable;

public class RpcfxRequest implements Serializable {

	private static final long serialVersionUID = 1L;
 
  private String serviceClass;

  private String method;

  private Object[] params;

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
