package com.liviu.home;

import java.util.Objects;

/**
 *
 * @author Liviu Catuneanu Cica
 */
public class Cluster {
    public String cname;
    public int pos;
    public int neg;
    public String assignedClass;
    
    public Cluster(String cname){
        this.cname = cname;
        pos = 0;
        neg = 0;
    }

    public boolean equals(String name) {
        return (this.cname.equalsIgnoreCase(name));
    }
    
    
}
