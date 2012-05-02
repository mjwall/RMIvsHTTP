package mjwall.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class OneUpResponse implements Serializable {
    
    private int result;
    
    @XmlElement
    public int getResult() {
        return result;
    }
    
    public void setResult(int result) {
        this.result = result;
    }

 }
