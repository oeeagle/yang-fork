package com.example.engdb;

import com.tailf.conf.*;
import com.example.engdb.namespaces.*;
import com.tailf.dp.*;
import com.tailf.maapi.*;
import com.tailf.dp.DpCallbackException;
import com.tailf.dp.DpTrans;
import com.tailf.dp.annotations.DataCallback;
import com.tailf.dp.annotations.TransCallback;
import com.tailf.dp.proto.DataCBType;
import com.tailf.dp.proto.TransCBType;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.Socket;

public class engdbDp   {

    private static Maapi m = null;
    private static ArrayList<String> fakeData;

    @DataCallback(callPoint="test-stats-cp",
                  callType=DataCBType.ITERATOR)
    public Iterator<? extends Object> iterator(DpTrans trans,
                                     ConfObject[] keyPath)
        throws DpCallbackException {

        return fakeData.iterator();
    }

    @DataCallback(callPoint="test-stats-cp",
                  callType=DataCBType.GET_NEXT)
    public ConfKey getKey(DpTrans trans, ConfObject[] keyPath,
                          Object obj) {

        ConfBuf b = new ConfBuf((String)obj);
        return new ConfKey( new ConfObject[] { b });
    }


    @DataCallback(callPoint="test-stats-cp",
                  callType=DataCBType.GET_ELEM)
    public ConfValue getElem(DpTrans trans, ConfObject[] keyPath)
        throws DpCallbackException {

        return new ConfInt32(44);
    }

    @DataCallback(callPoint="test-stats-cp",
                  callType=DataCBType.NUM_INSTANCES)
    public int numInstances(DpTrans trans, ConfObject[] keyPath)
        throws DpCallbackException {
        return fakeData.size();
    }


    @TransCallback(callType=TransCBType.INIT)
    public void init(DpTrans trans) throws DpCallbackException {
        try {
            if (engdbDp.m == null) {
                // Need a Maapi socket so that we can attach
                Socket  s = new Socket("localhost", Conf.NCS_PORT);
                engdbDp.m = new Maapi(s);
                engdbDp.fakeData = new ArrayList<String>();
                engdbDp.fakeData.add("k1");
                engdbDp.fakeData.add("k2");
            }

            engdbDp.m.attach(trans.getTransaction(), 0,
                           trans.getUserInfo().getUserId());

            return;
        }
        catch (Exception e) {
            throw new DpCallbackException("Failed to attach", e);
        }
    }


    @TransCallback(callType=TransCBType.FINISH)
    public void finish(DpTrans trans) throws DpCallbackException {
        try {
            m.detach(trans.getTransaction());
        }
        catch (Exception e) {
            ;
        }
    }
}
