package com.quick.tim.mobileserviceprovider.entity;
// Generated 5 Jun, 2013 6:08:00 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * StudentExamSummaryId generated by hbm2java
 */
@Embeddable
public class StudentExamSummaryId  implements java.io.Serializable {


     private int exId;
     private String username;

    public StudentExamSummaryId() {
    }

    public StudentExamSummaryId(int exId, String username) {
       this.exId = exId;
       this.username = username;
    }
   

    @Column(name="ex_id", nullable=false)
    public int getExId() {
        return this.exId;
    }
    
    public void setExId(int exId) {
        this.exId = exId;
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
		 if ( !(other instanceof StudentExamSummaryId) ) return false;
		 StudentExamSummaryId castOther = ( StudentExamSummaryId ) other; 
         
		 return (this.getExId()==castOther.getExId())
 && ( (this.getUsername()==castOther.getUsername()) || ( this.getUsername()!=null && castOther.getUsername()!=null && this.getUsername().equals(castOther.getUsername()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getExId();
         result = 37 * result + ( getUsername() == null ? 0 : this.getUsername().hashCode() );
         return result;
   }   


}


