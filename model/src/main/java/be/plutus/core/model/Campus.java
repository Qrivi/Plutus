package be.plutus.core.model;

import be.plutus.common.Identifiable;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "campus" )
public class Campus extends Identifiable{

    @Valid
    @NotNull( message = "{NotNull.Campus.label}" )
    @OneToOne( cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn( name = "label_id" )
    private Label label;

    @Min( value = -90, message = "{Min.Campus.latitude}" )
    @Max( value = 90, message = "{Max.Campus.latitude}" )
    @Column( name = "lat" )
    private Double latitude;

    @Min( value = -180, message = "{Min.Campus.longitude}" )
    @Max( value = 180, message = "{Max.Campus.longitude}" )
    @Column( name = "longitude" )
    private Double longitude;

    @Column( name = "address" )
    private String address;

    @Column( name = "postcode" )
    private Integer postcode;

    @Column( name = "city" )
    private String city;

    @Column( name = "phone" )
    private String phone;

    @Column( name = "email" )
    private String email;

    @Column( name = "website" )
    private String website;

    public Campus(){
    }

    public Label getLabel(){
        return label;
    }

    public void setLabel( Label label ){
        this.label = label;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude( Double latitude ){
        this.latitude = latitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public void setLongitude( Double longitude ){
        this.longitude = longitude;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress( String address ){
        this.address = address;
    }

    public Integer getPostcode(){
        return postcode;
    }

    public void setPostcode( Integer postcode ){
        this.postcode = postcode;
    }

    public String getCity(){
        return city;
    }

    public void setCity( String city ){
        this.city = city;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone( String phone ){
        this.phone = phone;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail( String email ){
        this.email = email;
    }

    public String getWebsite(){
        return website;
    }

    public void setWebsite( String website ){
        this.website = website;
    }
}
