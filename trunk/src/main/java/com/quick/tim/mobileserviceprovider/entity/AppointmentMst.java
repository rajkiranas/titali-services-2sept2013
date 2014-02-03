package com.quick.tim.mobileserviceprovider.entity;
// Generated 18 Jun, 2013 3:18:14 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import javax.persistence.*;

/**
 * AppointmentMst generated by hbm2java
 */
@Entity
@Table(name="appointment_mst"
    
)
@SequenceGenerator(name = "appmst_seq_id", sequenceName = "seq_appmst_appointment_id",allocationSize=1)
public class AppointmentMst  implements java.io.Serializable {
     private long appointmentId;     
     private String ownerName;
     private String ownerUsername;
     private Date startdate;
     private Date enddate;
     private Date starttime;
     private Date endtime;
     private String eventDescription;
     private String eventCaption;
     private String eventStyle;
     private Boolean isallday;
     private String forWhom;

  
    public AppointmentMst() {
    }

	
    public AppointmentMst(long appointmentId, Date startdate, Date enddate) {
        this.appointmentId = appointmentId;
        this.startdate = startdate;
        this.enddate = enddate;
    }
    public AppointmentMst(long appointmentId, Date startdate, Date enddate, Date starttime, Date endtime, String eventDescription, String eventCaption, String eventStyle, Boolean isallday) {
       this.appointmentId = appointmentId;
       
       this.startdate = startdate;
       this.enddate = enddate;
       this.starttime = starttime;
       this.endtime = endtime;
       this.eventDescription = eventDescription;
       this.eventCaption = eventCaption;
       this.eventStyle = eventStyle;
       this.isallday = isallday;
    }
   
    @Id    
    @Column(name="appointment_id", unique=true, nullable=false, precision=38, scale=0)
    @GeneratedValue(generator = "appmst_seq_id", strategy = GenerationType.SEQUENCE)
     
    public long getAppointmentId() {
        return this.appointmentId;
    }
    
    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="startdate", nullable=false, length=13)
    public Date getStartdate() {
        return this.startdate;
    }
    
    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    @Temporal(TemporalType.DATE)
    @Column(name="enddate", nullable=false, length=13)
    public Date getEnddate() {
        return this.enddate;
    }
    
    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="starttime", length=15)
    public Date getStarttime() {
        return this.starttime;
    }
    
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="endtime", length=15)
    public Date getEndtime() {
        return this.endtime;
    }
    
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
    
    @Column(name="event_description")
    public String getEventDescription() {
        return this.eventDescription;
    }
    
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    
    @Column(name="event_caption")
    public String getEventCaption() {
        return this.eventCaption;
    }
    
    public void setEventCaption(String eventCaption) {
        this.eventCaption = eventCaption;
    }
    
    @Column(name="event_style")
    public String getEventStyle() {
        return this.eventStyle;
    }
    
    public void setEventStyle(String eventStyle) {
        this.eventStyle = eventStyle;
    }
    
    @Column(name="isallday")
    public Boolean getIsallday() {
        return this.isallday;
    }
    
    public void setIsallday(Boolean isallday) {
        this.isallday = isallday;
    }

    /**
     * @return the ownerName
     */
    @Column(name="owner_name", length = 200)
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * @param ownerName the ownerName to set
     */
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    /**
     * @return the ownerUsername
     */
    @Column(name="owner_username", length = 250)
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * @param ownerUsername the ownerUsername to set
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * @return the forWhom
     */
    @Column(name="for_whom")
    public String getForWhom() {
        return forWhom;
    }

    /**
     * @param forWhom the forWhom to set
     */
    public void setForWhom(String forWhom) {
        this.forWhom = forWhom;
    }
}