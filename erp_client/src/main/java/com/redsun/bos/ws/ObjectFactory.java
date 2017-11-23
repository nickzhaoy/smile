
package com.redsun.bos.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.redsun.bos.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddWaybill_QNAME = new QName("http://ws.bos.redsun.com/", "addWaybill");
    private final static QName _GetWaybilldetailBySn_QNAME = new QName("http://ws.bos.redsun.com/", "getWaybilldetailBySn");
    private final static QName _AddWaybillResponse_QNAME = new QName("http://ws.bos.redsun.com/", "addWaybillResponse");
    private final static QName _GetWaybilldetailBySnResponse_QNAME = new QName("http://ws.bos.redsun.com/", "getWaybilldetailBySnResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.redsun.bos.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddWaybill }
     * 
     */
    public AddWaybill createAddWaybill() {
        return new AddWaybill();
    }

    /**
     * Create an instance of {@link GetWaybilldetailBySnResponse }
     * 
     */
    public GetWaybilldetailBySnResponse createGetWaybilldetailBySnResponse() {
        return new GetWaybilldetailBySnResponse();
    }

    /**
     * Create an instance of {@link GetWaybilldetailBySn }
     * 
     */
    public GetWaybilldetailBySn createGetWaybilldetailBySn() {
        return new GetWaybilldetailBySn();
    }

    /**
     * Create an instance of {@link AddWaybillResponse }
     * 
     */
    public AddWaybillResponse createAddWaybillResponse() {
        return new AddWaybillResponse();
    }

    /**
     * Create an instance of {@link Waybilldetail }
     * 
     */
    public Waybilldetail createWaybilldetail() {
        return new Waybilldetail();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWaybill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "addWaybill")
    public JAXBElement<AddWaybill> createAddWaybill(AddWaybill value) {
        return new JAXBElement<AddWaybill>(_AddWaybill_QNAME, AddWaybill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWaybilldetailBySn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "getWaybilldetailBySn")
    public JAXBElement<GetWaybilldetailBySn> createGetWaybilldetailBySn(GetWaybilldetailBySn value) {
        return new JAXBElement<GetWaybilldetailBySn>(_GetWaybilldetailBySn_QNAME, GetWaybilldetailBySn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddWaybillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "addWaybillResponse")
    public JAXBElement<AddWaybillResponse> createAddWaybillResponse(AddWaybillResponse value) {
        return new JAXBElement<AddWaybillResponse>(_AddWaybillResponse_QNAME, AddWaybillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWaybilldetailBySnResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.bos.redsun.com/", name = "getWaybilldetailBySnResponse")
    public JAXBElement<GetWaybilldetailBySnResponse> createGetWaybilldetailBySnResponse(GetWaybilldetailBySnResponse value) {
        return new JAXBElement<GetWaybilldetailBySnResponse>(_GetWaybilldetailBySnResponse_QNAME, GetWaybilldetailBySnResponse.class, null, value);
    }

}
