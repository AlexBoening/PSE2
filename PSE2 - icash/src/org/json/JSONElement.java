/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.json;

import java.util.*;
import java.io.File;

/**
 *
 * @author WEID007
 */
public class JSONElement {
    String[] name;
    JSONElement[] subElement;
    
    public JSONElement() {
        
    }
    
    public void setName(String name) 
    {
        this.name = new String[1];
        this.name[0] = name;
    }
    
    public void setSubElement(ArrayList<JSONElement> list) {
        subElement = new JSONElement[list.size()];
        list.toArray(subElement);
    }
    
    public JSONElement[] getSubElement() {
        return subElement;
    }
}
