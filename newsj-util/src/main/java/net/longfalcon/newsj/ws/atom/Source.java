//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.07.12 at 05:59:54 PM PDT 
//


package net.longfalcon.newsj.ws.atom;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import org.w3c.dom.Element;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{http://www.w3.org/2005/Atom}author"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}category"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}contributor"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}generator"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}icon"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}id"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}link"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}logo"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}rights"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}subtitle"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}title"/>
 *         &lt;element ref="{http://www.w3.org/2005/Atom}updated"/>
 *         &lt;group ref="{http://www.w3.org/2005/Atom}extensionElement"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://www.w3.org/2005/Atom}atomCommonAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authorOrCategoryOrContributor"
})
@XmlRootElement(name = "source")
public class Source {

    @XmlElementRefs({
        @XmlElementRef(name = "subtitle", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "contributor", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "link", namespace = "http://www.w3.org/2005/Atom", type = Link.class, required = false),
        @XmlElementRef(name = "rights", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "title", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "id", namespace = "http://www.w3.org/2005/Atom", type = Id.class, required = false),
        @XmlElementRef(name = "author", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "generator", namespace = "http://www.w3.org/2005/Atom", type = Generator.class, required = false),
        @XmlElementRef(name = "logo", namespace = "http://www.w3.org/2005/Atom", type = Logo.class, required = false),
        @XmlElementRef(name = "icon", namespace = "http://www.w3.org/2005/Atom", type = Icon.class, required = false),
        @XmlElementRef(name = "category", namespace = "http://www.w3.org/2005/Atom", type = Category.class, required = false),
        @XmlElementRef(name = "updated", namespace = "http://www.w3.org/2005/Atom", type = JAXBElement.class, required = false)
    })
    @XmlAnyElement
    protected List<Object> authorOrCategoryOrContributor;
    @XmlAttribute(name = "base")
    @XmlSchemaType(name = "anyURI")
    protected String base;
    @XmlAttribute(name = "lang")
    protected String lang;

    /**
     * Gets the value of the authorOrCategoryOrContributor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorOrCategoryOrContributor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorOrCategoryOrContributor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Link }
     * {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}
     * {@link Element }
     * {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}
     * {@link JAXBElement }{@code <}{@link AtomTextConstruct }{@code >}
     * {@link Id }
     * {@link JAXBElement }{@code <}{@link AtomPersonConstruct }{@code >}
     * {@link Generator }
     * {@link Logo }
     * {@link Icon }
     * {@link Category }
     * {@link JAXBElement }{@code <}{@link AtomDateConstruct }{@code >}
     * 
     * 
     */
    public List<Object> getAuthorOrCategoryOrContributor() {
        if (authorOrCategoryOrContributor == null) {
            authorOrCategoryOrContributor = new ArrayList<Object>();
        }
        return this.authorOrCategoryOrContributor;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase(String value) {
        this.base = value;
    }

    /**
     * Gets the value of the lang property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets the value of the lang property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLang(String value) {
        this.lang = value;
    }

}