package com.quick.tim.mobileserviceprovider.entity;
// Generated Oct 24, 2013 2:28:19 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ForumEventLikesId generated by hbm2java
 */
@Embeddable
public class ForumEventLikesId  implements java.io.Serializable {


     private int eventDetailId;
     private String username;
     

    public ForumEventLikesId() {
    }

    public ForumEventLikesId(int eventDetailId, String username) {
       this.eventDetailId = eventDetailId;
       this.username = username;
       
    }
   

    @Column(name="event_detail_id", nullable=false)
    public int getEventDetailId() {
        return this.eventDetailId;
    }
    
    public void setEventDetailId(int eventDetailId) {
        this.eventDetailId = eventDetailId;
    }

    @Column(name="username", nullable=false, length=250)
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof ForumEventLikesId) ) return false;
		 ForumEventLikesId castOther = ( ForumEventLikesId ) other; 
         
		 return (this.getEventDetailId()==castOther.getEventDetailId())
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername())));
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getEventDetailId();
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         
         return result;
   }   


}


