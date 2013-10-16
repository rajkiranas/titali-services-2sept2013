package com.quick.tim.mobileserviceprovider.entity;
// Generated 5 Jun, 2013 6:08:00 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

/**
 * Whatsnew generated by hbm2java
 */
@Entity
@Table(name="whatsnew", schema="public"
)


public class Whatsnew  implements java.io.Serializable {


     private int itemid;
     private Std std;
     private Sub sub;
     private Date releasedate;
     private String bywhom;
     private String fordiv;
     private String topic;
     private String displaynotification;
     private String topicintro;

    public Whatsnew() {
    }

	
    public Whatsnew(int itemid) {
        this.itemid = itemid;
    }
    public Whatsnew(int itemid, Std std, Sub sub, Date releasedate, String bywhom, String fordiv, String topic, String displaynotification) {
       this.itemid = itemid;
       this.std = std;
       this.sub = sub;
       this.releasedate = releasedate;
       this.bywhom = bywhom;
       this.fordiv = fordiv;
       this.topic = topic;
       this.displaynotification = displaynotification;
    }
   
     
    @Id    
    @Column(name="itemid", unique=true, nullable=false)
    public int getItemid() {
        return this.itemid;
    }
    
    public void setItemid(int itemid) {
        this.itemid = itemid;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="forstd")
    public Std getStd() {
        return this.std;
    }
    
    public void setStd(Std std) {
        this.std = std;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sub")
    public Sub getSub() {
        return this.sub;
    }
    
    public void setSub(Sub sub) {
        this.sub = sub;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="releasedate", length=13)
    public Date getReleasedate() {
        return this.releasedate;
    }
    
    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }
    
    @Column(name="bywhom", length=200)
    public String getBywhom() {
        return this.bywhom;
    }
    
    public void setBywhom(String bywhom) {
        this.bywhom = bywhom;
    }
    
    @Column(name="fordiv", length=3)
    public String getFordiv() {
        return this.fordiv;
    }
    
    public void setFordiv(String fordiv) {
        this.fordiv = fordiv;
    }
    
    @Column(name="topic", length=30)
    public String getTopic() {
        return this.topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    @Column(name="displaynotification", length=150)
    public String getDisplaynotification() {
        return this.displaynotification;
    }
    
    public void setDisplaynotification(String displaynotification) {
        this.displaynotification = displaynotification;
    }

    /**
     * @return the topicintro
     */
    @Column(name="topic_intro", length=150)
    public String getTopicintro() {
        return topicintro;
    }

    /**
     * @param topicintro the topicintro to set
     */
    public void setTopicintro(String topicintro) {
        this.topicintro = topicintro;
    }




}


